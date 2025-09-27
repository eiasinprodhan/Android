package com.eiasin.landcalculator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class RectangleView extends View {

    private float east = 0, west = 0, north = 0, south = 0;
    private String unit = "\nবর্গ একক"; // default unit, you can set via setter if needed

    private final Paint paint = new Paint();
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final float textSizeDp = 14f; // text size in dp
    private float textSizePx;

    public RectangleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);
        paint.setAntiAlias(true);

        textSizePx = dpToPx(textSizeDp);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(textSizePx);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setSides(float east, float west, float north, float south) {
        this.east = east;
        this.west = west;
        this.north = north;
        this.south = south;
        invalidate();
    }

    // Optional: to set unit dynamically
    public void setUnit(String unit) {
        this.unit = unit;
        invalidate();
    }

    private float dpToPx(float dp) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (east <= 0 || west <= 0 || north <= 0 || south <= 0) {
            return;
        }

        int width = getWidth();
        int height = getHeight();

        float padding = dpToPx(30);  // Padding for text and edges

        // Calculate scale to fit rectangle inside view minus padding
        float maxSide = Math.max(Math.max(east, west), Math.max(north, south));
        float scaleX = (width - 2 * padding) / maxSide;
        float scaleY = (height - 2 * padding) / maxSide;
        float scale = Math.min(scaleX, scaleY);

        // Make rectangle smaller (60% of max scale)
        scale *= 0.6f;

        float e = east * scale;
        float w = west * scale;
        float n = north * scale;
        float s = south * scale;

        float centerX = width / 2f;
        float centerY = height / 2f;

        // Calculate corners of the quadrilateral clockwise starting top-left
        float topLeftX = centerX - n / 2f;
        float topLeftY = centerY - w / 2f;

        float topRightX = centerX + n / 2f;
        float topRightY = centerY - e / 2f;

        float bottomRightX = centerX + s / 2f;
        float bottomRightY = centerY + e / 2f;

        float bottomLeftX = centerX - s / 2f;
        float bottomLeftY = centerY + w / 2f;

        Path path = new Path();
        path.moveTo(topLeftX, topLeftY);
        path.lineTo(topRightX, topRightY);
        path.lineTo(bottomRightX, bottomRightY);
        path.lineTo(bottomLeftX, bottomLeftY);
        path.close();

        // Draw rectangle
        canvas.drawPath(path, paint);

        // Larger offset for labels to keep them far from lines
        float labelOffset = dpToPx(50);

        // Draw labels and values outside rectangle edges

        // পূর্ব (East) - right center side
        canvas.drawText("পূর্ব", (topRightX + bottomRightX) / 2f + labelOffset, (topRightY + bottomRightY) / 2f, textPaint);
        canvas.drawText(convertToBengaliNumber(east), (topRightX + bottomRightX) / 2f + labelOffset, (topRightY + bottomRightY) / 2f + textSizePx + dpToPx(4), textPaint);

        // পশ্চিম (West) - left center side
        canvas.drawText("পশ্চিম", (topLeftX + bottomLeftX) / 2f - labelOffset, (topLeftY + bottomLeftY) / 2f, textPaint);
        canvas.drawText(convertToBengaliNumber(west), (topLeftX + bottomLeftX) / 2f - labelOffset, (topLeftY + bottomLeftY) / 2f + textSizePx + dpToPx(4), textPaint);

        // উত্তর (North) - top center side
        canvas.drawText("উত্তর", centerX, topLeftY - labelOffset, textPaint);
        canvas.drawText(convertToBengaliNumber(north), centerX, topLeftY - labelOffset + textSizePx + dpToPx(4), textPaint);

        // দক্ষিণ (South) - bottom center side
        canvas.drawText("দক্ষিণ", centerX, bottomLeftY + labelOffset + textSizePx, textPaint);
        canvas.drawText(convertToBengaliNumber(south), centerX, bottomLeftY + labelOffset + textSizePx * 2 + dpToPx(4), textPaint);

        // Draw result in center with unit
        double area = ((east + west) / 2.0) * ((north + south) / 2.0);
        String areaText = "ফলাফল\n" + convertToBengaliNumber(area) + " " + unit;

        // Draw multiline text centered - simple approach
        String[] lines = areaText.split("\n");
        float totalTextHeight = lines.length * (textSizePx + dpToPx(4));
        float startY = centerY - totalTextHeight / 2 + textSizePx;

        for (int i = 0; i < lines.length; i++) {
            canvas.drawText(lines[i], centerX, startY + i * (textSizePx + dpToPx(4)), textPaint);
        }
    }

    private String convertToBengaliNumber(double number) {
        String[] bengaliDigits = {"০", "১", "২", "৩", "৪", "৫", "৬", "৭", "৮", "৯"};
        String numStr = String.format("%.2f", number);
        StringBuilder sb = new StringBuilder();
        for (char c : numStr.toCharArray()) {
            if (c >= '0' && c <= '9') {
                sb.append(bengaliDigits[c - '0']);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
