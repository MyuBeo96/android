package com.fss.mobiletrading.function.portfolio;

import android.content.Context;
import android.widget.LinearLayout;

public abstract class PorfolioItemView extends LinearLayout {
	public PorfolioItemView(Context context) {
		super(context);
	}

	public abstract void setView(PorfolioItem item);
}
