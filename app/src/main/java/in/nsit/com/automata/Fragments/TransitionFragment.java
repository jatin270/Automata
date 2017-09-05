package in.nsit.com.automata.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import in.nsit.com.automata.GraphDisplay;
import in.nsit.com.automata.R;

/**
 * Created by ( Jatin Bansal ) on 21-01-2017.
 */

public class TransitionFragment extends DialogFragment implements View.OnClickListener{


    private EditText e1,e2,e3;
    Button submitbtn;
    Context context;
    public GraphDisplay p;
    int finish;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context=getActivity();
        View view=inflater.inflate(R.layout.transitionadd,container,false);
        e1=(EditText) view.findViewById(R.id.start);
        e2=(EditText) view.findViewById(R.id.character);
        e3=(EditText) view.findViewById(R.id.end);
        submitbtn=(Button)view.findViewById(R.id.submit);
        finish=0;
        submitbtn.setOnClickListener(this);
        return  view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public String getet1(){
        return e1.getText().toString();
    }
    public String getet2(){
        return e2.getText().toString();
    }
    public String getet3(){
        return e3.getText().toString();
    }

    @Override
    public int show(FragmentTransaction transaction, String tag) {
        return super.show(transaction, tag);
    }

    @Override
    public void onClick(View v) {

        if(!e1.getText().toString().isEmpty() &&!e2.getText().toString().isEmpty()&&!e3.getText().toString().isEmpty()) {

            String number1 = e1.getText().toString();
            String number2 = e3.getText().toString();
            int result1,result2;

            try {
                result1 = Integer.parseInt(number1);
                result2 = Integer.parseInt(number2);
            }catch (NumberFormatException e){
                result1=-1;
                result2=-1;
            }
            String s = e2.getText().toString();
            char a[] = s.toCharArray();

            if(!number1.isEmpty()&&!number2.isEmpty()&&!s.isEmpty()&&(a[0]<='g'))
            p.transitionadding(result1,result2,a[0],0);
        }

    }
}
