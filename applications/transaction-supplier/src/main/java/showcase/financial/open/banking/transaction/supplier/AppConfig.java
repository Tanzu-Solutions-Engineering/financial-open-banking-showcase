package showcase.financial.open.banking.transaction.supplier;

import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.patterns.creational.Creator;
import nyla.solutions.core.patterns.creational.generator.FullNameCreator;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import nyla.solutions.core.patterns.creational.generator.PickRandomTextCreator;
import nyla.solutions.core.util.Digits;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.function.context.config.JsonMessageConverter;
import org.springframework.cloud.function.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import showcase.financial.open.banking.transactions.Transaction;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@Configuration
@Slf4j
public class AppConfig {


    private AtomicInteger count  = new AtomicInteger(0);

    @Value("${transaction.supplier.max.count}")
    private int maxCount;

    @Bean
    MessageConverter messageConverter(JsonMapper jsonMapper)
    {
        return new JsonMessageConverter(jsonMapper);
    }

    @Bean
    Digits digits()
    {
        return new Digits();
    }

    @Bean
    Creator<Transaction> creator(Digits digits)
    {
        short minYear = 2024;
        short maxYear = 2035;

        short minMonth = 1;
        short maxMonth = 12;
        return new JavaBeanGeneratorCreator(Transaction.class)
                .generateNestedAll()
                .randomizeAll()
                .creatorForProperty("name_on_card", new FullNameCreator())
                .creatorForProperty("currency", ()-> "US")
                .creatorForProperty("expiry_year",  () -> digits.generateShort(minYear,maxYear))
                .creatorForProperty("expiry_month",  () -> digits.generateShort(minMonth,maxMonth))
                .creatorForProperty("brand",  PickRandomTextCreator.options("Amex",
                        "Discover","Wells Fargo","Citigroup","Chase","Barclays",
                        "MasterCard","PNC Bank","Visa","Navy Federal Credit Union"))
                .creatorForProperty("card_type",  PickRandomTextCreator.options("Amex",
                        "Rewards","Cashback","Travel","Business","Corporate"))
                .creatorForProperty("description",PickRandomTextCreator.options("issued by a financial company",
                        " minimum payment each period","Summary of account activity","basic terms that you may come across when using your credit card."));
    }

    @Bean
    Supplier<Transaction> bankTransactions(Creator<Transaction> creator){

        return () -> {

            if(count.get() >= maxCount)
                return null;

            var transaction = creator.create();
            log.info("Count: {}, Created transaction: {}",count.addAndGet(1),transaction);

            return transaction;
        };
    }
}
