package com.jack.iot.route.impl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author jackzhous
 * @package com.jack.iot.route.impl
 * @filename SocketFactory
 * date on 2019/2/11 3:18 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public interface SocketFactory {


    Socket createSocket();


    Socket connect(Socket socket, InetSocketAddress local, InetSocketAddress remote, int timeOut) throws IOException;

}
