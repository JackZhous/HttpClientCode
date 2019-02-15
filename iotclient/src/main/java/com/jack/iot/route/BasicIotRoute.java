package com.jack.iot.route;

import com.jack.iot.route.impl.IotRoute;

import java.net.InetSocketAddress;

/**
 * @author jackzhous
 * @package com.jack.iot.route
 * @filename BasicIotRoute
 * date on 2019/2/15 2:25 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public class BasicIotRoute implements IotRoute {


    private InetSocketAddress target;

    private InetSocketAddress local;

    private String schme;

    public BasicIotRoute(InetSocketAddress target, InetSocketAddress local, String schme) {
        this.target = target;
        this.local = local;
        this.schme = schme;
    }

    @Override
    public InetSocketAddress getClientAddress() {
        return local;
    }

    @Override
    public InetSocketAddress getServerAddress() {
        return target;
    }

    @Override
    public String getSchme() {
        return schme;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this){
            return true;
        }
        if(obj instanceof IotRoute){
            IotRoute route = (IotRoute)obj;
            return target.getHostName().equals(route.getServerAddress().getHostName()) &&
                            target.getPort() == route.getServerAddress().getPort();
        }
        return false;
    }


    @Override
    public int hashCode() {
        return (target.getPort()+target.getHostName()).hashCode();
    }
}
