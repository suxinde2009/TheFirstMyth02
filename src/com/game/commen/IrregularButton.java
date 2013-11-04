package com.game.commen;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

public class IrregularButton extends Button  
{  
    public interface TouchChecker  
    {  
        boolean isInTouchArea(int x, int y, int width, int height);  
    }  
      
      
    private TouchChecker touchChecker;  
      
    public IrregularButton(Context context)  
    {  
        super(context);  
          
        init();  
    }  
    public IrregularButton(Context context, AttributeSet attrs)  
    {  
        super(context, attrs);  
          
        init();  
    }  
    public IrregularButton(Context context, AttributeSet attrs, int defStyle)  
    {  
        super(context, attrs, defStyle);  
          
        init();  
    }  
  
    private void init()  
    {  
    }  
      
    public void setTouchChecker(TouchChecker touchChecker)  
    {  
        this.touchChecker = touchChecker;  
    }  
      
    @Override  
    public boolean onTouchEvent(MotionEvent event)  
    {  
        if (touchChecker != null)  
        {  
            if (event.getAction() == MotionEvent.ACTION_DOWN)   
            {  
                if (touchChecker.isInTouchArea((int) event.getX(), (int) event.getY(), getWidth(), getHeight()))  
                {  
                    return super.onTouchEvent(event);  
                }  
                else  
                {  
                    return false;  
                }  
            }  
        }  
          
        return super.onTouchEvent(event);  
    }  
      
      
}  