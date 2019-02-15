package com.jack.iot.conn.layer;

import com.jack.iot.conn.imple.InputBuffer;
import com.jack.iot.help.ArgsUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author jackzhous
 * @package com.jack.iot.conn
 * @filename InputBufferImpl
 * date on 2019/2/12 11:32 AM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public class InputBufferImpl implements InputBuffer {

    private InputStream stream;
    private byte[] buff;

    private int bufferLen;
    private int bufferPos;

    public InputBufferImpl(int buffSize) {
        this.buff = new byte[buffSize];
    }

    private int streamRead(byte[] des, int off, int len) throws IOException {
        ArgsUtils.isEmpty("inputbuffer des", des);
        return stream.read(des, off, len);
    }


    @Override
    public int read(byte[] buf, int off, int len) throws IOException {
        if (buf == null) {
            return 0;
        }

        return streamRead(buf, off, len);
    }

    @Override
    public int read(byte[] buf) throws IOException {
        if (buf == null) {
            return 0;
        }

        return streamRead(buf, 0 , buf.length);
    }


    public void bind(InputStream stream){
        this.stream = stream;
    }

    public boolean isBounded(){
        return stream != null;
    }



    public void clear(){
        bufferLen = 0;
        bufferPos = 0;
    }
}
