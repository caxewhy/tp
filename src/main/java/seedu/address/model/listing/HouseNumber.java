package seedu.address.model.listing;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a {@code Listing}'s house number in the real estate system.
 * Guarantees: immutable; is valid as declared in {@link #isValidHouseNumber(String)}
 */
public class HouseNumber {

    public static final String MESSAGE_CONSTRAINTS =
            "House number must be at most 3 characters long, "
                    + "consisting of only letters and numbers. The last character cannot be 'I' or 'O'.";

    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9]{1,3}$";

    public final String houseNumber;

    /**
     * Constructs a {@code HouseNumber}.
     *
     * @param houseNumber A valid house number.
     */
    public HouseNumber(String houseNumber) {
        requireNonNull(houseNumber);
        checkArgument(isValidHouseNumber(houseNumber), MESSAGE_CONSTRAINTS);
        this.houseNumber = houseNumber;
    }

    /**
     * Returns true if a given string is a valid house number.
     */
    public static boolean isValidHouseNumber(String test) {
        boolean isLastCharacterValid = !(test.toUpperCase().endsWith("I") || test.toUpperCase().endsWith("O"));
        return test.matches(VALIDATION_REGEX) && isLastCharacterValid;
    }

    @Override
    public String toString() {
        return houseNumber;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HouseNumber)) {
            return false;
        }

        HouseNumber otherHouseNumber = (HouseNumber) other;
        return houseNumber.equals(otherHouseNumber.houseNumber);
    }

    @Override
    public int hashCode() {
        return houseNumber.hashCode();
    }

}
