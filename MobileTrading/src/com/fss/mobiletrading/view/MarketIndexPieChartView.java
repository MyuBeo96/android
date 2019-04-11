package com.fss.mobiletrading.view;

import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.tcscuat.mobiletrading.R;
import com.fss.mobiletrading.consts.StringConst;
import com.fss.mobiletrading.object.ResultObj;
import com.tcscuat.mobiletrading.AbstractFragment;

@SuppressLint({ "ValidFragment" })
public class MarketIndexPieChartView extends AbstractFragment {
	LinearLayout layout;
	List<String> listSession;

	public MarketIndexPieChartView() {
	}

	public MarketIndexPieChartView(List<String> paramList) {
		this.listSession = paramList;
	}

	private View openPieChart() {

		double[] distribution = new double[3];
		// Pie Chart Section Value
		try {
			distribution[0] = Double.parseDouble(listSession.get(0));
			distribution[1] = Double.parseDouble(listSession.get(1));
			distribution[2] = Double.parseDouble(listSession.get(2));
		} catch (Exception e) {
			// TODO: handle exception
		}

		// Color of each Pie Chart Sections
		int[] colors = { getResources().getColor(R.color.color_Mua),
				getResources().getColor(R.color.color_ThamChieu),
				getResources().getColor(R.color.color_Ban) };

		// Instantiating CategorySeries to plot Pie Chart
		CategorySeries distributionSeries = new CategorySeries(StringConst.EMPTY);
		for (int i = 0; i < distribution.length; i++) {
			// Adding a slice with its values and custodycd to the Pie Chart
			distributionSeries.add(distribution[i]);
		}

		// Instantiating a renderer for the Pie Chart
		DefaultRenderer defaultRenderer = new DefaultRenderer();
		for (int i = 0; i < distribution.length; i++) {
			SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
			seriesRenderer.setColor(colors[i]);
			seriesRenderer.setShowLegendItem(false);
			seriesRenderer.setDisplayChartValues(false);
			// Adding a renderer for a slice
			defaultRenderer.addSeriesRenderer(seriesRenderer);
			defaultRenderer.setPanEnabled(false);
			defaultRenderer.setClickEnabled(false);
		}

		return ChartFactory.getPieChartView(getActivity(), distributionSeries,
				defaultRenderer);

	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(
				R.layout.marketindex_piechartview, paramViewGroup, false);
		this.layout = ((LinearLayout) localView
				.findViewById(R.id.lay_marketindex_piechartview));
		if (listSession != null) {
			this.layout.addView(openPieChart());
		}
		return localView;
	}

	public void setListPoints(List<String> paramList) {
		this.listSession = paramList;
		update();
	}

	public void update() {
		this.layout.removeAllViews();
		this.layout.addView(openPieChart());
	}

	@Override
	protected void process(String key, ResultObj rObj) {
		// TODO Auto-generated method stub

	}


}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.fragment.MarketIndexPieChartView JD-Core Version: 0.6.0
 */