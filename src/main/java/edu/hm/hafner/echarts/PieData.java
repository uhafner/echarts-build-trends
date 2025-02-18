package edu.hm.hafner.echarts;

import edu.hm.hafner.util.Generated;

import java.util.Objects;

/**
 * UI model for an ECharts pie chart.
 *
 * <p>
 * This class will be automatically converted to a JSON object.
 * </p>
 *
 * @author Ullrich Hafner
 */
public class PieData {
    private final int value;
    private final String name;

    /**
     * A new data point for a pie or doughnut chart.
     *
     * @param name
     *         name of the data point
     * @param value
     *         value of the data point
     */
    public PieData(final String name, final int value) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    @Override @Generated
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PieData pieData = (PieData) o;
        return value == pieData.value && Objects.equals(name, pieData.name);
    }

    @Override @Generated
    public int hashCode() {
        return Objects.hash(value, name);
    }

    @Override @Generated
    public String toString() {
        return String.format("%s->%d", name, value);
    }
}
