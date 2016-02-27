package com.example.guardeec.dorminet.requests;

import com.example.guardeec.dorminet.storage.Status;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Guardeec on 27.02.16.
 */
public class CheckRequest implements Runnable {

    @Override
    public void run() {
        try {
            HttpURLConnection connect = (HttpURLConnection) new URL("http://10.42.0.1:8080/DormServer%5Fwar/check?name=GodSaveTheQuinn&pass=L33TsupaH4X0R").openConnection();
            connect.setRequestMethod("GET");
            connect.setRequestProperty("Content-length", "0");
            connect.setUseCaches(false);
            connect.setAllowUserInteraction(false);
            connect.setConnectTimeout(1000);
            connect.setReadTimeout(1000);
            connect.connect();

            int status = connect.getResponseCode();

            switch (status) {
                case 200:{

                }
                case 201:{
                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            connect.getInputStream(), "UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    String answer = sb.toString();
                    if (answer.contains("inProgress")){
                        Status.getInstance().setStatusWork();
                    }
                    if (answer.contains("disable")){

                    }
                    if (answer.contains("enable")){
                        Status.getInstance().setStatusOn(answer.substring(7));
                    }
                    break;
                }
                case 404:{
                    Status.getInstance().setStatusOff();
                    break;
                }
                case 451:{
                    Status.getInstance().setStatusOff();
                    break;
                }
            }
        } catch (MalformedURLException ex) {
            Status.getInstance().setStatusOff();
        } catch (IOException ex) {
            Status.getInstance().setStatusOff();
        }
    }
}
