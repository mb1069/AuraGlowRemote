package io.github.dadude941.auraglowremote.nec;

/**
 * A factory capable of creating IR commands that adhere to NEC protocol.
 */
public interface NECCommand {
    /**
     * Adds an integer to the command sequence. The specified integer is immediately appended to the
     * end of the command sequence. The length of the integer (in bits) is specified in the second
     * argument.
     * @param value The value to be appended to the command sequence.
     * @param length The length (in bits) of the value to be appended to the command sequence.
     * @return A reference to the instance of the current factory.
     * @throws IllegalArgumentException Thrown if adding the integer would overflow the 2s IR limit.
     */
    public NECCommand addInteger(int value, byte length) throws IllegalArgumentException;

    /**
     * Adds a single bit to the command sequence.
     * @param set If true, high bit will be appended to the command sequence, otherwise a low bit
     *            will be added.
     * @return A reference to the instance of the current factory.
     * @throws IllegalArgumentException Thrown if adding the bit would overflow the 2s IR limit.
     */
    public NECCommand addBit(boolean set) throws IllegalArgumentException;

    /**
     * Adds repeat sequence and returns the pattern. Repeats will be appended so that they adhere to
     * the frame length.
     * @param repeats Indicates how many repeat codes should be appended into the command sequence.
     * @return An integer array representing the command sequence.
     * @throws IllegalArgumentException Thrown if adding the repeat codes would overflow the 2s IR
     * limit.
     */
    public int[] getPattern(int repeats) throws IllegalArgumentException;
}
