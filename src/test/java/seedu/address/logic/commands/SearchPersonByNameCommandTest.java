package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class SearchPersonByNameCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() throws CommandException {
        List<String> firstPredicate = Arrays.asList("Alice");
        List<String> secondPredicate = Arrays.asList("Bob");

        SearchPersonByNameCommand findFirstCommand = new SearchPersonByNameCommand(firstPredicate);
        SearchPersonByNameCommand findSecondCommand = new SearchPersonByNameCommand(secondPredicate);

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        SearchPersonByNameCommand findFirstCommandCopy = new SearchPersonByNameCommand(firstPredicate);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(findFirstCommand, 1);

        // null -> returns false
        assertNotEquals(findFirstCommand, null);

        // different person -> returns false
        assertNotEquals(findFirstCommand, findSecondCommand);
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() throws CommandException {
        List<String> keywords = Arrays.asList(" "); // Invalid keyword

        Exception exception = assertThrows(CommandException.class, () -> new SearchPersonByNameCommand(keywords));

        String expectedMessage =
                String.format("ERROR: Invalid keyword ' '. \n%s", SearchPersonByNameCommand.MESSAGE_USAGE);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() throws CommandException {
        String expectedMessage = "1 persons found matching the keywords.";
        List<String> keywords = Arrays.asList("Alice", "Bob");

        SearchPersonByNameCommand command = new SearchPersonByNameCommand(keywords);
        expectedModel.updateFilteredPersonList(person -> keywords.contains(person.getName().fullName));

        assertEquals(new CommandResult(expectedMessage), command.execute(model));
    }
}
