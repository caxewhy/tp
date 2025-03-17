package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOUSE_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOWER_BOUND_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROPERTY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIT_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UPPER_BOUND_PRICE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.listing.HouseNumber;
import seedu.address.model.listing.Listing;
import seedu.address.model.listing.PostalCode;
import seedu.address.model.listing.PropertyName;
import seedu.address.model.listing.UnitNumber;
import seedu.address.model.price.PriceRange;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagRegistry;

/**
 * Adds a listing to the address book.
 */
public class AddListingCommand extends Command {
    public static final String COMMAND_WORD = "addListing";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a listing to the address book. "
            + "Parameters: "
            + PREFIX_POSTAL_CODE + "POSTAL_CODE "
            + "[" + PREFIX_UNIT_NUMBER + "UNIT_NUMBER] "
            + "[" + PREFIX_HOUSE_NUMBER + "HOUSE_NUMBER] "
            + "[" + PREFIX_LOWER_BOUND_PRICE + "LOWER_BOUND_PRICE] "
            + "[" + PREFIX_UPPER_BOUND_PRICE + "UPPER_BOUND_PRICE] "
            + "[" + PREFIX_PROPERTY_NAME + "PROPERTY_NAME] "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_NEW_TAG + "NEW_TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_POSTAL_CODE + "654321 "
            + PREFIX_HOUSE_NUMBER + "12 "
            + PREFIX_LOWER_BOUND_PRICE + "300000 "
            + PREFIX_UPPER_BOUND_PRICE + "600000 "
            + PREFIX_PROPERTY_NAME + "Sunny Villa "
            + PREFIX_TAG + "quiet "
            + PREFIX_TAG + "pet-friendly "
            + PREFIX_NEW_TAG + "family-friendly "
            + PREFIX_NEW_TAG + "spacious";

    public static final String MESSAGE_SUCCESS = "New listing added: %1$s";
    public static final String MESSAGE_DUPLICATE_LISTING = "This listing already exists in the address book";
    public static final String MESSAGE_DUPLICATE_TAGS = "At least one of the new tags given already exist.";
    public static final String MESSAGE_INVALID_TAGS = "At least one of the tags given does not exist.";

    private final Listing toAdd;
    private final Set<String> tagSet;
    private final Set<String> newTagSet;

    /**
     * Creates an AddListingCommand to add the specified {@code Listing}
     */
    public AddListingCommand(Listing listing, Set<String> tagSet, Set<String> newTagSet) {
        requireNonNull(listing);
        toAdd = listing;
        this.tagSet = tagSet;
        this.newTagSet = newTagSet;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTags(tagSet)) {
            throw new CommandException(MESSAGE_INVALID_TAGS);
        }

        if (model.hasNewTags(newTagSet)) {
            throw new CommandException(MESSAGE_DUPLICATE_TAGS);
        }

        model.addTags(newTagSet);
        Listing listingWithTags = createListingWithTags();

        if (model.hasListing(listingWithTags)) {
            throw new CommandException(MESSAGE_DUPLICATE_LISTING);
        }
        model.addListing(listingWithTags);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddListingCommand)) {
            return false;
        }

        AddListingCommand otherAddListingCommand = (AddListingCommand) other;
        return toAdd.equals(otherAddListingCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }

    private Listing createListingWithTags() {

        TagRegistry tagRegistry = TagRegistry.of();
        Set<String> tagNames = new HashSet<>(newTagSet);
        tagNames.addAll(tagSet);
        Set<Tag> tags = new HashSet<>();

        for (String tagName: tagNames) {
            Tag tag = tagRegistry.get(tagName);
            List<Listing> listings = new ArrayList<>(tag.getListings());
            listings.add(toAdd);
            Tag editedTag = new Tag(tagName, tag.getPropertyPreferences(), listings);
            tagRegistry.setTag(tag, editedTag);
            tags.add(tag);
        }

        UnitNumber unitNumber = toAdd.getUnitNumber();
        HouseNumber houseNumber = toAdd.getHouseNumber();
        PostalCode postalCode = toAdd.getPostalCode();
        PriceRange priceRange = toAdd.getPriceRange();
        PropertyName propertyName = toAdd.getPropertyName();
        Listing newListing;

        if (unitNumber == null) {
            newListing = new Listing(postalCode, houseNumber, priceRange, propertyName, tags, toAdd.getOwners());
        } else {
            newListing = new Listing(postalCode, unitNumber, priceRange, propertyName, tags, toAdd.getOwners());
        }

        return newListing;
    }
}
