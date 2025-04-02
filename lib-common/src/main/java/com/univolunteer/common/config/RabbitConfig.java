package com.univolunteer.common.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@ConditionalOnClass(DispatcherServlet.class)
public class RabbitConfig {

    // 创建队列
    @Bean
    public Queue notificationQueue() {
        return new Queue("notificationQueue", true);  // 队列名称：notificationQueue，true 表示持久化
    }

    // 创建交换机
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("notificationExchange");
    }

    // 将队列和交换机绑定
    @Bean
    public Binding binding(Queue notificationQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(notificationQueue)
                             .to(topicExchange)
                             .with("notification.*");  // 路由键
    }
}
