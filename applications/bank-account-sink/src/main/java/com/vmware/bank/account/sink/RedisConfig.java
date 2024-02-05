package com.vmware.bank.account.sink;


import com.vmware.financial.open.banking.account.repository.AccountRepository;
import com.vmware.financial.open.banking.account.redis.service.AccountDataService;
import com.vmware.financial.open.banking.domain.account.BankAccount;
import nyla.solutions.core.util.Text;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.Calendar;
import java.util.List;

@Configuration
@Profile("redis")
@ComponentScan(basePackageClasses = AccountDataService.class)
@EnableRedisRepositories(basePackageClasses = AccountRepository.class)
public class RedisConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${spring.data.redis.cluster.nodes}")
    private List<String> nodes;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisClusterConfiguration config = new RedisClusterConfiguration(nodes);
        return new JedisConnectionFactory(config);
    }




    @Bean
    RedisTemplate<String, BankAccount> template(RedisConnectionFactory factory,
                                                RedisSerializer<Object> serializer) {
        var template = new RedisTemplate<String, BankAccount>();

        template.setDefaultSerializer(serializer);
        template.setConnectionFactory(factory);

        return template;
    }

    @Bean
    RedisSerializer<?> redisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean
    ApplicationContextAware listener(RedisTemplate<String, String> redisTemplate) {
        return context ->
                redisTemplate.opsForValue().set(applicationName,
                        Text.formatDate(Calendar.getInstance().getTime()));
    }
}
