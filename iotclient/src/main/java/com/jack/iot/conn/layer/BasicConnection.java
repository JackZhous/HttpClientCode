package com.jack.iot.conn.layer;

import com.jack.iot.conn.imple.InputBuffer;
import com.jack.iot.conn.imple.IotClientConnection;
import com.jack.iot.conn.imple.IotConnection;
import com.jack.iot.conn.imple.OutputBuffer;
import com.jack.iot.help.ArgsUtils;
import com.jack.iot.txrx.impl.IotEntityRequest;
import com.jack.iot.txrx.impl.IotRequest;
import com.jack.iot.txrx.impl.IotResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author jackzhous
 * @package com.jack.iot.conn
 * @filename BasicConnection
 * date on 2019/2/12 11:24 AM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public class BasicConnection implements IotConnection {

    private AtomicReference<Socket> socktHolder;
    private InputBufferImpl inBuf;
    private OutputBufferImp outBuf;


    public BasicConnection(int bufferSize) {

        socktHolder =  new AtomicReference<Socket>();
        inBuf = new InputBufferImpl(bufferSize);
        outBuf = new OutputBufferImp(bufferSize);

    }

    protected InputBuffer getInputBuffer(){
        return inBuf;
    }

    protected OutputBuffer getOutBuffer(){
        return outBuf;
    }

    protected Socket getSocket(){
        return socktHolder.get();
    }

    protected void ensureOpen() throws IOException {
        Socket socket = socktHolder.get();
        if(socket == null){
            throw new IllegalStateException("socket open error");
        }
        if(!inBuf.isBounded()){
            inBuf.bind(getInputStream(socket));
        }

        if(!outBuf.isBounded()){
            outBuf.bind(getOutputStream(socket));
        }

    }


    private InputStream getInputStream(Socket socket) throws IOException {
        return socket.getInputStream();
    }

    private OutputStream getOutputStream(Socket socket) throws IOException {
        return socket.getOutputStream();
    }



    protected void bind(Socket socket){
        ArgsUtils.isEmpty("BasicConnection socket", socket);
        socktHolder.set(socket);
        inBuf.bind(null);
        outBuf.bind(null);
    }

    @Override
    public boolean isOpen() {
        return socktHolder.get() != null;
    }

    @Override
    public void setSocketTimeOut(int timeOut) {
        Socket socket = socktHolder.get();
        if(socket != null){
            try {
                socket.setSoTimeout(timeOut);
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getSocketTimeOut() {
        Socket socket = socktHolder.get();
        if(socket != null){
            try {
                return socket.getSoTimeout();
            } catch (SocketException e) {
                return -1;
            }
        }
        return -1;
    }

    @Override
    public void close() throws IOException {
        Socket socket = socktHolder.getAndSet(null);
        if(socket != null){
            inBuf.clear();
            outBuf.flush();

            try {
                try {
                    socket.shutdownInput();
                } catch (IOException e) {
                }

                try {
                    socket.shutdownOutput();
                } catch (IOException e) {
                }
            } finally {
                socket.close();
            }
        }
    }

    @Override
    public void shutDown() throws IOException {
        Socket socket = socktHolder.getAndSet(null);        //获取旧值，并设定新值
        if(socket != null){
            try {
                socket.setSoLinger(true, 0);        //收到关闭命令后，网卡立即发送结束包
            } catch (SocketException e) {
            } finally {
                socket.close();
            }
        }
    }
}
