package com.dmycqq.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundByteHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class Client extends ChannelInboundByteHandlerAdapter {
	private static SocketAddress socketAddress = new InetSocketAddress(8080);
	private final ByteBuf buf = Unpooled.wrappedBuffer("Hello".getBytes());

	public static void main(String[] args) throws Exception {
		EventLoopGroup g = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(g);
		b.channel(NioSocketChannel.class);
		b.option(ChannelOption.TCP_NODELAY, true);
		b.handler(new ChannelInitializer<SocketChannel>() {
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new Client());
			}
		});
		ChannelFuture f = b.connect(socketAddress).sync();
		f.channel().closeFuture().sync();
		g.shutdownGracefully();
	}
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		System.out.println(ctx);
		ctx.write(buf);
	}

	@Override
	protected void inboundBufferUpdated(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		ByteBuf out = ctx.nextOutboundByteBuffer();
		out.writeBytes(in);
		ctx.flush();
	}
	
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
