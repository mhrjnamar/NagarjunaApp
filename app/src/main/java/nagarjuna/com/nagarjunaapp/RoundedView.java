package nagarjuna.com.nagarjunaapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by inficare on 12/21/15.
 */
public class RoundedView extends View {
    private static final String TAG = "RoundedView";
    private final int DEFAULT_SIZE = 50;
    private final String DEFAULT_TEXT = "1";
    private final int DEFAULT_BACKGROUND_COLOR = Color.RED;
    private final int DEFAULT_TEXT_COLOR = Color.WHITE;
    private static float DEFAULT_TITLE_SIZE = 25f;

    private static float textSize = DEFAULT_TITLE_SIZE;
    private int size = DEFAULT_SIZE;
    private int mTextColor = DEFAULT_TEXT_COLOR;
    private int mBackgroundColor = DEFAULT_BACKGROUND_COLOR;
    private TextPaint textPaint ;
    private Paint background;
    private String text = DEFAULT_TEXT;
    private Typeface mFont = Typeface.defaultFromStyle(Typeface.NORMAL);
    private int mViewSize;
    private RectF rectF;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    public RoundedView(Context context) {
        super(context);
        init();
    }

    public RoundedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
        requestLayout();
        invalidate();
    }

    private void init(){
        // init textPaint
        textPaint = new TextPaint();
        textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTypeface(mFont);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setLinearText(true);
        textPaint.setColor(getRoundViewTextColor());
        textPaint.setTextSize(textSize);

        //inti Background Paint
        background = new Paint();
        background.setFlags(Paint.ANTI_ALIAS_FLAG);
        background.setStyle(Paint.Style.FILL);
        background.setColor(getRoundViewBackgroundColor());

        // init inner rect
        rectF = new RectF();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure: "+getSize());
        int width = resolveSize(getSize(), widthMeasureSpec);
        int height = resolveSize(getSize(), heightMeasureSpec);
        mViewSize = Math.min(width, height);
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        rectF.set(0, 0, mViewSize, mViewSize);
        rectF.offset((getWidth() - mViewSize) / 2, (getHeight() - mViewSize) / 2);

        float centerX = rectF.centerX();
        float centerY = rectF.centerY();

        int xPos = (int) centerX;
        int yPos = (int) (centerY - (textPaint.descent() + textPaint.ascent()) / 2);

        canvas.drawOval(rectF, background);

        canvas.drawText(text,
                xPos,
                yPos,
                textPaint);
    }

    public void setRoundViewTextColor(int color){
        mTextColor = color;
        textPaint.setColor(mTextColor);
        invalidate();
    }
    public int getRoundViewTextColor(){
        return mTextColor;
    }
    public void setRoundViewBackgroundColor(int color){
        mBackgroundColor = color;
        background.setColor(mBackgroundColor);
        invalidate();
    }
    public int getRoundViewBackgroundColor(){
        return mBackgroundColor;
    }
    public void setTextTypeface(Typeface font){
        this.mFont = font;
        textPaint.setTypeface(font);
    }

    public Typeface getTextTypeFace(){
        return mFont;
    }
}