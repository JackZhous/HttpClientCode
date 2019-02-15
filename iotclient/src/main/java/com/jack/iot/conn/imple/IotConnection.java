package com.jack.iot.conn.imple;

import java.io.IOException;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.imple
 * @filename IotConnection
 * date on 2019/2/11 4:49 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public interface IotConnection{

    boolean isOpen();

    void setSocketTimeOut(int timeOut);

    int getSocketTimeOut();

    void close() throws IOException;

    void shutDown() throws IOException;

}
