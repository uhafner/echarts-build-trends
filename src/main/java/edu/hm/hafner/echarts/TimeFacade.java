package edu.hm.hafner.echarts;

import edu.hm.hafner.util.VisibleForTesting;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.Temporal;

/**
 * Facade to the current time. Encapsulates all calls that require the current time so that tests can replace this
 * facade with a stub.
 *
 * @author Ullrich Hafner
 */
@SuppressWarnings("checkstyle:FinalClass")
public final class TimeFacade {
    @SuppressWarnings("NonFinalStaticField")
    private static TimeFacade instance = new TimeFacade();

    public static TimeFacade getInstance() {
        return instance;
    }

    /**
     * Replaces a stubbed facade with the real facade.
     */
    @VisibleForTesting
    public static void reset() {
        setInstance(new TimeFacade());
    }

    @VisibleForTesting
    public static void setInstance(final TimeFacade stubFacade) {
        instance = stubFacade;
    }

    private TimeFacade() {
        // prevents instantiation
    }

    /**
     * Return today, i.e., the current date from the system clock in the default time-zone.
     *
     * @return current date from the system clock in the default time-zone
     */
    @SuppressWarnings("JavaTimeDefaultTimeZone") // TODO: It would make sense to render charts using the time zone of the user
    public Temporal getToday() {
        return LocalDate.now();
    }

    /**
     * Return the date of a build, i.e., a {@code LocalDate} with the same year, month and day as today.
     *
     * @param build
     *         the build to get the date for
     *
     * @return current date from the system clock in the default time-zone
     */
    public LocalDate getBuildDate(final Build build) {
        return Instant.ofEpochSecond(build.getBuildTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
