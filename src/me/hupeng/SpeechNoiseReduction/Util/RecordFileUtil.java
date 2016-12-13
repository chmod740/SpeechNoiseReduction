package me.hupeng.SpeechNoiseReduction.Util;


import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 录音文件处理工具类
 * @author hupeng@imudges.com
 * @version 1.0
 */
public class RecordFileUtil {
    /**
     * 文件路径对象
     */
    private File fpath;

    /**
     * 日志ID
     */
    private int id;

    /**
     *  音频流对象
     */
    private DataOutputStream dos = null;

    //文件名
    private String fileName;

    //录音文件对象
    private File audioFile;

    /**
     * 初始化值
     * @param id 日志ID
     */
    public void init(int id){
        //文件路径
        String pathStr = Environment.getExternalStorageDirectory().getAbsolutePath()+"/data/SNR_RecordFile/";
        fpath = new File(pathStr);
        //判断文件夹是否存在,若不存在则创建
        if (!fpath.exists()){
            fpath.mkdirs();
        }
        this.id = id;
    }

    /**
     * 记录文件
     * @param loveLogID 日志ID
     */
    public RecordFileUtil(int loveLogID){
        //每个loveLog对应一个录音文件
        init(loveLogID);
    }

    /**
     * 将数据写入buffer
     * @param buffer 音频数据
     */
    public void writeBuffer(short buffer){
        if (dos==null){
            try {
                audioFile = File.createTempFile("file_" + id+"", ".pcm", fpath);
                dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(audioFile)));
                fileName = audioFile.getName();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        try{
            dos.writeShort(buffer);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 停止写入buffer
     */
    public void stopWriteBuffer(){
        if (dos!=null){
            try{
                dos.close();
                dos = null;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        stopWriteBuffer();
        super.finalize();
    }

    /**
     * 得到文件名
     * @return 文件名
     */
    public String getFileName(){
        return fileName;
    }
}
