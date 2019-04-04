package com.chajianhua;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.chajianhua.pluginlib.PluginManager;
import com.chajianhua.pluginlib.ProxyActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PluginManager.getInstance().init(this);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加载apk文件
                String apkPath = utils.copyAssestAndWrite(MainActivity.this, "pppp.apk");

                PluginManager.getInstance().loadApk(apkPath);
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到指定的Activity
                Intent intent = new Intent(MainActivity.this, ProxyActivity.class);
                intent.putExtra("className","com.chajianhua.pluginapp.ChajianActivity");
                startActivity(intent);
            }
        });
    }
}
