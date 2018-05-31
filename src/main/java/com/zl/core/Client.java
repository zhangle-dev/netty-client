package com.zl.core;

import java.util.List;

import org.apache.log4j.Logger;

import com.zl.core.exception.ConnectStatusException;
import com.zl.core.exception.ReadTimeoutException;
import com.zl.core.exception.WriteTimeoutException;
import com.zl.core.interface_.IClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.netty.util.concurrent.Future;

/**
 * netty客户端，里面封装了对netty的操作
 * @author zl
 *
 */
public class Client<REQ,RSP> implements IClient<REQ,RSP> {

	/*
	 * 与服务器之间的连接通道
	 */
	private SocketChannel channel;
	/*
	 * 客户端状态
	 */
	private Status status;
	/*
	 * 读取超时时间
	 */
	private Integer readTimeoutSeconds;
	/*
	 * 发送超时时间
	 */
	private Integer writeTimeoutSeconds;

	
	private ChannelHandlerContext ctx;
	private EventLoopGroup eventLoopGroup;
	private Bootstrap bootstrap;
	private Logger logger;

	
	/**
	 *
	 * @param host 服务端主机名
	 * @param port 服务端端口
	 * @throws Exception
	 */
	public Client(String host,Integer port,List<ChannelHandler> handlers,Integer readTimeoutSeconds,Integer writeTimeoutSeconds) throws Exception {
		status = Status.INIT;
		logger = Logger.getLogger(Client.class);
		logger.info("client core init ...");
		this.readTimeoutSeconds = readTimeoutSeconds;
		this.writeTimeoutSeconds = writeTimeoutSeconds;
		initClient(host,port,handlers);
	}

	/**
	 * 初始化连接
	 * @param host 服务端主机名
	 * @param port 服务端端口
	 * @param handlers netty中handler,如编码器和解码器
	 * @throws Exception
	 */
	private void initClient(String host, Integer port, final List<ChannelHandler> handlers) throws Exception {

		logger.info("set host:" + host +",port:" + port);
		eventLoopGroup = new NioEventLoopGroup();
		bootstrap = new Bootstrap();
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.group(eventLoopGroup);
		bootstrap.remoteAddress(host, port);
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel socketChannel) throws Exception {

				ChannelPipeline pipeline = socketChannel.pipeline();

				for (ChannelHandler channelHandler : handlers) {
					pipeline.addLast(channelHandler);
				}

				pipeline.addLast(new ReadTimeoutHandler(readTimeoutSeconds));
				pipeline.addLast(new WriteTimeoutHandler(writeTimeoutSeconds));
				pipeline.addLast(new ChannelInboundHandlerAdapter() {

					@Override
					public void channelActive(ChannelHandlerContext ctx) throws Exception {
						Client.this.ctx = ctx;
						super.channelActive(ctx);
					}

					@Override
					public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
					}

					@Override
					public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
						//TODO 失败
					}
                });
			}
		});
		
	}

	@Override
	public boolean start() {
		logger.info("client core starting ...");
		status = Status.STARTING;
		
		if(Status.INIT != status && Status.CLOSED != status) {
			logger.error("current status is " + status + ",so not start!");
			return false;
		}
		try {
			channel = (SocketChannel) bootstrap.connect().sync().channel();
			status = Status.RUN;
			logger.info("connection success !");
			return true;
		} catch (InterruptedException e) {
			logger.error("connect error,message is :" + e.getMessage());
			return false;
		}
	}
	
	@Override
	public Status status() {
		return status;
	}
	
	@Override
	public Future<RSP> request(REQ packet) throws ReadTimeoutException, WriteTimeoutException, ConnectStatusException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void request(REQ packet, ClientCallback<RSP> callback)
			throws ReadTimeoutException, WriteTimeoutException, ConnectStatusException {
		
	}

	@Override
	public void close() {
		status = Status.CLOSING;
		channel.close();
		try {
			eventLoopGroup.shutdownGracefully().sync();
		} catch (InterruptedException e) {
			logger.error("close eventLoopGroup error,message is:" + e.getMessage());
		}
		logger.info("channel closed success!");
		status = Status.CLOSED;
	}
    	
}
