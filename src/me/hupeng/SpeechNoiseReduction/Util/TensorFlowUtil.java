package me.hupeng.SpeechNoiseReduction.Util;

import me.hupeng.SpeechNoiseReduction.mina.MinaUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUPENG on 2016/12/13.
 */
public class TensorFlowUtil {
    private List<Short>list = new ArrayList<>();

    private MinaUtil minaUtil = null;

    public synchronized void add(short[] buffer){
//        list.addAll(java.util.Arrays.asList(buffer));
        for (int i = 0 ; i < buffer.length ; i ++){
            list.add(buffer[i]);
        }
        String tmp= "";
        while(list.size() > 600){
            for (int i =  0; i < 600 ; i ++ ){
                tmp = tmp + " " + i;
            }
        }
        if (minaUtil != null){
            minaUtil.send(tmp);
        }
    }

    public void setMinaUtil(MinaUtil minaUtil){
        this.minaUtil = minaUtil;
    }

    public TensorFlowUtil(MinaUtil minaUtil){
        setMinaUtil(minaUtil);
    }
}
