package com.jack.iot.txrx.impl;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author jackzhous
 * @package com.jack.iot.txrx.impl
 * @filename IotEntity
 * date on 2019/2/11 12:43 PM
 * @describe
 * the entity is represent the real content, not include header;
 * it include the basic send and receive method
 * @email jackzhouyu@foxmail.com
 **/
public interface IotEntity {

    void writeTo(OutputStream stream);          //send method

}
