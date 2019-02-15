package com.jack.iot.route;

import com.jack.iot.help.ArgsUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jackzhous
 * @package com.jack.iot.route
 * @filename SocketFactoryRegister
 * date on 2019/2/11 3:33 PM
 * @describe 构建socket的工程建造类
 * @email jackzhouyu@foxmail.com
 **/
public final class SocketFactoryRegister<T> {

    private final Map<String, T> factory;

    public SocketFactoryRegister() {
        this.factory = new HashMap<String, T>();
    }


    public static <T> SocketFactoryRegister<T> create(){
        return new SocketFactoryRegister<T>();
    }

    public SocketFactoryRegister<T> register(String key, T object){
        ArgsUtils.isEmpty("key", key);
        ArgsUtils.isEmpty("value", object);

        factory.put(key, object);
        return this;
    }

    public Register<T> build(){
        return new Register<T>(factory);
    }

}
