package in.nsit.com.automata.FiniteAutomata;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

import in.nsit.com.automata.Fragments.LoadFragment;
import in.nsit.com.automata.Fragments.OutputFragment;
import in.nsit.com.automata.Fragments.SavefileFragment;
import in.nsit.com.automata.GraphDisplay;
import in.nsit.com.automata.L;
import in.nsit.com.automata.R;

public class WorkingA extends AppCompatActivity {


    GraphDisplay graphDisplay;
    FragmentManager manager;
    SavefileFragment savingfile;
    FragmentTransaction trans;

    LoadFragment loadFragment;

    OutputFragment outputFragment;
    int ct=0;
    public String outputString;
    public  Button generate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_working);
        graphDisplay = (GraphDisplay) getSupportFragmentManager().findFragmentById(R.id.graphdisplay);
        generate= (Button) findViewById(R.id.generateOutputFile);
        outputFragment=new OutputFragment();
        if(generate!=null) {
            generate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    manager = getSupportFragmentManager();
                    outputFragment.show(manager, "Output");
                    outputFragment.s = outputString;
                }
            });
        }

        graphDisplay.finiteWorking=WorkingA.this;
        graphDisplay.savedInstance = savedInstanceState;
        graphDisplay.CodeIndex=1;
        try {
            graphDisplay.loadautomata("temporaryfile");
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
    }

    public  void clean(View v){
        graphDisplay.clean();
    }
    //  Function to  add non initialised state
    public void addstate(View v) {
        graphDisplay.addstate(100,100,-1);
    }
    // function to add transition  fragment in the activity
    public void addTransition(View v) {
        graphDisplay.addTransition(v);
    }

    // function to add simulate fragment
    public void simulate(View v) {
        graphDisplay.simulate(1);
    }

    public void save(View v) throws IOException {

        savingfile = new SavefileFragment();
        manager =getSupportFragmentManager();
        trans = manager.beginTransaction();
        savingfile.show(manager, "SaveFragment");
        savingfile.p = WorkingA.this;
        savingfile.Codeindex=1;
        //graphDisplay.saveautomata();
    }

    public void savehelp(String s) throws IOException {
        graphDisplay.saveautomata(s);
        trans = manager.beginTransaction();
        trans.remove(savingfile);
        trans.commit();
    }
    public void load(View v) throws FileNotFoundException {

        loadFragment = new LoadFragment();
        manager =getSupportFragmentManager();
        trans = manager.beginTransaction();
        loadFragment.Codeindex=1;
        loadFragment.show(manager, "LoadFragment");
        loadFragment.p = WorkingA.this;
    }
    public void loadhelp(String s) throws FileNotFoundException {

        graphDisplay.loadautomata(s);
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
