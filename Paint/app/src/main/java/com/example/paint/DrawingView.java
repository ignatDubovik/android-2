package com.example.paint;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DrawingView extends View {

    public static final int LINE = 1;
    public static final int RECTANGLE = 3;
    public static final int SQUARE = 4;
    public static final int CIRCLE = 5;
    public static final int TRIANGLE = 6;
    public static final int SMOOTHLINE = 2;
    public static final int ERASER = 7;
    public static final int TEXT = 8;
    public static final int BLUR = 9;
    public static float FONT_SIZE = (float) 14.0;
    public static final float TOUCH_TOLERANCE = 4;
    public static final float TOUCH_STROKE_WIDTH = 5;

    public int mCurrentShape;
    public int mCurrentColor;
    private TextView editText;
    public static final int DEFAULT_BG_COLOR = Color.WHITE;
    protected Path mPath;
    protected Paint mPaint;
    protected Paint mPaintFinal;
    protected Bitmap mBitmap;
    protected Canvas mCanvas;
    protected boolean isDrawing = false;
    protected boolean isDrawingEnded = false;
    protected float mStartX;
    protected float mStartY;
    protected float mx;
    protected float my;

    public DrawingView(Context context) {
        super(context);
        init();
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);

        if (isDrawing){
            switch (mCurrentShape) {
                case LINE:
                    onDrawLine(canvas);
                    break;
                case RECTANGLE:
                    onDrawRectangle(canvas);
                    break;
                case SQUARE:
                    onDrawSquare(canvas);
                    break;
                case CIRCLE:
                    onDrawCircle(canvas);
                    break;
                case TRIANGLE:
                    onDrawTriangle(canvas);
                    break;
            }
        }
    }

    protected void init() {
        mPath = new Path();
        mCurrentColor = Color.BLACK;
        mPaint = new Paint(Paint.DITHER_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(mCurrentColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(TOUCH_STROKE_WIDTH);

        mPaintFinal = new Paint(Paint.DITHER_FLAG);
        mPaintFinal.setAntiAlias(true);
        mPaintFinal.setDither(true);
        mPaintFinal.setColor(mCurrentColor);
        mPaintFinal.setStyle(Paint.Style.STROKE);
        mPaintFinal.setStrokeJoin(Paint.Join.ROUND);
        mPaintFinal.setStrokeCap(Paint.Cap.ROUND);
        mPaintFinal.setStrokeWidth(TOUCH_STROKE_WIDTH);
    }

    protected void reset() {
        mPath = new Path();
        countTouch=0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mx = event.getX();
        my = event.getY();
        mPaint.setColor(mCurrentColor);
        mPaintFinal.setColor(mCurrentColor);
        mPaint.setMaskFilter(null);
        mPaintFinal.setMaskFilter(null);
        switch (mCurrentShape) {
            case LINE:
                onTouchEventLine(event);
                break;
            case SMOOTHLINE:
                onTouchEventSmoothLine(event);
                break;
            case RECTANGLE:
                onTouchEventRectangle(event);
                break;
            case SQUARE:
                onTouchEventSquare(event);
                break;
            case CIRCLE:
                onTouchEventCircle(event);
                break;
            case TRIANGLE:
                onTouchEventTriangle(event);
                break;
            case ERASER:
                this.mPaint.setColor(DEFAULT_BG_COLOR);
                this.mPaintFinal.setColor(DEFAULT_BG_COLOR);
                onTouchEventEraser(event);
                break;
            case BLUR:
                mPaint.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));
                mPaintFinal.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));
                onTouchEventSmoothLine(event);
                break;
            case TEXT:
                onTouchEventText(event);
                break;

        }
        return true;
    }

    private void onDrawLine(Canvas canvas) {

        float dx = Math.abs(mx - mStartX);
        float dy = Math.abs(my - mStartY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            canvas.drawLine(mStartX, mStartY, mx, my, mPaint);

        }
    }

    private void onTouchEventLine(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDrawing = true;
                mStartX = mx;
                mStartY = my;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                isDrawing = false;
                mCanvas.drawLine(mStartX, mStartY, mx, my, mPaintFinal);
                invalidate();
                break;
        }
    }

    private void onTouchEventText(MotionEvent event) {

        if (editText == null) {
            final Context context = getContext();
            AppCompatActivity activity = (AppCompatActivity) context;
            editText = new EditText(context);
            editText.setGravity(Gravity.LEFT);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            editText.setLayoutParams(params);
            RelativeLayout layout = activity.findViewById(R.id.relative);
            layout.addView(editText);
            layout.setVisibility(VISIBLE);
            editText.forceLayout();
            editText.setVisibility(INVISIBLE);
            editText.setTextSize(FONT_SIZE);
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.post(new Runnable() {
                @Override
                public void run() {
                    editText.setVisibility(VISIBLE);

                    editText.setX(mStartX);
                    editText.setY(mStartY);

                    InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    editText.requestFocus();
                    manager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                }
            });
        } else {
            float viewX = editText.getX();
            float viewY = editText.getY() + editText.getHeight();

            mCanvas.drawText(editText.getText().toString(),mStartX,mStartY,mPaint);

            Context context = getContext();
            AppCompatActivity activity = (AppCompatActivity) context;
            RelativeLayout layout = activity.findViewById(R.id.relative);
            layout.removeAllViews();
            layout.setVisibility(INVISIBLE);
            editText = null;
        }
    }

    private void onTouchEventEraser(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDrawing = true;
                mStartX = mx;
                mStartY = my;

                mPath.reset();
                mPath.moveTo(mx, my);

                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:

                float dx = Math.abs(mx - mStartX);
                float dy = Math.abs(my - mStartY);
                if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                    mPath.quadTo(mStartX, mStartY, (mx + mStartX) / 2, (my + mStartY) / 2);
                    mStartX = mx;
                    mStartY = my;
                }
                mPaint.setStrokeWidth(20);
                mCanvas.drawPath(mPath, mPaint);
                mPaint.setStrokeWidth(TOUCH_STROKE_WIDTH);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                isDrawing = false;
                mPaint.setStrokeWidth(20);
                mPath.lineTo(mStartX, mStartY);
                mCanvas.drawPath(mPath, mPaintFinal);
                mPaint.setStrokeWidth(TOUCH_STROKE_WIDTH);
                mPath.reset();
                invalidate();
                break;
        }
    }

    private void onTouchEventSmoothLine(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDrawing = true;
                mStartX = mx;
                mStartY = my;

                mPath.reset();
                mPath.moveTo(mx, my);

                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:

                float dx = Math.abs(mx - mStartX);
                float dy = Math.abs(my - mStartY);
                if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                    mPath.quadTo(mStartX, mStartY, (mx + mStartX) / 2, (my + mStartY) / 2);
                    mStartX = mx;
                    mStartY = my;
                }
                mCanvas.drawPath(mPath, mPaint);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                isDrawing = false;
                mPath.lineTo(mStartX, mStartY);
                mCanvas.drawPath(mPath, mPaintFinal);
                mPath.reset();
                invalidate();
                break;
        }
    }

    int countTouch =0;
    float basexTriangle =0;
    float baseyTriangle =0;

    private void onDrawTriangle(Canvas canvas){

        if (countTouch<3){
            canvas.drawLine(mStartX,mStartY,mx,my,mPaint);
        }else if (countTouch==3){
            canvas.drawLine(mx,my,mStartX,mStartY,mPaint);
            canvas.drawLine(mx,my,basexTriangle,baseyTriangle,mPaint);

        }
    }

    private void onTouchEventTriangle(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                countTouch++;
                if (countTouch==1){
                    isDrawing = true;
                    mStartX = mx;
                    mStartY = my;
                } else if (countTouch==3){
                    isDrawing = true;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                countTouch++;
                isDrawing = false;
                if (countTouch<3){
                    basexTriangle=mx;
                    baseyTriangle=my;
                    mCanvas.drawLine(mStartX,mStartY,mx,my,mPaintFinal);
                } else if (countTouch>=3){
                    mCanvas.drawLine(mx,my,mStartX,mStartY,mPaintFinal);
                    mCanvas.drawLine(mx,my,basexTriangle,baseyTriangle,mPaintFinal);
                    countTouch =0;
                }
                invalidate();
                break;
        }
    }

    private void onDrawCircle(Canvas canvas){
        canvas.drawCircle(mStartX, mStartY, calculateRadius(mStartX, mStartY, mx, my), mPaint);
    }

    private void onTouchEventCircle(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDrawing = true;
                mStartX = mx;
                mStartY = my;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                isDrawing = false;
                mCanvas.drawCircle(mStartX, mStartY, calculateRadius(mStartX,mStartY,mx,my), mPaintFinal);
                invalidate();
                break;
        }
    }

    protected float calculateRadius(float x1, float y1, float x2, float y2) {

        return (float) Math.sqrt(
                Math.pow(x1 - x2, 2) +
                        Math.pow(y1 - y2, 2)
        );
    }

    private void onDrawRectangle(Canvas canvas) {
        drawRectangle(canvas,mPaint);
    }

    private void onTouchEventRectangle(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDrawing = true;
                mStartX = mx;
                mStartY = my;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                isDrawing = false;
                drawRectangle(mCanvas,mPaintFinal);
                invalidate();
                break;
        }
        ;
    }

    private void drawRectangle(Canvas canvas,Paint paint){
        float right = mStartX > mx ? mStartX : mx;
        float left = mStartX > mx ? mx : mStartX;
        float bottom = mStartY > my ? mStartY : my;
        float top = mStartY > my ? my : mStartY;
        canvas.drawRect(left, top , right, bottom, paint);
    }

    private void onDrawSquare(Canvas canvas) {
        onDrawRectangle(canvas);
    }

    private void onTouchEventSquare(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDrawing = true;
                mStartX = mx;
                mStartY = my;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                adjustSquare(mx, my);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                isDrawing = false;
                adjustSquare(mx, my);
                drawRectangle(mCanvas,mPaintFinal);
                invalidate();
                break;
        }
    }

    protected void adjustSquare(float x, float y) {
        float deltaX = Math.abs(mStartX - x);
        float deltaY = Math.abs(mStartY - y);

        float max = Math.max(deltaX, deltaY);

        mx = mStartX - x < 0 ? mStartX + max : mStartX - max;
        my = mStartY - y < 0 ? mStartY + max : mStartY - max;
    }

    public void clear () {
        mCanvas.drawColor(Color.WHITE);
        invalidate();
    }


}

