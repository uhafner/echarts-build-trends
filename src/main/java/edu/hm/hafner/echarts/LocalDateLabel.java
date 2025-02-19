package edu.hm.hafner.echarts;

import edu.hm.hafner.util.Generated;
import edu.umd.cs.findbugs.annotations.NonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

/**
 * Chart label showing the build date.
 *
 * @author Ullrich Hafner
 */
public class LocalDateLabel implements Comparable<LocalDateLabel> {
    private final LocalDate date;
    private final DateTimeFormatter formatter;

    /**
     * Creates a new instance of {@link LocalDateLabel}.
     *
     * @param date
     *         the date of the build
     */
    public LocalDateLabel(final LocalDate date) {
        this.date = date;
        formatter = DateTimeFormatter.ofPattern("MM-dd", Locale.ENGLISH);
    }

    @Override
    public int compareTo(@NonNull final LocalDateLabel o) {
        return date.compareTo(o.date);
    }

    @Override
    public String toString() {
        return formatter.format(date);
    }

    @Override @Generated
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        var that = (LocalDateLabel) o;
        return date.equals(that.date) && formatter.equals(that.formatter);
    }

    @Override @Generated
    public int hashCode() {
        return Objects.hash(date, formatter);
    }
}
