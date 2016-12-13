package me.hupeng.SpeechNoiseReduction.mina;



import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;



public class MyImageDecoder extends CumulativeProtocolDecoder {

    @Override
    protected boolean doDecode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput) throws Exception {
        if(ioBuffer.remaining() > 4){//前4字节是包头
            //标记当前position的快照标记mark，以便后继的reset操作能恢复position位置
            ioBuffer.mark();
            int len = ioBuffer.getInt();
            int client_id = ioBuffer.getInt();

            if (len == 0){
                ioBuffer.position(ioBuffer.position()+ioBuffer.limit()-4);
                return true;
            }

            //注意上面的get操作会导致下面的remaining()值发生变化
            if(ioBuffer.remaining() < len){
                //如果消息内容不够，则重置恢复position位置到操作前,进入下一轮, 接收新数据，以拼凑成完整数据
                ioBuffer.reset();
                return false;
            }else{
                //消息内容足够

                ioBuffer.reset();
                int length = ioBuffer.getInt();
                client_id = ioBuffer.getInt();


                byte dest[] = new byte[length];
                ioBuffer.get(dest);

                //图片裁剪

                return true;
            }
        }
        return false;//处理成功，让父类进行接收下个包
    }



}
