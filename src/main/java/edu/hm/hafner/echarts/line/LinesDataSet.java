package edu.hm.hafner.echarts.line;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.DoubleStream;

import static java.util.stream.Collectors.*;

/**
 * Model of a line chart with multiple data sets. A unique ID represents each data-set. The actual data of each
 * data set is stored in a list of double values which represent a value for an X-axis tick. To get multiple
 * data sets correctly aligned, the data points for each data set must contain exactly the same number of values.
 *
 * @author Ullrich Hafner
 */
public class LinesDataSet {
    private final Map<String, List<Double>> dataSetSeries = new HashMap<>();
    private final List<String> domainAxisLabels = new ArrayList<>();
    private final List<Integer> buildNumbers = new ArrayList<>();
    private int decimals = 2;

    public int getDomainAxisSize() {
        return domainAxisLabels.size();
    }

    public List<String> getDomainAxisLabels() {
        return domainAxisLabels;
    }

    public Set<String> getDataSetIds() {
        return dataSetSeries.keySet();
    }

    public void setDecimals(final int decimals) {
        this.decimals = decimals;
    }

    /**
     * Returns whether the specified data set series exists.
     *
     * @param dataSetId
     *         the ID of the series
     *
     * @return {@code true} if the series exists, {@code false} otherwise
     */
    public boolean containsSeries(final String dataSetId) {
        return dataSetSeries.containsKey(dataSetId);
    }

    public boolean isEmpty() {
        return domainAxisLabels.isEmpty();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    /**
     * Returns the data series of the specified dataSetId.
     *
     * @param dataSetId
     *         the ID of the series
     *
     * @return the series (list of integer values for each X-Axis label)
     */
    public List<Double> getSeries(final String dataSetId) {
        if (!containsSeries(dataSetId)) {
            throw new NoSuchElementException("No dataset '%s' registered".formatted(dataSetId));
        }

        return getSeriesFromMap(dataSetId).stream().map(this::round).collect(toList());
    }

    /**
     * Returns the minimum value within all data sets.
     *
     * @return the minimum value
     * @throws NoSuchElementException
     *         if there is no value in the data set
     */
    public double getMinimumValue() {
        return streamAllValues()
                .min()
                .orElseThrow(NoSuchElementException::new);
    }

    /**
     * Returns the maximum value within all data sets.
     *
     * @return the maximum value
     * @throws NoSuchElementException
     *         if there is no value in the data set
     */
    public double getMaximumValue() {
        return streamAllValues()
                .max()
                .orElseThrow(NoSuchElementException::new);
    }

    private DoubleStream streamAllValues() {
        return dataSetSeries.values().stream()
                .flatMap(List::stream)
                .map(this::round)
                .mapToDouble(Double::doubleValue);
    }

    /**
     * Adds data points for a new domainAxisLabel. The data points for the X-axis tick are given by a map. Each
     * dataSetId provides one value for the specified X-axis label.
     *
     * @param domainAxisLabel
     *         the label of the X-axis
     * @param dataSetValues
     *         the values for each of the series at the given X-axis tick
     */
    public void add(final String domainAxisLabel, final Map<String, Double> dataSetValues) {
        domainAxisLabels.add(domainAxisLabel);

        for (Map.Entry<String, Double> dataPoints : dataSetValues.entrySet()) {
            dataSetSeries.putIfAbsent(dataPoints.getKey(), new ArrayList<>());
            getSeriesFromMap(dataPoints.getKey()).add(Objects.requireNonNull(dataPoints.getValue()));
        }
    }

    /**
     * Adds data points for a new domainAxisLabel. The data points for the X-axis tick are given by a map. Each
     * dataSetId provides one value for the specified X-axis label.
     *
     * @param domainAxisLabel
     *         the label of the X-axis
     * @param dataSetValues
     *         the values for each of the series at the given X-axis tick
     * @param buildNumber
     *         the number of the associated build
     */
    public void add(final String domainAxisLabel, final Map<String, Double> dataSetValues, final int buildNumber) {
        add(domainAxisLabel, dataSetValues);

        if (buildNumbers.contains(buildNumber)) {
            throw new IllegalStateException("Build number already registered: " + buildNumber);
        }

        buildNumbers.add(buildNumber);
    }

    private List<Double> getSeriesFromMap(final String key) {
        return Objects.requireNonNull(dataSetSeries.get(key));
    }

    private double round(final double value) {
        return Math.round(value * Math.pow(10, decimals)) / Math.pow(10, decimals);
    }

    public List<Integer> getBuildNumbers() {
        return buildNumbers;
    }
}
