package com.jack.iot.conn.imple;

import java.net.Socket;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.imple
 * @filename MangeClientConnection
 * date on 2019/2/11 4:45 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public interface MangeClientConnection extends IotClientConnection {

    String getId();

    Socket getSocket();

    void bindSocket(Socket socket);

}
