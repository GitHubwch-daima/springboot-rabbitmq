package com.azure.javapt.helloworld;

import com.azure.javapt.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Customer {

    public static void main(String[] args) throws IOException {
        // 创建连接对象
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("hello", false, false, false, null);
        // 消费消息
        // 参数一：消费那个队列的消息 队列名称
        // 参数二：开始消息的自动确认机制
        // 参数三：消费时的回调接口
        channel.basicConsume("hello", true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("new String(body) = " + new String(body));
            }
        });
        //这里不要关闭通道，不然就会收不到消息
        //RabbitMQUtils.closeConnectionAndChanel(channel,connection);
    }
}
