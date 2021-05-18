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
    void shouldReadStringProperty() {
        JacksonFacade jackson = new JacksonFacade();

        assertThat(jackson.getString(
                "{\"numberOfBuilds\":\"25\",\"numberOfDays\":\"0\",\"buildAsDomain\":\"true\",\"chartType\":\"loc\"}",
                "chartType", "default")).isEqualTo("loc");
        assertThat(jackson.getString("{\"numberOfBuilds\":\"25\",\"chartType\":\"loc\"}",
                "other", "default")).isEqualTo("default");
        assertThat(jackson.getString("!!!", "other", "default")).isEqualTo("default");
    }

    @Test
    void shouldReadIntegerProperty() {
        JacksonFacade jackson = new JacksonFacade();

        assertThat(jackson.getInteger(
                "{\"numberOfBuilds\":\"25\",\"numberOfDays\":\"0\",\"buildAsDomain\":\"true\",\"chartType\":\"loc\"}",
                "numberOfBuilds", 2)).isEqualTo(25);
        assertThat(jackson.getInteger("{\"numberOfBuilds\":\"25\",\"chartType\":\"loc\"}",
                "other", 1)).isEqualTo(1);
        assertThat(jackson.getInteger("!!!", "other", 2)).isEqualTo(2);
    }
}
