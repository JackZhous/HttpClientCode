package com.jack.iot.conn.layer;

import com.jack.iot.conn.imple.IotClientConnection;
import com.jack.iot.conn.imple.IotMsgReader;
import com.jack.iot.conn.imple.IotMsgWriter;
import com.jack.iot.conn.parse.DefaultIotMsgFactory;
import com.jack.iot.txrx.impl.IotEntityRequest;
import com.jack.iot.txrx.impl.IotRequest;
import com.jack.iot.txrx.impl.IotResponse;

import java.io.IOException;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.layer
 * @filename DefaultBConnection
 * date on 2019/2/12 3:42 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public class DefaultBConnection extends BasicConnection implements IotClientConnection {

    private IotMsgReader<IotResponse> reader;
    private IotMsgWriter<IotRequest> writer;


    public DefaultBConnection(int bufferSize,IotMsgReader<IotResponse> reader,
                              IotMsgWriter<IotRequest> writer) {
        super(bufferSize);
        this.reader = reader != null ? reader :
                DefaultIotMsgFactory.INSTANCE.create(getInputBuffer());
        this.writer = writer != null ? writer :
                DefaultIotMsgFactory.INSTANCE.create(getOutBuffer());
    }

    @Override
    public void sendRequest(IotEntityRequest request) {

    }

    @Override
    public void sendRequestHeader(IotRequest request) throws IOException {
        writer.write(request);
    }

    @Override
    public IotResponse receiveReponse() {
        return null;
    }

    @Override
    public void receiveRessponseHeader(IotResponse response) {

    }
}
