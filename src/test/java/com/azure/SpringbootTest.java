package com.azure;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class SpringbootTest {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testHello1() {
        rabbitTemplate.convertAndSend("hello", "hello,world!");
    }

    @Test
    public void testWork() {

        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work", "work：" + (i + 1));
        }
    }

    @Test
    public void testFanout1() {
        rabbitTemplate.convertAndSend("logsTest", "", "fanout模型");
    }

    @Test
    public void testRoute1() {
        rabbitTemplate.convertAndSend("route", "error", "testRoute");
    }


    @Test
    public void testTopic1() {
        rabbitTemplate.convertAndSend("topics", "user.save", "topics");
    }
}
