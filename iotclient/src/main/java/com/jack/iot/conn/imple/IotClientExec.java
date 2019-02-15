package com.jack.iot.conn.imple;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.imple
 * @filename IotClientExec
 * date on 2019/2/13 2:58 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public interface IotClientExec {

    boolean isAborted();

    void setCancellable(Cancellable cancellable);
}
