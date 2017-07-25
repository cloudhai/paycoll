package com.hai.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by cloud on 2017/6/15.
 */
public class FixLengthHeaderDecoder extends ByteToMessageDecoder {
    private final static int ILLEGAL_LENGTH = 2048;
    private final static int FLAG = 0xA518314B;
    private final static int MIN_LENGTH = 8;
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        Object res = decoder(byteBuf);
        if(res != null){
            list.add(res);
        }
    }

    private final Object decoder(ByteBuf buf){
        if(buf.readableBytes()<MIN_LENGTH){
            return null;
        }
        if(buf.readableBytes()>ILLEGAL_LENGTH){
            buf.skipBytes(buf.readableBytes());
        }
        int beginReader;
        while(true){
            //记录开始的位置
            beginReader = buf.readerIndex();
            //标记开始读的位置
            buf.markReaderIndex();
            //读取4个字节并与消息开始标志对比
            if(FLAG == buf.readInt()){
                //找到消息开始标记
                break;
            }
            //回到开始读的位置
            buf.resetReaderIndex();
            //往下跳一个字节，继续找消息开始位置
            buf.readByte();
            //跳过一个字节后，如果消息长度不够，则继续等
            if(buf.readableBytes()<MIN_LENGTH){
                return null;
            }
        }
        //读取消息的长度
        int msgLength = buf.readInt();
        //检查消息有没有全部传过来
        if(buf.readableBytes()<msgLength){
            //消息不全，重新回开始位置
            buf.readerIndex(beginReader);
            return null;
        }
        byte[] data = new byte[msgLength];
        buf.readBytes(data);
        return new String(data);

    }
}
