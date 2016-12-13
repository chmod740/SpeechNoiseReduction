package me.hupeng.SpeechNoiseReduction.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.textline.TextLineDecoder;
import org.apache.mina.filter.codec.textline.TextLineEncoder;

public class MyImageFactory implements ProtocolCodecFactory {
    private MyImageEncoder myImageEncoder;
    private MySimpleDecoder myImageDecoder;

    public MyImageFactory(){
        myImageEncoder = new MyImageEncoder();
        myImageDecoder = new MySimpleDecoder();
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return new TextLineEncoder();
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return new TextLineDecoder();
    }
}
