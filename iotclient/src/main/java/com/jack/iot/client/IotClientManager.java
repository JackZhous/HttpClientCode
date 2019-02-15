package com.jack.iot.client;

import com.jack.iot.client.impl.IotClient;
import com.jack.iot.conn.imple.IotClientConnectionManager;
import com.jack.iot.conn.imple.IotClientExec;
import com.jack.iot.conn.imple.IotClientExecChain;
import com.jack.iot.route.SocketConfig;
import com.jack.iot.route.impl.IotRoute;
import com.jack.iot.txrx.impl.IotRequest;
import com.jack.iot.txrx.impl.IotResponse;

/**
 * @author jackzhous
 * @package com.jack.iot.conn
 * @filename IotClientManager
 * date on 2019/2/14 2:55 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public class IotClientManager implements IotClient {

    private IotClientExecChain clientChain;
    private IotClientConnectionManager connectionManager;

    public IotClientManager(IotClientExecChain clientChain, IotClientConnectionManager manager) {
        this.clientChain = clientChain;
        connectionManager = manager;
    }

    @Override
    public IotResponse doExecu(IotRequest request, IotRoute route, SocketConfig config) {
        IotClientExec clentExec = null;
        if(request instanceof  IotClientExec){
            clentExec = (IotClientExec)request;
        }

        return clientChain.execute(request, route, config, clentExec);
    }
}
