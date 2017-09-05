package in.nsit.com.automata.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import in.nsit.com.automata.GraphDisplay;
import in.nsit.com.automata.L;
import in.nsit.com.automata.R;

/**
 * Created by ( Jatin Bansal ) on 15-01-2017.
 */

public class StateFragment extends DialogFragment implements View.OnClickListener {


    public  Button b2,b3;
    Context context;
    public GraphDisplay p;
    public int stateno;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context=getActivity();
        View view=inflater.inflate(R.layout.stateselect,container,false);
        b2=(Button)view.findViewById(R.id.finalstate);
        b3=(Button)view.findViewById(R.id.delete);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {

            if(v.getId()==R.id.finalstate)
            {
                L.toast(context,"final state");
                p.addfinalstate(stateno,0);
            }
        else
                if(v.getId()==R.id.delete)
                {
                    p.delete(stateno);
                }
    }
}
