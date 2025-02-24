package edu.hm.hafner.echarts;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static edu.hm.hafner.echarts.assertions.Assertions.*;

/**
 * Tests the class {@link PercentagePieChart}.
 *
 * @author Andreas Riepl
 * @author Oliver Scholz
 */
class PercentagePieChartTest {
    private static final String GREEN = Palette.GREEN.getNormal();
    private static final String YELLOW = Palette.YELLOW.getNormal();
    private static final String RED = Palette.RED.getNormal();
    private static final String GRAY = Palette.GRAY.getNormal();
    private static final String FIRST = "COLOR";
    private static final String SECOND = "BG";

    @Test
    void shouldThrowExceptionIfInvalid() {
        var chart = new PercentagePieChart();

        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> chart.create(-1))
                .withMessageContaining("Percentage -1");
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> chart.create(101))
                .withMessageContaining("Percentage 101");
    }

    @Test
    void shouldComputeColorWithCustomMapper() {
        var chart = new PercentagePieChart();

        Function<Integer, String> colorMapper = p -> p < 10 ? FIRST : SECOND;
        assertThat(chart.createWithStringMapper(9, colorMapper))
                .hasColors(FIRST, GRAY)
                .hasData(createFilledTo(9), getNotFilledTo(91));
        assertThat(chart.createWithStringMapper(10, colorMapper))
                .hasColors(SECOND, GRAY)
                .hasData(createFilledTo(10), getNotFilledTo(90));
    }

    @Test
    void shouldComputeColorToRedLowerBoundary() {
        var chart = new PercentagePieChart();

        assertThat(chart.create(0)).hasColors(RED, GRAY)
                .hasData(createFilledTo(0), getNotFilledTo(100));
    }

    @Test
    void shouldComputeColorToRedUpperBoundary() {
        var chart = new PercentagePieChart();

        assertThat(chart.create(49)).hasColors(RED, GRAY)
                .hasData(createFilledTo(49), getNotFilledTo(51));
    }

    @Test
    void shouldComputeColorToYellowLowerBoundary() {
        var chart = new PercentagePieChart();

        assertThat(chart.create(50)).hasColors(YELLOW, GRAY)
                .hasData(createFilledTo(50), getNotFilledTo(50));
    }

    @Test
    void shouldComputeColorToYellowUpperBoundary() {
        var chart = new PercentagePieChart();

        assertThat(chart.create(79)).hasColors(YELLOW, GRAY)
                .hasData(createFilledTo(79), getNotFilledTo(21));
    }

    @Test
    void shouldComputeColorToGreenLowerBoundary() {
        var chart = new PercentagePieChart();

        assertThat(chart.create(80)).hasColors(GREEN, GRAY)
                .hasData(createFilledTo(80), getNotFilledTo(20));
    }

    @Test
    void shouldComputeColorToGreenUpperBoundary() {
        var chart = new PercentagePieChart();

        assertThat(chart.create(100)).hasColors(GREEN, GRAY)
                .hasData(createFilledTo(100), getNotFilledTo(0));
    }

    private PieData getNotFilledTo(final int percentage) {
        return createPieData(percentage, "NotFilled");
    }

    private PieData createFilledTo(final int percentage) {
        return createPieData(percentage, "Filled");
    }

    private PieData createPieData(final int percentage, final String filled) {
        return new PieData(filled, percentage);
    }
}
