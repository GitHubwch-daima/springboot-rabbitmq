package com.azure.javapt.topics;

import com.azure.javapt.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Customer1 {

    public static void main(String[] args) throws IOException {
        // 创建连接对象
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明一个交换机名
        String exchangeName = "topics";
        //通道声明交换机以及交换机类型
        channel.exchangeDeclare(exchangeName, "topic");
        //创建一个临时队列
        String queueName = channel.queueDeclare().getQueue();
        //基于route key绑定队列和交换机 *代表后边可以跟一个
        channel.queueBind(queueName, exchangeName, "user.*");
        // 消费消息
        // 参数一：消费那个队列的消息 队列名称
        // 参数二：开始消息的自动确认机制
        // 参数三：消费时的回调接口
        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("Customer1 = " + new String(body));
            }
        });
        //这里不要关闭通道，不然就会收不到消息
        //RabbitMQUtils.closeConnectionAndChanel(channel,connection);
    }
}
