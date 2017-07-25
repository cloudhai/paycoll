package com.hai.netty;

import com.hai.util.LogUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.channel.*;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;


/**
 * Created by cloud on 2017/5/25.
 */
public class NettyServer {
    private int port;
    public NettyServer(int port){
        this.port = port;
    }

    public void run(){
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup workers = new NioEventLoopGroup();
        try{
            ServerBootstrap server = new ServerBootstrap();
            server.group(boss,workers)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .option(ChannelOption.SO_BACKLOG,100)
                    .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
//                            p.addLast("decoder",new StringDecoder(Charset.forName("UTF-8")));
//                            p.addLast("encoder",new StringEncoder(Charset.forName("UTF-8")));
                            p.addLast("myhandler",new FixLengthHeaderDecoder());
                            p.addLast("msg",new Myheadler());

                        }
                    });
            ChannelFuture future = server.bind(port).sync();
            future.channel().closeFuture().sync();

        }catch (Exception e){
            LogUtils.log.error("neety 启动失败 msg:"+e.getMessage());

        }
        finally {
            workers.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        NettyServer server = new NettyServer(8880);
        server.run();
        System.out.println("start success!");
    }

    class Myheadler extends SimpleChannelInboundHandler<String>{

        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
            System.out.println("receive msg:"+s);
        }
    }


}
