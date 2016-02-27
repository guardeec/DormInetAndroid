package com.example.guardeec.dorminet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guardeec.dorminet.requests.CheckRequest;
import com.example.guardeec.dorminet.requests.SpoofRequest;
import com.example.guardeec.dorminet.storage.Status;

public class MainActivity extends AppCompatActivity {

    Button btnSpoof;
    Button btnUpdate;
    ImageView image;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSpoof = (Button) findViewById(R.id.BtnSpoof);
        btnUpdate = (Button) findViewById(R.id.BtnUpdate);
        image = (ImageView) findViewById(R.id.ImageStatus);
        text = (TextView) findViewById(R.id.textView);

        imageChange(Status.getInstance().getStatusId());
        text.setText(Status.getInstance().getStatus());

        View.OnClickListener onClickListenerSpoof = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new SpoofRequest());
                thread.start();
                Status.getInstance().setStatusWork();
            }
        };

        View.OnClickListener onClickListenerUpdate = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new CheckRequest());
                thread.start();


                imageChange(Status.getInstance().getStatusId());
                text.setText(Status.getInstance().getStatus());
            }
        };

        btnSpoof.setOnClickListener(onClickListenerSpoof);
        btnUpdate.setOnClickListener(onClickListenerUpdate);
    }

    private void imageChange(int state){
        switch (state){
            case 0:{
                image.setImageResource(R.drawable.stop);
                break;
            }
            case 1:{
                image.setImageResource(R.drawable.work);
                break;
            }
            case 2:{
                image.setImageResource(R.drawable.on);
                break;
            }
        }
    }
}
