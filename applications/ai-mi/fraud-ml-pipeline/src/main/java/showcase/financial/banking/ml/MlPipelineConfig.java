package showcase.financial.banking.ml;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Supplier;

@Configuration
public class MlPipelineConfig {

    @Bean
    Queue<byte[]> queue(){
        return new ConcurrentLinkedQueue<>();
    }

    @Bean
    Supplier<byte[]> modelSupplier(Queue<byte[]> queue)
    {
        return new Supplier<byte[]>() {
            @Override
            public byte[] get() {
                return queue.poll();
            }
        };
    }
}
