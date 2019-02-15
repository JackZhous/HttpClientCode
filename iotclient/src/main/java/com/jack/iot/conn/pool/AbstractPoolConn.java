package com.jack.iot.conn.pool;

import com.jack.iot.conn.imple.ConnFAC;
import com.jack.iot.conn.imple.ConnPool;
import com.jack.iot.conn.imple.FutureCallback;
import com.jack.iot.help.ArgsUtils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.pool
 * @filename AbstractPoolConn
 * date on 2019/2/13 4:07 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
abstract class AbstractPoolConn<R, C, E extends PoolEntry<R, C>> implements ConnPool<R, E> {

    private ConnFAC<R, C> connFactory;

    private Lock lock;
    private Condition condition;
    private final Map<R, RouteToCPool<R, C, E>> routeSpecied;
    private final Set<E> leased;
    private final LinkedList<E> available;
    private final LinkedList<Future<E>> pending;

    private volatile int validateAfterInactivity;       //连接最大过期时间
    private volatile int defaultRouteMax;
    private volatile int maxTotal;
    private final Map<R, Integer> routeMax;


    private boolean shutDown = false;


    public AbstractPoolConn(ConnFAC<R, C> connFactory, Integer maxRoute, Integer maxTotale) {
        this.connFactory = connFactory;

        lock = new ReentrantLock();
        condition = lock.newCondition();
        defaultRouteMax = maxRoute;
        this.maxTotal = maxTotale;
        routeSpecied = new HashMap<>();
        leased = new HashSet<>();
        available = new LinkedList<>();
        pending = new LinkedList<>();

        routeMax = new HashMap<>();
    }




    protected abstract E createEntry(R route, C conn);

    //租用
    protected  void onLeaseEntry(E entry){

    }

    public Integer getMaxPerRoute(R route){
        ArgsUtils.isEmpty("route pool ", route);
        lock.lock();
        try {
            return routeMax.get(route);
        } finally {
            lock.unlock();
        }
    }

    public void setMaxPerRoute(R route, Integer max){
        ArgsUtils.isEmpty("route pool ", route);
        lock.lock();
        try {
            if(max > 0){
                routeMax.put(route, max);
            }
        } finally {
            lock.unlock();
        }
    }

    private Integer getMaxRoute(R route){
        Integer max = routeMax.get(route);

        return max == null ? defaultRouteMax : max;
    }

    private RouteToCPool<R, C, E> getRoutePool(final R route){
        RouteToCPool<R, C, E> pool = routeSpecied.get(route);
        if(pool == null){
            pool = new RouteToCPool<R, C, E>(route) {
                @Override
                protected E createEntry(C conn) {
                    return AbstractPoolConn.this.createEntry(route, conn);
                }
            };

            routeSpecied.put(route, pool);
        }

        return pool;
    }

    public void setValidateAfterInactivity(int validateAfterInactivity) {
        this.validateAfterInactivity = validateAfterInactivity;
    }

    public int getValidateAfterInactivity() {
        return validateAfterInactivity;
    }

