package com.mtrading.mobile.graph;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.afree.chart.AFreeChart;
import org.afree.chart.ChartFactory;
import org.afree.chart.axis.NumberAxis;
import org.afree.chart.axis.NumberTickUnit;
import org.afree.chart.axis.TickUnit;
import org.afree.chart.axis.TickUnitSource;
import org.afree.chart.axis.ValueAxis;
import org.afree.chart.event.ChartProgressEvent;
import org.afree.chart.event.ChartProgressListener;
import org.afree.chart.plot.PlotOrientation;
import org.afree.chart.plot.ValueMarker;
import org.afree.chart.plot.XYPlot;
import org.afree.chart.renderer.xy.StandardXYBarPainter;
import org.afree.chart.renderer.xy.XYBarRenderer;
import org.afree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.afree.data.time.Second;
import org.afree.data.xy.XYDataItem;
import org.afree.data.xy.XYDataset;
import org.afree.data.xy.XYSeries;
import org.afree.data.xy.XYSeriesCollection;
import org.afree.graphics.PaintType;
import org.afree.graphics.SolidColor;
import org.afree.ui.LengthAdjustmentType;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.fss.mobiletrading.common.StaticObjectManager;
import com.fss.mobiletrading.consts.StringConst;
import com.fscuat.mobiletrading.R;

public class IndexVolumeChartView extends CustomChartView {
	static int verticallinecolor = Color.parseColor("#DDDDDD");
	static int horizontallinecolor = Color.parseColor("#DDDDDD");
	static int domainCrosshairColor = Color.WHITE;
	static int rangeCrosshairColor = Color.WHITE;
	static int volumeBarColor = Color.parseColor("#50ffffff");
	static double barWidth = 0.2d;

	final static int startday = 32400;
	final static int endday = 54000;
	static int default_time = startday;
	final static String colon = ":";

	public static double averageIndex;

	IndexVolumeChartPoint firstPoint;
	SimpleDateFormat df2 = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss");
	static String upperDate = StaticObjectManager.loginInfo.TxDateString
			+ " 15:00:01";
	SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
	Second endDay = Second.parseSecond(upperDate);
	DecimalFormat indexFormat = new DecimalFormat("00.0");
	DecimalFormat volumeFormat = new DecimalFormat("#.# M");
	DecimalFormat fullvolFormat = new DecimalFormat("#,###");
	IndexVolumeChartPoint viewItem;

	Context context;
	List<IndexVolumeChartPoint> data;
	AFreeChart afchart;
	double maxIndex;
	double minIndex;
	double maxVolume;
	double minVolume;
	XYDataset indexDataSet = new XYSeriesCollection();
	XYDataset volumeDataSet = new XYSeriesCollection();
	XYSeries indexXYSeries = new XYSeries(StringConst.EMPTY);
	XYSeries volumeXYSeries = new XYSeries(StringConst.EMPTY);

	public IndexVolumeChartView(Context context,
			List<IndexVolumeChartPoint> data) {
		super(context);
		this.data = new ArrayList<IndexVolumeChartPoint>();
		this.context = context;
		createSeries(data);
		createDataSet();
		afchart = createChart();
		setChart(afchart);
	}

	public double temp_AverageIndex = Double.MIN_VALUE;

	private void createSeries(List<IndexVolumeChartPoint> pr_data) {
		if (pr_data == null || pr_data.size() == 0) {
			indexXYSeries.clear();
			volumeXYSeries.clear();
			maxIndex = 0;
			maxVolume = 0;
			minIndex = 0;
			minVolume = 0;
			averageIndex = 0;
			return;
		}
		this.data.clear();
		this.data.addAll(pr_data);
		indexXYSeries.clear();
		volumeXYSeries.clear();

		// initData config
		firstPoint = data.get(0);
		maxIndex = firstPoint.getIndex();
		maxVolume = firstPoint.getVolume();
		minIndex = firstPoint.getIndex();
		minVolume = firstPoint.getVolume();
		if (temp_AverageIndex == Double.MIN_VALUE) {
			averageIndex = firstPoint.getIndex();
		} else {
			averageIndex = temp_AverageIndex;
		}

		int datasize = data.size();
		for (int i = 0; i < datasize; i++) {
			IndexVolumeChartPoint item = data.get(i);
			// update serier
			indexXYSeries.add(item.getTime(), item.getIndex());
			volumeXYSeries.add(item.getTime(), item.getVolume());

			// update min max
			if (maxIndex < item.getIndex()) {
				maxIndex = item.getIndex();
			}
			if (maxVolume < item.getVolume()) {
				maxVolume = item.getVolume();
			}
			if (minIndex > item.getIndex()) {
				minIndex = item.getIndex();
			}
			if (minVolume > item.getVolume()) {
				minVolume = item.getVolume();
			}
		}
	}

