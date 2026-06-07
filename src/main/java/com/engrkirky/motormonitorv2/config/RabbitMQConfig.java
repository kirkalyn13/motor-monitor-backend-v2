package com.engrkirky.motormonitorv2.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:/application.properties")
public class RabbitMQConfig {

    public static final String METRICS_QUEUE_NAME = "motormonitorv2.metrics.queue";
    public static final String ALARMS_QUEUE_NAME = "motormonitorv2.alarms.queue";
    public static final String EXCHANGE_NAME = "motormonitorv2.exchange";
    public static final String METRICS_ROUTING_KEY = "motormonitorv2.metrics.routing-key";
    public static final String ALARMS_ROUTING_KEY = "motormonitorv2.alarms.routing-key";

    @Bean
    public Queue metricsQueue() {
        return new Queue(METRICS_QUEUE_NAME, true);
    }

    @Bean
    public Queue alarmsQueue() {
        return new Queue(ALARMS_QUEUE_NAME, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding metricsBinding(Queue metricsQueue, TopicExchange exchange) {
        return BindingBuilder.bind(metricsQueue).to(exchange).with(METRICS_ROUTING_KEY);
    }

    @Bean
    public Binding alarmsBinding(Queue alarmsQueue, TopicExchange exchange) {
        return BindingBuilder.bind(alarmsQueue).to(exchange).with(ALARMS_ROUTING_KEY);
    }
}

