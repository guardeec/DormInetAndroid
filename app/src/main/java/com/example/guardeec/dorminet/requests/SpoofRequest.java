package com.example.guardeec.dorminet.requests;

import android.os.Handler;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Guardeec on 27.02.16.
 */
public class SpoofRequest implements Runnable {

    @Override
    public void run() {
        try {
            HttpURLConnection connect = (HttpURLConnection) new URL("http://10.42.0.1:8080/DormServerTest/change?name=GodSaveTheQuinn&pass=L33TsupaH4X0R").openConnection();
            connect.setRequestMethod("GET");
            connect.setRequestProperty("Content-length", "0");
            connect.setUseCaches(false);
            connect.setAllowUserInteraction(false);
            connect.setConnectTimeout(1000);
            connect.setReadTimeout(1000);
            connect.connect();

            int status = connect.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            connect.getInputStream(), "UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    br.close();
            }
        } catch (IOException ignored) {
        }
    }
}
