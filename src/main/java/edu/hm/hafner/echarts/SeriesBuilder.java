package edu.hm.hafner.echarts;

import edu.hm.hafner.echarts.ChartModelConfiguration.AxisType;
import edu.hm.hafner.util.VisibleForTesting;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * Provides the base algorithms to create a data set for a static analysis graph. The actual series for each result
 * needs to be implemented by subclasses in method {@link #computeSeries}.
 *
 * @param <T>
 *         type of the result
 *
 * @author Ullrich Hafner
 */
// FIXME: can we change the signature to double?
public abstract class SeriesBuilder<T> {
    private final ResultTime resultTime;

    /**
     * Creates a new {@link SeriesBuilder}.
     */
    protected SeriesBuilder() {
        this(new ResultTime());
    }

    @VisibleForTesting
    SeriesBuilder(final ResultTime resultTime) {
        this.resultTime = resultTime;
    }

    /**
     * Creates a new data set for a category graph from the specified static analysis results. The list of results (each
     * one provided by an iterator) must be sorted by build number in descending order. I.e., the iterator starts with
     * the newest build and stops at the oldest build. The actual series for each result needs to be implemented by
     * subclasses by overriding method {@link #computeSeries}.
     *
     * @param configuration
     *         configures the data set (how many results should be process, etc.)
     * @param results
     *         the ordered static analysis results
     *
     * @return the created data set
     */
    public LinesDataSet createAggregatedDataSet(final ChartModelConfiguration configuration,
            final List<Iterable<? extends BuildResult<T>>> results) {
        return createDataSetPerDay(averageByDate(configuration, results));
    }

    /**
     * Creates a new data set for a category graph from the specified static analysis results. The results (provided by
     * an iterator) must be sorted by build number in descending order. I.e., the iterator starts with the newest build
     * and stops at the oldest build. The actual series for each result needs to be implemented by sub classes by
     * overriding method {@link #computeSeries}.
     *
     * @param configuration
     *         configures the data set (how many results should be process, etc.)
     * @param results
     *         the ordered static analysis results
     *
     * @return the created data set
     */
    public LinesDataSet createDataSet(final ChartModelConfiguration configuration,
            final Iterable<? extends BuildResult<T>> results) {
        SortedMap<Build, Map<String, Integer>> seriesPerBuild = createSeriesPerBuild(configuration, results);

        if (configuration.getAxisType() == AxisType.BUILD) {
            return createDataSetPerBuildNumber(seriesPerBuild);
        }
        else {
            return createDataSetPerDay(averageByDate(seriesPerBuild));
        }
    }

    private SortedMap<Build, Map<String, Integer>> createSeriesPerBuild(
            final ChartModelConfiguration configuration, final Iterable<? extends BuildResult<T>> results) {
        int buildCount = 0;
        SortedMap<Build, Map<String, Integer>> valuesPerBuildNumber = new TreeMap<>();
        for (BuildResult<T> current : results) {
            if (resultTime.isResultTooOld(configuration, current)) {
                break;
            }
            Map<String, Integer> series = computeSeries(current.getResult());
            if (!series.isEmpty()) {
                valuesPerBuildNumber.put(current.getBuild(), series);
            }

            if (configuration.isBuildCountDefined()) {
                buildCount++;
                if (buildCount >= configuration.getBuildCount()) {
                    break;
                }
            }
        }
        fillMissingValues(valuesPerBuildNumber);

        return valuesPerBuildNumber;
    }

    private void fillMissingValues(final SortedMap<Build, Map<String, Integer>> valuesPerBuildNumber) {
        Set<String> dataSets = valuesPerBuildNumber.values()
                .stream()
                .flatMap(values -> Stream.of(values.keySet()))
                .flatMap(Set::stream)
                .collect(toSet());

        for (Map<String, Integer> series : valuesPerBuildNumber.values()) {
            dataSets.forEach(dataSet -> series.putIfAbsent(dataSet, 0));
        }
    }

    /**
     * Returns the series to plot for the specified build result.
     *
     * @param current
     *         the result of the current build for which the series should be computed
     *
     * @return the series to plot
     */
    protected abstract Map<String, Integer> computeSeries(T current);

    /**
     * Creates a data set that contains a series per build number.
     *
     * @param valuesPerBuild
     *         the collected values
     *
     * @return a data set
     */
    private LinesDataSet createDataSetPerBuildNumber(
            final SortedMap<Build, Map<String, Integer>> valuesPerBuild) {
        var model = new LinesDataSet();
        for (Map.Entry<Build, Map<String, Integer>> series : valuesPerBuild.entrySet()) {
            model.add(series.getKey().getDisplayName(), series.getValue(), series.getKey().getNumber());
        }
        return model;
    }

    /**
     * Creates a data set that contains one series of values per day.
     *
     * @param averagePerDay
     *         the collected values averaged by day
     *
     * @return a data set
     */
    private LinesDataSet createDataSetPerDay(final SortedMap<LocalDate, Map<String, Integer>> averagePerDay) {
        var model = new LinesDataSet();
        for (Map.Entry<LocalDate, Map<String, Integer>> series : averagePerDay.entrySet()) {
            var label = new LocalDateLabel(series.getKey()).toString();
            model.add(label, series.getValue());
        }
        return model;
    }

    /**
     * Aggregates multiple series per day to one single series per day by computing the average value.
     *
     * @param multiSeriesPerDate
     *         the values given as multiple series per day
     *
     * @return the values as one series per day (average)
     */
    private SortedMap<LocalDate, Map<String, Integer>> createSeriesPerDay(
            final Map<LocalDate, List<Map<String, Integer>>> multiSeriesPerDate) {
        SortedMap<LocalDate, Map<String, Integer>> seriesPerDate = new TreeMap<>();

        for (Entry<LocalDate, List<Map<String, Integer>>> entry : multiSeriesPerDate.entrySet()) {
            List<Map<String, Integer>> seriesPerDay = entry.getValue();

            Map<String, Integer> mapOfDay =
                    seriesPerDay.stream()
                            .flatMap(m -> m.entrySet().stream())
                            .collect(groupingBy(Map.Entry::getKey, summingInt(Map.Entry::getValue)));
            Map<String, Integer> averagePerDay =
                    mapOfDay.entrySet().stream()
                            .collect(toMap(Map.Entry::getKey, e -> e.getValue() / seriesPerDay.size()));
            seriesPerDate.put(entry.getKey(), averagePerDay);
        }
        return seriesPerDate;
    }

    /**
     * Aggregates the series per build to a series per date.
     *
     * @param valuesPerBuild
     *         the series per build
     *
     * @return the series per date
     */
    private SortedMap<LocalDate, Map<String, Integer>> averageByDate(
            final SortedMap<Build, Map<String, Integer>> valuesPerBuild) {
        return createSeriesPerDay(createMultiSeriesPerDay(valuesPerBuild));
    }

    private SortedMap<LocalDate, Map<String, Integer>> averageByDate(final ChartModelConfiguration configuration,
            final List<Iterable<? extends BuildResult<T>>> results) {
        Map<LocalDate, List<Map<String, Integer>>> valuesPerDate = new TreeMap<>();
        for (Iterable<? extends BuildResult<T>> result : results) {
            valuesPerDate.putAll(createMultiSeriesPerDay(createSeriesPerBuild(configuration, result)));
        }
        return createSeriesPerDay(valuesPerDate);
    }

    /**
     * Creates a mapping of values per day.
     *
     * @param valuesPerBuild
     *         the values per build
     *
     * @return the multi map with the values per day
     */
    @SuppressFBWarnings("WMI")
    private Map<LocalDate, List<Map<String, Integer>>> createMultiSeriesPerDay(
            final Map<Build, Map<String, Integer>> valuesPerBuild) {
        Map<LocalDate, List<Map<String, Integer>>> valuesPerDate = new TreeMap<>();
        for (Build build : valuesPerBuild.keySet()) {
            var buildDate = TimeFacade.getInstance().getBuildDate(build);
            valuesPerDate.computeIfAbsent(buildDate, k -> new ArrayList<>()).add(valuesPerBuild.get(build));
        }
        return valuesPerDate;
    }
}
