package com.eiasin.landcalculator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class TriangleView extends View {

    private float a = 0, b = 0, c = 0;
    private double area = 0;
    private final Paint paint = new Paint();
    private final Paint textPaint = new Paint();

    public TriangleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);
        paint.setAntiAlias(true);

        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30f);
        textPaint.setAntiAlias(true);
    }

    public void setSides(float a, float b, float c) {
        this.a = a;
        this.b = b;
        this.c = c;

        if (isValidTriangle(a, b, c)) {
            area = calculateArea(a, b, c);
        } else {
            area = 0;
        }

        invalidate();
    }

    private boolean isValidTriangle(float a, float b, float c) {
        return a + b > c && a + c > b && b + c > a;
    }

    // Heron's formula
    private double calculateArea(float a, float b, float c) {
        double s = (a + b + c) / 2.0;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!isValidTriangle(a, b, c) || area == 0) {
            // Invalid triangle, don't draw
            return;
        }

        int width = getWidth();
        int height = getHeight();

        // Coordinates of triangle points (A, B, C)
        // Place point A at (0,0)
        // Place point B at (c,0) — side c along X-axis
        // Calculate point C using law of cosines:

        // Using coordinates:
        // A = (0,0)
        // B = (c,0)
        // Cx = (b² + c² - a²) / (2c)
        // Cy = sqrt(b² - Cx²)

        double Cx = (b*b + c*c - a*a) / (2 * c);
        double Cy = Math.sqrt(b*b - Cx*Cx);

        // Find scale to fit triangle in view with some margin
        float margin = 40f;
        double maxX = Math.max(c, Cx);
        double maxY = Cy;

        float scaleX = (width - 2 * margin) / (float) maxX;
        float scaleY = (height - 2 * margin) / (float) maxY;
        float scale = Math.min(scaleX, scaleY);

        // Scale points
        float Ax = margin;
        float Ay = height - margin;

        float Bx = Ax + (float)(c * scale);
        float By = Ay;

        float Cx_scaled = Ax + (float)(Cx * scale);
        float Cy_scaled = Ay - (float)(Cy * scale);

        // Draw triangle
        Path path = new Path();
        path.moveTo(Ax, Ay);
        path.lineTo(Bx, By);
        path.lineTo(Cx_scaled, Cy_scaled);
        path.close();

        canvas.drawPath(path, paint);

        // Draw sides label near midpoint of each side, with a bit offset

        // Side a between B and C
        float midAx = (Bx + Cx_scaled) / 2;
        float midAy = (By + Cy_scaled) / 2;

        // Side b between A and C
        float midBx = (Ax + Cx_scaled) / 2;
        float midBy = (Ay + Cy_scaled) / 2;

        // Side c between A and B
        float midCx = (Ax + Bx) / 2;
        float midCy = (Ay + By) / 2;

        // Draw side a
        String sideAText = "পাশ ১\n" + convertToBengaliNumber(a);
        String[] sideALines = sideAText.split("\n");
        float xA = midAx + 20;
        float yA = midAy - 20;
        float lineHeightA = textPaint.getTextSize() + 5;  // Adjust spacing as needed
        for (int i = 0; i < sideALines.length; i++) {
            canvas.drawText(sideALines[i], xA, yA + i * lineHeightA, textPaint);
        }

// Draw side b
        String sideBText = "পাশ ২\n" + convertToBengaliNumber(b);
        String[] sideBLines = sideBText.split("\n");
        float xB = midBx - 150;
        float yB = midBy;
        float lineHeightB = textPaint.getTextSize() + 5;
        for (int i = 0; i < sideBLines.length; i++) {
            canvas.drawText(sideBLines[i], xB, yB + i * lineHeightB, textPaint);
        }

// Draw side c
        String sideCText = "পাশ ৩ঃ " + convertToBengaliNumber(c);
        String[] sideCLines = sideCText.split("\n");
        float xC = midCx - 50;
        float yC = midCy + 40;
        float lineHeightC = textPaint.getTextSize() + 5;
        for (int i = 0; i < sideCLines.length; i++) {
            canvas.drawText(sideCLines[i], xC, yC + i * lineHeightC, textPaint);
        }


        // Draw area text near center of triangle (centroid approx)

        float centroidX = (Ax + Bx + Cx_scaled) / 3;
        float centroidY = (Ay + By + Cy_scaled) / 3;

        String areaText = "ফলাফল: " + convertToBengaliNumber(area);
        float textWidth = textPaint.measureText(areaText);
        canvas.drawText(areaText, centroidX - textWidth / 2, centroidY, textPaint);
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
