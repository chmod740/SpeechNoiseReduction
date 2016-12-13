package me.hupeng.SpeechNoiseReduction.UI;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import android.widget.Toast;
import org.apache.mina.core.session.IoSession;

import me.hupeng.SpeechNoiseReduction.Listener.AudioListener;
import me.hupeng.SpeechNoiseReduction.R;
import me.hupeng.SpeechNoiseReduction.Util.AudioRecorder;
import me.hupeng.SpeechNoiseReduction.mina.MinaUtil;
import me.hupeng.SpeechNoiseReduction.mina.MyData;
import me.hupeng.SpeechNoiseReduction.mina.SimpleMinaListener;

public class MainActivity extends Activity {
//    static {
//        System.loadLibrary("tensorflow_demo");
//    }

    private ImageView playButton;
    private ImageView stopButton;

    private boolean isStart = false;
    private boolean isPause = false;

    private AudioRecorder audioRecorder = new AudioRecorder((int)(System.currentTimeMillis() / 1000));

    private ImageView light1;
    /**
     * 初始化控件以及变量
     * */
    private void initial(){
        playButton = (ImageView)findViewById(R.id.play_button);
        playButton.setOnClickListener(new MyOnClickListener());
        stopButton = (ImageView)findViewById(R.id.stop_button);
        stopButton.setOnClickListener(new MyOnClickListener());

        audioRecorder.setListener(new MyAudioListener());

        MinaUtil minaUtil = MinaUtil.getInstance(new SimpleMinaListener() {
            @Override
            public void onReceive(Object obj, IoSession ioSession) {

            }

            @Override
            public void onLine(String msg) {

            }

            @Override
            public void offLine() {

            }
        },false,"183.175.12.163");
        int data [] = new int[320];
        for (int i =  0 ; i < 320 ; i++){
            data[i] = i;
        }
        MyData myData = new MyData();
        myData.data = data;
        minaUtil.send(myData);
        minaUtil.send(myData);
        minaUtil.send(myData);
        minaUtil.send(myData);minaUtil.send(myData);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){

                }
            }
        }).start();
    }

    /**
     * 开始录音
     * */
    private void start(){

        light1=(ImageView)findViewById(R.id.light1);

        if (isStart){
            return;
        }

        playButton.setBackgroundResource(R.drawable.pause_on);
        stopButton.setBackgroundResource(R.drawable.stop_on);

        audioRecorder.startRecord();

        isStart = true;
    }

    /**
     * 暂停录音
     * */
    private void pause(){
        if (!isStart ){
            return;
        }
        //已经是开始的状态了
        if (isPause){
            //原来是暂停状态，继续录音
            playButton.setBackgroundResource(R.drawable.pause_on);
            isPause = false;

        }else {
            //原来是录音状态，暂停录音
            playButton.setBackgroundResource(R.drawable.play_on);
            isPause = true;
            //
        }
        audioRecorder.pauseOrRestart();
    }

    /**
     * 停止录音
     * */
    private void stop(){
        if (!isStart){
            return;
        }

        playButton.setBackgroundResource(R.drawable.play_on);
        stopButton.setBackgroundResource(R.drawable.stop_off);

        isPause = false;
        isStart = false;

        audioRecorder.stopRecord();
        light1.setBackgroundResource(R.drawable.light0);
    }


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initial();
//        TensorFlowInferenceInterface tensorFlowInferenceInterface = new TensorFlowInferenceInterface();
    }


    class MyOnClickListener implements View.OnClickListener{


        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.play_button:
                    if (!isStart){
                        start();
                    }else {
                       pause();
                    }
                    break;
                case R.id.stop_button:
                    stop();
            }
        }
    }







    class MyAudioListener implements AudioListener{

        @Override
        public void getBuffer(short[] buffer) {

        }

        @Override
        public void getDB(double v) {
            System.out.println(v);
            int lightLevel = (int)((v/100*254))>254?254:((int)(v/100*254));
            Message message = new Message();
            message.obj = lightLevel;
            handler.sendMessage(message);
        }

        @Override
        public void getPower(double v) {
            System.out.println(v);
        }
    }


    /**
     * 线程间的通信,接收消息,刷新主线程UI
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int lightLevel = (Integer) msg.obj;
            //System.out.println(lightLevel);
            changeBrightness(lightLevel);
            super.handleMessage(msg);
        }
    };

    /**
     * 调整灯泡的亮度
     * @param brightness 亮度(取值范围0-255，0是最暗，255是最亮)
     */
    public void changeBrightness(int brightness){
        //state=brightness;
        //light1=(ImageView)findViewById(R.id.light1);

        light1.setBackgroundResource(R.drawable.light1);

        brightness+=120;
        brightness = brightness>255?255:brightness;
        light1.getBackground().setAlpha(brightness);

        Log.i("info*******",brightness + "");
    }


    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
