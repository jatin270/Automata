package in.nsit.com.automata.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import in.nsit.com.automata.L;
import in.nsit.com.automata.PushDownCode.PushDownWorking;
import in.nsit.com.automata.R;
import in.nsit.com.automata.FiniteAutomata.WorkingA;
import in.nsit.com.automata.TuringMachine.TuringWorking;

/**
 * Created by ( Jatin Bansal ) on 20-02-2017.
 */

public class LoadFragment extends DialogFragment {

    View v;
    EditText t;
    Button load;
    public WorkingA p;
    public PushDownWorking w;
    public TuringWorking tm;
    public int Codeindex=1;
    ListView mainListView;
    DeleteConfirmation deleteConfirmation;
    FragmentManager manager;
    FragmentTransaction trans;
    TextView textView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);

    }

    int k;
    Scanner scanner;
    ArrayAdapter<String> listAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.loadfragment, container, false);
        t = (EditText) v.findViewById(R.id.loadname);
        load = (Button) v.findViewById(R.id.loadfile);

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               loadfunction(t.getText().toString());

            }
        });


        mainListView = (ListView) v.findViewById(R.id.loadlist);
        listAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);

        k = 0;
        scanner = null;

        setData();

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                loadfunction(textView.getText().toString());
            }
        });

        mainListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                deleteConfirmation = new DeleteConfirmation();
                manager = getActivity().getSupportFragmentManager();
                deleteConfirmation.loadfragment=LoadFragment.this;
                textView= (TextView) view.findViewById(android.R.id.text1);
                deleteConfirmation.show(manager, "TransitionFragment");
                return true;
            }
        });

        return v;
    }
    public void remove() {

        manager=getActivity().getSupportFragmentManager();
        trans=manager.beginTransaction();
        trans.remove(deleteConfirmation);
        trans.commit();

    }

    public  void setData()
    {

        if(Codeindex==1) {
            sharedPreferences = getActivity().getSharedPreferences("Names", Context.MODE_PRIVATE);
            k = sharedPreferences.getInt("size", 0);

            try {
                Log.d("Check","Hey----------------------------------");
                scanner = new Scanner(new File(getActivity().getFilesDir(), "Filenames"));
            } catch (FileNotFoundException e) {
                k = 0;
            }
        }
        else
        if(Codeindex==2){
            sharedPreferences = getActivity().getSharedPreferences("PDANames", Context.MODE_PRIVATE);
            k = sharedPreferences.getInt("size", 0);
            Log.d("K",""+k);
            try {
                scanner = new Scanner(new File(getActivity().getFilesDir(), "PDAFilenames"));
            } catch (FileNotFoundException e) {
                k = 0;
            }

        }
        else
        if(Codeindex==3) {
            sharedPreferences = getActivity().getSharedPreferences("TMNames", Context.MODE_PRIVATE);
            k = sharedPreferences.getInt("size", 0);
            Log.d("K",""+k);
            try {
                scanner = new Scanner(new File(getActivity().getFilesDir(), "TMFilenames"));
            } catch (FileNotFoundException e) {
                k = 0;
            }

        }

        if (k != 0){
            for (int i = 0; i < k; i++) {

                try {
                    String element = scanner.nextLine();
                    Log.d("elements",element);
                    if (!element.equals(""))
                        listAdapter.add(element);
                }
                catch (NoSuchElementException e)
                {

                }
            }
        }
        mainListView.setAdapter( listAdapter );

    }

    SharedPreferences sharedPreferences;

    public void yesdeleteElement() throws IOException {
        remove();
            L.toast(getActivity(),textView.getText().toString());
            Scanner scanner = null;
            int k = 0;
            if(Codeindex==1) {
                sharedPreferences = getActivity().getSharedPreferences("Names", Context.MODE_PRIVATE);
                try {
                    scanner = new Scanner(new File(getActivity().getFilesDir(), "Filenames"));
                } catch (FileNotFoundException e) {
                    k = 0;
                }
            }
            else
                if(Codeindex==2){
                    sharedPreferences = getActivity().getSharedPreferences("PDANames", Context.MODE_PRIVATE);
                    try {
                        scanner = new Scanner(new File(getActivity().getFilesDir(), "PDAFilenames"));
                    } catch (FileNotFoundException e) {
                        k = 0;
                    }

                }
            else
                if(Codeindex==3){
                    sharedPreferences = getActivity().getSharedPreferences("TMNames", Context.MODE_PRIVATE);
                    try {
                        scanner = new Scanner(new File(getActivity().getFilesDir(), "TMFilenames"));
                    } catch (FileNotFoundException e) {
                        k = 0;
                    }
                }
            k = sharedPreferences.getInt("size", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            k--;
            editor.putInt("size", k);
            editor.commit();

            String names = "";
            int flag = 0;
            if (k != 0) {
                for (int i = 0; i < k+1 ; i++) {
                    try {
                       String temp=scanner.nextLine();
                        if(!temp.equals(textView.getText().toString()))
                        names = names + temp + "\n";
                    } catch (NoSuchElementException e) {
                        e.printStackTrace();
                        flag--;
                        k = 0;
                        break;
                    }
                }
            } else
                k = 0;
            if (scanner != null)
                scanner.close();

        Log.d("Names",names);
            File namepath = new File(getActivity().getFilesDir(), "TMFilenames");
            FileOutputStream fileOutputStream = new FileOutputStream(namepath);
            fileOutputStream.write(names.getBytes());
            fileOutputStream.close();

    }


    void loadfunction(String s){
        if (s != null) {
            try {
                if(Codeindex==1)
                    p.loadhelp(s);
                else
                if(Codeindex==2)
                    w.PDALoad(s);
                else
                if(Codeindex==3)
                    tm.TMLoad(s);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            L.toast(getActivity(), "Enter the field");
        }

    }



}

