package com.jack.iot.conn.imple;

import com.jack.iot.route.IotConfig;
import com.jack.iot.route.SocketConfig;
import com.jack.iot.route.impl.IotRoute;
import com.jack.iot.txrx.impl.IotRequest;
import com.jack.iot.txrx.impl.IotResponse;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.imple
 * @filename IotClientExecChain
 * date on 2019/2/13 2:55 PM
 * @describe
 * 责任链请求标准接口
 * @email jackzhouyu@foxmail.com
 **/
public interface IotClientExecChain {


    IotResponse execute(IotRequest request, IotRoute route, SocketConfig config, IotClientExec exec);

}
