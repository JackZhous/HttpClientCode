package com.jack.iot.txrx.impl;

/**
 * @author jackzhous
 * @package com.jack.iot.txrx.impl
 * @filename IotEntityRequest
 * date on 2019/2/11 12:41 PM
 * @describe
 * the interface represent the request has entity, like http post
 * @email jackzhouyu@foxmail.com
 **/
public interface IotEntityRequest extends IotRequest {


    void setEntity(IotEntity entity);

    IotEntity getEntity();
}
