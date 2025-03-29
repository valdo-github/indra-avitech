package indra.avitechcom.fifo;

import indra.avitechcom.command.Command;
import indra.avitechcom.repository.UserRepository;
import indra.avitechcom.service.CommandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import static indra.avitechcom.Application.waitToTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class ProducerConsumerIntegrationTest {

    private CommandService service = Mockito.mock(CommandService.class);

    @BeforeEach
    void before() {
        CommandService.setINSTANCE(service);
    }

    @Test
    public void test() throws Exception {
        UserRepository userRepository = UserRepository.getInstance();

        BlockingDeque<Command> queue = new LinkedBlockingDeque<>();

        CommandProducer producer = new CommandProducer(queue, "Producer");
        CommandConsumer consumer = new CommandConsumer(queue, "Consumer");
        Thread thread1 = new Thread(producer);
        Thread thread2 = new Thread(consumer);
        thread1.start();
        thread2.start();

        producer.add(1, "a1", "Robert");
        producer.printAll();
        producer.deleteAll();

        waitToTest(queue, producer);

        thread1.interrupt();
        thread2.interrupt();
        userRepository.closeSessionFactory();

        verify(service).save(any());
        verify(service).printAll();
        verify(service).deleteAll();
        verify(service).deleteAll();
        verifyNoMoreInteractions(service);
    }

}
