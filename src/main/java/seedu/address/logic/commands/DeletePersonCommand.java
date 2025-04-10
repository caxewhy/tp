package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CommandUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.Listing;
import seedu.address.model.person.Person;
import seedu.address.model.person.PropertyPreference;
import seedu.address.model.tag.Tag;

/**
 * Deletes a {@code Person} from the address book.
 * The {@code Person} is identified using it's displayed index.
 */
public class DeletePersonCommand extends Command {

    public static final String COMMAND_WORD = "deletePerson";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the specified person from the matchEstate."
            + "\nParameters: "
            + "PERSON_INDEX (must be a positive integer)"
            + "\nExample: "
            + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final Index targetPersonIndex;

    /**
     * Creates a {@code DeletePersonCommand} to delete the specified {@code Person}.
     *
     * @param targetPersonIndex The index of the person in the filtered person list to be deleted.
     */
    public DeletePersonCommand(Index targetPersonIndex) {
        requireNonNull(targetPersonIndex);
        this.targetPersonIndex = targetPersonIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToDelete = CommandUtil.getValidatedPerson(model, targetPersonIndex, MESSAGE_USAGE);

        removeListingOwnership(personToDelete, model);
        removePersonPropertyPreferenceFromTags(personToDelete, model);

        model.deletePerson(personToDelete);
        model.resetAllLists();

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeletePersonCommand)) {
            return false;
        }

        DeletePersonCommand otherDeletePersonCommand = (DeletePersonCommand) other;
        return targetPersonIndex.equals(otherDeletePersonCommand.targetPersonIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetPersonIndex", targetPersonIndex)
                .toString();
    }

    private void removeListingOwnership(Person personToDelete, Model model) {
        List<Listing> listings = new ArrayList<>(personToDelete.getListings());
        for (Listing listing : listings) {
            listing.removeOwner(personToDelete);
            model.setListing(listing, listing);
        }
    }

    private void removePersonPropertyPreferenceFromTags(Person personToDelete, Model model) {
        List<PropertyPreference> propertyPreferences = new ArrayList<>(personToDelete.getPropertyPreferences());
        for (PropertyPreference propertyPreference : propertyPreferences) {
            Set<Tag> tags = new HashSet<>(propertyPreference.getTags());

            for (Tag tag: tags) {
                tag.removePropertyPreference(propertyPreference);
                model.setTag(tag, tag);
            }
        }
    }
}
