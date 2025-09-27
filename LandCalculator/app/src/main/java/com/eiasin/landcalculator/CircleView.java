package com.eiasin.landcalculator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CircleView extends View {

    private float radius = 0f;
    private float area = 0f;
    private Paint circlePaint;
    private Paint borderPaint;
    private Paint textPaint;

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(Color.WHITE);
        circlePaint.setStyle(Paint.Style.FILL);

        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setColor(Color.BLACK);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(6f);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(48f);
        textPaint.setTextAlign(Paint.Align.LEFT);
    }

    public void setRadius(float radius) {
        this.radius = radius;
        this.area = (float) (Math.PI * radius * radius);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Make the view square to keep circle perfect
        int size = Math.min(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        int measureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        super.onMeasure(measureSpec, measureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;

        float maxDisplayRadius = getWidth() * 0.4f;

        // Scale radius but enforce minimum visible radius for small input radius values
        float scaleFactor = 10f;
        float displayRadius = radius > 0 ? radius * scaleFactor : 0;

        displayRadius = Math.max(displayRadius, 30f);
        displayRadius = Math.min(displayRadius, maxDisplayRadius);

        if (displayRadius > 0) {
            // Draw filled white circle
            canvas.drawCircle(centerX, centerY, displayRadius, circlePaint);
            // Draw black border
            canvas.drawCircle(centerX, centerY, displayRadius, borderPaint);

            // Draw radius text above the circle, centered horizontally
            String radiusText = "ব্যাসার্ধ: " + radius;
            float radiusTextWidth = textPaint.measureText(radiusText);
            canvas.drawText(radiusText, centerX - radiusTextWidth / 2f, centerY - displayRadius - 40, textPaint);

            // Draw area text below the circle, centered horizontally
            String areaText = String.format("ক্ষেতফল: %.2f", area);
            float areaTextWidth = textPaint.measureText(areaText);
            canvas.drawText(areaText, centerX - areaTextWidth / 2f, centerY + displayRadius + 70, textPaint);
        }
    }
}
