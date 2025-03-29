package indra.avitechcom.command.impl;

import indra.avitechcom.command.Command;
import indra.avitechcom.model.UserBO;
import indra.avitechcom.repository.UserRepository;
import indra.avitechcom.service.CommandService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class CommandPrintAll implements Command {

    @Override
    public void execute() {

        CommandService.getInstance().printAll();
    }
}
