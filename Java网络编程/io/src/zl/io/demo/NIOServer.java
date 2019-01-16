package zl.io.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author zlCalma
 * @date 2019/1/15 17:04.
 */
public class NIOServer {

    public static void main(String[] args) throws IOException {

        //创建选择器
        Selector selector = Selector.open();

        //将通道注册到选择器上
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        ssChannel.configureBlocking(false);//定义通道为非阻塞
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);

        ServerSocket serverSocket = ssChannel.socket();
        InetSocketAddress address = new InetSocketAddress("127.0.0.1",8888);
        serverSocket.bind(address);

        while (true){

            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = keys.iterator();

            while(keyIterator.hasNext()){
                SelectionKey key = keyIterator.next();
                if(key.isAcceptable()){
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);

                    socketChannel.register(selector,SelectionKey.OP_READ);
                }else if(key.isReadable()){
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    System.out.println(readDataFromSocketChannel(socketChannel));
                    socketChannel.close();
                }

                keyIterator.remove();
            }
        }
    }

    private static String readDataFromSocketChannel(SocketChannel socketChannel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        StringBuilder data  = new StringBuilder();

        while(true){
            buffer.clear();
            int n = socketChannel.read(buffer);
            if(n==-1){
                break;
            }
            buffer.flip();
            int limit = buffer.limit();
            char[] dst = new char[limit];
            for(int i=0;i<limit;i++){
                dst[i] = (char) buffer.get(i);
            }
            data.append(dst);
            buffer.clear();
        }
        return data.toString();
    }
}
