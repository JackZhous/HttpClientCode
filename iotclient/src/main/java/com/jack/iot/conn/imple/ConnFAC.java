package com.jack.iot.conn.imple;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.imple
 * @filename ConnFAC
 * date on 2019/2/13 3:48 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public interface ConnFAC<R, C> {

    C create(R route);

}
