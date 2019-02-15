package com.jack.iot.conn.imple;

import java.util.concurrent.Future;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.imple
 * @filename ConnPool
 * date on 2019/2/13 4:09 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public interface ConnPool<R, E> {

    Future<E> lease(R route, Object state, FutureCallback<E> callback);

    void release(E entry, boolean resuseable);
}
