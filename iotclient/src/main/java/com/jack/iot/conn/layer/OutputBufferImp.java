package com.jack.iot.conn.layer;

import com.jack.iot.conn.imple.OutputBuffer;
import com.jack.iot.help.ArgsUtils;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author jackzhous
 * @package com.jack.iot.conn.layer
 * @filename OutputBufferImp
 * date on 2019/2/12 11:33 AM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public class OutputBufferImp implements OutputBuffer {

    private OutputStream stream;


    private byte[] buff;

    private int bufferLen;
    private int bufferPos;

    public OutputBufferImp(int buffSize) {
        this.buff = new byte[buffSize];
    }

    private void streamWrite(byte[] buf, int off, int len) throws IOException {
        ArgsUtils.isEmpty("write stream",stream);
        stream.write(buf, off, len);
    }




    @Override
    public void write(byte[] buf, int off, int len) throws IOException {
        streamWrite(buf, off, len);
    }

    @Override
    public void write(byte[] buf) throws IOException {
         write(buf,0, buf.length);
    }

    @Override
    public void write(byte data) throws IOException {
        byte[] buf = new byte[1];
        buf[0] = data;
        write(buf,0, 1);
    }


    public void bind(OutputStream stream){
        this.stream = stream;
    }

    public boolean isBounded(){
        return stream != null;
    }

    public void flush(){

    }
}
