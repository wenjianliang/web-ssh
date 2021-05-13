package vip.r0n9;

import com.jcraft.jsch.*;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import java.io.IOException;

public class MyTest {
    static  Session session;

    public static void main(String[] args) throws Exception {
//        JSch jsch = new JSch();
//
//        session = jsch.getSession("root", "104.156.227.78", 22);
//        session.setPassword("o8(DUzg2@8-Rve*T");
//
//        java.util.Properties config = new java.util.Properties();
//        config.put("StrictHostKeyChecking", "no");
//        session.setConfig(config);
//
//
//        session.connect();

//        String res = sendCommand("pwd");
//        System.out.println(res);
        listFolderStructure("root","o8(DUzg2@8-Rve*T","104.156.227.78",22,"ls");

    }

    public static void listFolderStructure(String username, String password,
                                           String host, int port, String command) throws Exception {

        Session session = null;
        ChannelExec channel = null;

        try {
            session = new JSch().getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
            channel.setOutputStream(responseStream);
            channel.setErrStream((System.err));
            channel.connect();

            while (channel.isConnected()) {
                Thread.sleep(100);
            }

            String responseString = new String(responseStream.toByteArray());
            System.out.println(responseString);
            System.out.println(channel.getExitStatus());
        } finally {
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
    }

}
