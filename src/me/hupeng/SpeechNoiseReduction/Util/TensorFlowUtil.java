package me.hupeng.SpeechNoiseReduction.Util;

import me.hupeng.SpeechNoiseReduction.mina.MinaUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUPENG on 2016/12/13.
 */
public class TensorFlowUtil extends Thread{
    private List<Short>list = new ArrayList<>();

    private short[] buffer;

    private MinaUtil minaUtil = null;

    public synchronized void add(short[] buffer){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0 ; i < buffer.length ; i ++){
                    list.add(buffer[i]);
                }
                String tmp= "";
                while(list.size() > 600){
                    for (int i =  0; i < 600 ; i ++ ){
                        tmp = tmp + " " + list.get(i);
                    }
                }
                tmp = tmp + "\n";
                if (minaUtil != null){
                    minaUtil.send(tmp);
                }
            }
        }).start();
//        list.addAll(java.util.Arrays.asList(buffer));

    }

    public void setMinaUtil(MinaUtil minaUtil){
        this.minaUtil = minaUtil;
    }

    public TensorFlowUtil(MinaUtil minaUtil){
        setMinaUtil(minaUtil);
    }


    @Override
    public void run() {

        super.run();
    }
}
