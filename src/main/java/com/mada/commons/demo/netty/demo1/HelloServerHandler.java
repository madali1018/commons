package com.mada.commons.demo.netty.demo1;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by madali on 2018/11/20 16:41
 */
@ChannelHandler.Sharable
public class HelloServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        ctx.writeAndFlush("hello: " + msg + "\n");
    }

}
