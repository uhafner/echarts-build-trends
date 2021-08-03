package edu.hm.hafner.echarts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.hm.hafner.echarts.LineSeries.FilledMode;
import edu.hm.hafner.echarts.LineSeries.StackedMode;

import static edu.hm.hafner.echarts.assertions.Assertions.*;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.*;

/**
 * Tests the class {@link LinesChartModel}.
 *
 * @author Ullrich Hafner
 */
class LinesChartModelTest {
    private static final String COLOR = "#fff";
    private static final List<String> BUILDS = Arrays.asList("#1", "#2", "#3");
    private static final String ID = "spotbugs";

    @Test
    void shouldBeEmptyWhenCreated() {
        LinesChartModel model = new LinesChartModel();

        assertThat(model.getId()).isEmpty();
        assertThat(model).hasNoSeries();
        assertThat(model).hasNoDomainAxisLabels();
        assertThat(model).hasNoBuildNumbers();
    }

    @Test
    void shouldAddLabels() {
        LinesChartModel model = new LinesChartModel(ID);

        model.setDomainAxisLabels(BUILDS);

        assertThat(model).hasId(ID);
        assertThat(model.size()).isEqualTo(3);
        assertThat(model).hasDomainAxisLabels("#1", "#2", "#3");
        assertThat(model).hasToString(
                "{\"domainAxisLabels\":[\"#1\",\"#2\",\"#3\"],\"buildNumbers\":[],\"series\":[],\"id\":\"spotbugs\",\"domainAxisItemName\":\"Build\"}");

        String anotherId = "anotherId";
        model.setId(anotherId);
        assertThat(model).hasId(anotherId);
    }

    @Test
    void testGetSeries() {
        LinesChartModel model = new LinesChartModel(ID);
        LineSeries series = new LineSeries("TestName", "TestColor", StackedMode.STACKED, FilledMode.FILLED);

        model.addSeries(series);

        assertThat(model).hasSeries(series);
    }

    @Test
    void testGetDomainAxisLabels() {
        LinesChartModel singleLabelModel = new LinesChartModel(ID);
        singleLabelModel.setDomainAxisLabels(Collections.singletonList("a"));
        assertThat(singleLabelModel).hasDomainAxisLabels("a");

        LinesChartModel multipleLabelModel = new LinesChartModel(ID);
        multipleLabelModel.setDomainAxisLabels(Arrays.asList("a", "b", "c"));

        assertThat(multipleLabelModel).hasDomainAxisLabels("a", "b", "c");

        multipleLabelModel.setDomainAxisLabels(Arrays.asList("d", "e", "f"));

        assertThat(multipleLabelModel).hasDomainAxisLabels("a", "b", "c", "d", "e", "f");
    }

    @Test
    void shouldCreateLineModel() {
        LinesChartModel model = new LinesChartModel(ID);
        List<String> builds = new ArrayList<>();
        List<LineSeries> series = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            builds.add("#" + (i + 1));
        }
        series.add(new LineSeries("High", COLOR, StackedMode.STACKED, FilledMode.FILLED));
        series.add(new LineSeries("Normal", COLOR, StackedMode.STACKED, FilledMode.FILLED));
        series.add(new LineSeries("Low", COLOR, StackedMode.STACKED, FilledMode.FILLED));

        for (LineSeries severity : series) {
            for (int i = 0; i < 5; i++) {
                severity.add(i * 10);
            }
        }

        model.setDomainAxisLabels(builds);
        model.addSeries(series);

        assertThatJson(model).node("domainAxisLabels")
                .isArray().hasSize(5)
                .contains("#1")
                .contains("#2")
                .contains("#3")
                .contains("#4")
                .contains("#5");

        assertThatJson(model).node("series").isArray().hasSize(3);
    }
}
