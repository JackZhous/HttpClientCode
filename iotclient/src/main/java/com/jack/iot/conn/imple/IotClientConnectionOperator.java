package com.jack.iot.conn.imple;

import com.jack.iot.route.SocketConfig;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.imple
 * @filename IotClientConnectionOperator
 * date on 2019/2/11 3:07 PM
 * @describe
 * 底层连接标准发放，socket的连接要按照这个接口来完成
 * @email jackzhouyu@foxmail.com
 **/
public interface IotClientConnectionOperator {

    void connect(MangeClientConnection connection,
                 InetSocketAddress localAddress,
                 InetSocketAddress remoteAddress,
                 String schme,
                 int connectTimeOut,
                 SocketConfig config) throws IOException;

}
