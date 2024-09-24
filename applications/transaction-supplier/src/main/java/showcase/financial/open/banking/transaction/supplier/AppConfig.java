package showcase.financial.open.banking.transaction.supplier;

import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.patterns.creational.Creator;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.springframework.cloud.function.context.config.JsonMessageConverter;
import org.springframework.cloud.function.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import showcase.financial.open.banking.transactions.Transaction;

import java.util.function.Supplier;

@Configuration
@Slf4j
public class AppConfig {

    @Bean
    MessageConverter messageConverter(JsonMapper jsonMapper)
    {
        return new JsonMessageConverter(jsonMapper);
    }

    @Bean
    Creator<Transaction> creator()
    {
        return JavaBeanGeneratorCreator.of(Transaction.class);
    }

    @Bean
    Supplier<Transaction> bankTransactions(Creator<Transaction> creator){

        return () -> {
            var transaction = creator.create();
            log.info("Created transaction: {}",transaction);
            return transaction;
        };
    }
}
