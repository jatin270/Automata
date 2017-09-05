package in.nsit.com.automata.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import in.nsit.com.automata.GraphDisplay;
import in.nsit.com.automata.R;

/**
 * Created by ( Jatin Bansal ) on 15-03-2017.
 */

public class TuringMachineTransition extends DialogFragment {

    View v;

    private EditText current,read,write,direction,next;
    private Button submitbtn;
    public GraphDisplay p;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.tmtransition,container,false);

        current= (EditText) v.findViewById(R.id.currentState);
        read=(EditText)v.findViewById(R.id.readSymbol);
        write=(EditText)v.findViewById(R.id.writeSymbol);
        direction=(EditText)v.findViewById(R.id.direction);
        next=(EditText)v.findViewById(R.id.nextState);
        submitbtn= (Button) v.findViewById(R.id.tmsubmit);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start=current.getText().toString();
                String stop=next.getText().toString();
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
                String write1=write.getText().toString();
                String direction1=direction.getText().toString();

                if(!start.isEmpty()&&!stop.isEmpty()&&!read1.isEmpty()&&!write1.isEmpty()&&!direction1.isEmpty())
                p.transitionaddingTM(result1,result2,read.getText().toString(),write.getText().toString(),direction.getText().toString(),0);
            }
        });
        return v;
    }




}
