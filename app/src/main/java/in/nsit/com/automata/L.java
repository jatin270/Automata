package in.nsit.com.automata;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ( Jatin Bansal ) on 21-01-2017.
 */

public class L {

    public static void toast(Context context,String message) {

        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    public static void log(String message){
        Log.d("Tag",message);
    }
}
