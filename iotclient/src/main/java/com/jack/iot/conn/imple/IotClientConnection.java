package com.jack.iot.conn.imple;

import com.jack.iot.txrx.impl.IotEntityRequest;
import com.jack.iot.txrx.impl.IotRequest;
import com.jack.iot.txrx.impl.IotResponse;

import java.io.IOException;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.imple
 * @filename IotClientConnection
 * date on 2019/2/11 12:39 PM
 * @describe
 * basic connection status and methods
 * @email jackzhouyu@foxmail.com
 **/
public interface IotClientConnection extends IotConnection{

    void sendRequest(IotEntityRequest request);

    void sendRequestHeader(IotRequest request) throws IOException;

    IotResponse receiveReponse() throws IOException;


    void receiveRessponseHeader(IotResponse response);
}
