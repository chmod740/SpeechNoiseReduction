package me.hupeng.SpeechNoiseReduction.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;


public class MySimpleDecoder implements ProtocolDecoder {

    @Override
    public void decode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {
        MyData myData = new MyData();
        int data [] = new int[320];
        for (int i = 0 ; i < 320 ; i ++){
            data[i] = ioBuffer.getInt();
        }
        //myData.x = ioBuffer.getInt();
        //myData.y = ioBuffer.getInt();
        myData.data = data;
        protocolDecoderOutput.write(myData);

    }

    @Override
    public void finishDecode(IoSession ioSession, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {

    }

    @Override
    public void dispose(IoSession ioSession) throws Exception {

    }
}
