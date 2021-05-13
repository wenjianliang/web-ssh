package vip.r0n9;

import com.jcraft.jsch.*;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    static  Session session;

    public static void main(String[] args) throws JSchException, IOException {
        JSch jsch = new JSch();

        session = jsch.getSession("root", "104.156.227.78", 22);
        session.setPassword("o8(DUzg2@8-Rve*T");

        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);


        session.connect();

        String res = sendCommand("pwd");
        System.out.println(res);
//        String test ="\\u82f9";
//        System.out.println(unicodeToString(test));

    }
    public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            //group 6728
            String group = matcher.group(2);
            //ch:'木' 26408
            ch = (char) Integer.parseInt(group, 16);
            //group1 \u6728
            String group1 = matcher.group(1);
            str = str.replace(group1, ch + "");
        }
        return str;
    }
    public static String sendCommand(String command) {
        StringBuilder outputBuffer = new StringBuilder();
        Channel channel = null;
        try {
            channel = session.openChannel("shell");
            channel.setInputStream(System.in, true);
            channel.setOutputStream(System.out, true);
            channel.connect();

//            int readByte = commandOutput.read();
//
//            while (readByte != 0xffffffff) {
//                outputBuffer.append((char) readByte);
//                readByte = commandOutput.read();
//            }
//
//            channel.disconnect();
//        } catch (IOException ioX) {
//            return null;
        } catch (JSchException jschX) {
            return null;
        }

        return outputBuffer.toString();
    }

}
