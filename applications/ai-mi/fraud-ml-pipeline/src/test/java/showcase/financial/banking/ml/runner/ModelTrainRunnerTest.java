package showcase.financial.banking.ml.runner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.ApplicationArguments;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class ModelTrainRunnerTest {

    @Mock
    private ApplicationArguments args;

    @Mock
    private Queue<byte[]> queue;

    private ModelTrainRunner subject;

    @BeforeEach
    void setUp() {
        subject = new ModelTrainRunner(queue);
    }

    @Test
    void run() throws Exception {
        subject.run(args);

        verify(queue).add(any());
    }
}