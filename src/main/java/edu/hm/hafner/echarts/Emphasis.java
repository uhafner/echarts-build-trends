package edu.hm.hafner.echarts;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Emphasis: currently we only need this to disable the emphasis for line charts.
 *
 * <p>
 * This class will be automatically converted to a JSON object.
 * </p>
 *
 * @author Ullrich Hafner
 */
@SuppressWarnings("FieldCanBeLocal")
public class Emphasis {
    @SuppressFBWarnings("SS_SHOULD_BE_STATIC")
    @SuppressWarnings("FieldCanBeStatic")
    private final boolean disabled = true;

    public boolean isDisabled() {
        return disabled;
    }
}
