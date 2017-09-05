package in.nsit.com.automata.Fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import in.nsit.com.automata.GraphDisplay;
import in.nsit.com.automata.R;

/**
 * Created by ( Jatin Bansal ) on 10-03-2017.
 */

public class PushDownTransition extends android.support.v4.app.DialogFragment {

    View v;
    private EditText intial,read,pop,push,end;
    private Button submitbtn;


    public GraphDisplay p;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.pushdowntransition,container,false);

        intial= (EditText) v.findViewById(R.id.startPDA);
        read=(EditText)v.findViewById(R.id.PDAread);
        pop= (EditText) v.findViewById(R.id.PDApop);
        push=(EditText)v.findViewById(R.id.PDAPush);
        end=(EditText)v.findViewById(R.id.endPDA);
        submitbtn=(Button)v.findViewById(R.id.PDAtransitionsubmit);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start=intial.getText().toString();
                String stop=end.getText().toString();
                int result1;
                int result2;
                try {
                     result1 = Integer.parseInt(start);
                     result2 = Integer.parseInt(stop);
                }catch (NumberFormatException e){
                    result1=-1;
                    result2=-1;
                }
                String read1=read.getText().toString();
                String pop1=pop.getText().toString();
                String push1=push.getText().toString();

                if(!start.isEmpty()&&!stop.isEmpty()&&!read1.isEmpty()&&!pop1.isEmpty()&&!push1.isEmpty())
                p.transitionaddingPDA(result1,result2,read.getText().toString(),pop.getText().toString(),push.getText().toString(),0);

            }
        });
        return v;
    }




}
