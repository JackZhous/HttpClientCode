package com.jack.iot.conn.pool;

import com.jack.iot.conn.imple.IotConnection;
import com.jack.iot.conn.imple.MangeClientConnection;
import com.jack.iot.conn.pool.PoolEntry;
import com.jack.iot.route.impl.IotRoute;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author jackzhous
 * @package com.jack.iot.conn
 * @filename ConEntry
 * date on 2019/2/12 9:15 AM
 * @describe
 * 代理connection类
 * @email jackzhouyu@foxmail.com
 **/
public class ConEntry extends PoolEntry<IotRoute, MangeClientConnection> {


    public ConEntry(IotRoute route, MangeClientConnection connection,
                    String id, long timeToLive, TimeUnit unit) {
        super(route, connection, id, timeToLive, unit);
    }



    public void shutdownConnection() throws IOException {
        final IotConnection conn = getConnection();
        conn.shutDown();
    }


    public void closeConnection() throws IOException {
        final IotConnection conn = getConnection();
        conn.close();
    }


    @Override
    protected void close() {
        try {
            closeConnection();
        } catch (IOException e) {
            System.err.print("closing IO connection error");
        }
    }

    @Override
    protected boolean isClosed() {
        IotConnection connection = getConnection();
        return !connection.isOpen();
    }
}
