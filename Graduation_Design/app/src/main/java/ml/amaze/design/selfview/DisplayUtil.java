package ml.amaze.design.selfview;

import android.content.Context;
import android.util.TypedValue;

/**
 *
 * @author hxj
 * @date 2017/12/10 0010
 */

public class DisplayUtil {

    public static float dp2px(float dpValue, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue, context.getResources().getDisplayMetrics());
    }

    public static float sp2px(float spValue, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spValue, context.getResources().getDisplayMetrics());
    }
}