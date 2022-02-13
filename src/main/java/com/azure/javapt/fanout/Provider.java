package com.azure.javapt.fanout;

import com.azure.javapt.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Provider {

    public static void main(String[] args) throws IOException {
        //获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        //将通道声明指定的交换机
        // 参数一：交换机名称
        // 参数二：交换机类型 fanout 广播类型
        channel.exchangeDeclare("logs", "fanout");
        //发送消息
        channel.basicPublish("logs", "", null, "fanout type message".getBytes(StandardCharsets.UTF_8));
        //释放资源
        RabbitMQUtils.closeConnectionAndChanel(channel, connection);
    }
}
