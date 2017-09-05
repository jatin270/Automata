package in.nsit.com.automata.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.nsit.com.automata.R;

/**
 * Created by ( Jatin Bansal ) on 24-03-2017.
 */

public class OutputFragment extends DialogFragment {

    View v;
    public String s;
    TextView outputText;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.outputgenerated,container,false);
        outputText= (TextView) v.findViewById(R.id.result);
        outputText.setText(s);

        return v;
    }


}
