package demo.netty.demo1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * 1.启动server类
 * 2.cmd输入 telnet localhost 8888
 * 输入 字符串，会显示server对字符串的响应
 * <p>
 * Created by madali on 2018/11/20 16:46
 */
public class Server {

    private static final int max_length = 2048;
    private static final int port = 8888;

    public static void main(String[] args) throws InterruptedException {
        // 创建事件循环组
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 指定事件循环组
            bootstrap.group(group)
                    // 指定所使用的nio传输channel
                    .channel(NioServerSocketChannel.class)
                    // 指定本地监听的地址
                    .localAddress(new InetSocketAddress(port))
                    // 添加一个handler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(
                                    new StringEncoder(CharsetUtil.UTF_8),
                                    new LineBasedFrameDecoder(max_length),
                                    new StringDecoder(CharsetUtil.UTF_8),
                                    new HelloServerHandler()
                            );
                        }
                    });
            // 异步的绑定服务器，调用sync()方法来执行同步，直到绑定完成
            ChannelFuture future = bootstrap.bind().sync();
            // 获取该channel的CloseFuture，并且阻塞当前线程直到它完成
            future.channel().closeFuture().sync();
        } finally {
            // 关闭事件循环组
            group.shutdownGracefully().sync();
        }

    }

}
