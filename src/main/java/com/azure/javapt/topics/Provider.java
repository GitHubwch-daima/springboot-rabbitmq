package com.azure.javapt.topics;

import com.azure.javapt.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Provider {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "topics";
        //声明交换机以及交换机类型 topic：动态路由
        channel.exchangeDeclare(exchangeName, "topic");
        //发布消息
        String routeKey = "user.save.test";
        channel.basicPublish(exchangeName, routeKey, null, ("动态路由，routeKey：" + routeKey).getBytes(StandardCharsets.UTF_8));
        //关闭资源
        RabbitMQUtils.closeConnectionAndChanel(channel, connection);

    }
}
