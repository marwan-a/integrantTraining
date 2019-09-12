import * as CanvasJS from '../canvasjs.min';
export function createChart(dps) {
    var chart = new CanvasJS.Chart("chartContainer", {
      animationEnabled: true,
      exportEnabled: true,
      title: {
        text: "Tweets Sentiment (chart updates automatically every 10 seconds)"
      },
      data: [{
        type: "column",
        dataPoints: dps
      }]
    });
    chart.render();
  }