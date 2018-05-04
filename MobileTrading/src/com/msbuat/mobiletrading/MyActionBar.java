package com.msbuat.mobiletrading;

import android.app.Service;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyActionBar extends RelativeLayout implements OnClickListener {

	ImageView img_chooseAfacctno;
	TextView tv_title;
	TextView tv_afacctno;
	LinearLayout layout_optionmenu;
	LinearLayout lay_chooseafacctno;
	LinearLayout lay_actionHome;
	LayoutInflater inflater;
	RelativeLayout layout_actionbar_main;
	Action actionAfacctno;

	List<Integer> addedActions;

	public MyActionBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		inflater = (LayoutInflater) context
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.myactionbar, this);
		img_chooseAfacctno = (ImageView) findViewById(R.id.img_myactionbar_chooseafacctno);
		tv_title = (TextView) findViewById(R.id.text_myactionbar_title);
		tv_afacctno = (TextView) findViewById(R.id.text_myactionbar_afacctno);
		layout_optionmenu = (LinearLayout) findViewById(R.id.layout_myactionbar_optionmenu);
		lay_chooseafacctno = (LinearLayout) findViewById(R.id.lay_myactionbar_chooseafacctno);
		lay_actionHome = (LinearLayout) findViewById(R.id.layout_myactionbar_home);
		layout_actionbar_main = (RelativeLayout) findViewById(R.id.layout_actionbar_main);
		lay_actionHome.setOnClickListener(this);
		tv_afacctno.setOnClickListener(this);
		img_chooseAfacctno.setOnClickListener(this);
		addedActions = new ArrayList<Integer>();
	}
	public void setActionbarColor(@ColorRes int color) {
		layout_actionbar_main.setBackgroundColor(ContextCompat.getColor(getContext(),color));
	}
	public void setTitle(String title) {
		tv_title.setText(title);
	}

	public void setAfacctno(String acc) {
		tv_afacctno.setText(acc);
	}

	public void setHomeLogoAction(Action action) {
		lay_actionHome.addView(inflateHomeAction(action));
	}

	public void clearHomeLogoAction() {
		lay_actionHome.removeAllViews();
	}

	public void setAfacctnoAction(Action action) {
		actionAfacctno = action;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.text_myactionbar_title:

			break;
		case R.id.text_myactionbar_afacctno:
			if (actionAfacctno != null) {
				actionAfacctno.performAction(tv_afacctno);
			}
			break;
		case R.id.img_myactionbar_chooseafacctno:
			if (actionAfacctno != null) {
				actionAfacctno.performAction(tv_afacctno);
			}
			break;

		default:
			final Object tag = v.getTag();
			if (tag instanceof Action) {
				final Action action = (Action) tag;
				action.performAction(v);
			}
			break;
		}
	}

	public void addActions(LinkedList<Action> actionList) {
		int actions = actionList.size();
		for (int i = 0; i < actions; i++) {
			addAction(actionList.get(i));
		}
	}

	public void addAction(Action action) {
		final int index = layout_optionmenu.getChildCount();
		addAction(action, index);
	}

	public void addAction(Action action, int index) {
		if (addedActions.contains(action.hashCode())) {
			return;
		}
		addedActions.add(action.hashCode());
		layout_optionmenu.addView(inflateAction(action), index);
	}

	private View inflateAction(Action action) {
		View view = inflater.inflate(R.layout.myactionbaritem,
				layout_optionmenu, false);
		ImageView imgView = (ImageView) view
				.findViewById(R.id.imgbtn_myopionmenuitem);
		TextView text = (TextView) view.findViewById(R.id.text_myopionmenuitem);
		if (action.getDrawable() == 0) {
			imgView.setVisibility(GONE);
		} else {
			imgView.setImageResource(action.getDrawable());
		}
		text.setText(action.getText());
		view.setTag(action);
		view.setOnClickListener(this);
		return view;
	}

	private View inflateHomeAction(Action action) {
		View view = inflater.inflate(R.layout.homeactionbaritem,
				layout_optionmenu, false);
		ImageView imgView = (ImageView) view
				.findViewById(R.id.imgbtn_myopionmenuitem);
		TextView text = (TextView) view.findViewById(R.id.text_myopionmenuitem);
		if (action.getDrawable() == 0) {
			imgView.setVisibility(GONE);
		} else {
			imgView.setImageResource(action.getDrawable());
		}
		text.setText(action.getText());
		view.setTag(action);
		view.setOnClickListener(this);
		return view;
	}

	public void removeAllAction() {
		addedActions.clear();
		layout_optionmenu.removeAllViews();
	}

	public interface Action {
		public int getDrawable();

		public String getText();

		public void performAction(View view);

	}

	public static abstract class AbstractAction implements Action {
		final private int mDrawable;

		public AbstractAction(int drawable) {
			mDrawable = drawable;
		}

		@Override
		public int getDrawable() {
			return mDrawable;
		}

	}

}
