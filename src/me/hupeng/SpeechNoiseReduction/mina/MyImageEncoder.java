package me.hupeng.SpeechNoiseReduction.mina;


import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

public class MyImageEncoder implements ProtocolEncoder {

    @Override
    public void encode(IoSession ioSession, Object message, ProtocolEncoderOutput out) throws Exception {

        if (message != null){

            MyData myData = (MyData)message;
            IoBuffer ioBuffer;
            ioBuffer = IoBuffer.allocate(1024).setAutoExpand(true);
            ioBuffer.setAutoShrink(true);
            ioBuffer.setAutoExpand(true);

            String t = "";
            for (int i  = 0  ; i < 320 ; i ++){
                t =  t + i + " ";
            }
            CharsetEncoder charsetEncoder = (CharsetEncoder)ioSession.getAttribute("encoder");
            if(charsetEncoder == null){
                charsetEncoder = Charset.defaultCharset().newEncoder();
                ioSession.setAttribute("encoder", charsetEncoder);
            }
            ioBuffer.putString(t, charsetEncoder);
            //ioBuffer.putInt(myData.x);
            //ioBuffer.putInt(myData.y);
            ioBuffer.flip();
            out.write(ioBuffer);
        }
    }

    @Override
    public void dispose(IoSession ioSession) throws Exception {
    }
}
