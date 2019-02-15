package com.jack.iot.help;

/**
 * @author jackzhous
 * @package com.jack.iot.help
 * @filename ArgsUtils
 * date on 2019/2/11 3:38 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public class ArgsUtils {

    /**
     * 参数空判断
     * @param info
     * @param obj
     */
    public static void isEmpty(String info, Object obj){
        if(obj == null){
            throw new IllegalArgumentException(info + " is empty");
        }
    }


    public static void isTrue(boolean state, String msg){
        if(state){
            throw new IllegalStateException(msg);
        }
    }
}
