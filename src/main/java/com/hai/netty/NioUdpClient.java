package com.hai.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * Created by cloud on 2017/6/6.
 */
public class NioUdpClient {
    public static void main(String[] args) throws IOException {
        DatagramChannel channel = DatagramChannel.open();
        channel.socket();
        channel.send(ByteBuffer.wrap("this is a test msg".getBytes()),new InetSocketAddress("192.168.1.10",8899));
    }
}
