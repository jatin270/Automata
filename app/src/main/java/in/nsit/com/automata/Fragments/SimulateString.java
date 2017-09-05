package in.nsit.com.automata.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import in.nsit.com.automata.GraphDisplay;
import in.nsit.com.automata.R;

/**
 * Created by ( Jatin Bansal ) on 21-01-2017.
 */

public class SimulateString extends DialogFragment implements View.OnClickListener {

    EditText editText;
    Button submit;
    public int simulationIndex;
    public GraphDisplay p;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.simulatestring,container,false);
        editText= (EditText) v.findViewById(R.id.editText);
        submit=(Button) v.findViewById(R.id.simulatebtn);
        submit.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        try {
            if(simulationIndex==1)
            p.simulate1(editText.getText().toString());
            else
                if(simulationIndex==2)
                    p.simulate2(editText.getText().toString());
            else
                if(simulationIndex==3)
                    p.simulate3(editText.getText().toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
