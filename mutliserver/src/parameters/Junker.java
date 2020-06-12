package parameters;

import java.io.Serializable;

public final class Junker implements Serializable {

    private final long[] digits;
    private final double[] cogits;
    private final String[] lines;
    private final Junker[] guts;

    public Junker(long[] digits, double[] cogits, String[] lines, Junker[] guts) {
        this.digits = digits;
        this.cogits = cogits;
        this.lines = lines;
        this.guts = guts;
    }

    public Junker[] Guts() { return guts; }

    public long[] Digits() { return digits; }

    public double[] Cogits() { return cogits; }

    public String[] Lines() { return lines; }
}