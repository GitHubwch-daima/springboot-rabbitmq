package com.azure.javapt.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Properties;

public class RabbitMQUtils {

    private static ConnectionFactory connectionFactory;
    private static Properties properties;

    static {
        //重量级资源 类加载执行只执行一次
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("101.200.190.58");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/test");
        connectionFactory.setUsername("test");
        connectionFactory.setPassword("test");
    }

    //定义提供连接对象的方法
    public static Connection getConnection() {
        try {
            return connectionFactory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //关闭通道和连接工具的方法
    public static void closeConnectionAndChanel(Channel channel, Connection connection) {
        try {
            if (channel != null) channel.close();
            if (connection != null) connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
