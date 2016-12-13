package me.hupeng.SpeechNoiseReduction.TensorFlow;

import android.graphics.Bitmap;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import java.util.List;

/**
 * Created by hupeng on 11/27/16.
 */

public class  TensorFlowImageClassifier implements Classifier {


    private TensorFlowInferenceInterface tensorFlowInferenceInterface;

    //继承了识别类 必须返回一个识别的结果必须返回一个识别的结果，包含识别的结果的几个部分，包含了识别ID    识别结果     识别的结果         目标的的矩形区域的float的值
    @Override
    public List<Recognition> recognizeImage(Bitmap bitmap) {
        return null;
    }

    //关闭
    @Override
    public void close() {

    }


}