    @Override
    public Future<E> lease(final R route, final Object state, final FutureCallback<E> callback) {
        ArgsUtils.isEmpty("Pool route", route);
        return new Future<E>() {

            private final AtomicBoolean cancelled = new AtomicBoolean(false);
            private final AtomicBoolean done = new AtomicBoolean(false);
            private final AtomicReference<E> entryRef = new AtomicReference<>(null);

            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                if(cancelled.compareAndSet(false, true)){
                    done.set(true);
                    lock.lock();
                    try {
                        condition.signalAll();
                        if(callback != null){
                            callback.cancelled();
                        }
                    } finally {
                        lock.unlock();
                    }
                    return true;
                }
                return false;
            }

            @Override
            public boolean isCancelled() {
                return cancelled.get();
            }

            @Override
            public boolean isDone() {
                return done.get();
            }

            @Override
            public E get() throws ExecutionException, InterruptedException {
                try {
                    return get(0, TimeUnit.MILLISECONDS);
                } catch (TimeoutException e) {
                    throw new ExecutionException(e);
                }
            }

            @Override
            public E get(long timeout, TimeUnit unit) throws
                    InterruptedException, TimeoutException, ExecutionException {
                E entry = entryRef.get();
                if(entry != null){
                    return entry;
                }

                synchronized (this){
                    E leaseEntry;
                    for (;;){
                        leaseEntry = getPoolEntryBlocking(route, state, timeout, unit, this);
                        if(validateAfterInactivity > 0){
                            //上一次entry使用过后到现在一直是未使用状态
                            if(validateAfterInactivity + leaseEntry.getUpdateTime() < System.currentTimeMillis()){

                            }
                        }


                        entryRef.set(leaseEntry);
                        done.set(true);
                        onLeaseEntry(leaseEntry);
                        if(callback != null){
                            callback.completed(leaseEntry);
                        }
                        return leaseEntry;
                    }
                }
            }
        };
    }

    /**
     * 释放connection
     * @param entry
     * @param resuseable
     */
    @Override
    public void release(E entry, boolean resuseable) {
        this.lock.lock();
        try {
            if (this.leased.remove(entry)) {
                final RouteToCPool<R, C, E> pool = getRoutePool(entry.getRoute());
                pool.free(entry, resuseable);
                if (resuseable && !this.shutDown) {
                    this.available.addFirst(entry);
                } else {
                    entry.close();
                }

                Future<E> future = pool.nextPending();
                if (future != null) {
                    this.pending.remove(future);
                } else {
                    future = this.pending.poll();
                }
                if (future != null) {
                    this.condition.signalAll();
                }
            }
        } finally {
            this.lock.unlock();
        }
    }

    private E getPoolEntryBlocking(final R route, final Object state,
                                   final long timeout, final TimeUnit timeUnit,
                                   final Future<E> future)
            throws  InterruptedException, TimeoutException{

        Date deadLine = null;
        if(timeout > 0){
            deadLine = new Date(System.currentTimeMillis() + timeUnit.toMillis(timeout));
        }

        lock.lock();
        try {
            RouteToCPool<R, C, E> pool = getRoutePool(route);
            E entry;
            for(;;){
                ArgsUtils.isTrue(shutDown, "pool is closed");
                //遍历当前路由是否有空闲connection，并且没有过期，过期了就关闭连接
                for(;;){
                    entry = pool.getFree(state);
                    if(entry == null){
                        break;
                    }

                    if(entry.isExpire(System.currentTimeMillis())){
                        entry.close();
                    }

                    if(entry.isClosed()){
                        available.remove(entry);
                        pool.free(entry, false);
                    }else{
                        break;
                    }
                }

                if(entry != null){
                    available.remove(entry);
                    leased.add(entry);
                    return entry;
                }

                //检查当前路由是否还有剩余空间创建新连接,
                Integer maxRoute = getMaxRoute(route);
                int poolSizeMore = Math.max(0, pool.getAllocatedSize()+1 - maxRoute);
                //如果存在的连接数超过最大连接数，关闭这些多余的路由
                if(poolSizeMore > 0){
                    for(int i = 0; i < poolSizeMore ; i++){
                        E lastUsed = pool.getLastUsed();
                        if(lastUsed == null){
                            break;
                        }

                        lastUsed.close();
                        available.remove(lastUsed);
                        pool.remove(lastUsed);
                    }
                }

                //指定路由分配空间仍小于该路由的最大数
                if(pool.getAllocatedSize() < maxRoute){
                    int totalUsed = leased.size();
                    int freeCapacity = Math.max(0, maxTotal - totalUsed);
                    //所有路由的剩余连接数仍有剩余
                    if(freeCapacity > 0){
                        //保证不超过所有路由的连接数.后面会再次创建一个连接数
                        if(available.size() > freeCapacity -1){
                            if(!available.isEmpty()){
                                E lastUsed = available.getLast();

                                RouteToCPool<R, C, E> otherRoute = getRoutePool(lastUsed.getRoute());
                                otherRoute.remove(lastUsed);
                                lastUsed.close();
                                available.remove(lastUsed);
                            }
                        }

                        C conn = connFactory.create(route);
                        E newEntry = pool.add(conn);
                        leased.add(newEntry);
                        return newEntry;
                    }
                }

                boolean success;
                try {
                    if(future.isCancelled()){
                        throw new InterruptedException("Operation interrupted");
                    }
                    pending.add(future);
                    pool.queue(future);
                    if(deadLine != null){
                        success = condition.awaitUntil(deadLine);
                    }else{
                        condition.await();
                        success = true;
                    }
                    if(future.isCancelled()){
                        throw new InterruptedException("Operation interrupted");
                    }
                } finally {
                    pending.remove(future);
                    pool.unqueue(future);
                }
                if (!success && (deadLine != null && deadLine.getTime() <= System.currentTimeMillis())) {
                    break;
                }

            }
            throw new TimeoutException("Timeout waiting for connection");
        } finally {
            lock.unlock();
        }
    }
}
