package demo.netty.demo2;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by madali on 2018/11/20 17:07
 */
@ChannelHandler.Sharable
public class HelloServerHandler extends SimpleChannelInboundHandler<String> {

    // 存储连接和用户信息的map，key为连接编号，value为用户名
    private Map<String, String> userMap = new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        String id = ctx.channel().id().asShortText();

        // 如果msg以name开头，则设置当前连接的用户名
        if (msg.startsWith("name")) {
            String name = "没有名字";
            if (msg.length() > 4) {
                name = msg.substring(4);
            }
            userMap.put(id, name);
            ctx.writeAndFlush("用户名被设置为:" + name + "\n");
            return;
        }

        ctx.writeAndFlush(userMap.get(id) + ":" + msg + "\n");
    }

}
