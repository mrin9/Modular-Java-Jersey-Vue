export const miniDonutOptions = {
  cutoutPercentage: 75,
  legend: {
    display: false,
  },
  tooltips: {
    enabled: false,
  },
};

export const timeChartOptions = {
  legend: {
    display: false,
  },
  tooltips: {
    mode: 'index',
    intersect: false,
    xPadding: 5,
    cornerRadius: 2,
    caretSize: 0,
    bodyFontSize: 11,
    titleFontSize: 11,
  },
  scales: {
    yAxes: [{
      stacked: true,
      ticks: {
        beginAtZero: true,
        fontSize: 10,
      },
      gridLines: {
        display: true,
        drawBorder: false,
      },
      barPercentage: 1,
      categoryPercentage: 0.50,
    }],
    xAxes: [{
      stacked: true,
      type: 'time',
      ticks: {
        display: true,
        fontSize: 10,
        autoSkip: true,
        autoSkipPadding: 5,
        maxRotation: 0,
      },
      gridLines: {
        display: true,
        drawBorder: true,
        drawTicks: true,
        drawOnChartArea: false,
        color: '#333',
        lineWidth: 1.5,
        tickMarkLength: 10,
      },
      barPercentage: 0.90,
      // categoryPercentage:1
    }],
  },
};
