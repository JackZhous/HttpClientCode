package com.jack.iot.route.impl;

import java.net.InetSocketAddress;

/**
 * @author jackzhous
 * @package com.jack.iot.txrx.impl
 * @filename IotRoute
 * date on 2019/2/11 2:35 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public interface IotRoute {


    InetSocketAddress getClientAddress();


    InetSocketAddress getServerAddress();


    String getSchme();

}
