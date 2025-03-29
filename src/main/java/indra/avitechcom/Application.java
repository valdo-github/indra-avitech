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

        CommandProducer producer = new CommandProducer(queue, "Producer");
        CommandConsumer consumer = new CommandConsumer(queue, "Consumer");

        Thread thread1 = new Thread(producer);
        Thread thread2 = new Thread(consumer);
        thread1.start();
        thread2.start();

        log.info("\n\nHere we go:\n");

        producer.add(1, "a1", "Robert");
        producer.add(2, "a2", "Martin");
        producer.printAll();
        producer.deleteAll();
        producer.printAll();

        waitToTest(queue, producer);
        log.info("\n\nDone\n");



        thread1.interrupt();
        thread2.interrupt();
        userRepository.closeSessionFactory();
    }

    public static void waitToTest(BlockingDeque<Command> queue, CommandProducer producer) throws InterruptedException {
        int maxWaitInMillis = 1000;
        while(!queue.isEmpty() || !producer.getRequestCommands().isEmpty() || maxWaitInMillis-- > 0) {
            Thread.sleep(1);
        }
    }
}
