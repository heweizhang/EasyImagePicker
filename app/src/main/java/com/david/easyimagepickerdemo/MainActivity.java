package com.david.easyimagepickerdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.david.easyimagepicker.view.ImageSelectActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_view_images).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this,ImageSelectActivity.class));
            }
        });
    }
}
