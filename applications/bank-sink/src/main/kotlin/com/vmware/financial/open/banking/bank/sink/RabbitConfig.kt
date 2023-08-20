package com.vmware.financial.open.banking.account

import com.rabbitmq.stream.ConsumerBuilder
import com.rabbitmq.stream.OffsetSpecification
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean
import org.springframework.amqp.rabbit.connection.ThreadChannelConnectionFactory
import org.springframework.amqp.rabbit.listener.MessageListenerContainer
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cloud.stream.config.ListenerContainerCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.rabbit.stream.listener.StreamListenerContainer


/**
 * RabbitMQ Configuration
 * @author Gregory Green
 */
@Configuration
class RabbitConfig {

    @Value("\${spring.rabbitmq.username:guest}")
    private var username: String = "guest";

    @Value("\${spring.rabbitmq.password:guest}")
    private var password:  String = "guest";

    @Value("\${spring.rabbitmq.host:localhost}")
    private var hostname: String = "localhost";

    @Value("\${spring.application.name}")
    private var applicationName: String? = null


    @Bean
    fun connectionFactory( ) : ConnectionFactory
    {
        var factory = RabbitConnectionFactoryBean();
        factory.setHost(hostname);
        factory.setUsername(username);
        factory.setPassword(password);

        var tcf = ThreadChannelConnectionFactory(factory.rabbitConnectionFactory);
        tcf.setConnectionNameStrategy(ConnectionNameStrategy { applicationName!! })
        return tcf
    }

    @Bean
    fun convert() : MessageConverter
    {
        return Jackson2JsonMessageConverter();
    }


    @Bean
    @Profile("stream")
    @ConditionalOnProperty(name = ["rabbitmq.streaming.replay"],havingValue = "true")
    fun customizer(): ListenerContainerCustomizer<MessageListenerContainer>? {
        return ListenerContainerCustomizer { cont: MessageListenerContainer, dest: String?, group: String? ->
            val container = cont as StreamListenerContainer
            container.setConsumerCustomizer { name: String?, builder: ConsumerBuilder ->
                builder.offset(
                    OffsetSpecification.first()
                )
            }
        }
    }
}