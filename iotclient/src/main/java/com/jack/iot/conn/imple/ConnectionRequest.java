package com.jack.iot.conn.imple;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.imple
 * @filename ConnectionRequest
 * date on 2019/2/11 2:27 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public interface ConnectionRequest extends Cancellable {


    /**
     * 阻塞式从连接池里面获取connection
     * @param timeOut
     * @param timeUnit
     * @return
     */
    IotClientConnection getConnection(long timeOut, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException;
}
