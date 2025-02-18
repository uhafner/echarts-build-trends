package edu.hm.hafner.echarts;

import org.junit.jupiter.api.Test;

import edu.hm.hafner.echarts.ChartModelConfiguration.AxisType;

import static edu.hm.hafner.echarts.assertions.Assertions.*;

/**
 * Tests the class {@link ChartModelConfiguration}.
 *
 * @author Ullrich Hafner
 */
class ChartModelConfigurationTest {
    @Test
    void shouldProvideDefaultValues() {
        var configuration = new ChartModelConfiguration();

        assertThat(configuration).hasAxisType(ChartModelConfiguration.DEFAULT_DOMAIN_AXIS_TYPE);
        assertThat(configuration).hasBuildCount(ChartModelConfiguration.DEFAULT_BUILD_COUNT);
        assertThat(configuration).hasDayCount(ChartModelConfiguration.DEFAULT_DAY_COUNT);
    }

    @Test
    void shouldCreateDefaultFromEmptyJson() {
        ChartModelConfiguration configuration = ChartModelConfiguration.fromJson("{}");

        assertThat(configuration).hasAxisType(ChartModelConfiguration.DEFAULT_DOMAIN_AXIS_TYPE);
        assertThat(configuration).hasBuildCount(ChartModelConfiguration.DEFAULT_BUILD_COUNT);
        assertThat(configuration).hasDayCount(ChartModelConfiguration.DEFAULT_DAY_COUNT);
    }

    @Test
    void shouldCreateDefaultFromBrokenJson() {
        ChartModelConfiguration configuration = ChartModelConfiguration.fromJson("?");

        assertThat(configuration).hasAxisType(ChartModelConfiguration.DEFAULT_DOMAIN_AXIS_TYPE);
        assertThat(configuration).hasBuildCount(ChartModelConfiguration.DEFAULT_BUILD_COUNT);
        assertThat(configuration).hasDayCount(ChartModelConfiguration.DEFAULT_DAY_COUNT);
    }

    @Test
    void shouldCreateFromJsonWithAxisSelection() {
        ChartModelConfiguration buildDomainAxis = ChartModelConfiguration.fromJson("{\"buildAsDomain\": \"true\"}");

        assertThat(buildDomainAxis).hasAxisType(AxisType.BUILD);
        assertThat(buildDomainAxis).hasBuildCount(ChartModelConfiguration.DEFAULT_BUILD_COUNT);
        assertThat(buildDomainAxis).hasDayCount(ChartModelConfiguration.DEFAULT_DAY_COUNT);

        ChartModelConfiguration dataDomainAxis = ChartModelConfiguration.fromJson("{\"buildAsDomain\": \"false\"}");

        assertThat(dataDomainAxis).hasAxisType(AxisType.DATE);
        assertThat(dataDomainAxis).hasBuildCount(ChartModelConfiguration.DEFAULT_BUILD_COUNT);
        assertThat(dataDomainAxis).hasDayCount(ChartModelConfiguration.DEFAULT_DAY_COUNT);

        ChartModelConfiguration brokenDomainAxis = ChartModelConfiguration.fromJson("{\"buildAsDomain\": \"undefined\"}");

        assertThat(brokenDomainAxis).hasAxisType(ChartModelConfiguration.DEFAULT_DOMAIN_AXIS_TYPE);
        assertThat(brokenDomainAxis).hasBuildCount(ChartModelConfiguration.DEFAULT_BUILD_COUNT);
        assertThat(brokenDomainAxis).hasDayCount(ChartModelConfiguration.DEFAULT_DAY_COUNT);
    }

