package com.jack.iot.help;

/**
 * @author jackzhous
 * @package com.jack.iot.help
 * @filename ShiftUtil
 * date on 2019/2/12 4:31 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public class ShiftUtil {

    public static byte[] intToByte(int data){
        return  toByte(data, 4);
    }

    /**
     * offset表示移动几位
     */
    public static byte[] toByte(long data, int offset){
        byte b[] = new byte[offset];
        int step = 0;
        for(int i = 0 ; i < offset; i++){
            b[i] = (byte) (0xff & (data >> step));
            step+=8;
        }
        return b;
    }


    public static byte[] longToByte(long data){
        return toByte(data, 8);
    }


    public static int byteToInt(byte[] data, int offset, int len){
        int value = 0;

        for(int i = len-1 ; i > 0; i--){
            value = value | data[offset+i];
            value = value << 8;
        }

        value = value & data[offset];
        return value;
    }

    public static long byteToLonng(byte[] data, int offset, int len){
        long value = 0;

        for(int i = len-1 ; i > 0; i--){
            value = value | data[offset+i];
            value = value << 8;
        }

        value = value & data[offset];
        return value;
    }

    public static byte[] splitByteArrays(byte[] data, int off, int len){
        byte[] value = new byte[len];
        System.arraycopy(data, off, value, 0, len);

        return value;
    }

}