	private boolean addToSeries(List<IndexVolumeChartPoint> pr_data) {
		if (pr_data == null || pr_data.size() == 0) {
			return false;
		}
		// initData config
		if (firstPoint == null) {
			firstPoint = pr_data.get(0);
			maxIndex = firstPoint.getIndex();
			maxVolume = firstPoint.getVolume();
			minIndex = firstPoint.getIndex();
			minVolume = firstPoint.getVolume();
			if (temp_AverageIndex == Double.MIN_VALUE) {
				averageIndex = firstPoint.getIndex();
			} else {
				averageIndex = temp_AverageIndex;
			}
		}
		int datasize = pr_data.size();

		for (int i = 0; i < datasize; i++) {
			IndexVolumeChartPoint item = pr_data.get(i);
			// update serier
			indexXYSeries.add(item.getTime(), item.getIndex());
			volumeXYSeries.add(item.getTime(), item.getVolume());
			// update min max
			if (maxIndex < item.getIndex()) {
				maxIndex = item.getIndex();
			}
			if (maxVolume < item.getVolume()) {
				maxVolume = item.getVolume();
			}
			if (minIndex > item.getIndex()) {
				minIndex = item.getIndex();
			}
			if (minVolume > item.getVolume()) {
				minVolume = item.getVolume();
			}
		}

		this.data.addAll(pr_data);
		return true;
	}

	private void createDataSet() {
		((XYSeriesCollection) indexDataSet).addSeries(indexXYSeries);
		((XYSeriesCollection) volumeDataSet).addSeries(volumeXYSeries);
	}

