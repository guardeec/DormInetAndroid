package com.example.guardeec.dorminet.requests;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guardeec.dorminet.R;
import com.example.guardeec.dorminet.pojo.PojoPing;
import com.example.guardeec.dorminet.storage.Statistics;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Created by Guardeec on 27.02.16.
 */
public class CheckRequest implements Runnable {

    Gson gson = new Gson();

    @SuppressLint("SetTextI18n")
    @Override
    public void run() {
        for (;;){

            /*
            Получаем состояние сервера
             */
            String answer = "disable";
            try {

                HttpURLConnection connect = (HttpURLConnection) new URL("http://10.42.0.1:8080/DormServerTest/check?name=GodSaveTheQuinn&pass=L33TsupaH4X0R").openConnection();
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
                            sb.append(line).append("\n");
                        }
                        br.close();
                        answer = sb.toString();
                        break;
                    }
                    case 404:{
                        answer = "disable";
                        break;
                    }
                    case 451:{
                        answer = "disable";
                        break;
                    }
                }
            } catch (IOException ex) {
                answer = "disable";
            }


            /*
            Получаем краткую характеристику
             */
            String shortStatistics = "";
            try {
                HttpURLConnection connect = (HttpURLConnection) new URL("http://10.42.0.1:8080/DormServerTest/get?name=GodSaveTheQuinn&pass=L33TsupaH4X0R").openConnection();
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
                            sb.append(line).append("\n");
                        }
                        br.close();
                        shortStatistics = sb.toString();
                        break;
                    }
                    case 404:{
                        shortStatistics = null;
                        break;
                    }
                    case 451:{
                        shortStatistics = null;
                        break;
                    }
                }
            } catch (IOException ex) {
                shortStatistics = null;
            }



            /*
            Получаем полную характеристику
             */
            String wholeStatistics = "";
            try {
                HttpURLConnection connect = (HttpURLConnection) new URL("http://10.42.0.1:8080/DormServerTest/getWhole?name=GodSaveTheQuinn&pass=L33TsupaH4X0R").openConnection();
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
                            sb.append(line).append("\n");
                        }
                        br.close();
                        wholeStatistics = sb.toString();
                        break;
                    }
                    case 404:{
                        wholeStatistics = null;
                        break;
                    }
                    case 451:{
                        wholeStatistics = null;
                        break;
                    }
                }
            } catch (IOException ex) {
                wholeStatistics = null;
            }

            System.out.println(answer);
            System.out.println(shortStatistics);
            System.out.println(wholeStatistics);
            System.out.println();

            Statistics statistics = Statistics.getInstance();
            statistics.setTextForUser(answer);

            if (shortStatistics!=null){
                statistics.setMediana(Float.parseFloat(shortStatistics.substring(shortStatistics.indexOf("median=")+7, shortStatistics.indexOf("&"))));
                statistics.setAverage(Float.parseFloat(shortStatistics.substring(shortStatistics.indexOf("&average=")+9)));
            }
            if (wholeStatistics!=null){
                statistics.setPojoPings((PojoPing[]) gson.fromJson(wholeStatistics, PojoPing[].class));
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignored) {
            }
        }
    }


}
