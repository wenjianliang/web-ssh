package vip.r0n9.pool;

import com.jcraft.jsch.ChannelExec;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

public class PoolTest {
    public static void main(String[] args) {
        GenericObjectPoolConfig config=new GenericObjectPoolConfig();

        config.setMaxIdle(5);//最大活跃数
        config.setMinIdle(1);//最小活跃数
        config.setMaxTotal(20);//最大总数

        //创建资源池
        SSHChannelPool channelPool=new SSHChannelPool(config,"root","104.156.227.78",22,"o8(DUzg2@8-Rve*T");


        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    while (true) {
                        try {
                            System.out.println("池中处于闲置状态的实例pool.getNumIdle()："+channelPool.getNumIdle());

                            System.out.println("池中所有在用实例数量pool.getNumActive()："+channelPool.getNumActive());

                            //获取连接
                            SSHChannel sshChannel=channelPool.getResource();
                            //发送消息
                            ChannelExec channelExec = (ChannelExec) sshChannel.getSession().openChannel("exec");
                            while (channelExec.isConnected()) {
                                Thread.sleep(100);
                            }
                            channelExec.setCommand("ls");
                            ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
                            channelExec.setOutputStream(responseStream,true);

                            channelExec.setErrStream((System.err),true);

                            channelExec.connect();
                            while (channelExec.isConnected()) {
                                Thread.sleep(100);
                            }
                            String responseString = new String(responseStream.toByteArray());
                            System.out.println("return str:"+responseString);
                           // Thread.sleep(2000);
                            System.out.println("==============================="+channelExec.getExitStatus());
                            //回收资源
                            //sshChannel.getChannelExec().disconnect();
                            //channelExec.disconnect();
                            Thread.sleep(5000);
                            channelPool.returnResource(sshChannel);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }).start();

        }
    }
}
