package weiqing_qq_383129257.fssm;

import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 结城明日奈 on 2017/5/1.
 */
public class ReadMsg extends Thread {

    private InputStream inputStream;

    public ReadMsg(InputStream in) {
        inputStream = in;
    }

    @Override
    public void run() {
        super.run();
        try {
            if (inputStream != null) {
                DataInputStream dis = new DataInputStream(inputStream);
                String res = dis.readUTF();
                Log.d("服务器响应:", res);
                dis.close();
                inputStream.close();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