	private AFreeChart createChart() {
		if (context == null) {
			return null;
		}
		AFreeChart afchart = ChartFactory.createXYLineChart(null, // title
				null, // x-axis label
				null, // y-axis label
				indexDataSet, // data
				PlotOrientation.VERTICAL, false, // create legend?
				false, // generate tooltips?
				false // generate URLs?
				);

		// set background color
		afchart.setBackgroundPaintType(new SolidColor(context.getResources()
				.getColor(R.color.transparent)));

		XYPlot plot = (XYPlot) afchart.getPlot();
		// set background cho phan do thi ben trong truc
		plot.setBackgroundPaintType(new SolidColor(context.getResources()
				.getColor(R.color.transparent)));

		plot.setDomainGridlinesVisible(false);
		plot.setRangeGridlinesVisible(false);

		// visible crosshair khi click vào 1 điểm trên đồ thị
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		// set màu cho 2 đường crosshair
		plot.setDomainCrosshairPaintType(new SolidColor(domainCrosshairColor));
		plot.setRangeCrosshairPaintType(new SolidColor(rangeCrosshairColor));

		IndexRenderer myRenderer = new IndexRenderer(indexXYSeries);
		plot.setRenderer(myRenderer);

		// trục thời gian
		ValueAxis axis = (ValueAxis) plot.getDomainAxis();
		axis.setRangeWithMargins(startday, endday);
		axis.setTickLabelPaintType(new SolidColor(Color.WHITE));
		axis.setStandardTickUnits(new TickUnitSource() {

			@Override
			public TickUnit getLargerTickUnit(TickUnit arg0) {

				return arg0;
			}

			@Override
			public TickUnit getCeilingTickUnit(double arg0) {
				CustomDomainTickUnit tickUnit = new CustomDomainTickUnit(5000);
				return tickUnit;
			}

			@Override
			public TickUnit getCeilingTickUnit(TickUnit arg0) {
				return arg0;
			}
		});

		// trục index
		NumberAxis indexAxis = (NumberAxis) plot.getRangeAxis();
		double tickUnitIndex = (maxIndex - minIndex) / 2;
		indexAxis.setRange(minIndex - 1, maxIndex + 1);
		indexAxis.setTickLabelsVisible(true);
		indexAxis.setTickUnit(new NumberTickUnit(tickUnitIndex));
		indexAxis.setTickLabelPaintType(new SolidColor(Color.WHITE));
		indexAxis.setNumberFormatOverride(indexFormat);

		// Vẽ đường trung bình
		ValueMarker averageLine = new ValueMarker(averageIndex);
		averageLine.setLabelOffsetType(LengthAdjustmentType.EXPAND);
		averageLine.setPaintType(new SolidColor(Color.YELLOW));
		averageLine.setStroke(2.0F);
		plot.addRangeMarker(averageLine);

		// trục volume
		double tickUnitVolume = (maxVolume - minVolume) / 2;
		NumberAxis volumeAxis = new NumberAxis();
		volumeAxis.setRangeWithMargins(minVolume, maxVolume);
		volumeAxis.setTickLabelPaintType(new SolidColor(Color.WHITE));
		volumeAxis.setNumberFormatOverride(volumeFormat);
		volumeAxis.setTickUnit(new NumberTickUnit(tickUnitVolume));
		plot.setRangeAxis(1, volumeAxis);

		VolumeRenderer renderer2 = new VolumeRenderer(volumeXYSeries);
		plot.setDataset(1, volumeDataSet);
		plot.setRenderer(1, renderer2);
		plot.mapDatasetToRangeAxis(1, 1);

		// xu ly su kien chart thay doi
		afchart.addProgressListener(new ChartProgressListener() {

			@Override
			public void chartProgress(ChartProgressEvent event) {
				if (onFinishedDrawChart != null) {
					onFinishedDrawChart.onFinishedDraw();
				}
				if (itemClick == null) {
					XYPlot plot = (XYPlot) event.getChart().getPlot();
					double xValue = plot.getDomainCrosshairValue();
					int index = indexXYSeries.indexOf(xValue);
					if (data.size() > index && index >= 0) {
						IndexVolumeChartPoint item = data.get(index);
						if (onChartClickListener != null) {
							int color;
							if (item.getIndex() > averageIndex) {
								color = Color.GREEN;
							} else if (item.getIndex() < averageIndex) {
								color = Color.RED;
							} else {
								color = Color.YELLOW;
							}
							onChartClickListener.onClick(item.strTime,
									String.valueOf(item.getIndex()),
									fullvolFormat.format(item.getFullvolume()),
									color, item);
						}
					}
				} else {
					if (onChartClickListener != null) {
						int color;
						if (itemClick.getIndex() > averageIndex) {
							color = Color.GREEN;
						} else if (itemClick.getIndex() < averageIndex) {
							color = Color.RED;
						} else {
							color = Color.YELLOW;
						}
						onChartClickListener.onClick(
								itemClick.strTime,
								String.valueOf(itemClick.getIndex()),
								fullvolFormat.format(itemClick.getFullvolume()),
								color, itemClick);
						count++;
						if (count == 2) {
							itemClick = null;
						}
					}
				}
			}
		});
		return afchart;
	}

	public void createChart(List<IndexVolumeChartPoint> dataToUpdate) {
		createSeries(dataToUpdate);
		updateRange(afchart);
		afchart.fireChartChanged();
		repaint();
	}

	public void updateChart(List<IndexVolumeChartPoint> dataToUpdate) {
		boolean isUpdated = addToSeries(dataToUpdate);
		if (isUpdated) {

			updateRange(afchart);
			afchart.fireChartChanged();
			repaint();
		}
	}

	public void createChart2(List<IndexVolumeChartPoint> dataToUpdate) {
		createSeries(dataToUpdate);
		updateRange(afchart);
	}

	public void updateChart2(List<IndexVolumeChartPoint> dataToUpdate) {
		boolean isUpdated = addToSeries(dataToUpdate);
		if (isUpdated) {
			updateRange(afchart);
		}
	}

	/**
	 * lưu lại item đang được click vào tradelog
	 */
	IndexVolumeChartPoint itemClick;

	/**
	 * do việc set crosshair khiến chart gọi 2 lần hàm chartProgress, nên sẽ
	 * dùng biến count để đểm số lần vẽ <br>
	 * khi count == 2 (set xong crosshair) thì xóa biến itemClick
	 */
	int count = 0;

	public void setAutoClickChart(IndexVolumeChartPoint item) {
		XYPlot plot = (XYPlot) afchart.getPlot();
		plot.setRangeCrosshairValue(item.index);
		plot.setDomainCrosshairValue(item.time);
		itemClick = item;
		count = 0;
	}

