package com.azure.javapt.workqueue;

import com.azure.javapt.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Provider {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
        //通过通道声明队列
        channel.queueDeclare("work", true, false, false, null);
        for (int i = 0; i < 1000; i++) {
            //生产消息
            channel.basicPublish("", "work", null, (i + "hello work queue").getBytes(StandardCharsets.UTF_8));
        }
        //关闭资源
        RabbitMQUtils.closeConnectionAndChanel(channel, connection);
    }
}
