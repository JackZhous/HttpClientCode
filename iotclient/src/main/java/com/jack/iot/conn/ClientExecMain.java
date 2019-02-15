package com.jack.iot.conn;

import com.jack.iot.conn.imple.ConnectionRequest;
import com.jack.iot.conn.imple.IotClientConnection;
import com.jack.iot.conn.imple.IotClientConnectionManager;
import com.jack.iot.conn.imple.IotClientExec;
import com.jack.iot.conn.imple.IotClientExecChain;
import com.jack.iot.conn.imple.IotConnection;
import com.jack.iot.conn.imple.MangeClientConnection;
import com.jack.iot.help.ArgsUtils;
import com.jack.iot.help.ConfigUtil;
import com.jack.iot.route.IotConfig;
import com.jack.iot.route.SocketConfig;
import com.jack.iot.route.impl.IotRoute;
import com.jack.iot.txrx.ErrorReponse;
import com.jack.iot.txrx.impl.IotRequest;
import com.jack.iot.txrx.impl.IotResponse;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author jackzhous
 * @package com.jack.iot.conn
 * @filename ClientExecMain
 * date on 2019/2/13 2:59 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public class ClientExecMain implements IotClientExecChain {


    private IotClientConnectionManager connectionManager;

    public ClientExecMain(IotClientConnectionManager connectionManager) {
        this.connectionManager = connectionManager != null ? connectionManager :
                new PoolingClientConnectionManager();
    }

    public ClientExecMain() {
        this(null);
    }

    @Override
    public IotResponse execute(IotRequest request, IotRoute route, SocketConfig config, IotClientExec exec) {
        ArgsUtils.isEmpty("mainExec request", request);
        ArgsUtils.isEmpty("mainExec route", route);

        ConnectionRequest connRequest  = connectionManager.
                                            requestConnection(route, ConfigUtil.CONN_START);
        if(exec != null){
            if(exec.isAborted()){
                connRequest.cancel();
                throw new IllegalStateException("request aborted");
            }else {
                exec.setCancellable(connRequest);
            }
        }

        IotClientConnection connection;
        try {
            connection = connRequest.getConnection(config.getConnectTimeout(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Request aborted", e);
        } catch (ExecutionException ex) {
            Throwable cause = ex.getCause();
            if (cause == null) {
                cause = ex;
            }
            throw new IllegalStateException("Request execution failed", cause);
        } catch (TimeoutException e) {
            throw new IllegalStateException("Request TimeoutException", e);
        }

        try {
            int execuCount;
            for(execuCount = 1; ; execuCount++){
                if(execuCount > 1 && !request.isRepeatable()){
                    throw new IllegalStateException("can not retry request, now it's retry " + execuCount);
                }

                if(!connection.isOpen()){
                    try {
                        openConnection(connection, route , config);
                    } catch (IOException e) {
                        System.err.print(ConfigUtil.TAG + " connection Server failed");
                        return new ErrorReponse(e, " connection Server failed");
                    }
                }

                int timeOut = config.getConnectTimeout();
                if(timeOut > 0){
                    connection.setSocketTimeOut(timeOut);
                }

                IotResponse response = execute(request, connection);
                connectionManager.releaseConnection(connection, null, 0, TimeUnit.MILLISECONDS);

                return response;
            }
        } catch (IOException e) {
            return new ErrorReponse(e, " connection failed");
        }

    }


    private IotResponse execute(
            final IotRequest request,
            final IotClientConnection conn) throws IOException {
        ArgsUtils.isEmpty("reuest " , request);
        ArgsUtils.isEmpty("conn " , conn);

        try {
            IotResponse response;
            conn.sendRequestHeader(request);
            response = conn.receiveReponse();
            return response;
        } catch (IOException e) {
            return new ErrorReponse(e, "send failed");
        }

    }


    protected void openConnection(IotClientConnection connection, IotRoute route, SocketConfig config) throws IOException {
        connectionManager.connect(connection, route, config, config.getConnectTimeout());
    }
}
