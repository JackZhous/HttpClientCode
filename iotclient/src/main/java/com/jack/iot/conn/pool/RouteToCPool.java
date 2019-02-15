package com.jack.iot.conn.pool;

import com.jack.iot.route.impl.IotRoute;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.Future;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.pool
 * @filename RouteToCPool
 * date on 2019/2/13 4:21 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
abstract class RouteToCPool<R, C, E extends PoolEntry<R, C>> {

    private R route;                //路由
    private Set<E> leased;          //租用的
    private LinkedList<E> avaliable;    //可用的
    private LinkedList<Future<E>>   pending;  //待定的


    public RouteToCPool(R route) {
        this.route = route;
        leased = new HashSet<>();
        avaliable = new LinkedList<>();
        pending = new LinkedList<>();
    }

    public int getAllocatedSize() {
        return this.avaliable.size() + this.leased.size();
    }

    public E getLastUsed(){
        return avaliable.isEmpty() ? null : avaliable.getLast();
    }

    protected abstract E createEntry(C conn);

    public int getLeasedCount() {
        return leased.size();
    }

    public int getAvaliableCount() {
        return avaliable.size();
    }

    public int getPendingCount() {
        return pending.size();
    }


    public R getRoute() {
        return route;
    }

    /**
     * 获取空闲的Connection
     * @return
     */
    public E getFree(Object state){
        if(!avaliable.isEmpty()){
            if(state != null){
                Iterator<E> iterator = avaliable.iterator();
                while (iterator.hasNext()){
                    E entry = iterator.next();
                    if(state.equals(entry.getState())){
                        avaliable.remove(entry);
                        leased.add(entry);
                        return entry;
                    }
                }
            }

            Iterator<E> iterator = avaliable.iterator();
            while (iterator.hasNext()){
                E entry = iterator.next();
                if(entry.getState() == null){
                    avaliable.remove(entry);
                    leased.add(entry);
                    return entry;
                }
            }
        }

        return null;
    }

    public void queue(final Future<E> future) {
        if (future == null) {
            return;
        }
        this.pending.add(future);
    }

    public void unqueue(final Future<E> future) {
        if (future == null) {
            return;
        }

        this.pending.remove(future);
    }

    public E add(C conn){
        E entry = createEntry(conn);
        leased.add(entry);
        return  entry;
    }

    public boolean remove(E entry){
        if(!avaliable.remove(entry)){
            if(!leased.remove(entry)){
                return false;
            }
        }

        return true;
    }

    public Future<E> nextPending() {
        return this.pending.poll();
    }


    public void free(E entry, boolean reuse){
        leased.remove(entry);
        if(reuse){
            avaliable.add(entry);
        }
    }
}
