package com.chajianhua.pluginlib;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * 代理
 */

public class ProxyActivity extends Activity {
    private String mClassName;
    private PluginApk mPluginApk;
    private IPlugin mIpligin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClassName = getIntent().getStringExtra("className");
        mPluginApk = PluginManager.getInstance().getPluginApk();
        launchPluginActivity();
    }

    private void launchPluginActivity() {
        if (mPluginApk == null){
            throw new RuntimeException("请先加载插件apk");
        }

        try {
            Class<?> clazz = mPluginApk.mClassLoader.loadClass(mClassName);
            //Acitivty实例对象，这里的clazz没有生命周期和上下文
            Object object = clazz.newInstance();

            if (object instanceof IPlugin){
                mIpligin = (IPlugin) object;
                mIpligin.attach(this);
                Bundle bundle = new Bundle();
                bundle.putInt("FROM", IPlugin.FROM_EXTERNAL);
                mIpligin.onCreate(bundle);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resources getResources() {
        return mPluginApk != null ? mPluginApk.mResources : super.getResources();
    }

    @Override
    public AssetManager getAssets() {
        return mPluginApk != null ? mPluginApk.mAssetManager : super.getAssets();
    }

    @Override
    public ClassLoader getClassLoader() {
        return mPluginApk != null ? mPluginApk.mClassLoader : super.getClassLoader();
    }
}
