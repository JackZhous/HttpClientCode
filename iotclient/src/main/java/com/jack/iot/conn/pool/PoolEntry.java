package com.jack.iot.conn.pool;

import com.jack.iot.help.ArgsUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.pool
 * @filename PoolEntry
 * date on 2019/2/12 9:26 AM
 * @describe
 * 连接池的最小组成单位，每一个PoolEntry代表一个connection的底层封装，同时
 * 该类也屏蔽了底层connection的实现
 * @email jackzhouyu@foxmail.com
 **/
abstract class PoolEntry<R, C> {


    private R route;
    private C connection;
    private String id;
    private long createTime;
    private long updateTime;
    private long timeToLive;
    private TimeUnit unit;

    private Object state;
    private final long validityDeadline;            //连接存活最长时间

    private long expire;            //连接过期时间

    public PoolEntry(R route, C connection, String id, long timeToLive, TimeUnit unit) {
        ArgsUtils.isEmpty("pool entry route", route);
        ArgsUtils.isEmpty("poolentry connection", connection);
        ArgsUtils.isEmpty("poolentry id", id);
        this.route = route;
        this.connection = connection;
        this.id = id;
        this.createTime = System.currentTimeMillis();
        updateTime = createTime;
        this.timeToLive = timeToLive;
        this.unit = unit;
        if(timeToLive > 0){
            validityDeadline = createTime + unit.toMillis(
                    timeToLive);
        }else{
            validityDeadline =  Long.MAX_VALUE;
        }
        expire = validityDeadline;
    }


    public long getUpdateTime() {
        return updateTime;
    }

    public boolean isExpire(long now) {
        return now >= expire;
    }

    public C getConnection(){
        return connection;
    }


    public R getRoute(){
        return route;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    protected abstract void close();

    protected abstract boolean isClosed();

    public synchronized void updateExpiry(final long time, final TimeUnit timeUnit) {
        ArgsUtils.isEmpty( "Time unit", timeUnit);
        this.updateTime = System.currentTimeMillis();
        final long newExpiry;
        if (time > 0) {
            newExpiry = this.updateTime + timeUnit.toMillis(time);
        } else {
            newExpiry = Long.MAX_VALUE;
        }
        this.expire = Math.min(newExpiry, this.validityDeadline);
    }
}
