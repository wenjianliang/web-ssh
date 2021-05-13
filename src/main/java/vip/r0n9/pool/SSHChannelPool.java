package vip.r0n9.pool;


import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class SSHChannelPool extends Pool<SSHChannel> {
    public SSHChannelPool(final GenericObjectPoolConfig poolConfig, String username, String host, int port, String password) {
        super(poolConfig,new ChannelFactory(username, host, port, password));
    }
    public SSHChannelPool(String username, String host, int port, String password) {
        super(new GenericObjectPoolConfig(),new ChannelFactory(username, host, port, password));
    }
    @Override
    public SSHChannel getResource() {
        SSHChannel connection = super.getResource();
        return connection;
    }
    @Override
    public void returnBrokenResource(final SSHChannel resource) {
        if (resource != null) {
            returnBrokenResourceObject(resource);
        }
    }
    @Override
    public void returnResource(final SSHChannel resource) {
        if (resource != null) {
            try {
                returnResourceObject(resource);
            } catch (Exception e) {
                returnBrokenResource(resource);
            }
        }
    }
}
