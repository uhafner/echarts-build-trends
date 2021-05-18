package edu.hm.hafner.echarts;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests the class {@link JacksonFacade}.
 *
 * @author Ullrich Hafner
 */
class JacksonFacadeTest {
    @Test
    void shouldReadProperty() {
        JacksonFacade jackson = new JacksonFacade();

        assertThat(jackson.getString("{\"numberOfBuilds\":\"25\",\"numberOfDays\":\"0\",\"buildAsDomain\":\"true\",\"chartType\":\"loc\"}",
                "chartType", "default")).isEqualTo("loc");
        assertThat(jackson.getString("{\"numberOfBuilds\":\"25\",\"chartType\":\"loc\"}",
                "other", "default")).isEqualTo("default");

    }
}
