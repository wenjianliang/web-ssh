package vip.r0n9.pool;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class ChannelFactory implements PooledObjectFactory<SSHChannel> {
    private String username;
    private String host;
    private int port;
    private String password;

    public ChannelFactory(String username, String host, int port, String password) {
        this.username = username;
        this.host = host;
        this.port = port;
        this.password = password;
    }

    @Override
    public PooledObject<SSHChannel> makeObject() throws Exception {
        SSHChannel conn=new SSHChannel(username,host,port,password);
        return new DefaultPooledObject<SSHChannel>(conn);
    }

    @Override
    public void destroyObject(PooledObject<SSHChannel> pooledObject) throws Exception {

    }

    @Override
    public boolean validateObject(PooledObject<SSHChannel> pooledObject) {
        return false;
    }

    @Override
    public void activateObject(PooledObject<SSHChannel> pooledObject) throws Exception {

    }

    @Override
    public void passivateObject(PooledObject<SSHChannel> pooledObject) throws Exception {

    }
}
