package indra.avitechcom.command.impl;

import indra.avitechcom.command.Command;
import indra.avitechcom.model.UserDTO;
import indra.avitechcom.service.CommandService;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class CommandAdd implements Command {

    private final UserDTO user;

    @Override
    public void execute() {
        CommandService
                .getInstance()
                .save(user);
    }
}
