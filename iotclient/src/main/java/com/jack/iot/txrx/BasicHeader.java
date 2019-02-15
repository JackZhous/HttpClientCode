package com.jack.iot.txrx;

import com.jack.iot.txrx.impl.Header;

/**
 * @author jackzhous
 * @package com.jack.iot.txrx
 * @filename BasicHeader
 * date on 2019/2/13 11:10 AM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public class BasicHeader implements Header {

    private String name;            //header名称
    private byte[] value;           //header内容


    public BasicHeader(String name, byte[] value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public byte[] getValue() {
        return value;
    }


    @Override
    public void setValue(byte[] value) {
        this.value = value;
    }
}
