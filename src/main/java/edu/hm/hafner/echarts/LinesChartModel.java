package edu.hm.hafner.echarts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.hm.hafner.util.Generated;
import edu.umd.cs.findbugs.annotations.CheckForNull;

/**
 * UI model for an ECharts line chart. Simple data bean that will be converted to JSON. On the client side the
 * properties need to be placed into the correct place in the options structure.
 * <p>
 * This class will be automatically converted to a JSON object.
 * </p>
 *
 * @author Ullrich Hafner
 */
@SuppressWarnings("PMD.DataClass")
public class LinesChartModel {
    private final List<String> domainAxisLabels = new ArrayList<>();
    private final List<Integer> buildNumbers = new ArrayList<>();
    private final List<LineSeries> series = new ArrayList<>();

    private String domainAxisItemName = "Build";
    private boolean integerRangeAxis = true;

    @CheckForNull
    private Integer rangeMax;
    @CheckForNull
    private Integer rangeMin;

    /**
     * Creates a new {@link LinesChartModel}.
     *
     * @param dataSet
     *         the dataset to render
     */
    public LinesChartModel(final LinesDataSet dataSet) {
        this();

        if (!dataSet.isEmpty()) {
            domainAxisLabels.addAll(dataSet.getDomainAxisLabels());
            buildNumbers.addAll(dataSet.getBuildNumbers());
        }
    }

    /**
     * Creates a new {@link LinesChartModel}.
     *
     * @param labels
     *         the X-axis labels of the model
     * @param builds
     *         the build numbers of the model
     */
    public LinesChartModel(final List<String> labels, final List<Integer> builds) {
        domainAxisLabels.addAll(labels);
        buildNumbers.addAll(builds);
    }

    /**
     * Creates a new {@link LinesChartModel}.
     */
    public LinesChartModel() {
        // nothing to do
    }

    /**
     * Sets the name of the X-axis items. This name is used in the tooltip of the charts.
     *
     * @param name
     *         the name
     */
    public void setDomainAxisItemName(final String name) {
        domainAxisItemName = name;
    }

    public String getDomainAxisItemName() {
        return domainAxisItemName;
    }

    /**
     * Sets the type of the range axis (Y-axis) to a continuous axis. Otherwise, an integer axis is used.
     */
    public void useContinuousRangeAxis() {
        integerRangeAxis = false;
    }

    public boolean isIntegerRangeAxis() {
        return integerRangeAxis;
    }

    /**
     * Sets the maximum value of the range axis. By default, the maximum is automatically computed.
     *
     * @param rangeMax
     *         the maximum to use
     */
    public void setRangeMax(final int rangeMax) {
        this.rangeMax = rangeMax;
    }

    @CheckForNull
    public Integer getRangeMax() {
        return rangeMax;
    }

    /**
     * Sets the minimum value of the range axis. By default, the minimum is automatically computed.
     *
     * @param rangeMin
     *         the minimum to use
     */
    public void setRangeMin(final int rangeMin) {
        this.rangeMin = rangeMin;
    }

    @CheckForNull
    public Integer getRangeMin() {
        return rangeMin;
    }

    /**
     * Adds the specified domain axis (X-axis) labels to this model.
     *
     * @param labels
     *         the X-axis labels of the model
     */
    public void setDomainAxisLabels(final List<String> labels) {
        domainAxisLabels.addAll(labels);
    }

    /**
     * Adds the specified build numbers to this model.
     *
     * @param builds
     *         the build numbers of the model
     */
    public void setBuildNumbers(final List<Integer> builds) {
        buildNumbers.addAll(builds);
    }

    /**
     * Adds the series to this model.
     *
     * @param lineSeries
     *         the series of the model
     */
    void addSeries(final List<LineSeries> lineSeries) {
        series.addAll(lineSeries);
    }

    /**
     * Adds the series to this model.
     *
     * @param lineSeries
     *         the series of the model
     */
    public void addSeries(final LineSeries... lineSeries) {
        Collections.addAll(series, lineSeries);
    }

    public List<String> getDomainAxisLabels() {
        return domainAxisLabels;
    }

    public List<Integer> getBuildNumbers() {
        return buildNumbers;
    }

    public List<LineSeries> getSeries() {
        return series;
    }

    /**
     * Returns the number of points in the series.
     *
     * @return number of points
     */
    public int size() {
        return domainAxisLabels.size();
    }

    @Override
    @Generated
    public String toString() {
        return new JacksonFacade().toJson(this);
    }
}
