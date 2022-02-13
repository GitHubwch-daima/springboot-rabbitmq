package com.azure;

import com.azure.javapt.utils.RabbitMQUtils;
import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@SpringBootTest
class SpringbootRabbitmqBlrApplicationTests {

    //生产消息
    @Test
    void testSendMessage() throws IOException, TimeoutException {
        //创建连接MQ的连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置连接rabbitmq主机
        connectionFactory.setHost("101.200.190.58");
        //设置端口
        connectionFactory.setPort(5672);
        //设置连接主机名
        connectionFactory.setVirtualHost("/test");
        //设置用户名密码
        connectionFactory.setUsername("test");
        connectionFactory.setPassword("test");
        //获取链接通道
        Connection connection = connectionFactory.newConnection();
        //获取连接中通道
        Channel channel = connection.createChannel();
        //通道绑定对应的消息队列
        //参数1、队列名称 如果队列不存在就直接创建
        //参数2、用来定义队列特性是否需要持久化 ture开启
        //参数3、是否独占队列 exclusive
        //参数4、是否在消费完成之后自动删除队列 autoDelete ture开启
        channel.queueDeclare("hello", false, false, false, null);
        //发布消息
        //参数1、交换机名称 参数2、队列名称 参数3、传递消息额外设置(MessageProperties.PERSISTENT_TEXT_PLAIN开启持久化) 参数4、消息的具体内容
        channel.basicPublish("", "hello", null, "hello,rabbitMQ".getBytes(StandardCharsets.UTF_8));
        channel.close();
        connection.close();
    }

    //消费者
    @Test
    public void testCustomer() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("101.200.190.58");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/test");
        connectionFactory.setUsername("test");
        connectionFactory.setPassword("test");

        // 创建连接对象
        Connection connection = connectionFactory.newConnection();
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
        //channel.close();
        //connection.close();
    }

    @Test
    public void test() throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("hello", false, false, false, null);
        channel.basicPublish("", "hello", null, "hello,rabbitMQ".getBytes(StandardCharsets.UTF_8));
        RabbitMQUtils.closeConnectionAndChanel(channel, connection);
    }

}

