package com.azure.javapt.workqueue;

import com.azure.javapt.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Customer2 {

    public static void main(String[] args) throws IOException {
        // 创建连接对象
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("work", true, false, false, null);
        //一次只接受一条未确认的消息
        channel.basicQos(1);
        // 消费消息
        // 参数一：消费那个队列的消息 队列名称
        // 参数二：开始消息的自动确认机制
        // 参数三：消费时的回调接口
        channel.basicConsume("work", false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("new Customer2 = " + new String(body));
                //手动确认消息
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
        //这里不要关闭通道，不然就会收不到消息
        //RabbitMQUtils.closeConnectionAndChanel(channel,connection);
    }
}
