import React, { useEffect, useState } from 'react';
import { Grid } from '@mui/material';
import Highcharts from 'highcharts';
import HighchartsReact from 'highcharts-react-official';
import drilldown from 'highcharts/modules/drilldown';
import { fetchBranch } from '../../pages/dashboard/BranchDetails/action';


drilldown(Highcharts);

const getColorPalette = (length) => {
  const colors = Highcharts.getOptions().colors;
  const palette = [];
  for (let i = 0; i < length; i++) {
    palette.push(colors[i % colors.length]);
  }
  return palette;
};

const BranchSearch = () => {
  const [chartOptions, setChartOptions] = useState(null);

  useEffect(() => {
    fetchBranch().then((res) => {
      const branches = [...new Set(res.data.map((item) => item.branch))];
      const branchColors = getColorPalette(branches.length);

      const initialChartOptions = {
        chart: {
          type: 'column'
        },
        title: {
          text: 'Branches Drilldown'
        },
        xAxis: {
          type: 'category'
        },
        yAxis: {
          title: {
            text: 'Total'
          }
        },
        series: [
          {
            name: 'Branches',
            colorByPoint: true,
            data: branches.map((branch, index) => ({
              name: branch,
              y: res.data.filter((item) => item.branch === branch).length,
              drilldown: branch,
              color: branchColors[index]
            }))
          }
        ],
        drilldown: {
          series: branches
            .map((branch) => {
              const regions = [...new Set(res.data.filter((item) => item.branch === branch).map((item) => item.region))];
              const regionColors = getColorPalette(regions.length);

              return {
                name: branch,
                id: branch,
                data: regions.map((region, index) => ({
                  name: region,
                  y: res.data.filter((item) => item.branch === branch && item.region === region).length,
                  drilldown: `${branch}-${region}`,
                  color: regionColors[index]
                }))
              };
            })
            .concat(
              branches.flatMap((branch) => {
                const regions = [...new Set(res.data.filter((item) => item.branch === branch).map((item) => item.region))];
                return regions.map((region) => {
                  const sections = [
                    ...new Set(res.data.filter((item) => item.branch === branch && item.region === region).map((item) => item.section))
                  ];
                  const sectionColors = getColorPalette(sections.length);

                  return {
                    name: region,
                    id: `${branch}-${region}`,
                    data: sections.map((section, index) => ({
                      name: section,
                      y: res.data.filter((item) => item.branch === branch && item.region === region && item.section === section).length,
                      drilldown: null,
                      color: sectionColors[index]
                    }))
                  };
                });
              })
            )
        }
      };

      setChartOptions(initialChartOptions);
    });
  }, []);

  const handlePointClick = (e) => {
    const clickedPoint = e.point;
    if (clickedPoint.drilldown) {
      if (chartOptions.drilldown) {
        const drilldownSeries = chartOptions.drilldown.series.find((series) => series.id === clickedPoint.drilldown);
        if (drilldownSeries) {
          setChartOptions({
            ...chartOptions,
            series: [
              {
                name: drilldownSeries.name,
                colorByPoint: true,
                data: drilldownSeries.data.map((regionData) => ({
                  name: regionData.name,
                  y: regionData.y,
                  drilldown: regionData.drilldown,
                  color: regionData.color
                }))
              }
            ],
            drilldown: {
              series: chartOptions.drilldown.series.filter((series) => series.id !== clickedPoint.drilldown)
            }
          });
        }
      }
    }
  };

  return (
    <Grid container spacing={1}>
      <Grid item xs={12}>
        {chartOptions && (
          <HighchartsReact
            highcharts={Highcharts}
            options={chartOptions}
            callback={(chart) => {
              chart.series[0]?.points?.forEach((point) => {
                point.update({
                  events: {
                    click: handlePointClick
                  }
                });
              });
            }}
          />
        )}
      </Grid>
    </Grid>
  );
};

export default BranchSearch;
