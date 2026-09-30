package edu.hm.hafner.echarts;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

/**
 * Tests the class {@link PieData}.
 *
 * @author Ullrich Hafner
 */
class PieDataTest {
    @Test
    void shouldConvertListOfPointsToJson() {
        List<PieData> models = new ArrayList<>();
        var first = new PieData("ONE", 1);
        var second = new PieData("TWO", 2);
        models.add(first);
        models.add(second);

        assertThatJson(new ObjectMapper().writeValueAsString(models))
                .isArray()
                .hasSize(2)
                .contains(first)
                .contains(second);
    }
}
