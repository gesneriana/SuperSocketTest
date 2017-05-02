package weiqing_qq_383129257.fssm;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by 结城明日奈 on 2017/5/1.
 */
public class SendMsg extends Thread {

    private static String IpAddress = "192.168.43.73";
    private static int Port = 1994;
    public static Socket socket = null;

    public String msg;

    /**
     * 开启一个线程发送消息到服务器
     */
    @Override
    public void run() {
        super.run();
        try {
            if (socket != null && !socket.isConnected()) {
                socket.close();
                socket = null;
            }
            if (socket == null) {
                socket = new Socket(IpAddress, Port);
                socket.setTcpNoDelay(true);
            }

            // 获取客户端的输出流
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())), true);
            msg = msg + "\r\n";
            out.print(msg);
            out.flush();

            if(!socket.isInputShutdown()) {
                ReadMsg rm=new ReadMsg(socket.getInputStream());
                rm.start();
            }

        } catch (UnknownHostException uhe) {
            Log.e("uhe error msg", uhe.getMessage() == null ? "无消息" : uhe.getMessage());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            // Log.e("ioe error msg", ioe.getMessage() == null ? "无消息" : ioe.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            // Log.e("error msg", ex.getMessage() == null ? "无消息" : ex.getMessage());
        }
        this.interrupt();
    }

    /**
     * 发送消息到服务器端
     */
    private void sendMsg() {
        try {
            if (socket != null && !socket.isConnected()) {
                socket.close();
                socket = null;
            }
            if (socket == null) {
                socket = new Socket(IpAddress, Port);
            }

            // 获取客户端的输出流
            PrintWriter out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())), true);
            out.println(msg.replace("\n", "") + "\r\n");

            InputStream in = socket.getInputStream();
            StringBuilder sb = new StringBuilder();
            int ch = in.read();
            while (ch != -1) {
                sb.append(ch);
                ch = in.read();
            }

            System.out.println("响应数据:" + sb.toString());

        } catch (UnknownHostException uhe) {
            Log.e("uhe error msg", uhe.getMessage() == null ? "无消息" : uhe.getMessage());
        } catch (IOException ioe) {
            Log.e("ioe error msg", ioe.getMessage() == null ? "无消息" : ioe.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            // Log.e("error msg", ex.getMessage() == null ? "无消息" : ex.getMessage());
        }
    }
}
