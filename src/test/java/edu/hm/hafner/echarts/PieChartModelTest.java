package edu.hm.hafner.echarts;

import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Tests the class {@link PieChartModel}.
 *
 * @author Nils Engelbrecht
 */
class PieChartModelTest {
    private static final String NAME = "piechartName";

    @Test
    void shouldCreatePieModel() {
        var model = new PieChartModel(NAME);
        var first = new PieData("ONE", 1);
        var second = new PieData("TWO", 2);

        model.add(first, Palette.BLUE);
        model.add(second, Palette.RED);

        verifyJsonRepresentation(model, first, second);
        verifyProperties(model, first, second);
    }

    private void verifyProperties(final PieChartModel model, final PieData first, final PieData second) {
        assertThat(model.getColors()).hasSize(2);
        assertThat(model.getColors())
                .contains(Palette.BLUE.getNormal())
                .contains(Palette.RED.getNormal());

        assertThat(model.getData()).hasSize(2)
                .contains(first)
                .contains(second);

        assertThat(model.getName()).isEqualTo(NAME);
    }

    private void verifyJsonRepresentation(final PieChartModel model, final PieData first, final PieData second) {
        assertThatJson(model).node("data")
                .isArray().hasSize(2)
                .contains(first)
                .contains(second);

        assertThatJson(model).node("colors")
                .isArray().hasSize(2)
                .contains(Palette.BLUE.getNormal())
                .contains(Palette.RED.getNormal());

        assertThatJson(model).node("name").isEqualTo(NAME);
    }
}
