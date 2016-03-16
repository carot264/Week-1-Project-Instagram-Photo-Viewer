package vn.dise.instagramphotoviewer.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class ExpandableTextView extends TextView {

    public static final String EXPAND_TEXT = "...[See More]";

    public ExpandableTextView(Context context) {
        super(context);
    }
    public ExpandableTextView(Context context,AttributeSet attr) {
        super(context, attr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        onPreDraw();
        CharSequence fullText = getText();
        if(getLineCount() > getMaxLines()) {
            int lineEndIndex = getLayout().getLineEnd(getMaxLines() - 1);
            CharSequence newText = fullText.subSequence(0, lineEndIndex - EXPAND_TEXT.length() + 1) + EXPAND_TEXT;
            super.setText(newText);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            onPreDraw();
        }
    }
}