	private void updateRange(AFreeChart chart) {
		XYPlot plot = chart.getXYPlot();
		// trục index

		NumberAxis indexAxis = (NumberAxis) plot.getRangeAxis(0);
		double tickUnitIndex = (maxIndex - minIndex) / 2;
		indexAxis.setRangeWithMargins(minIndex, maxIndex);
		indexAxis.setTickUnit(new NumberTickUnit(tickUnitIndex));
		// Vẽ đường trung bình
		plot.clearRangeMarkers();
		ValueMarker averageLine = new ValueMarker(averageIndex);
		averageLine.setLabelOffsetType(LengthAdjustmentType.EXPAND);
		averageLine.setPaintType(new SolidColor(Color.YELLOW));
		averageLine.setStroke(2.0F);
		plot.addRangeMarker(averageLine);
		// trục volume
		double tickUnitVolume = (maxVolume - minVolume) / 2;
		NumberAxis volumeAxis = (NumberAxis) plot.getRangeAxis(1);
		volumeAxis.setRangeWithMargins(minVolume, maxVolume);
		volumeAxis.setTickLabelPaintType(new SolidColor(Color.WHITE));
		volumeAxis.setNumberFormatOverride(volumeFormat);
		volumeAxis.setTickUnit(new NumberTickUnit(tickUnitVolume));
	}

	class IndexRenderer extends XYLineAndShapeRenderer {
		XYSeries data;

		public IndexRenderer(XYSeries data) {
			this.data = data;
			setBaseShapesVisible(false);
			setSeriesPaintType(0, new SolidColor(Color.parseColor("#66ccff")));
		}

		@Override
		public PaintType getItemPaintType(int row, int column) {
			if (data == null) {
				return super.getItemPaintType(row, column);
			}
			if (column == data.getItemCount() - 1) {
				return new SolidColor(Color.TRANSPARENT);
			} else {
				XYDataItem item = data.getDataItem(column);
				if (item == null) {
					return super.getItemPaintType(row, column);
				} else {
					if (item.getYValue() > averageIndex) {
						return new SolidColor(Color.GREEN);
					} else if (item.getYValue() < averageIndex) {
						return new SolidColor(Color.RED);
					} else {
						return new SolidColor(Color.RED);
					}
				}
			}

		}
	}

	class VolumeRenderer extends XYBarRenderer {
		XYSeries data;

		public VolumeRenderer(XYSeries data) {
			this.data = data;
			setMargin(barWidth);
			setBarPainter(new StandardXYBarPainter());
			setShadowVisible(false);
			setSeriesPaintType(0, new SolidColor(volumeBarColor));
			setBasePaintType(new SolidColor(volumeBarColor));
		}

		@Override
		public PaintType getItemPaintType(int row, int column) {
			return super.getItemPaintType(row, column);
		}
	}

	class CustomDomainTickUnit extends NumberTickUnit {

		public CustomDomainTickUnit(double size) {
			super(size);
		}

		@Override
		public String valueToString(double value) {
			return parseValueToStringTime((int) value);
		}

	}

	OnChartClickListener onChartClickListener;
	OnFinishedDrawChart onFinishedDrawChart;

	public void setOnChartClickListener(
			OnChartClickListener onChartClickListener) {
		this.onChartClickListener = onChartClickListener;
	}

	public void setOnFinishedDrawChart(OnFinishedDrawChart onFinishedDrawChart) {
		this.onFinishedDrawChart = onFinishedDrawChart;
	}

	public interface OnChartClickListener {
		public void onClick(String time, String index, String volume,
				int color, IndexVolumeChartPoint point);
	}

	public interface OnFinishedDrawChart {
		public void onFinishedDraw();
	}

	public static int parseTimeStringToInteger(String time) {
		int value = default_time;
		String[] arr = time.split(colon);
		if (arr.length == 3) {
			int hour = Integer.parseInt(arr[0]) * 3600;
			int minute = Integer.parseInt(arr[1]) * 60;
			int second = Integer.parseInt(arr[2]);
			value = hour + minute + second;
		}
		return value;
	}

	public static String parseValueToStringTime(int value) {
		StringBuilder builder = new StringBuilder();
		int hour = value / 3600;
		int minute = (value % 3600) / 60;
		int second = (value % 3600) % 60;
		builder.append(hour);
		builder.append(colon);
		builder.append(minute);
		builder.append(colon);
		builder.append(second);
		return builder.toString();
	}

}
