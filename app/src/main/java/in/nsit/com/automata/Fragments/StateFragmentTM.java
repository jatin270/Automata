package in.nsit.com.automata.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import in.nsit.com.automata.GraphDisplay;
import in.nsit.com.automata.R;

/**
 * Created by ( Jatin Bansal ) on 26-03-2017.
 */

public class StateFragmentTM extends DialogFragment {


    public Button b1,b2,b3;
    Context context;
    public GraphDisplay p;
    public int stateno;
    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.stateselecttm,container,false);

        context=getActivity();
        b1=(Button)v.findViewById(R.id.rejectState);
        b2=(Button)v.findViewById(R.id.finalstate);
        b3=(Button)v.findViewById(R.id.delete);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.addrejectState(stateno,0);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.addfinalstate(stateno,0);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.delete(stateno);
            }
        });

        return  v;
    }


}
