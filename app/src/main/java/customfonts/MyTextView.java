package customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by RamSl-la Saeed on 20-Jan-17.
 */
public class MyTextView extends TextView {
    public MyTextView(Context context) {
        super(context);
        init();
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        if (!isInEditMode()){
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"Lato-Regular.ttf");
            setTypeface(tf);
        }
    }
}
