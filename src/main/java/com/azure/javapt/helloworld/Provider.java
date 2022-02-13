package com.azure.javapt.helloworld;

import com.azure.javapt.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

//生产者
public class Provider {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("hello", false, false, false, null);
        channel.basicPublish("", "hello", null, "hello,rabbitMQ".getBytes(StandardCharsets.UTF_8));
        RabbitMQUtils.closeConnectionAndChanel(channel, connection);
    }
}
