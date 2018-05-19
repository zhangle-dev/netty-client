package com.zl.codec.http;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpContent;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.DefaultLastHttpContent;

public class DefaultFullHttpDecoder extends ChannelInboundHandlerAdapter {

	private DefaultHttpResponse resp;
	private List<DefaultHttpContent> contents = new ArrayList<DefaultHttpContent>();
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		if(msg instanceof DefaultHttpResponse) {
			//如果是response
			resp = (DefaultHttpResponse) msg;
		}else if(msg instanceof DefaultLastHttpContent) {
			//如果是LastHttpContent
			contents.add((DefaultHttpContent) msg);
			
			ByteBuf contentBuf = Unpooled.buffer();
			for (DefaultHttpContent content : contents) {
				ByteBuf buf = content.content();
				contentBuf.writeBytes(buf);
			}
			DefaultFullHttpResponse response = new DefaultFullHttpResponse(resp.protocolVersion(), resp.status(),contentBuf);
			super.channelRead(ctx, response);
		}else {
			//如果是HttpContent
			contents.add((DefaultHttpContent) msg);
		}
	}
}
