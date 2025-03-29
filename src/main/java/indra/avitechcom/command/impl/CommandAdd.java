package indra.avitechcom.command.impl;

import indra.avitechcom.command.Command;
import indra.avitechcom.model.UserBO;
import indra.avitechcom.service.CommandService;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class CommandAdd implements Command {

    private final UserBO user;

    @Override
    public void execute() {
        CommandService.getInstance().save(user);
    }
}
