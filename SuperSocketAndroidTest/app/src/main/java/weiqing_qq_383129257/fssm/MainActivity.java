package weiqing_qq_383129257.fssm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {


    private EditText txt_content = null;
    private Button btn_send = null;
    private SendMsg sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_content = (EditText) findViewById(R.id.txt_content);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sm = new SendMsg();
                sm.msg = txt_content.getText().toString();
                sm.start();

            }
        });
    }

    @Override
    protected void onDestroy() {
        if (SendMsg.socket != null && SendMsg.socket.isConnected()) {
            try {
                SendMsg.socket.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage() == null ? "关闭连接时出现错误" : ex.getMessage(), Toast.LENGTH_SHORT);
            }
        }
        super.onDestroy();
    }
}