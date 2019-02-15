package com.jack.iot.conn.pool;

import com.jack.iot.conn.imple.IotClientConnection;
import com.jack.iot.conn.imple.IotConnection;
import com.jack.iot.conn.imple.MangeClientConnection;
import com.jack.iot.help.ArgsUtils;
import com.jack.iot.txrx.impl.IotEntityRequest;
import com.jack.iot.txrx.impl.IotRequest;
import com.jack.iot.txrx.impl.IotResponse;

import java.io.IOException;
import java.net.Socket;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.imple
 * @filename ConProxy
 * date on 2019/2/12 9:12 AM
 * @describe
 * 底层connection的代理类，和平时的静态代理有不同
 * @email jackzhouyu@foxmail.com
 **/
public class ConProxy implements MangeClientConnection {

    private volatile ConEntry entry;

    public ConProxy(final ConEntry entry) {
        this.entry = entry;
    }


    public ConEntry getEntry() {
        return entry;
    }

    public ConEntry detach(){
        ConEntry local = this.entry;
        this.entry = null;
        return local;
    }

    public MangeClientConnection getValidConnection(){
        ConEntry entry = this.entry;
        ArgsUtils.isEmpty("ConProxy entry", entry);
        MangeClientConnection connection = entry.getConnection();
        ArgsUtils.isEmpty("ConProxy conec", connection);

        return connection;
    }


    @Override
    public String getId() {
        return getValidConnection().getId();
    }

    @Override
    public Socket getSocket() {
        return getValidConnection().getSocket();
    }

    @Override
    public void bindSocket(Socket socket) {
        getValidConnection().bindSocket(socket);
    }

    @Override
    public void sendRequest(IotEntityRequest request) {
        getValidConnection().sendRequest(request);
    }

    @Override
    public void sendRequestHeader(IotRequest request) throws IOException {
        getValidConnection().sendRequestHeader(request);
    }

    @Override
    public IotResponse receiveReponse() throws IOException {
        return getValidConnection().receiveReponse();
    }

    @Override
    public void receiveRessponseHeader(IotResponse response) {

    }

    @Override
    public boolean isOpen() {
        return getValidConnection().isOpen();
    }

    @Override
    public void setSocketTimeOut(int timeOut) {
        getValidConnection().setSocketTimeOut(timeOut);
    }

    @Override
    public int getSocketTimeOut() {
        return getValidConnection().getSocketTimeOut();
    }

    @Override
    public void close() throws IOException {
        getValidConnection().close();
    }

    @Override
    public void shutDown() throws IOException {
        getValidConnection().shutDown();
    }


    private static ConProxy getProxy(IotClientConnection connection){
        if(!ConProxy.class.isInstance(connection)){
            throw new IllegalStateException("illegal connection");
        }

        return ConProxy.class.cast(connection);
    }

    public static ConEntry getConEntry(IotClientConnection conn){
        ArgsUtils.isEmpty("ConProxy conn", conn);
        final ConEntry entry = getProxy(conn).getEntry();
        ArgsUtils.isEmpty("ConProxy entry", entry);
        return entry;
    }

    public static IotClientConnection newProxy(ConEntry entry){
        return new ConProxy(entry);
    }

    public static ConEntry detach(IotClientConnection conn){
        return getProxy(conn).detach();
    }
}
