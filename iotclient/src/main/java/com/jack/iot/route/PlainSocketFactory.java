package com.jack.iot.route;

import com.jack.iot.route.impl.SocketFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author jackzhous
 * @package com.jack.iot.route
 * @filename PlainSocketFactory
 * date on 2019/2/11 3:21 PM
 * @describe
 * 普通无加密连接工程类
 * @email jackzhouyu@foxmail.com
 **/
public class PlainSocketFactory implements SocketFactory {

    private static final PlainSocketFactory INSTANCE = new PlainSocketFactory();

    public static PlainSocketFactory getInstance(){
        return INSTANCE;
    }

    public PlainSocketFactory() {
    }

    @Override
    public Socket createSocket() {
        return new Socket();
    }

    @Override
    public Socket connect(Socket socket,
                          InetSocketAddress local,
                          InetSocketAddress remote,
                          int timeOut) throws IOException {

        Socket soc = (socket != null) ? socket : createSocket();

        if(local != null){
            try {
                soc.bind(local);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            soc.connect(remote, timeOut);
        } catch (IOException e) {
            try {
                soc.close();
            } catch (IOException e1) {
            }
            throw e;
        }

        return soc;
    }
}
