package com.vmware.bank.account.sink;

import com.rabbitmq.stream.Environment;
import com.rabbitmq.stream.OffsetSpecification;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.amqp.rabbit.connection.ThreadChannelConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.rabbit.stream.config.StreamRabbitListenerContainerFactory;
import org.springframework.rabbit.stream.listener.StreamListenerContainer;

@Configuration
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
    ConnectionFactory connectionFactory( )
    {
        var factory = new RabbitConnectionFactoryBean();
        factory.setHost(hostname);
        factory.setUsername(username);
        factory.setPassword(password);

        var tcf = new ThreadChannelConnectionFactory(factory.getRabbitConnectionFactory());
        tcf.setConnectionNameStrategy( f -> { return applicationName;});
        return tcf;
    }

    @Bean
    MessageConverter convert()
    {
        return new Jackson2JsonMessageConverter();
    }

    @ConditionalOnProperty(name = "rabbitmq.streaming.replay",havingValue = "true")
    @Bean
    RabbitListenerContainerFactory<StreamListenerContainer> containerFactory(Environment env) {
        StreamRabbitListenerContainerFactory factory = new StreamRabbitListenerContainerFactory(env);
        factory.setNativeListener(true);
        factory.setConsumerCustomizer((id, builder) -> {
            builder
//                    name(applicationName)
                    .offset(OffsetSpecification.first())
                    .autoTrackingStrategy();
        });
        return factory;
    }


}
