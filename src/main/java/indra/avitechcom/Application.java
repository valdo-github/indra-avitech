package indra.avitechcom;

import indra.avitechcom.command.Command;
import indra.avitechcom.fifo.CommandConsumer;
import indra.avitechcom.fifo.CommandProducer;
import indra.avitechcom.repository.UserRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Slf4j
public class Application {

    @SneakyThrows
    public static void main(String[] args) {
        UserRepository userRepository = UserRepository.getInstance();

        BlockingDeque<Command> queue = new LinkedBlockingDeque<>();

        CommandProducer producer1 = new CommandProducer(queue, "Producer1");
        CommandProducer producer2 = new CommandProducer(queue, "Producer2");
        CommandConsumer consumer1 = new CommandConsumer(queue, "Consumer1");
        CommandConsumer consumer2 = new CommandConsumer(queue, "Consumer2");

        Thread thread1 = new Thread(producer1);
        Thread thread2 = new Thread(producer2);
        Thread thread3 = new Thread(consumer1);
        Thread thread4 = new Thread(consumer2);
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        log.info("\n\nHere we go:\n");

        producer1.add(1, "a1", "Robert");
        producer2.add(2, "a2", "Martin");
        producer1.printAll();
        producer2.deleteAll();
        producer2.printAll();

        waitToTest(queue, producer1);
        waitToTest(queue, producer2);
        log.info("\n\nDone\n");



        thread1.interrupt();
        thread2.interrupt();
        thread3.interrupt();
        thread4.interrupt();
        userRepository.closeSessionFactory();
    }

    public static void waitToTest(BlockingDeque<Command> queue, CommandProducer producer) throws InterruptedException {
        int maxWaitInMillis = 1000;
        while(!queue.isEmpty() || !producer.getRequestCommands().isEmpty() || maxWaitInMillis-- > 0) {
            Thread.sleep(1);
        }
    }
}
