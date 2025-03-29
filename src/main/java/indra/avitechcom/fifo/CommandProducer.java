package indra.avitechcom.fifo;

import indra.avitechcom.command.Command;
import indra.avitechcom.command.impl.CommandAdd;
import indra.avitechcom.command.impl.CommandDeleteAll;
import indra.avitechcom.command.impl.CommandPrintAll;
import indra.avitechcom.model.UserDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

@Slf4j
@RequiredArgsConstructor
public class CommandProducer implements Runnable {

    private static final int SLEEP_IN_MILLIS_DEFAULT = 10;

    private final BlockingQueue<Command> queue;
    private final String name;
    private final Integer sleepInMillis;

    @Getter
    private final ArrayList<Command> requestCommands = new ArrayList<>();

    public CommandProducer(BlockingQueue<Command> queue, String name) {
        this(queue, name, SLEEP_IN_MILLIS_DEFAULT);
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (!requestCommands.isEmpty()) {
                    Command command = requestCommands.remove(0);
                    log.info("{} sending command: {}", name, command);
                    queue.put(command);
                }
                Thread.sleep(sleepInMillis);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.info("{} interrupted", name);
        }

    }

    public void add(int id, String guid, String name) {

        UserDTO user = UserDTO.builder()
                .id(id)
                .guid(guid)
                .name(name)
                .build();
        CommandAdd commandAdd = new CommandAdd(user);

        log.debug("Initializing ADD: {}", commandAdd);
        requestCommands.add(commandAdd);
    }
    public void printAll() {
        log.debug("Initializing PRINT_ALL command");
        requestCommands.add(new CommandPrintAll());
    }
    public void deleteAll() {
        log.debug("Initializing DELETE_ALL command");
        requestCommands.add(new CommandDeleteAll());
    }

}