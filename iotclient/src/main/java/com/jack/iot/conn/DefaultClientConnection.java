package com.jack.iot.conn;

import com.jack.iot.conn.imple.IotMsgReader;
import com.jack.iot.conn.imple.IotMsgWriter;
import com.jack.iot.conn.imple.MangeClientConnection;
import com.jack.iot.conn.imple.MsgFactory;
import com.jack.iot.conn.layer.BasicConnection;
import com.jack.iot.txrx.impl.IotEntityRequest;
import com.jack.iot.txrx.impl.IotRequest;
import com.jack.iot.txrx.impl.IotResponse;

import java.io.IOException;
import java.net.Socket;

/**
 * @author jackzhous
 * @package com.jack.iot.conn
 * @filename DefaultClientConnection
 * date on 2019/2/11 1:00 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public class DefaultClientConnection extends BasicConnection implements MangeClientConnection {

    private String id;
    private IotMsgReader<IotResponse> reader;
    private IotMsgWriter<IotRequest>  writer;

    public DefaultClientConnection(int bufferSize, String id, MsgFactory msgFactory) {
        super(bufferSize);

        this.id = id;
        this.reader = msgFactory.create(getInputBuffer());
        this.writer = msgFactory.create(getOutBuffer());
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Socket getSocket() {
        return super.getSocket();
    }

    @Override
    public void bindSocket(Socket socket) {
        super.bind(socket);
    }

    @Override
    public void sendRequest(IotEntityRequest request) {
//        writer.write();
    }

    @Override
    public void sendRequestHeader(IotRequest request) throws IOException {
        ensureOpen();
        writer.write(request);
    }

    @Override
    public IotResponse receiveReponse() throws IOException {
        ensureOpen();
        return reader.readAndParse();
    }

    @Override
    public void receiveRessponseHeader(IotResponse response) {

    }
}
