package com.jack.iot.conn.imple;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.imple
 * @filename FutureCallback
 * date on 2019/2/13 4:10 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public interface FutureCallback<T> {

    void completed(T result);

    void failed(Exception ex);

    void cancelled();
}
