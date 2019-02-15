package com.jack.iot.conn;

import android.text.format.Time;

import com.jack.iot.conn.imple.ConnectionRequest;
import com.jack.iot.conn.imple.FutureCallback;
import com.jack.iot.conn.imple.IotClientConnection;
import com.jack.iot.conn.imple.IotClientConnectionManager;
import com.jack.iot.conn.imple.IotClientConnectionOperator;
import com.jack.iot.conn.imple.IotConnection;
import com.jack.iot.conn.imple.MangeClientConnection;
import com.jack.iot.conn.pool.PoolConn;
import com.jack.iot.conn.pool.ConEntry;
import com.jack.iot.conn.pool.ConProxy;
import com.jack.iot.help.ArgsUtils;
import com.jack.iot.help.ConfigUtil;
import com.jack.iot.route.PlainSocketFactory;
import com.jack.iot.route.Register;
import com.jack.iot.route.SocketConfig;
import com.jack.iot.route.SocketFactoryRegister;
import com.jack.iot.route.impl.IotRoute;
import com.jack.iot.route.impl.SocketFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author jackzhous
 * @package com.jack.iot.conn
 * @filename PoolingClientConnectionManager
 * date on 2019/2/11 11:35 AM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public  class PoolingClientConnectionManager implements IotClientConnectionManager {


    private IotClientConnectionOperator operator;
    private PoolConn pool;

    private Register<SocketFactory> defaultRegisterFactory =
                                SocketFactoryRegister.<SocketFactory>create()
                                        .register("iot", PlainSocketFactory.getInstance())
                                        .build();

    public PoolingClientConnectionManager(IotClientConnectionOperator operator,
                                          long timeToLive, TimeUnit unit) {
        this.operator = operator != null ? operator : new DefaultClientConnectionOperator(defaultRegisterFactory);
        this.pool = new PoolConn(new ConnectionFactory(),
                4, 20, timeToLive, unit);
        pool.setValidateAfterInactivity(2000);
    }

    public PoolingClientConnectionManager() {
//        this(null);
    }

    @Override
    public ConnectionRequest requestConnection(IotRoute route, Object state) {

        ArgsUtils.isEmpty("connection manager route", route);
        final Future<ConEntry> future = pool.lease(route, state, new FutureCallback<ConEntry>() {
            @Override
            public void completed(ConEntry result) {
                System.out.print(ConfigUtil.TAG + "requestConnection " + result.getConnection().getId());
            }

            @Override
            public void failed(Exception ex) {
                System.out.print(ConfigUtil.TAG + "requestConnection failed");
                ex.printStackTrace();
            }

            @Override
            public void cancelled() {
                System.out.print(ConfigUtil.TAG + "requestConnection canceld");
            }
        });

        return new ConnectionRequest() {
            @Override
            public IotClientConnection getConnection(long timeOut, TimeUnit timeUnit) throws
                    InterruptedException, ExecutionException, TimeoutException {
                return leaseConnection(future, timeOut, timeUnit);
            }

            @Override
            public boolean cancel() {
                return future.cancel(true);
            }
        };
    }

    @Override
    public void connect(IotClientConnection connection, IotRoute route, SocketConfig config, int connectTimeOut) throws IOException {

        MangeClientConnection manConn;
        synchronized (connection){
            ConEntry entry = ConProxy.getConEntry(connection);
            manConn = entry.getConnection();
        }

        operator.connect(manConn, route.getClientAddress(), route.getServerAddress(), route.getSchme(), connectTimeOut, config);
    }

    @Override
    public void releaseConnection(IotClientConnection connection, Object state, long validate, TimeUnit unit) {
        ArgsUtils.isEmpty("releaseConnection connection", connection);
        synchronized (connection){
            final ConEntry entry = ConProxy.detach(connection);
            MangeClientConnection conn = entry.getConnection();
            try {
                if(conn.isOpen()){
                    TimeUnit tu = unit == null ? TimeUnit.MILLISECONDS : unit;
                    entry.setState(state);
                    entry.updateExpiry(validate, tu);
                }
                conn.setSocketTimeOut(0);
            } finally {
                pool.release(entry, conn.isOpen());
            }
        }
    }

    @Override
    public IotClientConnection leaseConnection(Future<ConEntry> fEntry, long timeOut, TimeUnit unit)
            throws ExecutionException, InterruptedException, TimeoutException {
        ArgsUtils.isEmpty("leaseConnection fEntry", fEntry);

        ConEntry entry;

        try {
            entry = fEntry.get(timeOut, unit);
            if(entry == null || fEntry.isCancelled()){
                throw new IllegalStateException("future getEntry failed");
            }

            ArgsUtils.isTrue(entry.getConnection() == null, "connection is invalid");

            return ConProxy.newProxy(entry);
        } catch (TimeoutException e) {
            throw new TimeoutException("proxy get entry time out");
        }
    }

    @Override
    public void shutDown() {

    }
}
