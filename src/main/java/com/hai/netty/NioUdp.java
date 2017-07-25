package com.hai.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Created by cloud on 2017/6/6.
 */
public class NioUdp {
    public static void main(String[] args) throws IOException {
        final DatagramChannel channel = DatagramChannel.open();
        channel.socket().bind(new InetSocketAddress(8899));
        final ByteBuffer buf = ByteBuffer.allocate(1024);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        channel.receive(buf);
                        while(buf.remaining()>0){
                            byte[] bs = new byte[1024];
                            buf.get(bs,0,buf.remaining());
                            System.out.println(new String(bs));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
