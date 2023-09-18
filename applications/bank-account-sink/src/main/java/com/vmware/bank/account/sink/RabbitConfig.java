package com.vmware.bank.account.sink;

import com.rabbitmq.stream.OffsetSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.config.ListenerContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.rabbit.stream.listener.StreamListenerContainer;

@Configuration
@Slf4j
public class RabbitConfig {


    @Value("${spring.rabbitmq.username:guest}")
    private String username;

    @Value("${spring.rabbitmq.password:guest}")
    private String password;

    @Value("${spring.rabbitmq.host:localhost}")
    private String hostname;

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    MessageConverter convert()
    {
        return new Jackson2JsonMessageConverter();
    }

    @ConditionalOnProperty(name = "rabbitmq.streaming.replay",havingValue = "true")
    @Bean
    ListenerContainerCustomizer<MessageListenerContainer> customizer() {
        return (cont, dest, group) -> {
            StreamListenerContainer container = (StreamListenerContainer) cont;
            container.setConsumerCustomizer((name, builder) -> {
                builder.noTrackingStrategy();
                builder.offset(OffsetSpecification.offset(0));
                builder.subscriptionListener(context ->{

                    context.offsetSpecification(OffsetSpecification.offset(0));
                    log.info("***************REPLAY********************");
                });
            });
        };
    }

}
