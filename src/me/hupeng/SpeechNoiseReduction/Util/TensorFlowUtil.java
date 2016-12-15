package me.hupeng.SpeechNoiseReduction.Util;

import me.hupeng.SpeechNoiseReduction.mina.MinaUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUPENG on 2016/12/13.
 */
public class TensorFlowUtil  {
    private List<Short>list = new ArrayList<>();

    private  short[] buffer;

    private MinaUtil minaUtil = null;

    public  void add(short[] buffer){
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (list) {

                    for (int i = 0 ; i < buffer.length ; i ++){
                        list.add(buffer[i]);

                    }
                    String tmp= "";
                    while(list.size() > 601){
                        for (int i =  0; i < 600 ; i ++ ){
                            Short s = list.get(0);
//                            if (s != null)
                                tmp = tmp + " " + list.get(0);
//                            else
//                                i--;
                            list.remove(0);
                        }

                        break;
                    }
                    tmp = tmp + "\n";
                    if (minaUtil != null && tmp.length() > 599){
                        minaUtil.send(tmp);
                    }
                    System.out.println("********" + tmp);
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



}
