package io.github.dadude941.auraglowremote.nec;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//TODO should ensure that the command frame does not exceed length?

/**
 * Command factory capable of generating IR commands that adhere to NEC protocol.
 */
class NECFactory implements NECCommand {
    /** Maximum time limit. */
    protected static final int MAX_TIME       = 2000000;
    /** Maximum length of any single frame. **/
    protected static final int FRAME_LENGTH   = 108000;
    /** Length of burst for high bit. */
    protected static final int BURST_ONE      = 563;
    /** Length of burst for low bit. */
    protected static final int BURST_ZERO     = 563;
    /** Length of burst for repeat code. */
    protected static final int BURST_REPEAT   = 9000;
    /** Length of burst for handshake. */
    protected static final int BURST_HANDSHAKE= 9000;
    /** Length of burst for end of file. */
    protected static final int BURST_EOF      = 563;
    /** Length of space for high bit. */
    protected static final int SPACE_ONE      = 1688;
    /** Length of space for low bit. */
    protected static final int SPACE_ZERO     = 563;
    /** Length of space for repeat code. */
    protected static final int SPACE_REPEAT   = 2250;
    /** Length of space for handshake. */
    protected static final int SPACE_HANDSHAKE= 4500;

    private final List<Integer> pattern;
    private int accumulatedLength;

    /**
     * Default constructor. Initialises internal variables and appends handshake sequence.
     */
    protected NECFactory(){
        this.pattern = new ArrayList<>();
        this.accumulatedLength = 0;

        addHandshake();
    }

    @Override
    public NECCommand addInteger(int value, byte length) throws IllegalArgumentException {
        int mask = (int)Math.pow(2, length)+1;

        for(int i=0; i<length; i++){
            addBit(((value << i)&mask) != 0);
        }

        return this;
    }

    @Override
    public NECCommand addBit(boolean set) throws IllegalArgumentException {
        int burst = set ? BURST_ONE : BURST_ZERO;
        int space = set ? SPACE_ONE : SPACE_ZERO;

        addSlice(burst);
        addSlice(space);

        return this;
    }

    @Override
    public int[] getPattern(int repeats) throws IllegalArgumentException {
        finishAndRepeat(repeats);

        int[] primitivePattern = new int[pattern.size()];
        Iterator<Integer> it = pattern.iterator();
        for(int i=0; i<primitivePattern.length; i++){
            primitivePattern[i] = it.next();
        }

        return primitivePattern;
    }


    /**
     * Adds a slice of specific length to the current command sequence.
     * @param length Length of the slice (in micro-seconds)
     * @throws IllegalArgumentException Thrown if adding the slice would overflow the 2s IR limit.
     */
    protected void addSlice(int length) throws IllegalArgumentException {
        pattern.add(length);

        if(accumulatedLength > MAX_TIME){
            throw new IllegalArgumentException(
                "Total transmission length must not exceed "+MAX_TIME+"us (given: "+accumulatedLength+")"
            );
        }
    }

    /**
     * Appends the command-end sequence and adds a specific number of repeat commands.
     * @param count Number of repeat commands to append.
     * @throws IllegalArgumentException Thrown if adding the finish sequence or repeat commands
     * would overflow the 2s IR limit.
     */
    protected void finishAndRepeat(int count) throws IllegalArgumentException {
        addSlice(BURST_EOF);

        for(int i=0; i<count; i++){
            addSlice(FRAME_LENGTH - (accumulatedLength % FRAME_LENGTH));
            addSlice(BURST_REPEAT);
            addSlice(SPACE_REPEAT);
            addSlice(BURST_EOF);
        }
    }

    /**
     * Appends handshake sequence.
     * @throws IllegalArgumentException Thrown if adding the integer would overflow the 2s IR limit.
     */
    protected void addHandshake() throws IllegalArgumentException {
        addSlice(BURST_HANDSHAKE);
        addSlice(SPACE_HANDSHAKE);
    }
}
