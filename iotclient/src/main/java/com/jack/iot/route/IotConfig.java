package com.jack.iot.route;

/**
 * @author jackzhous
 * @package com.jack.iot.route
 * @filename IotConfig
 * date on 2019/2/14 3:31 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public class IotConfig {

    private final int connectionRequestTimeout;
    private final int connectTimeout;
    private final int socketTimeout;

    public IotConfig(int connectionRequestTimeout, int connectTimeout, int socketTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
        this.connectTimeout = connectTimeout;
        this.socketTimeout = socketTimeout;
    }


    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }
}
