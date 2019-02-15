package com.jack.iot.conn.imple;

import com.jack.iot.conn.pool.ConEntry;
import com.jack.iot.route.SocketConfig;
import com.jack.iot.route.impl.IotRoute;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.imple
 * @filename IotClientConnectionManager
 * date on 2019/2/11 12:37 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public interface IotClientConnectionManager {

    /**
     * 根据路由信息获取特定的连接
     * @param route
     * @param state
     * @return
     */
    ConnectionRequest requestConnection(IotRoute route, Object state);


    /**
     * 连接，并且初始化connection，主要是将socket绑定到connection上去
     * @param connection
     * @param route
     * @param config
     * @param connectTimeOut   建立连接时超时时间
     */
    void connect(IotClientConnection connection, IotRoute route, SocketConfig config, int connectTimeOut) throws IOException;


    /**
     * 释放连接
     * @param connection
     * @param state
     */
    void releaseConnection(IotClientConnection connection, Object state, long validate, TimeUnit unit);

    /**
     * 租用连接
     */
    IotClientConnection leaseConnection(Future<ConEntry> fEntry, long timeOut, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException;

    /**
     * 关闭所有连接
     */
    void shutDown();

}