package com.jack.iot.route;

import com.jack.iot.route.impl.SocketFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author jackzhous
 * @package com.jack.iot.route
 * @filename EncryptSocketFactory
 * date on 2019/2/11 3:31 PM
 * @describe
 * 加密连接，暂未实现
 * @email jackzhouyu@foxmail.com
 **/
public class EncryptSocketFactory implements SocketFactory {

    @Override
    public Socket createSocket() {
        return null;
    }

    @Override
    public Socket connect(Socket socket,
                          InetSocketAddress local,
                          InetSocketAddress remote,
                          int timeOut) throws IOException {
        return null;
    }
}
