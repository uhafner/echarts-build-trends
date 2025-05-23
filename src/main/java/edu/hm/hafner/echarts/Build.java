package edu.hm.hafner.echarts;

import edu.hm.hafner.util.Generated;
import edu.hm.hafner.util.VisibleForTesting;

import java.util.Objects;

/**
 * Represents the build that contains results that should be rendered using ECharts.
 *
 * @author Ullrich Hafner
 */
public class Build implements Comparable<Build> {
    private final int buildTime;
    private final int number;
    private final String displayName;

    /**
     * Creates a new instance of {@link Build}.
     *
     * @param number
     *         build number
     */
    @VisibleForTesting
    public Build(final int number) {
        this(number, "#" + number, 0);
    }

    /**
     * Creates a new instance of {@link Build}.
     *
     * @param number
     *         build number
     * @param displayName
     *         human-readable name of the build
     * @param buildTime
     *         the build time (given as number of seconds since the standard base time known as "the epoch", namely
     *         January 1, 1970, 00:00:00 GMT).
     */
    public Build(final int number, final String displayName, final int buildTime) {
        this.buildTime = buildTime;
        this.number = number;
        this.displayName = displayName;
    }

    /**
     * Returns the start time of this build (given as number of seconds since the standard base time known as "the
     * epoch", namely January 1, 1970, 00:00:00 GMT).
     *
     * @return start time of this build
     */
    public long getBuildTime() {
        return buildTime;
    }

    /**
     * Returns the number of this build as assigned by Jenkins' scheduler.
     *
     * @return the number of this build
     */
    public int getNumber() {
        return number;
    }

    /**
     * Returns a human-readable label for this build.
     *
     * @return the name to be used in the user interface
     */
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public int compareTo(final Build o) {
        return getNumber() - o.getNumber();
    }

    @Override @Generated
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        var that = (Build) o;
        return number == that.number;
    }

    @Override @Generated
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override @Generated
    public String toString() {
        return getDisplayName();
    }
}
