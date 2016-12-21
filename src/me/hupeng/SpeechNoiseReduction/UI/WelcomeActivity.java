package me.hupeng.SpeechNoiseReduction.UI;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import me.hupeng.SpeechNoiseReduction.R;
import me.hupeng.SpeechNoiseReduction.Util.Config;
import me.hupeng.SpeechNoiseReduction.mina.Mina;
import me.hupeng.SpeechNoiseReduction.mina.MinaUtil;
import me.hupeng.SpeechNoiseReduction.mina.SimpleMinaListener;
import org.apache.mina.core.session.IoSession;

/**
 * Created by hupeng on 11/27/16.
 */

public class WelcomeActivity extends Activity {

    private void initial(){
        //如果需要使用网络，从此处开始加载网络通信模块
        if (Config.isUseNetWork){
            MinaUtil minaUtil = MinaUtil.getInstance(false, Config.tensorFlowHost);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initial();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /**标题是属于View的，所以窗口所有的修饰部分被隐藏后标题依然有效,需要去掉标题**/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome);
        handler.sendEmptyMessageDelayed(0,3000);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            getHome();
            super.handleMessage(msg);
        }
    };

    public void getHome(){
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
