package com.cnqisoft.gps;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @author Binary on 2020/4/30
 */
public class SocketHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        ChannelManager.remove(ctx.channel().id());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        try {
            while (in.isReadable()) {
                byte packageLength = in.readByte();
                byte protocolNumber = in.readByte();
                byte[] dataContent = new byte[in.readableBytes()];
                in.readBytes(dataContent);
                GpsEvent gpsEvent = GpsEvent.getInstance(protocolNumber);
                System.out.println("协议号：" + Utils.byteToHexString(protocolNumber));
                System.out.println();
                if (gpsEvent != null) {
                    GpsMessage gpsMessage = new GpsMessage(packageLength, protocolNumber, dataContent, ctx.channel());
                    gpsEvent.handle(gpsMessage);
                }
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
    }

}
