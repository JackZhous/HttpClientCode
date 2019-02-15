package com.jack.iot.conn;

import com.jack.iot.conn.imple.IotClientConnectionOperator;
import com.jack.iot.conn.imple.MangeClientConnection;
import com.jack.iot.help.ArgsUtils;
import com.jack.iot.route.Register;
import com.jack.iot.route.SocketConfig;
import com.jack.iot.route.impl.SocketFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * @author jackzhous
 * @package com.jack.iot.conn
 * @filename DefaultClientConnectionOperator
 * date on 2019/2/11 3:50 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public class DefaultClientConnectionOperator implements IotClientConnectionOperator {


    private Register<SocketFactory> socketRegister;

    public DefaultClientConnectionOperator(Register<SocketFactory> socketRegister) {
        ArgsUtils.isEmpty("operator register", socketRegister);
        this.socketRegister = socketRegister;
    }

    @Override
    public void connect(MangeClientConnection connection,
                        InetSocketAddress localAddress,
                        InetSocketAddress remoteAddress,
                        String schme,
                        int connectTimeOut,
                        SocketConfig config) throws SocketException {


        ArgsUtils.isEmpty("operator connection", connection);
        ArgsUtils.isEmpty("operator remoteIP", remoteAddress);
        SocketFactory socketFactory = socketRegister.lookFor(schme);

        Socket soc = socketFactory.createSocket();

        try {
            soc.setKeepAlive(config.isKeepAlive());
            soc.setSoTimeout(config.getSocketTimeout());
            soc.setReceiveBufferSize(config.getRevBufSize());
            soc.setSendBufferSize(config.getSndBufSize());
            soc.setTcpNoDelay(config.isTcpNoDelay());
        } catch (SocketException e) {
            e.printStackTrace();
        }

        try {
            soc = socketFactory.connect(soc, localAddress, remoteAddress, connectTimeOut);
            connection.bindSocket(soc);
        }  catch (IOException e) {
           throw new SocketException("connection failed");
        }

    }



}
