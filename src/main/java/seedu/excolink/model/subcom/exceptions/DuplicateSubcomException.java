package seedu.excolink.model.subcom.exceptions;

/**
 * Signals that the operation will result in duplicate Subcoms (Subcoms are considered duplicates if they have the same
 * identity).
 */
public class DuplicateSubcomException extends RuntimeException {
    public DuplicateSubcomException() {
        super("Operation would result in duplicate subcoms");
    }
}
