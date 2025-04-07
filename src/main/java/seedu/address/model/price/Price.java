package seedu.address.model.price;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a Price in the real estate system.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class Price {
    public static final String MESSAGE_CONSTRAINTS =
            "Price must be a non-negative number with up to 16 digits before the decimal point, "
                    + "and optionally up to 2 decimal places. At least 1 digit must be present. "
                    + "If no value is given, the lower bound price will be unbounded.";

    public static final String VALIDATION_REGEX = "^[0-9]{1,16}(?:\\.\\d{1,2})?$";

    public final BigDecimal price;

    /**
     * Constructs a {@code Price}.
     *
     * @param price A valid price.
     */
    public Price(String price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), MESSAGE_CONSTRAINTS);
        this.price = new BigDecimal(price);
    }

    /**
     * Returns true if a given string is a valid price.
     */
    public static boolean isValidPrice(String test) {
        if (test == null) {
            return true;
        }

        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Compares the value of this price with the value of another price.
     *
     * @param otherPrice The other price to compare with.
     * @return 0 if the values are equal. 1 if this price is greater. -1 if this price is smaller.
     */
    public int compare(Price otherPrice) {
        requireNonNull(otherPrice);

        return this.price.compareTo(otherPrice.price);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Price)) {
            return false;
        }

        Price otherPrice = (Price) other;
        return this.price.equals(otherPrice.price);
    }

    @Override
    public int hashCode() {
        return this.price.hashCode();
    }

    @Override
    public String toString() {
        return "$" + price.setScale(2, RoundingMode.HALF_DOWN).toPlainString();
    }

}
