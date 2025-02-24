package edu.hm.hafner.echarts;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

/**
 * Test the class {@link LinesDataSet}.
 *
 * @author Lorenz Munsch
 * @author Ullrich Hafner
 */
class LinesDataSetTest {
    private static final String X_AXIS_LABEL = "X_AXIS_LABEL";
    private static final String FIRST_DATA_SET = "Eins";
    private static final String SECOND_DATA_SET = "Zwei";
    private static final String ANOTHER_LABEL = "another-label";

    @Test
    void shouldAddSeries() {
        var linesDataSet = new LinesDataSet();
        assertThat(linesDataSet.getDomainAxisSize()).isEqualTo(0);
        assertThat(linesDataSet.hasSeries(FIRST_DATA_SET)).isFalse();

        linesDataSet.add(X_AXIS_LABEL, createSeries(1));

        verifyFirstSeries(linesDataSet);

        assertThat(linesDataSet.getBuildNumbers()).isEmpty();
    }

    @Test
    void shouldAddSeriesWithUrls() {
        var linesDataSet = new LinesDataSet();
        assertThat(linesDataSet.getDomainAxisSize()).isEqualTo(0);
        assertThat(linesDataSet.hasSeries(FIRST_DATA_SET)).isFalse();

        linesDataSet.add(X_AXIS_LABEL, createSeries(1), 1);

        verifyFirstSeries(linesDataSet);

        assertThat(linesDataSet.getBuildNumbers()).containsOnly(1);

        linesDataSet.add(ANOTHER_LABEL, createSeries(3), 2);

        assertThat(linesDataSet.getDomainAxisSize()).isEqualTo(2);
        assertThat(linesDataSet.getDomainAxisLabels()).containsOnly(X_AXIS_LABEL, ANOTHER_LABEL);
        assertThat(linesDataSet.getBuildNumbers()).containsOnly(1, 2);
        assertThat(linesDataSet.getDataSetIds()).containsOnlyOnce(FIRST_DATA_SET, SECOND_DATA_SET);

        assertThat(linesDataSet.hasSeries(FIRST_DATA_SET)).isTrue();
        assertThat(linesDataSet.getSeries(FIRST_DATA_SET)).containsExactly(1, 3);

        assertThat(linesDataSet.hasSeries(SECOND_DATA_SET)).isTrue();
        assertThat(linesDataSet.getSeries(SECOND_DATA_SET)).containsExactly(2, 4);
    }

    private void verifyFirstSeries(final LinesDataSet linesDataSet) {
        assertThat(linesDataSet.getDomainAxisSize()).isEqualTo(1);
        assertThat(linesDataSet.getDomainAxisLabels()).containsOnly(X_AXIS_LABEL);
        assertThat(linesDataSet.getDataSetIds()).containsOnlyOnce(FIRST_DATA_SET, SECOND_DATA_SET);

        assertThat(linesDataSet.hasSeries(FIRST_DATA_SET)).isTrue();
        assertThat(linesDataSet.getSeries(FIRST_DATA_SET)).containsOnly(1);

        assertThat(linesDataSet.hasSeries(SECOND_DATA_SET)).isTrue();
        assertThat(linesDataSet.getSeries(SECOND_DATA_SET)).containsOnly(2);
    }

    @Test
    void shouldThrowExceptionIfSeriesDoesNotExist() {
        var linesDataSet = new LinesDataSet();

        linesDataSet.add(X_AXIS_LABEL, createSeries(1));

        assertThatThrownBy(() -> linesDataSet.getSeries("WrongId"))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No dataset")
                .hasMessageContaining("registered");
    }

    @Test
    void shouldThrowExceptionIfSameLabelIsAddedTwice() {
        var linesDataSet = new LinesDataSet();
        Map<String, Integer> dataSetSeries = Collections.emptyMap();

        linesDataSet.add(X_AXIS_LABEL, dataSetSeries);
        linesDataSet.add(X_AXIS_LABEL, dataSetSeries);

        assertThat(linesDataSet.getDomainAxisSize()).isEqualTo(2);
        assertThat(linesDataSet.getDomainAxisLabels()).containsExactly(X_AXIS_LABEL, X_AXIS_LABEL);
    }

    @Test
    void shouldThrowExceptionIfSameBuildNumberIsAddedTwice() {
        var linesDataSet = new LinesDataSet();
        Map<String, Integer> dataSetSeries = Collections.emptyMap();

        linesDataSet.add(X_AXIS_LABEL, dataSetSeries, 1);
        assertThatThrownBy(() -> linesDataSet.add(ANOTHER_LABEL, dataSetSeries, 1))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Build number already registered: 1");
    }

    private Map<String, Integer> createSeries(final int start) {
        Map<String, Integer> dataSetSeries = new HashMap<>();
        dataSetSeries.put(FIRST_DATA_SET, start);
        dataSetSeries.put(SECOND_DATA_SET, start + 1);

        return dataSetSeries;
    }
}
