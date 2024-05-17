/* global jQuery3 */
jQuery3(document).ready(function () {
    renderPieCharts();
});

/**
 * Renders all charts that have the class 'echarts' using ECharts.
 */
function renderPieCharts() {
    /**
     * Renders a trend chart in the a div using ECharts.
     *
     * @param {String} chartDivId - the ID of the div where the chart should be shown in
     */
    function renderPieChart(chartDivId) {
        function isEmpty(string) {
            return (!string || string.length === 0);
        }

        /**
         * Returns the title properties of the chart.
         *
         * @param {String} title - the title
         */
        function getTitle(title) {
            if (!isEmpty(title)) {
                return {
                    text: title,
                    textStyle: {
                        fontWeight: 'normal',
                        fontSize: '16'
                    },
                    left: 'center'
                };
            }
            return null;
        }

        const chartPlaceHolder = jQuery3("#" + chartDivId);
        const model = JSON.parse(chartPlaceHolder.attr('data-chart-model'));
        const title = chartPlaceHolder.attr('data-title');
        const chartDiv = chartPlaceHolder[0];
        const chart = echarts.init(chartDiv);
        chartDiv.echart = chart;

        const options = {
            title: getTitle(title),
            tooltip: {
                trigger: 'item',
                formatter: '{b}: {c} ({d}%)'
            },
            legend: {
                orient: 'horizontal',
                x: 'center',
                y: 'bottom',
                type: 'scroll'
            },
            series: [{
                type: 'pie',
                radius: ['30%', '70%'],
                avoidLabelOverlap: false,
                color: model.colors,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: false
                    }
                },
                labelLine: {
                    normal: {
                        show: true
                    }
                },
                data: model.data
            }
            ]
        };
        chart.setOption(options);
        chart.resize();

        const useLinks = chartPlaceHolder.attr('data-links');
        if (useLinks && useLinks !== "false") {
            chart.on('click', function (params) {
                window.location.assign(params.name);
            });
        }

        window.onresize = function () {
            chart.resize();
        };
    }

    const allCharts = jQuery3('div.echarts-pie-chart');
    allCharts.each(function () {
        const chart = jQuery3(this);
        const id = chart.attr('id');
        renderPieChart(id);
    });
}

