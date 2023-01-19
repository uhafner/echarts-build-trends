package edu.hm.hafner.echarts;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Area style for a stacked chart.
 * <p>
 * This class will be automatically converted to a JSON object.
 * </p>
 *
 * @author Ullrich Hafner
 */
@SuppressWarnings("FieldCanBeLocal")
public class AreaStyle {
    @SuppressFBWarnings("SS_SHOULD_BE_STATIC")
    @SuppressWarnings("FieldCanBeStatic")
    private final boolean normal = true;

    /**
     * Creates a new instance of {@link AreaStyle}.
     */
    public AreaStyle() {
    }

    public boolean isNormal() {
        return normal;
    }
}
