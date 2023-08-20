package com.vmware.financial.open.banking.account

import com.vmware.financial.open.banking.account.service.redis.AccountDataService
import com.vmware.financial.open.banking.domain.account.BankAccount
import nyla.solutions.core.util.Text
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializer
import java.util.*

@Profile("redis")
@Configuration
@ComponentScan(basePackageClasses = [AccountDataService::class])
@EnableRedisRepositories
class RedisConfig {

    @Value("\${spring.application.name}")
    private lateinit var applicationName: String

    @Bean
    fun template(factory : LettuceConnectionFactory,
                 serializer : RedisSerializer<Object>
    ) : RedisTemplate<String,BankAccount>
    {
        var template = RedisTemplate<String,BankAccount>()

        template.setDefaultSerializer(serializer)
        template.connectionFactory = factory

        return template
    }

    @Bean
    fun redisSerializer(): RedisSerializer<*> {
        return GenericJackson2JsonRedisSerializer()
    }
    @Bean
    fun listener(redisTemplate: RedisTemplate<String?, String?>): ApplicationContextAware {
        return ApplicationContextAware { context: ApplicationContext? ->
            redisTemplate.opsForValue()[applicationName] = Text.formatDate(Calendar.getInstance().time)
        }
    }
}