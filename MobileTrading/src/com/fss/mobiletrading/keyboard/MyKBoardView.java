package com.fss.mobiletrading.keyboard;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.inputmethodservice.Keyboard.Key;
import android.util.AttributeSet;

public class MyKBoardView extends android.inputmethodservice.KeyboardView {

	Context context;

	public MyKBoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Paint paint = new Paint();
		Typeface font = Typeface.createFromAsset(context.getAssets(),
				"fonts/AvenirNext_Regular.ttf");
		paint.setTypeface(font);
		paint.setTextSize(30);

		List<Key> keys = getKeyboard().getKeys();
		for (Key key : keys) {

			if (key.label != null)
				// canvas.drawText(key.label.toString(), key.x + (key.width /
				// 2),
				// key.y + 25, paint);
				canvas.drawText(key.label.toString(), key.x + (key.width / 2),
						key.y, paint);

		}
	}
}
