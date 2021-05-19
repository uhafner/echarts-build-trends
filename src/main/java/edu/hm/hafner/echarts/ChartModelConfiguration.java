package edu.hm.hafner.echarts;

/**
 * Configures the model of a trend chart.
 *
 * @author Ullrich Hafner
 */
public class ChartModelConfiguration {
    static final int DEFAULT_BUILD_COUNT = 50;
    static final int DEFAULT_DAY_COUNT = 0;
    static final AxisType DEFAULT_DOMAIN_AXIS_TYPE = AxisType.BUILD;

    private static final String NUMBER_OF_BUILDS_PROPERTY = "numberOfBuilds";
    private static final String NUMBER_OF_DAYS_PROPERTY = "numberOfDays";
    private static final String BUILD_AS_DOMAIN_PROPERTY = "buildAsDomain";
    private static final JacksonFacade JACKSON = new JacksonFacade();

    private final AxisType axisType;

    private final int buildCount;
    private final int dayCount;

    /**
     * Creates a new chart configuration with the Jenkins build number as X-Axis.
     */
    public ChartModelConfiguration() {
        this(DEFAULT_DOMAIN_AXIS_TYPE);
    }

    /**
     * Creates a new chart configuration with the specified X-Axis type.
     *
     * @param axisType
     *         the type of the X-Axis
     */
    public ChartModelConfiguration(final AxisType axisType) {
        this(axisType, DEFAULT_BUILD_COUNT, DEFAULT_DAY_COUNT);
    }

    /**
     * Creates a new chart configuration with the specified X-Axis type.
     *
     * @param axisType
     *         the type of the X-Axis
     * @param buildCount
     *         the number of builds to consider
     * @param dayCount
     *         the number of days to consider
     */
    public ChartModelConfiguration(final AxisType axisType, final int buildCount, final int dayCount) {
        this.axisType = axisType;
        this.buildCount = buildCount < 0 ? DEFAULT_BUILD_COUNT : buildCount;
        this.dayCount = dayCount < 0 ? DEFAULT_DAY_COUNT : dayCount;
    }

    /**
     * Creates a new chart configuration from the specified JSON configuration object.
     *
     * @param json
     *         the string in JSON representation that contains the configuration
     *
     * @return the created configuration instance
     */
    public static ChartModelConfiguration fromJson(final String json) {
        return new ChartModelConfiguration(
                JACKSON.getBoolean(json, BUILD_AS_DOMAIN_PROPERTY, true) ? AxisType.BUILD : AxisType.DATE,
                JACKSON.getInteger(json, NUMBER_OF_BUILDS_PROPERTY, ChartModelConfiguration.DEFAULT_BUILD_COUNT),
                JACKSON.getInteger(json, NUMBER_OF_DAYS_PROPERTY, ChartModelConfiguration.DEFAULT_DAY_COUNT));
    }

    /**
     * Returns the type of the X-axis.
     *
     * @return the X-axis type
     */
    public AxisType getAxisType() {
        return axisType;
    }

    /**
     * Returns the number of builds to consider.
     *
     * @return the number of builds to consider
     */
    public int getBuildCount() {
        return buildCount;
    }

    /**
     * Returns whether a valid build count is defined.
     *
     * @return {@code true} if there is a valid build count is defined, {@code false} otherwise
     */
    public boolean isBuildCountDefined() {
        return buildCount > 1;
    }

    /**
     * Returns the number of days to consider.
     *
     * @return the number of days to consider
     */
    public int getDayCount() {
        return dayCount;
    }

    /**
     * Returns whether a valid day count is defined.
     *
     * @return {@code true} if there is a valid day count is defined, {@code false} otherwise
     */
    public boolean isDayCountDefined() {
        return dayCount > 0;
    }

    /** Type of the X-Axis. */
    public enum AxisType {
        /** Build IDs or numbers. */
        BUILD,
        /** Dates with build results. */
        DATE
    }
}
