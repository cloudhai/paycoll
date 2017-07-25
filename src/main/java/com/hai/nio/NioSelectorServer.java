package com.hai.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by cloud on 2017/7/5.
 */
public class NioSelectorServer {
    private ReentrantLock lock = new ReentrantLock();
    public void run() throws IOException {
        Selector selector = Selector.open();
        Selector dataSelector = Selector.open();
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        channel.socket().bind(new InetSocketAddress(8888));
        channel.register(selector, SelectionKey.OP_ACCEPT);
        new Thread(new DataPaser(dataSelector)).start();
        System.out.println("start listen.....");
        while(true){
            int selected = selector.select();
            System.out.println("listen...");
            if(selected>0){
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                while(keys.hasNext()){
                    SelectionKey key = keys.next();
                    switch (key.readyOps()){
                        case SelectionKey.OP_ACCEPT:
                            System.out.println("accept");
                            SocketChannel client = channel.accept();
                            try{
                                System.out.println("start get lock");
                                lock.lock();
                                System.out.println("get lock");
                                client.configureBlocking(false);
                                dataSelector.wakeup();
                                client.register(dataSelector,SelectionKey.OP_READ);
                            }finally {
                                System.out.println("release lock");
                                lock.unlock();
                            }

                            break;
                        case SelectionKey.OP_CONNECT:
                            System.out.println("connect");
                            break;
                        case SelectionKey.OP_READ:
                            System.out.println("read");
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            SocketChannel socketChannel = (SocketChannel) key.channel();
                            socketChannel.read(buffer);
                            buffer.flip();
                            byte[] bytes = new byte[buffer.limit()];
                            buffer.get(bytes);
                            System.out.println(new String (bytes));
                            break;
                        case SelectionKey.OP_WRITE:
                            System.out.println("write");
                            break;
                    }
                    keys.remove();
                }
            }
        }
    }

    public void sendMsg(SocketChannel channel,String msg) {
        ByteBuffer buffer = ByteBuffer.allocate(msg.getBytes().length);
        try{
            buffer.put(msg.getBytes());
            buffer.flip();
            while(buffer.hasRemaining()){
                System.out.println(channel.write(buffer));
            }

        }catch (Exception e){

        }
        finally {
            buffer.clear();
        }

    }

    class DataPaser implements Runnable{

        private Selector selector;

        public DataPaser(Selector selector){
            this.selector = selector;
        }

        @Override
        public void run() {
            while(true){
                try {
                    int dataSelected = selector.select();
                    if(dataSelected>0){
                        Iterator<SelectionKey> dataKeys = selector.selectedKeys().iterator();
                        while(dataKeys.hasNext()){
                            SelectionKey key = dataKeys.next();
                            if(key.readyOps() == SelectionKey.OP_READ){
                                ByteBuffer buffer = ByteBuffer.allocate(1024);
                                SocketChannel socketChannel = (SocketChannel) key.channel();
                                socketChannel.read(buffer);
                                buffer.flip();
                                byte[] bytes = new byte[buffer.limit()];
                                buffer.get(bytes);
                                System.out.println(new String (bytes));
                                sendMsg(socketChannel,"hahahahaaaaaaaa");
                            }
                            dataKeys.remove();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    public static void main(String[] args) throws IOException {
        NioSelectorServer selectorServer = new NioSelectorServer();
        selectorServer.run();
    }
}
