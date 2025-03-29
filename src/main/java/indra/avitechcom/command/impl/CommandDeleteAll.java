package indra.avitechcom.command.impl;

import indra.avitechcom.command.Command;
import indra.avitechcom.service.CommandService;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class CommandDeleteAll implements Command {

    @Override
    public void execute() {

        CommandService
                .getInstance()
                .deleteAll();
    }
}
