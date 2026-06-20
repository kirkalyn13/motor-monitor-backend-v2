package com.engrkirky.motormonitorv2.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * RabbitMQ configuration for queues, exchanges, and bindings.
 */
@Configuration
@PropertySource(value = "classpath:/application.properties")
public class RabbitMQConfig {
    // --- Main Queue constants ---
    public static final String METRICS_QUEUE_NAME = "motormonitorv2.metrics.queue";
    public static final String ALARMS_QUEUE_NAME  = "motormonitorv2.alarms.queue";
    public static final String EXCHANGE_NAME       = "motormonitorv2.exchange";
    public static final String METRICS_ROUTING_KEY = "motormonitorv2.metrics.routing-key";
    public static final String ALARMS_ROUTING_KEY  = "motormonitorv2.alarms.routing-key";

    // --- DLQ constants ---
    public static final String DLX_NAME                  = "motormonitorv2.dlx";
    public static final String METRICS_DLQ_NAME          = "motormonitorv2.metrics.dlq";
    public static final String ALARMS_DLQ_NAME           = "motormonitorv2.alarms.dlq";
    public static final String METRICS_DLQ_ROUTING_KEY   = "motormonitorv2.metrics.dead";
    public static final String ALARMS_DLQ_ROUTING_KEY    = "motormonitorv2.alarms.dead";

    // --- Main queues (updated to point at DLX) ---

    @Bean
    public Queue metricsQueue() {
        return QueueBuilder.durable(METRICS_QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", DLX_NAME)
                .withArgument("x-dead-letter-routing-key", METRICS_DLQ_ROUTING_KEY)
                .build();
    }

    @Bean
    public Queue alarmsQueue() {
        return QueueBuilder.durable(ALARMS_QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", DLX_NAME)
                .withArgument("x-dead-letter-routing-key", ALARMS_DLQ_ROUTING_KEY)
                .build();
    }

    // --- Dead letter exchange + queues ---

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DLX_NAME);
    }

    @Bean
    public Queue metricsDeadLetterQueue() {
        return QueueBuilder.durable(METRICS_DLQ_NAME).build();
    }

    @Bean
    public Queue alarmsDeadLetterQueue() {
        return QueueBuilder.durable(ALARMS_DLQ_NAME).build();
    }

    @Bean
    public Binding metricsDeadLetterBinding() {
        return BindingBuilder.bind(metricsDeadLetterQueue())
                .to(deadLetterExchange())
                .with(METRICS_DLQ_ROUTING_KEY);
    }

    @Bean
    public Binding alarmsDeadLetterBinding() {
        return BindingBuilder.bind(alarmsDeadLetterQueue())
                .to(deadLetterExchange())
                .with(ALARMS_DLQ_ROUTING_KEY);
    }

    // --- Existing exchange and bindings (unchanged) ---

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

