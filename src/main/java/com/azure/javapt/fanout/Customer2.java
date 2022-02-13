package com.azure.javapt.fanout;

import com.azure.javapt.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Customer2 {

    public static void main(String[] args) throws IOException {
        // 创建连接对象
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        //通道绑定交换机
        channel.exchangeDeclare("logs", "fanout");
        //临时队列 queueName 队列名称
        String queueName = channel.queueDeclare().getQueue();
        //绑定交换机和队列
        channel.queueBind(queueName, "logs", "");
        //消费消息
        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("消费者2：" + new String(body));
            }
        });
        //这里不要关闭通道，不然就会收不到消息
        //RabbitMQUtils.closeConnectionAndChanel(channel,connection);
    }
}
