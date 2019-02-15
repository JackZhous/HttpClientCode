package com.jack.iot.conn.pool;

import com.jack.iot.conn.imple.ConnFAC;
import com.jack.iot.conn.imple.MangeClientConnection;
import com.jack.iot.route.impl.IotRoute;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.pool
 * @filename PoolConn
 * date on 2019/2/13 3:19 PM
 * @describe
 * connection集合的掌控者，所有的connection都要从它里面拿出
 * @email jackzhouyu@foxmail.com
 **/
public class PoolConn extends AbstractPoolConn<IotRoute, MangeClientConnection, ConEntry>{

    private static final AtomicLong COUNTER = new AtomicLong();

    private final long timeToLive;
    private final TimeUnit unit;
    public PoolConn(ConnFAC<IotRoute, MangeClientConnection> connFactory,
                    Integer maxPerRoute, Integer maxAllRoute, long timeToLive,
                    TimeUnit unit) {
        super(connFactory, maxPerRoute, maxAllRoute);

        this.timeToLive = timeToLive;
        this.unit = unit;
    }


    @Override
    protected ConEntry createEntry(IotRoute route, MangeClientConnection connection) {
        final String id = Long.toString(COUNTER.getAndIncrement());
        ConEntry entry = new ConEntry(route, connection, id, timeToLive, unit);

        return entry;
    }
}
