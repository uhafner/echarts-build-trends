package edu.hm.hafner.echarts;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

/**
 * Determines whether a build result is too old to be considered for a trend graph.
 *
 * @author Ullrich Hafner
 */
public class ResultTime {
    /**
     * Returns whether the specified build result is too old to be considered for the trend graph.
     *
     * @param configuration
     *         configuration of the model
     * @param analysisRun
     *         the results of a analysis run
     *
     * @return {@code true} if the build is too old
     */
    public boolean isResultTooOld(final ChartModelConfiguration configuration, final BuildResult<?> analysisRun) {
        return configuration.isDayCountDefined() && computeDayDelta(analysisRun) > configuration.getDayCount();
    }

    private long computeDayDelta(final BuildResult<?> result) {
        return Math.abs(ChronoUnit.DAYS.between(
                toLocalDate(result.getBuild().getBuildTime()), TimeFacade.getInstance().getToday()));
    }

    private LocalDate toLocalDate(final long timeInSeconds) {
        return Instant.ofEpochSecond(timeInSeconds).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
