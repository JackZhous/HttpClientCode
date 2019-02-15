package com.jack.iot.conn;

import com.jack.iot.conn.imple.ConnFAC;
import com.jack.iot.conn.imple.MangeClientConnection;
import com.jack.iot.conn.parse.DefaultIotMsgFactory;
import com.jack.iot.route.impl.IotRoute;
import com.jack.iot.txrx.impl.IotRequest;

/**
 * @author jackzhous
 * @package com.jack.iot.conn
 * @filename ConnectionFactory
 * date on 2019/2/13 3:43 PM
 * @describe TODO
 * @email jackzhouyu@foxmail.com
 **/
public class ConnectionFactory implements ConnFAC<IotRoute, MangeClientConnection> {

    private static final int BUFF_SIZE = 100;           //缓存字节大小

    @Override
    public MangeClientConnection create(IotRoute route) {

        String id = "iot_conn_" + System.currentTimeMillis();




        return new DefaultClientConnection(BUFF_SIZE, id, DefaultIotMsgFactory.INSTANCE);
    }
}
