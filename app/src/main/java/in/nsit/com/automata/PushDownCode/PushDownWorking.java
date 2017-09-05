package in.nsit.com.automata.PushDownCode;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.IOException;

import in.nsit.com.automata.FiniteAutomata.WorkingA;
import in.nsit.com.automata.Fragments.LoadFragment;
import in.nsit.com.automata.Fragments.OutputFragment;
import in.nsit.com.automata.Fragments.SavefileFragment;
import in.nsit.com.automata.GraphDisplay;
import in.nsit.com.automata.L;
import in.nsit.com.automata.R;

/**
 * Created by ( Jatin Bansal ) on 10-03-2017.
 */

public class PushDownWorking extends AppCompatActivity{

    GraphDisplay graphDisplay;
    FragmentManager manager;
    SavefileFragment savingfile;
    FragmentTransaction trans;
    LoadFragment loadFragment;
    OutputFragment outputFragment;

    int ct=0;
    public Button generate;
    public String outputString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working);
        graphDisplay = (GraphDisplay) getSupportFragmentManager().findFragmentById(R.id.graphdisplay);
        generate= (Button) findViewById(R.id.generateOutputFile);
        outputFragment=new OutputFragment();
        graphDisplay.pushDownWorking=PushDownWorking.this;
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager=getSupportFragmentManager();
                outputFragment.show(manager,"Output");
                outputFragment.s=outputString;
            }
        });
        graphDisplay.CodeIndex=2;
      try {
           graphDisplay.PDALoad("PDAtemporaryfile");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public  void clean(View v){
        graphDisplay.PDAclean();
    }
    //  Function to  add non initialised state
    public void addstate(View v) {
        graphDisplay.addstate(100,100,-1);
    }
    // function to add transition  fragment in the activity
    public void addTransition(View v) {
        graphDisplay.addTransitionPushDownAutomata(v);
    }

    // function to add simulate fragment
    public void simulate(View v) {
        graphDisplay.simulate(2);
    }
    public void save(View v) throws IOException {
        savingfile = new SavefileFragment();
        manager =getSupportFragmentManager();
        trans = manager.beginTransaction();
        savingfile.Codeindex=2;
        savingfile.w=PushDownWorking.this;
        savingfile.show(manager, "SaveFragment");
    }

    public void PDAsave(String s) throws IOException {
        graphDisplay.PDASave(s);
        trans = manager.beginTransaction();
        trans.remove(savingfile);
        trans.commit();
    }
    public void load(View v) throws FileNotFoundException {

        loadFragment = new LoadFragment();
        manager =getSupportFragmentManager();
        trans = manager.beginTransaction();
        loadFragment.Codeindex=2;
        loadFragment.w=PushDownWorking.this;
        loadFragment.show(manager, "LoadFragment");

    }
    public void PDALoad(String s) throws FileNotFoundException {

        graphDisplay.PDALoad(s);
        trans = manager.beginTransaction();
        trans.remove( loadFragment);
        trans.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Hey","PauseActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Hey","ResumeActivity");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Hey","StopActivity");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        graphDisplay.toremove=0;
        Log.d("Hey","RestartActivity");
    }
}
