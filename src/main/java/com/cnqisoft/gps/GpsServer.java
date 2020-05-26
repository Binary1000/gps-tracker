package com.cnqisoft.gps;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

/**
 * @author Binary
 */
public class GpsServer {

    private static boolean started;

    public static final int MAX_FRAME_LENGTH = 999;

    public synchronized static void start(int port, GpsEventListener gpsEventListener) {
        if (started) {
            throw new RuntimeException("服务器已经启动");
        }

        if (gpsEventListener == null) {
            throw new RuntimeException("gpsEventListener不能为空");
        }

        GpsEvent.setGpsEventListener(gpsEventListener);

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ChannelFuture channelFuture = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            byte[] start = new byte[]{0X78, 0X78};
                            byte[] end = new byte[]{0X0D, 0X0A};
                            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(MAX_FRAME_LENGTH, Unpooled.copiedBuffer(start), Unpooled.copiedBuffer(end), Unpooled.copiedBuffer(start)));
                            socketChannel.pipeline().addLast(new SocketHandler());
                        }
                    })
                    .bind(port).sync();

            System.out.println("服务器启动成功， 端口：" + port);
            started = true;

            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
