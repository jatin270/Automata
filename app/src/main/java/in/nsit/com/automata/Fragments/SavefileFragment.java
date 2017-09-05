package in.nsit.com.automata.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import in.nsit.com.automata.PushDownCode.PushDownWorking;
import in.nsit.com.automata.R;
import in.nsit.com.automata.FiniteAutomata.WorkingA;
import in.nsit.com.automata.TuringMachine.TuringWorking;

/**
 * Created by ( Jatin Bansal ) on 20-02-2017.
 */

public class SavefileFragment extends DialogFragment {

    View v;
    EditText t;
    Button save;
    public WorkingA p;
    public PushDownWorking w;
    public int Codeindex=1;
    public TuringWorking tm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v=inflater.inflate(R.layout.savingfilename,container,false);
        t=(EditText)v.findViewById(R.id.name);
        save=(Button)v.findViewById(R.id.savefile);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(t.getText().toString()!=null)
                {
                    try {
                        if(Codeindex==1)
                            p.savehelp(t.getText().toString());
                        if(Codeindex==2)
                            w.PDAsave(t.getText().toString());
                        if(Codeindex==3)
                            tm.TMsave(t.getText().toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        return v;
    }




}
