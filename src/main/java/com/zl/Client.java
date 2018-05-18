package com.zl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;

/**
 * netty�ͻ��ˣ������װ�˶�netty�Ĳ���
 * @author zl
 *
 */
public class Client<REQ,RSP> implements IClient<REQ,RSP> {

	/**
	 * �������֮�������ͨ��
	 */
	private SocketChannel channel;
	
	private boolean isConnection = false;
	
	private LinkedBlockingQueue<RSP> queue = new LinkedBlockingQueue<RSP>();
	
	private ChannelHandlerContext ctx;

	private EventLoopGroup eventLoopGroup;

	public Client() {
	}
	/**
	 * 
	 * @param host �����������
	 * @param port ����˶˿�
	 * @throws Exception 
	 */
	public Client(String host,Integer port,List<ChannelHandler> handlers) throws Exception {
		initClient(host,port,handlers);
	}

	/**
	 * ��ʼ������
	 * @param host �����������
	 * @param port ����˶˿�
	 * @param handlers netty��handler,��������ͽ�����
	 * @throws Exception 
	 */
	private void initClient(String host, Integer port, final List<ChannelHandler> handlers) throws Exception {
		
		ChannelFuture future = null;
		eventLoopGroup = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
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
				
                pipeline.addLast(new ReadTimeoutHandler(5000));
				pipeline.addLast(new WriteTimeoutHandler(5000));
				pipeline.addLast(new SimpleChannelInboundHandler<RSP>() {
					
					
					@Override
					public void channelActive(ChannelHandlerContext ctx) throws Exception {
						Client.this.ctx = ctx;
						super.channelActive(ctx);
					}

					@Override
					protected void channelRead0(ChannelHandlerContext ctx, RSP msg) throws Exception {
						queue.put(msg);
					}
				});
			}
		});
		future = bootstrap.connect(host, port).sync();
		if (future.isSuccess()) {
			channel = (SocketChannel) future.channel();
			isConnection = true;
			System.out.println("���ӳɹ�");
			
		} else {
			throw new Exception("����ʧ��");
		}
	}

	/**
	 * ����һ��packet����������Ӧ
	 * @param packet �����
	 * @return
	 * @throws Exception 
	 */
	public RSP execute(REQ packet) throws Exception {
		if(!isConnection) 
			throw new Exception("����δ����");
		RSP p;
		
		synchronized(queue) {
			channel.writeAndFlush(packet);
			p = queue.take();
		}	
		
		return p;
	}

	/**
	 * ���Ͷ������������packet�б�
	 * @param list ������б�
	 * @return
	 * @throws Exception 
	 */
	public List<RSP> execute(REQ... list) throws Exception {
		if(!isConnection) 
			throw new Exception("����δ����");
		
		List<RSP> resultList = new ArrayList<RSP>();
		
		for (REQ interverPacket : list) {
			synchronized(queue) {
				channel.writeAndFlush(interverPacket);
				RSP p = queue.take();
				resultList.add(p);
			}
		}
		return (List<RSP>) resultList;
	}

	/**
	 * ����һ������ɺ�ִ�лص�����
	 * @param packet �����
	 * @param callback �ص�����
	 * @throws Exception 
	 */
	public void execute(REQ packet, ClientCallback<RSP> callback) throws Exception {
		if(!isConnection) 
			throw new Exception("����δ����");
		
		RSP p;
		synchronized(queue) {
			channel.writeAndFlush(packet);
			p = queue.take();
		}	
		callback.callback(p);
	}

	/**
	 * ���Ͷ����,ÿ����ɺ�ִ�лص�����
	 * @param callback
	 * @param list
	 * @throws Exception
	 */
	public void execute(ClientCallback<RSP> callback, REQ... list) throws Exception {
		if(!isConnection) 
			throw new Exception("����δ����");
		
		for (REQ t : list) {
			RSP p;
			synchronized(queue) {
				channel.writeAndFlush(t);
				p = queue.take();
			}	
			callback.callback(p);
		}
		
	}
	
	/**
	 * �رյ�ǰ����
	 */
	public void close() {
		eventLoopGroup.shutdownGracefully();
		channel.close();
		ctx.close();
		isConnection = false;
	}
}
