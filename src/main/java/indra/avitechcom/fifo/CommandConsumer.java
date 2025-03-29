package indra.avitechcom.fifo;

import indra.avitechcom.command.Command;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;

@Slf4j
@RequiredArgsConstructor
public class CommandConsumer implements Runnable {

    private final BlockingQueue<Command> queue;
    private final String name;

    @Override
    public void run() {
        try {
            while (true) {
                Command command = queue.take();

                log.info("{} received command: {}", name, command);

                command.execute();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.info("{} interrupted", name);
        }
    }
}