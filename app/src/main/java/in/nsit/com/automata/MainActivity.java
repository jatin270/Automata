package in.nsit.com.automata;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

import in.nsit.com.automata.FiniteAutomata.WorkingA;
import in.nsit.com.automata.PushDownCode.PushDownWorking;
import in.nsit.com.automata.TuringMachine.TuringWorking;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void FiniteAutomata (View v){
        Intent myIntent = new Intent(MainActivity.this,WorkingA.class);
        startActivity(myIntent);
    }

    public void PushDownAutomata(View v){

        Intent myIntent = new Intent(MainActivity.this,PushDownWorking.class);
        startActivity(myIntent);

    }
    public void TuringMachine(View v){
        Intent myIntent=new Intent(MainActivity.this, TuringWorking.class);
        startActivity(myIntent);
    }

}
