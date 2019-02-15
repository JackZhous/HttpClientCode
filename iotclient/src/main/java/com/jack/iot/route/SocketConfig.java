package com.jack.iot.route;

import java.net.InetSocketAddress;

/**
 * @author jackzhous
 * @package com.jack.iot.route
 * @filename SocketConfig
 * date on 2019/2/11 2:59 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public class SocketConfig {

    private final int connectTimeout;
    private final int socketTimeout;

    private boolean keepAlive;

    private boolean tcpNoDelay;

    private int sndBufSize;

    private int revBufSize;


    SocketConfig(int connectTimeout, int socketTimeout, boolean keepAlive, boolean tcpNoDelay, int sndBufSize, int revBufSize) {
        this.connectTimeout = connectTimeout;
        this.socketTimeout = socketTimeout;
        this.keepAlive = keepAlive;
        this.tcpNoDelay = tcpNoDelay;
        this.sndBufSize = sndBufSize;
        this.revBufSize = revBufSize;
    }


    public int getConnectTimeout() {
        return connectTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public boolean isTcpNoDelay() {
        return tcpNoDelay;
    }

    public int getSndBufSize() {
        return sndBufSize;
    }

    public int getRevBufSize() {
        return revBufSize;
    }


    public static class Builder{

        private boolean keepAlive;

        private boolean tcpNoDelay;

        private int sndBufSize;

        private int revBufSize;

        private  int connectTimeout;
        private  int socketTimeout;

        public Builder() {
            this.keepAlive = true;
            this.tcpNoDelay = true;
        }

        public Builder setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder setSocketTimeout(int socketTimeout) {
            this.socketTimeout = socketTimeout;
            return this;
        }


        public Builder setKeepAlive(boolean keepAlive) {
            this.keepAlive = keepAlive;
            return this;
        }

        public Builder setTcpNoDelay(boolean tcpNoDelay) {
            this.tcpNoDelay = tcpNoDelay;
            return this;
        }

        public Builder setSndBufSize(int sndBufSize) {
            this.sndBufSize = sndBufSize;
            return this;
        }

        public Builder setRevBufSize(int revBufSize) {
            this.revBufSize = revBufSize;
            return this;
        }


        public SocketConfig build(){
            return new SocketConfig( connectTimeout, socketTimeout, keepAlive, tcpNoDelay, sndBufSize, revBufSize);
        }
    }
}
