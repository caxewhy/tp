package seedu.address.model.listing;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a {@code Listing}'s property name in the real estate system.
 * Guarantees: immutable; is valid as declared in {@link #isValidPropertyName(String)}
 */
public class PropertyName {
    public static final String MESSAGE_CONSTRAINTS =
            "Property names must be between 2 and 100 characters long "
                    + "and can only contain letters, numbers, apostrophes, periods, hyphens, and spaces.";

    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9'.\\- ]{2,100}$";

    public final String propertyName;

    /**
     * Constructs a {@code PropertyName}.
     *
     * @param propertyName A valid property name.
     */
    public PropertyName(String propertyName) {
        requireNonNull(propertyName);
        checkArgument(isValidPropertyName(propertyName), MESSAGE_CONSTRAINTS);
        this.propertyName = propertyName;
    }

    /**
     * Returns true if a given string is a valid property name.
     */
    public static boolean isValidPropertyName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return propertyName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PropertyName)) {
            return false;
        }

        PropertyName otherPropertyName = (PropertyName) other;
        return propertyName.equals(otherPropertyName.propertyName);
    }

    @Override
    public int hashCode() {
        return propertyName.hashCode();
    }

}
