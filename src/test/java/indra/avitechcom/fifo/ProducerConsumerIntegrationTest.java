package indra.avitechcom.fifo;

import indra.avitechcom.command.Command;
import indra.avitechcom.model.UserDTO;
import indra.avitechcom.repository.UserRepository;
import indra.avitechcom.service.CommandService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import static indra.avitechcom.Application.waitToTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class ProducerConsumerIntegrationTest {

    @Mock
    private CommandService service;

    @BeforeEach
    void before() {
        CommandService.setINSTANCE(service);
    }

    @AfterEach
    void after() {
        CommandService.setINSTANCE(null);
    }

    @Test
    public void test() throws Exception {
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

        verify(service).save(eq(new UserDTO(1, "a1", "Robert")));
        verify(service).printAll();
        verify(service).deleteAll();
        verify(service).deleteAll();
        verifyNoMoreInteractions(service);
    }

}
