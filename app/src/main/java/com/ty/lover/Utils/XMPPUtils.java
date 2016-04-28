package com.ty.lover.Utils;

import com.ty.lover.data.Const;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

/**
 * Created by Administrator on 2016/4/1.
 */
public class XMPPUtils {
    //连接实例
    private XMPPConnection connection;
    //类的实例
    private static XMPPUtils xmppUtils;
    //实例化IP与端口号对象
    Const aConst = new Const();

    private XMPPUtils() {  //构造方法实现连接
        ThreadUtils.runInThread(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });
    }

    private void init() {
        //1.创建连接配置对象
        ConnectionConfiguration conf = new ConnectionConfiguration(aConst.HOST, aConst.PORT);
        // 额外的配置
        conf.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);  //明文传输
        conf.setDebuggerEnabled(true);  // 开启调试模式
        //2.开始创建连接对象
        connection = new XMPPConnection(conf);
        //3.开始连接
        try {
            connection.connect();
        } catch (XMPPException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到连接
     */
    public XMPPConnection getConnection() {
        if (connection != null) {  //判断连接不为空
            try {
                if (!connection.isConnected()) {  //如果没有连接上的话
                    connection.connect();
                }
            } catch (XMPPException e) {
                e.printStackTrace();
            }
            return connection;
        }
        return null;  //如果连接为空，就返回空
    }

    /**
     * 登录功能
     */
    public void Login(String name, String pwd) {
        try {
            connection = getConnection();
            if (connection == null) {
                ConnectionConfiguration conf = new ConnectionConfiguration(aConst.HOST, aConst.PORT);
                connection = new XMPPConnection(conf);
            }
            if (!connection.isConnected()) {
                connection.connect();
            }
            connection.login(name, pwd);
            System.out.println("++++++++++++++++登录成功");
        } catch (XMPPException e) {
            e.printStackTrace();
            System.out.println("++++++++++++++++登录失败");
        }
    }

    //单例模式，synchronized 同步，就是一个对象在连接时，其他的就不能用
    public synchronized static XMPPUtils getInstance() {
        if (xmppUtils == null) {
            xmppUtils = new XMPPUtils();
        }
        return xmppUtils;
    }
}


