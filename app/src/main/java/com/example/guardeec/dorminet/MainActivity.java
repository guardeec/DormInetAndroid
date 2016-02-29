package com.example.guardeec.dorminet;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guardeec.dorminet.pojo.PojoPing;
import com.example.guardeec.dorminet.requests.CheckRequest;
import com.example.guardeec.dorminet.requests.SpoofRequest;
import com.example.guardeec.dorminet.storage.Statistics;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnSpoof;
    private ImageView image;
    private TextView text;
    private TextView average;
    private TextView time;
    private Bitmap bmp;

    private boolean pictureFlag = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSpoof = (Button) findViewById(R.id.BtnSpoof);
        image = (ImageView) findViewById(R.id.ImageStatus);
        text = (TextView) findViewById(R.id.textView);
        average = (TextView) findViewById(R.id.textView2);
        time = (TextView) findViewById(R.id.textView3);
        image.setImageResource(R.drawable.stop);
        text.setText("No connection");

        /*
        На кнопке сервер переключает инет
         */
        View.OnClickListener onClickListenerSpoof = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSpoof.setClickable(false);
                Thread spoofDormThread = new Thread(new SpoofRequest());
                spoofDormThread.start();
            }
        };
        btnSpoof.setOnClickListener(onClickListenerSpoof);
        /*
        Слушаем сервер - вся инфа идет в кэш
         */
        Thread serverListenThread = new Thread(new CheckRequest());
        serverListenThread.start();

        /*
        Берем инфу из кэша в ЮИ
         */
        Runnable uiUpdateRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        for (;;){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    /*
                                    Обновить сообщение для пользователя и изображение
                                     */
                                    Statistics statistics = Statistics.getInstance();
                                    if (statistics.getTextForUser().contains("disable")){
                                        btnSpoof.setClickable(true);
                                        image.setImageResource(R.drawable.stop);
                                        text.setText("Can`t find the server");
                                    }
                                    if (statistics.getTextForUser().contains("enable")){
                                        btnSpoof.setClickable(true);
                                        image.setImageResource(R.drawable.on);
                                        text.setText("MAC: "+statistics.getTextForUser().substring(statistics.getTextForUser().indexOf("\n")+1, statistics.getTextForUser().indexOf("\n")+18));
                                    }
                                    if (statistics.getTextForUser().contains("inProgress")){
                                        btnSpoof.setClickable(false);
                                        image.setImageResource(R.drawable.work);
                                        text.setText("Wait for few minutes");
                                    }
                                    average.setText(statistics.getAverage()+"P  ");
                                    time.setText("  "+statistics.getMediana()+"ms");

                                    if (pictureFlag){
                                        bmp = getMatrix(statistics.getPojoPings());
                                        image.setImageBitmap(bmp);
                                    }
                                }
                            });
                            wait(150);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread updateUIThread = new Thread(uiUpdateRunnable);
        updateUIThread.start();

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureFlag^=true;
            }
        });
    }

    private static Bitmap getMatrix(PojoPing[] pojoPings){
        Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);

        List<Rect> rectList = Cubes.makeCubes();
        int i=0;
        for (Rect rect : rectList){
            if (pojoPings[i]==null){
                paint.setColor(Color.WHITE);
            }else {
                if (pojoPings[i].getType()){
                    paint.setColor(Color.GRAY);
                    if (pojoPings[i].getPing()>255){
                        paint.setAlpha(0);
                    }else {
                        paint.setAlpha((int)(pojoPings[i].getPing()-255)*-1);
                    }
                }else {
                    paint.setColor(Color.RED);
                }
            }
            canvas.drawRect(rect, paint);
            i++;
        }
        return bitmap;
    }

    private static class Cubes {
        public static List<Rect> makeCubes(){
            List<Rect> list = new LinkedList();

            for (int i=0; i<200; i+=20){
                for (int q=0; q<200; q+=20){
                    Rect rect = new Rect();
                    rect.left = q;
                    rect.top = i;
                    rect.right = q+17;
                    rect.bottom = i+17;
                    list.add(rect);
                }
            }
            return list;
        }
    }
}
