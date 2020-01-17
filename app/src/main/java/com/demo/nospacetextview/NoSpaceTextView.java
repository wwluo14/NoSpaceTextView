package com.demo.nospacetextview;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * @author luowang8
 * @date 2020-01-17 16:36
 */

@SuppressLint("AppCompatCustomView")
public class NoSpaceTextView extends TextView {

	/**
	 * 控制measure()方法 刷新测量
	 */
	private boolean refreshMeasure = false;

	public NoSpaceTextView(Context context) {
		super(context);
	}

	public NoSpaceTextView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public NoSpaceTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		removeSpace(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		super.setText(text, type);
		// 每次文本内容改变时，需要测量两次，确保计算的高度没有问题
		refreshMeasure = true;
	}

	/**
	 * 这里处理文本的上下留白问题
	 */
	private void removeSpace(int widthspc, int heightspc) {

		int       paddingTop = 0;
		String[]  linesText  = getLinesText();
		TextPaint paint      = getPaint();
		Rect      rect       = new Rect();
		String    text       = linesText[0];
		paint.getTextBounds(text, 0, text.length(), rect);

		Paint.FontMetricsInt fontMetricsInt = new Paint.FontMetricsInt();
		paint.getFontMetricsInt(fontMetricsInt);

		paddingTop = (fontMetricsInt.top - rect.top);

		// 设置TextView向上的padding (小于0, 即把TextView文本内容向上移动)
		setPadding(getLeftPaddingOffset()
				, paddingTop + getTopPaddingOffset()
				, getRightPaddingOffset()
				, getBottomPaddingOffset());

		String endText = linesText[linesText.length - 1];
		paint.getTextBounds(endText, 0, endText.length(), rect);

		// 再减去最后一行文本的底部空白，得到的就是TextView内容上线贴边的的高度，到达消除文本上下留白的问题
		setMeasuredDimension(getMeasuredWidth()
				, getMeasuredHeight() - (fontMetricsInt.bottom - rect.bottom));

		if (refreshMeasure) {
			refreshMeasure = false;
			measure(widthspc, heightspc);
		}
	}

	/**
	 * 获取每一行的文本内容
	 */
	private String[] getLinesText() {

		int start = 0;
		int end   = 0;

		String[] texts = new String[getLineCount()];

		String text = getText().toString();

		Layout layout = getLayout();

		for (int i = 0; i < getLineCount(); i++) {
			end = layout.getLineEnd(i);

			String line = text.substring(start, end); //指定行的内容
			start = end;

			texts[i] = line;
		}

		return texts;
	}
}
