package com.oxb.test.app3;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.text.format.Time;
import android.view.SurfaceHolder;

/**
 * Created by oubai on 4/16/15.
 */
public class FirstWatchFace extends CanvasWatchFaceService {

    @Override
    public Engine onCreateEngine(){
        return new Engine();
    }

    private class Engine extends CanvasWatchFaceService.Engine{

        Time timeObject;

        Paint backgroundPaint, timeTextPaint;

        @Override
        public void onCreate(SurfaceHolder holder){
            super.onCreate(holder);

            timeObject = new Time();

            backgroundPaint = new Paint();
            backgroundPaint.setColor(Color.WHITE);
            backgroundPaint.setAntiAlias(true);

            timeTextPaint = new Paint();
            timeTextPaint.setColor(Color.BLUE);
            timeTextPaint.setTextSize(20);
            timeTextPaint.setAntiAlias(true);

        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds){
            timeObject.setToNow();
            String time = timeObject.hour + " : " + timeObject.minute;

            canvas.drawCircle(bounds.centerX(), bounds.centerY(), 100, backgroundPaint);
            canvas.drawText(time, bounds.centerX()-20, bounds.centerY(), timeTextPaint);

        }

    }

}
