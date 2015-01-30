package io.github.dadude941.auraglowremote;

/**
 * Known codes for AuraGlow bulbs.
 */
public interface AuraGlowCodes {
    /** Code used to turn the brightness up. */
    public static final int BRIGHTNESS_UP   = 0x00F700FF;
    /** Code used to turn the brightness down. */
    public static final int BRIGHTNESS_DOWN = 0x00F7807F;
    /** Code used to turn the bulb off. */
    public static final int TURN_OFF        = 0x00F740BF;
    /** Code used to turn the bulb on. */
    public static final int TURN_ON         = 0x00F7C03F;
    /** Code used to swithc the bulb to red color. */
    public static final int COLOR_RED       = 0x00F720DF;
    /** Code used to switch bulb to green color. */
    public static final int COLOR_GREEN     = 0x00F7A05F;
    /** Code used to switch the bulb to blue color. */
    public static final int COLOR_BLUE      = 0x00F7609F;
    /** Code used to switch the bulb to white color. */
    public static final int COLOR_WHITE     = 0x00F7E01F;
}
