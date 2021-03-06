package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.AddGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddGroupCommand object
 */
public class AddGroupCommandParser implements Parser<AddGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddGroupCommand
     * and returns an AddGroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public AddGroupCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String[] splitArg = args.trim().split("\\s+");

        if (splitArg.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGroupCommand.MESSAGE_USAGE));
        }

        String groupName = splitArg[0].toUpperCase();
        String houseName = splitArg[1];
        houseName = houseName.substring(0, 1).toUpperCase() + houseName.substring(1).toLowerCase();

        return new AddGroupCommand(groupName, houseName);
    };

}
