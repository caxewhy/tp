package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_ARGUMENTS_EMPTY;
import static seedu.address.logic.Messages.MESSAGE_EXPECTED_TWO_INDICES;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_OR_LISTING_DISPLAYED_INDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignListingCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code AssignListingCommandParser} object.
 */
public class AssignListingCommandParser implements Parser<AssignListingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AssignListingCommand
     * and returns an AssignListingCommand object for execution.
     *
     * @param args arguments to be parsed.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignListingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args);
        Index personIndex;
        Index listingIndex;

        checkCommandFormat(argMultimap, args);
        List<Index> multipleIndices;
        try {
            multipleIndices = ParserUtil.parseMultipleIndices(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_PERSON_OR_LISTING_DISPLAYED_INDEX,
                    AssignListingCommand.MESSAGE_USAGE),
                    pe);
        }

        try {
            if (multipleIndices.size() != 2) {
                throw new ParseException("Expected 2 indices");
            }
            personIndex = multipleIndices.get(0);
            listingIndex = multipleIndices.get(1);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_EXPECTED_TWO_INDICES,
                    AssignListingCommand.MESSAGE_USAGE), pe);
        }

        return new AssignListingCommand(personIndex, listingIndex);
    }

    private static void checkCommandFormat(ArgumentMultimap argMultimap, String args) throws ParseException {
        String preamble = argMultimap.getPreamble();

        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_ARGUMENTS_EMPTY,
                    AssignListingCommand.MESSAGE_USAGE));
        }

        if (preamble.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_EXPECTED_TWO_INDICES,
                    AssignListingCommand.MESSAGE_USAGE));
        }

    }
}
