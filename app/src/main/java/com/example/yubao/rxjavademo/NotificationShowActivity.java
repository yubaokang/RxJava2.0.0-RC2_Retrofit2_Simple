package com.example.yubao.rxjavademo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationShowActivity extends AppCompatActivity {

    @BindView(R.id.tv_show)
    TextView tv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_show);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String content = intent.getStringExtra("extra");
        tv_show.setText(content);
    }
}
