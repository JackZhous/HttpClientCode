package com.jack.iot.client;

import com.jack.iot.client.impl.IotClient;
import com.jack.iot.conn.ClientExecMain;
import com.jack.iot.conn.PoolingClientConnectionManager;
import com.jack.iot.conn.imple.IotClientConnectionManager;
import com.jack.iot.conn.imple.IotClientExecChain;

import java.util.concurrent.TimeUnit;

/**
 * @author jackzhous
 * @package com.jack.iot.help
 * @filename IotClientBuilder
 * date on 2019/2/11 11:37 AM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public class IotClientBuilder {

    private static IotClientBuilder builder = new IotClientBuilder();
    private long timeToLive;
    private TimeUnit timeUnit;

    public IotClientBuilder() {
    }

    public static IotClientBuilder getInstance(){
        if(builder == null){
            builder = new IotClientBuilder();
        }

        return builder;
    }

    public final IotClientBuilder setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
        return this;
    }

    public final IotClientBuilder setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        return this;
    }



    public IotClient build(){
        IotClientConnectionManager connMana = new PoolingClientConnectionManager(null,
                                                            timeToLive, timeUnit);
        IotClientExecChain mainExec = new ClientExecMain(connMana);

        IotClient clientMana = new IotClientManager(mainExec, connMana);

        return clientMana;
    }

}
