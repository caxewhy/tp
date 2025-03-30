package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.SearchPersonByNameCommand;
import seedu.address.logic.commands.exceptions.CommandException;

public class SearchPersonByNameCommandParserTest {

    private SearchPersonByNameCommandParser parser = new SearchPersonByNameCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        String expectedMessage = String.format(Messages.MESSAGE_MISSING_KEYWORD,
                SearchPersonByNameCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "     ", expectedMessage);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() throws CommandException {
        // no leading and trailing whitespaces
        List<String> expectedKeywords = Arrays.asList("Alice", "Bob");
        SearchPersonByNameCommand expectedSearchPersonByNameCommand = new SearchPersonByNameCommand(expectedKeywords);
        assertParseSuccess(parser, "Alice Bob", expectedSearchPersonByNameCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedSearchPersonByNameCommand);
    }
}
