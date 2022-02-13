package com.azure.javapt.routing;

import com.azure.javapt.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Provider {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        //指定交换机的名称
        String exchangeName = "logs_direct";
        //通过通道声明交换机
        // 参数一：交换机名称
        // 参数二：direct 路由模式
        channel.exchangeDeclare(exchangeName, "direct");
        //发送消息
        String routingKey = "error";
        channel.basicPublish(exchangeName, routingKey, null,
                ("这是direct模型基于route key：" + routingKey + "，发送的消息！").getBytes(StandardCharsets.UTF_8));
        //关闭资源
        RabbitMQUtils.closeConnectionAndChanel(channel, connection);
    }
}
