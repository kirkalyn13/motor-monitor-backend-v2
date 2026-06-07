package com.engrkirky.motormonitorv2.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * RabbitMQ configuration for queues, exchanges, and bindings.
 */
@Configuration
@PropertySource(value = "classpath:/application.properties")
public class RabbitMQConfig {

    public static final String METRICS_QUEUE_NAME = "motormonitorv2.metrics.queue";
    public static final String ALARMS_QUEUE_NAME = "motormonitorv2.alarms.queue";
    public static final String EXCHANGE_NAME = "motormonitorv2.exchange";
    public static final String METRICS_ROUTING_KEY = "motormonitorv2.metrics.routing-key";
    public static final String ALARMS_ROUTING_KEY = "motormonitorv2.alarms.routing-key";

    /**
     * Creates the metrics queue.
     *
     * @return metrics queue
     */
    @Bean
    public Queue metricsQueue() {
        return new Queue(METRICS_QUEUE_NAME, true);
    }

    /**
     * Creates the alarms queue.
     *
     * @return alarms queue
     */
    @Bean
    public Queue alarmsQueue() {
        return new Queue(ALARMS_QUEUE_NAME, true);
    }

    /**
     * Creates the topic exchange.
     *
     * @return topic exchange
     */
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    /**
     * Binds the metrics queue to the exchange.
     *
     * @param metricsQueue metrics queue
     * @param exchange topic exchange
     * @return queue binding
     */
    @Bean
    public Binding metricsBinding(Queue metricsQueue, TopicExchange exchange) {
        return BindingBuilder.bind(metricsQueue).to(exchange).with(METRICS_ROUTING_KEY);
    }

    /**
     * Binds the alarms queue to the exchange.
     *
     * @param alarmsQueue alarms queue
     * @param exchange topic exchange
     * @return queue binding
     */
    @Bean
    public Binding alarmsBinding(Queue alarmsQueue, TopicExchange exchange) {
        return BindingBuilder.bind(alarmsQueue).to(exchange).with(ALARMS_ROUTING_KEY);
    }
}

