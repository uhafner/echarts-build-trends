package edu.hm.hafner.echarts;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.*;

/**
 * Tests the class {@link PieData}.
 *
 * @author Ullrich Hafner
 */
class PieDataTest {
    @Test
    void shouldConvertListOfPointsToJson() {
        List<PieData> models = new ArrayList<>();
        PieData first = new PieData("ONE", 1);
        PieData second = new PieData("TWO", 2);
        models.add(first);
        models.add(second);

        assertThatJson(new JacksonFacade().toJson(models)).isArray().hasSize(2)
                .contains(first)
                .contains(second);
    }
}
