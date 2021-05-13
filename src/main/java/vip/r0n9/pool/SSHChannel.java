package vip.r0n9.pool;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.Data;

@Data
public class SSHChannel {
    public SSHChannel(String username, String host, int port, String password) throws Exception {
        this.username = username;
        this.host = host;
        this.port = port;
        this.password = password;
        this.connect();
    }

    private String username;
    private String host;
    private int port;
    private String password;

    private Session session;
//    private Channel channel;
    private JSch jSch;
    private ChannelExec channelExec;
    public void connect() throws Exception {
        jSch = new JSch();
        session = jSch.getSession(username, host, port);
        session.setPassword(password);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
        System.out.println("连接成功");

    }
}