    @Test
    void shouldCreateFromJsonWithBuildNumberSelection() {
        ChartModelConfiguration buildDomainAxis = ChartModelConfiguration.fromJson("{\"numberOfBuilds\": \"10\"}");

        assertThat(buildDomainAxis).hasAxisType(ChartModelConfiguration.DEFAULT_DOMAIN_AXIS_TYPE);
        assertThat(buildDomainAxis).hasBuildCount(10);
        assertThat(buildDomainAxis).hasDayCount(ChartModelConfiguration.DEFAULT_DAY_COUNT);

        ChartModelConfiguration dataDomainAxis = ChartModelConfiguration.fromJson("{\"numberOfBuilds\": \"-10\"}");

        assertThat(dataDomainAxis).hasAxisType(ChartModelConfiguration.DEFAULT_DOMAIN_AXIS_TYPE);
        assertThat(dataDomainAxis).hasBuildCount(ChartModelConfiguration.DEFAULT_BUILD_COUNT);
        assertThat(dataDomainAxis).hasDayCount(ChartModelConfiguration.DEFAULT_DAY_COUNT);

        ChartModelConfiguration brokenDomainAxis = ChartModelConfiguration.fromJson("{\"numberOfBuilds\": \"undefined\"}");

        assertThat(brokenDomainAxis).hasAxisType(ChartModelConfiguration.DEFAULT_DOMAIN_AXIS_TYPE);
        assertThat(brokenDomainAxis).hasBuildCount(ChartModelConfiguration.DEFAULT_BUILD_COUNT);
        assertThat(brokenDomainAxis).hasDayCount(ChartModelConfiguration.DEFAULT_DAY_COUNT);
    }

    @Test
    void shouldCreateFromJsonWithDateNumberSelection() {
        ChartModelConfiguration buildDomainAxis = ChartModelConfiguration.fromJson("{\"numberOfDays\": \"10\"}");

        assertThat(buildDomainAxis).hasAxisType(ChartModelConfiguration.DEFAULT_DOMAIN_AXIS_TYPE);
        assertThat(buildDomainAxis).hasBuildCount(ChartModelConfiguration.DEFAULT_BUILD_COUNT);
        assertThat(buildDomainAxis).hasDayCount(10);

        ChartModelConfiguration dataDomainAxis = ChartModelConfiguration.fromJson("{\"numberOfDays\": \"-10\"}");

        assertThat(dataDomainAxis).hasAxisType(ChartModelConfiguration.DEFAULT_DOMAIN_AXIS_TYPE);
        assertThat(dataDomainAxis).hasBuildCount(ChartModelConfiguration.DEFAULT_BUILD_COUNT);
        assertThat(dataDomainAxis).hasDayCount(ChartModelConfiguration.DEFAULT_DAY_COUNT);

        ChartModelConfiguration brokenDomainAxis = ChartModelConfiguration.fromJson("{\"numberOfDays\": \"undefined\"}");

        assertThat(brokenDomainAxis).hasAxisType(ChartModelConfiguration.DEFAULT_DOMAIN_AXIS_TYPE);
        assertThat(brokenDomainAxis).hasBuildCount(ChartModelConfiguration.DEFAULT_BUILD_COUNT);
        assertThat(brokenDomainAxis).hasDayCount(ChartModelConfiguration.DEFAULT_DAY_COUNT);
    }

    @Test
    void shouldCreateFromJson() {
        ChartModelConfiguration buildDomainAxis = ChartModelConfiguration.fromJson(
                "{\"numberOfDays\": \"5\", \"numberOfBuilds\": \"25\", \"buildAsDomain\": \"false\"}");

        assertThat(buildDomainAxis).hasAxisType(AxisType.DATE);
        assertThat(buildDomainAxis).hasBuildCount(25);
        assertThat(buildDomainAxis).hasDayCount(5);

        assertThat(buildDomainAxis).isDayCountDefined();
        assertThat(buildDomainAxis).isBuildCountDefined();
    }

    @Test
    void shouldVerifyActivationOfCounters() {
        assertThat(new ChartModelConfiguration(AxisType.BUILD, 2, 1))
                .hasAxisType(AxisType.BUILD)
                .hasBuildCount(2)
                .hasDayCount(1)
                .isDayCountDefined()
                .isBuildCountDefined();
        assertThat(new ChartModelConfiguration(AxisType.DATE, 1, 0))
                .hasAxisType(AxisType.DATE)
                .hasBuildCount(1)
                .hasDayCount(0)
                .isNotBuildCountDefined()
                .isNotDayCountDefined();
    }
}
