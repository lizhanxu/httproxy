import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.nio.ByteBuffer;
import java.util.Iterator;

import static io.netty.util.CharsetUtil.UTF_8;

/**
 * @ClassName HttpMessageHandler
 * @Description
 * @Date 2019/12/17
 * @Created by lizhanxu
 */
public class HttpMessageHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        System.out.println(msg.method());
        System.out.println(msg.protocolVersion());
        System.out.println(msg.uri());
        if (!msg.content().toString(UTF_8).equals("")){
            System.out.println(msg.content().toString(UTF_8));
        }
        HttpHeaders headers = msg.headers();
        Iterator iterator= headers.entries().listIterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

//        HttpForward.forward();

        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.NOT_FOUND);
        if (!msg.decoderResult().isSuccess()) {
            ctx.writeAndFlush(response);
        }else {
            response.setStatus(HttpResponseStatus.OK);
            StringBuffer buffer = new StringBuffer();
            buffer.append("Hello World");
            ByteBuf bf = Unpooled.copiedBuffer(buffer, UTF_8);
            response.content().writeBytes(bf);
            ctx.writeAndFlush(response);
        }
    }
}
