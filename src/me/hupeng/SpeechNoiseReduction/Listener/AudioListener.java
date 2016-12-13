package me.hupeng.SpeechNoiseReduction.Listener;

/**
 * Created by hupeng on 11/30/16.
 */

public interface AudioListener {
    public void getBuffer(short[] buffer);
    public void getDB(double v);
    public void getPower(double v);
}
