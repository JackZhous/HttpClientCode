package com.jack.iot.route;

import com.jack.iot.help.ArgsUtils;

import java.util.Map;

/**
 * @author jackzhous
 * @package com.jack.iot.route
 * @filename Register
 * date on 2019/2/11 3:43 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public class Register<T> {

    private final Map<String, T> factorys;


    public Register(Map<String, T> factory) {
        this.factorys = factory;
    }



    public T lookFor(String key){
        ArgsUtils.isEmpty("lookFor key", key);

        return factorys.get(key);
    }
}
