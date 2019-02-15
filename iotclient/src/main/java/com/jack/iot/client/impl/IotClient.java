package com.jack.iot.client.impl;

import com.jack.iot.route.SocketConfig;
import com.jack.iot.route.impl.IotRoute;
import com.jack.iot.txrx.impl.IotRequest;
import com.jack.iot.txrx.impl.IotResponse;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.imple
 * @filename IotClient
 * date on 2019/2/14 2:55 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public interface IotClient {


    IotResponse doExecu(IotRequest request, IotRoute route, SocketConfig config);

}
