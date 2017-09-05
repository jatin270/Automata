package in.nsit.com.automata;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import in.nsit.com.automata.FiniteAutomata.NFA;
import in.nsit.com.automata.FiniteAutomata.NFADirect;
import in.nsit.com.automata.FiniteAutomata.TransitionFunctionNFA;
import in.nsit.com.automata.FiniteAutomata.WorkingA;
import in.nsit.com.automata.Fragments.PushDownTransition;
import in.nsit.com.automata.Fragments.SimulateString;
import in.nsit.com.automata.Fragments.StateFragment;
import in.nsit.com.automata.Fragments.StateFragmentTM;
import in.nsit.com.automata.Fragments.TransitionFragment;
import in.nsit.com.automata.Fragments.TuringMachineTransition;
import in.nsit.com.automata.LayoutStuff.LocableHorizontalScrollView;
import in.nsit.com.automata.LayoutStuff.LockableScrollView;
import in.nsit.com.automata.PushDownCode.PDATransitionNode;
import in.nsit.com.automata.PushDownCode.PushDownWorking;
import in.nsit.com.automata.PushDownCode.PushdownMain;
import in.nsit.com.automata.TuringMachine.MachinesLibrary;
import in.nsit.com.automata.TuringMachine.Main;
import in.nsit.com.automata.TuringMachine.TuringMachineTransitionNode;
import in.nsit.com.automata.TuringMachine.TuringMain;
import in.nsit.com.automata.TuringMachine.TuringWorking;

import static android.content.Context.VIBRATOR_SERVICE;

/**
 * Created by ( Jatin Bansal ) on 02-02-2017.
 */

public class GraphDisplay extends Fragment {


    Button[] state;
    ImageView edges;
    RelativeLayout relativeLayout, relativeLayoutworking;
    NFA nfa;

    public WorkingA finiteWorking;
    public PushDownWorking pushDownWorking;
    public TuringWorking turingWorking;


    TransitionFunctionNFA transitionFunctionNFA;
    TransitionFragment transitionFragment;
    public int CodeIndex=1;

    Set<Integer> Undeterminedstates;

    Pair<Integer, Integer> p;
    Set<Integer> acceptingStates;
    Set<Integer> rejectingStates;
    boolean[][] arr;
    public Bundle savedInstance;
    public Map<Integer, Set<Integer>> edgeinfo;
    LocableHorizontalScrollView horizontalScrollView;
    LockableScrollView verticalscrollView;
    float zerostatecoorX = 0, zerostatecoorY = 100;

    Map<Pair<Integer, Integer>, Set<Pair<Character, Integer>>> transitioninfo;

    int x;
    int y;


    StateFragment frags;
    StateFragmentTM fragstm;
    FragmentManager manager;
    SimulateString simulateString;
    FragmentTransaction trans;
    PushDownTransition pushDownTransition;
    TuringMachineTransition turingMachineTransition;
    PDATransitionNode[] pdaTransitionNode;
    PDATransitionNode[] pdatemp;
    TuringMachineTransitionNode[] turingMachineTransitionNodes;
    TuringMachineTransitionNode[] tmtemp;
    public int toremove=0;

    ArrayList<View> buttons;
    Bitmap bitmap;
    LayoutInflater inflater;
    ViewGroup container;
    int edgecounter;
    View view;
    int[] visited;

    int flag;
    int erasingstop=0;
    int PDAtransitioncount;
    int TMtransitioncount;
    int[] once = new int[100];
    int[] movecnt = new int[100];

    int density;
    int count[][];
    SharedPreferences sharedPreferences;

    Canvas canvas;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        state = new Button[30];
        transitioninfo = new HashMap<>();
        frags = new StateFragment();
        fragstm=new StateFragmentTM();
        edgecounter = 0;
        PDAtransitioncount=0;
        TMtransitioncount=0;
        edgeinfo = new HashMap<>();
        pdaTransitionNode=new PDATransitionNode[100];
        turingMachineTransitionNodes=new TuringMachineTransitionNode[100];
        density= (int) getActivity().getResources().getDisplayMetrics().density;
        visited = new int[100];
        Arrays.fill(visited, 0);
        horizontalScrollView = new LocableHorizontalScrollView(getActivity());
        verticalscrollView = new LockableScrollView(getActivity());
        relativeLayoutworking = new RelativeLayout(getActivity());
        buttons = new ArrayList<View>();
        transitionFunctionNFA = new TransitionFunctionNFA();
        acceptingStates = new HashSet<>();
        rejectingStates=new HashSet<>();
        Undeterminedstates = new HashSet<>();
        arr = new boolean[100][100];
        count = new int[1000][1000];
        simulateString = new SimulateString();
        bitmap = Bitmap.createBitmap(
                (int) getActivity().getWindowManager().getDefaultDisplay().getWidth() * 3,
                (int) getActivity().getWindowManager().getDefaultDisplay().getHeight() * 2,
                Bitmap.Config.ARGB_8888);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.inflater = inflater;
        Arrays.fill(movecnt, 0);
        Arrays.fill(once, 42);
        this.container = container;
        view = inflater.inflate(R.layout.graphdisplay, container, false);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.graphsheet);
        relativeLayout.addView(horizontalScrollView);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        horizontalScrollView.setLayoutParams(layoutParams);
        horizontalScrollView.addView(verticalscrollView);
        HorizontalScrollView.LayoutParams lp = new HorizontalScrollView.LayoutParams(HorizontalScrollView.LayoutParams.MATCH_PARENT
                , HorizontalScrollView.LayoutParams.MATCH_PARENT);
        verticalscrollView.setLayoutParams(lp);
        verticalscrollView.addView(relativeLayoutworking);
        ScrollView.LayoutParams lp2 = new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT
                , ScrollView.LayoutParams.MATCH_PARENT);
        relativeLayoutworking.setLayoutParams(lp2);
        canvas = new Canvas(bitmap);
        horizontalScrollView.setHorizontalScrollBarEnabled(false);
        verticalscrollView.setVerticalScrollBarEnabled(false);
        return view;
    }

    public void addstate(int x, int y, int index) {


        int counter = 0;
        if (index == -1) {
            for (int i = 0; i < 100; i++) {
                if (visited[i] == 0) {
                    counter = i;
                    break;
                }
            }
            Undeterminedstates.add(counter);
        } else
            counter = index;

        visited[counter] = 1;
        state[counter] = new Button(getActivity());
        state[counter].setId(counter);
        state[counter].setText("q" + counter);
        state[counter].setScaleX((float) 0.8);
        state[counter].setScaleY((float) 0.8);

        if(android.os.Build.VERSION.SDK_INT >= 16) {
            GradientDrawable shape = new GradientDrawable();
            if (counter != 0)
                shape.setColor(Color.YELLOW);
            else {
                shape.setColor(Color.CYAN);
            }
            shape.setShape(GradientDrawable.OVAL);
            state[counter].setBackground(shape);
        }
        else
        {
            Log.d("Android Check","Android version");
            ColorDrawable shape =new ColorDrawable();
            if(counter!=0)
                shape.setColor(Color.YELLOW);
            else
                shape.setColor(Color.CYAN);
            state[counter].setBackground(shape);
        }


        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150, 150);
        layoutParams.leftMargin = x;
        layoutParams.topMargin = y;
        relativeLayoutworking.addView(state[counter], layoutParams);
        buttons.add(state[counter]);
        state[counter].setLayoutParams(layoutParams);

        if (counter == 0) {
            zerostatecoorX=x+10;
            zerostatecoorY=y+75;
            visited[0]=2;
            recreategraph();
        }
        state[counter].setOnTouchListener(new ChoiceTouchListener());

    }

    public void delete(int v) {

        if (v == 0) {
            L.toast(getActivity(), "Inital state once created cannot be destroyed.");
            trans = manager.beginTransaction();
            trans.remove((Fragment) frags);
            trans.commit();
            return;
        }
        visited[v] = 0;
        buttons.remove(state[v]);
        Undeterminedstates.remove(v);
        if (acceptingStates.contains(v))
            acceptingStates.remove(v);
        relativeLayoutworking.removeView(state[v]);
        Set<Pair<Integer, Character>> temp = new HashSet<>();
        Set<Pair<Integer, Character>> temp2 = new HashSet<>();
        if(CodeIndex==1) {
            for (final Map.Entry<Pair<Integer, Character>, Set<Integer>> entry : transitionFunctionNFA.function.entrySet()) {
                final int result1 = entry.getKey().first;
                if (result1 == v) {
                    temp.add(entry.getKey());
                    continue;
                }
                Set<Integer> s;

                s = entry.getValue();
                Iterator<Integer> itr = s.iterator();
                while (itr.hasNext()) {
                    final int p = itr.next();

                    if (p == v) {
                        temp2.add(entry.getKey());
                    }
                }
            }

            Iterator<Pair<Integer, Character>> itr = temp.iterator();

            while ((itr.hasNext())) {
                transitionFunctionNFA.function.remove(itr.next());
            }

            itr = temp2.iterator();
            while ((itr.hasNext())) {
                transitionFunctionNFA.function.get(itr.next()).remove(v);
            }
        }
        else
        if(CodeIndex==2)
        {
            pdatemp=new PDATransitionNode[100];
            int k=0;

            for(int i=0;i<PDAtransitioncount;i++)
            {
                if(pdaTransitionNode[i].getResult1()==v||pdaTransitionNode[i].getResult2()==v)
                {

                }
                else
                {
                    pdatemp[k]=new PDATransitionNode(pdaTransitionNode[i].getResult1(),pdaTransitionNode[i].getResult2(),pdaTransitionNode[i].getRead(),pdaTransitionNode[i].getPop(),pdaTransitionNode[i].getPush());
                    k++;
                }
            }
            pdaTransitionNode=new PDATransitionNode[100];
            for(int i=0;i<k;i++)
            {
                pdaTransitionNode[i]=new PDATransitionNode(pdatemp[i].getResult1(),pdatemp[i].getResult2(),pdatemp[i].getRead(),pdatemp[i].getPop(),pdatemp[i].getPush());

            }
            PDAtransitioncount=k;
        }
        else
        if(CodeIndex==3)
        {

            tmtemp=new TuringMachineTransitionNode[100];
            int k=0;

            for(int i=0;i<TMtransitioncount;i++)
            {
                if(turingMachineTransitionNodes[i].getResult1()==v||turingMachineTransitionNodes[i].getResult2()==v)
                {

                }
                else
                {
                    tmtemp[k]=new TuringMachineTransitionNode(turingMachineTransitionNodes[i].getResult1(),turingMachineTransitionNodes[i].getResult2(),turingMachineTransitionNodes[i].getRead(),turingMachineTransitionNodes[i].getWrite(),turingMachineTransitionNodes[i].getDirection());
                    k++;
                }
            }
            turingMachineTransitionNodes=new TuringMachineTransitionNode[100];
            for(int i=0;i<k;i++)
            {
                turingMachineTransitionNodes[i]=new TuringMachineTransitionNode(tmtemp[i].getResult1(),tmtemp[i].getResult2(),tmtemp[i].getRead(),tmtemp[i].getWrite(),tmtemp[i].getDirection());

            }
            TMtransitioncount=k;
        }

        trans = manager.beginTransaction();
        trans.remove(frags);
        trans.commit();
        Toast.makeText(getActivity(), v + " State deleted", Toast.LENGTH_SHORT).show();
        bitmap.eraseColor(Color.parseColor("#FFFFFFFF"));

        if(CodeIndex==1)
            new CreategraphAsynctask().execute();
        else
        if(CodeIndex==2)
            new PDACreategraphAsynctask().execute();
        else
        if(CodeIndex==3)
            new TMCreategraphAsynctask().execute();

    }

    public void addfinalstate(int v, int check) {

        int result1 = v;
        visited[v] = 2;
        acceptingStates.add(result1);
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(Color.RED);
        shape.setShape(GradientDrawable.OVAL);
        state[v].setBackground(shape);
        state[v].setBackground(shape);
        edges = new ImageView(getActivity());
        relativeLayoutworking.addView(edges);
        edges.setImageBitmap(bitmap);
        float radius = 75;
        Paint mpaint = new Paint();
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setStrokeWidth(5);
        mpaint.setColor(Color.RED);
        int startx = state[v].getLeft() + state[v].getWidth() / 2;
        int starty = state[v].getTop() + state[v].getHeight() / 2;
        canvas.drawCircle(startx, starty, radius, mpaint);

        if (check == 0) {
            trans = manager.beginTransaction();
            if(CodeIndex!=3)
                trans.remove((Fragment) frags);
            else
                trans.remove(fragstm);
            trans.commit();
            Toast.makeText(getActivity(), "Final State added", Toast.LENGTH_SHORT).show();
        }
    }

    public void addrejectState(int v,int check){

        int result1 = v;
        visited[v] = 2;
        rejectingStates.add(result1);
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(Color.BLUE);
        shape.setShape(GradientDrawable.OVAL);
        state[v].setBackground(shape);
        state[v].setBackground(shape);
        edges = new ImageView(getActivity());
        relativeLayoutworking.addView(edges);
        edges.setImageBitmap(bitmap);
        float radius = 75;
        Paint mpaint = new Paint();
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setStrokeWidth(5);
        mpaint.setColor(Color.BLUE);
        int startx = state[v].getLeft() + state[v].getWidth() / 2;
        int starty = state[v].getTop() + state[v].getHeight() / 2;
        canvas.drawCircle(startx, starty, radius, mpaint);

        if (check == 0) {
            trans = manager.beginTransaction();
            if(CodeIndex!=3)
                trans.remove(frags);
            else
                trans.remove(fragstm);
            trans.commit();
            Toast.makeText(getActivity(), "Final State added", Toast.LENGTH_SHORT).show();
        }
    }

    public void addTransition(View v) {

        transitionFragment = new TransitionFragment();
        manager = getActivity().getSupportFragmentManager();
        trans = manager.beginTransaction();
        transitionFragment.show(manager, "TransitionFragment");
        transitionFragment.p = GraphDisplay.this;

    }

    public void addTransitionPushDownAutomata(View v){

        pushDownTransition=new PushDownTransition();
        manager=getActivity().getSupportFragmentManager();
        pushDownTransition.show(manager,"PushDownTransaction");
        pushDownTransition.p=GraphDisplay.this;
    }

    public void addTransitionTuringMachine(View v) {

        turingMachineTransition=new TuringMachineTransition();
        manager=getActivity().getSupportFragmentManager();
        turingMachineTransition.show(manager,"TuringMachineTransition");
        turingMachineTransition.p=GraphDisplay.this;
    }

    public void transitionadding(int result1, int result2, char ch, int check) {

        if (Undeterminedstates.contains(result1) && Undeterminedstates.contains(result2)) {
            visited[result1] = 2;
            visited[result2] = 2;
        } else {
            L.toast(getActivity(), "State does not exist");
            trans = manager.beginTransaction();
            trans.remove(transitionFragment);
            trans.commit();
            return;
        }
        transitionFunctionNFA.setTransition(result1, result2, ch);
        if (transitioninfo.containsKey(Pair.create(result1, result2))) {
            transitioninfo.get(Pair.create(result1, result2)).add(Pair.create(ch, edgecounter));
        } else {
            Set<Pair<Character, Integer>> ts = new HashSet<>();
            ts.add(Pair.create(ch, edgecounter));
            transitioninfo.put(Pair.create(result1, result2), ts);
        }

        if (edgeinfo.containsKey(result1)) {
            edgeinfo.get(result1).add(result2);
        } else {
            Set<Integer> set = new HashSet<Integer>();
            set.add(result2);
            edgeinfo.put(result1, set);
        }
        if (edgeinfo.containsKey(result2)) {
            edgeinfo.get(result1).add(result1);
        } else {
            Set<Integer> set = new HashSet<Integer>();
            set.add(result1);
            edgeinfo.put(result2, set);
        }
        drawlines(result1, result2,ch," ");

        if (check == 1)
            return;

        trans = manager.beginTransaction();
        trans.remove((Fragment) transitionFragment);
        trans.commit();
        Toast.makeText(getActivity(), "Transition added", Toast.LENGTH_SHORT).show();
    }

    public void transitionaddingPDA(int result1,int result2,String read,String pop,String push,int check){

        Log.d("Data",""+result1+" "+result2+" "+read+" "+pop+" "+push);
        if (Undeterminedstates.contains(result1) && Undeterminedstates.contains(result2)) {
            visited[result1] = 2;
            visited[result2] = 2;
        } else {
            L.toast(getActivity(), "State does not exist");
            trans = manager.beginTransaction();
            trans.remove(pushDownTransition);
            trans.commit();
            return;
        }



        pdaTransitionNode[PDAtransitioncount]=new PDATransitionNode(result1,result2,read,pop,push);
        PDAtransitioncount++;
        drawlines(result1,result2,'U',read+","+pop+","+push);
        if(check==1)
            return;
        trans = manager.beginTransaction();
        trans.remove(pushDownTransition);
        trans.commit();
        return;

    }

    public void transitionaddingTM(int result1,int result2,String read,String write,String direction,int check){

        Log.d("Data",""+result1+" "+result2+" "+read+" "+write+" "+direction);
        if (Undeterminedstates.contains(result1) && Undeterminedstates.contains(result2)) {
            visited[result1] = 2;
            visited[result2] = 2;
        } else {
            L.toast(getActivity(), "State does not exist");
            trans = manager.beginTransaction();
            trans.remove(turingMachineTransition);
            trans.commit();
            return;
        }

        turingMachineTransitionNodes[TMtransitioncount]=new TuringMachineTransitionNode(result1,result2,read,write,direction);
        TMtransitioncount++;
        drawlines(result1,result2,'U',read+","+write+","+direction);
        if(check==1)
            return;
        trans = manager.beginTransaction();
        trans.remove(turingMachineTransition);
        trans.commit();
        return;

    }

    public void simulate(int val) {
        manager = getActivity().getSupportFragmentManager();
        trans = manager.beginTransaction();
        simulateString.simulationIndex=val;
        simulateString.show(manager, "Simulate");
        simulateString.p = GraphDisplay.this;

    }

    public void simulate1(String s) throws IOException {

        trans = manager.beginTransaction();
        trans.remove(simulateString);
        trans.commit();

        // nfa = new NFA(transitionFunctionNFA, 0, acceptingStates);
        NFADirect nfaDirect=new NFADirect(acceptingStates,transitionFunctionNFA);
        nfaDirect.loaddata(Undeterminedstates.size(),10);
        // nfa.convertdfatonfa(Undeterminedstates.size(), 2);
        boolean ouput= nfaDirect.execute(s);
        if (ouput)
            Toast.makeText(getActivity(), "Accepted", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getActivity(), "Rejected", Toast.LENGTH_LONG).show();

        finiteWorking.outputString=nfaDirect.outputString;
    }

    public void simulate2(String s) throws FileNotFoundException {

        trans = manager.beginTransaction();
        trans.remove(simulateString);
        trans.commit();
        boolean k;
        PushdownMain pushdownMain = new PushdownMain();
        pushdownMain.load(Undeterminedstates, acceptingStates, pdaTransitionNode, PDAtransitioncount);
        k = pushdownMain.execute(s);

        if (k == true)
            Toast.makeText(getActivity(), "Accepted", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getActivity(), "Rejected", Toast.LENGTH_LONG).show();

        pushDownWorking.outputString = pushdownMain.outputString;

    }

    public void simulate3(String s){

        trans = manager.beginTransaction();
        trans.remove(simulateString);
        trans.commit();
        boolean k=false;
        //MachinesLibrary machinesLibrary=new MachinesLibrary(Undeterminedstates,acceptingStates,null,turingMachineTransitionNodes,TMtransitioncount);
        //Main main=new Main();
        TuringMain turingMain=new TuringMain(Undeterminedstates,acceptingStates,rejectingStates,turingMachineTransitionNodes,TMtransitioncount);
        // k=main.execute(s,machinesLibrary.load());
        turingMain.loadData();
        k=turingMain.execute(s);
        if (k == true)
        {
            Toast.makeText(getActivity(), "Accepted", Toast.LENGTH_LONG).show();
            turingWorking.outputString=turingMain.outputString+"Accepted\n\n";
        }
        else
        {
            Toast.makeText(getActivity(), "Rejected", Toast.LENGTH_LONG).show();
            turingWorking.outputString=turingMain.outputString+"Rejected\n\n";
        }

    }

    public class ChoiceTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, final MotionEvent event) {

            verticalscrollView.setScrollingEnabled(false);
            horizontalScrollView.setScrollingEnabled(false);
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
            final Timer timer = new Timer();
            final int s = v.getId();
            final TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    show(s);
                }
            };

            switch (event.getAction() & MotionEvent.ACTION_MASK) {

                case MotionEvent.ACTION_DOWN:

                    once[s] = 0;
                    timer.schedule(task, 2000);
                    //Log.d("Hey","Action_Down");
                    verticalscrollView.setScrollingEnabled(false);
                    horizontalScrollView.setScrollingEnabled(false);
                    RelativeLayout.LayoutParams lparams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                    x = X - lparams.leftMargin;
                    y = Y - lparams.topMargin;
                    break;

                case MotionEvent.ACTION_BUTTON_PRESS:

                case MotionEvent.ACTION_BUTTON_RELEASE:
                    break;

                case MotionEvent.ACTION_UP:

                    task.cancel();
                    timer.cancel();
                    timer.purge();
                    movecnt[s] = 0;
                    once[s] = 1;
                    erasingstop=0;
                    int c = v.getId();
                    verticalscrollView.setScrollingEnabled(true);
                    horizontalScrollView.setScrollingEnabled(true);
                    // bitmap.eraseColor(Color.parseColor("#FFFFFFFF"));
                    if (visited[v.getId()] == 2)
                    {
                        if(CodeIndex==1)
                            new CreategraphAsynctask().execute();
                        else
                        if(CodeIndex==2)
                            new PDACreategraphAsynctask().execute();
                        if(CodeIndex==3)
                            new TMCreategraphAsynctask().execute();

                        /*
                        try {
                            saveautomata("temporaryfile");
                            clean();
                            loadautomata("temporaryfile");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        */

                    }
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;

                case MotionEvent.ACTION_POINTER_UP:
                    break;

                case MotionEvent.ACTION_MOVE:
                    flag = 1;

                    //Log.d("Hey","Action_Move");
                    movecnt[s]++;
                    verticalscrollView.setScrollingEnabled(false);
                    horizontalScrollView.setScrollingEnabled(false);
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                    layoutParams.leftMargin = X - x;
                    layoutParams.topMargin = Y - y;
                    layoutParams.rightMargin = (int) getResources().getDimension(R.dimen.activity_horizontal_margin);
                    layoutParams.bottomMargin = (int) getResources().getDimension(R.dimen.activity_horizontal_margin);
                    v.setLayoutParams(layoutParams);

                    if (v.getId() == 0) {
                        zerostatecoorX = X - x + 10 ;
                        zerostatecoorY = Y - y + 75;
                    }

                    if(erasingstop==0) {
                        if (visited[v.getId()] == 2) {
                            /*
                            Paint clearPaint = new Paint();
                            clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                            canvas.drawRect(0, 0, 2000, 2000, clearPaint);
                            */
                            erasingstop=1;
                            bitmap.eraseColor(Color.parseColor("#FFFFFFFF"));
                        }
                    }
                    // new CreategraphAsynctask().execute();
                    break;
            }

            relativeLayoutworking.invalidate();
            return true;
        }
    }

    int samestate[]=new int[50];

    void drawlines(int result1, int result2, char t,String s) {

        edges = null;
        System.gc();
        edges = new ImageView(getActivity());
        relativeLayoutworking.addView(edges);
        edges.setImageBitmap(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.rgb(255, 153, 51));
        paint.setStrokeWidth(5);

        Paint painttext = new Paint();
        painttext.setColor(Color.BLACK);
        painttext.setTextSize(40);

        if (result1 == -1 && result2 == -1) {
            canvas.drawLine(0, 100, 400, 500, paint);
            return;
        }

        float startx = (float) (state[result1].getLeft() + state[result1].getWidth() / 2);
        float starty = (float) (state[result1].getTop()) + state[result1].getHeight() / 2;
        float endx = (float) (state[result2].getLeft() + state[result2].getWidth() / 2);
        float endy = (float) (state[result2].getTop() + state[result2].getHeight() / 2);

        arr[result1][result2] = true;
        count[result1][result2]++;

        if (result1 == result2) {
            samestate[result1]++;
            float radius = 100;
            Paint mpaint = new Paint();
            mpaint.setStyle(Paint.Style.STROKE);
            mpaint.setStrokeWidth(5);
            mpaint.setColor(Color.rgb(255, 153, 51));
            canvas.drawCircle(startx - 75, starty - 75, radius, mpaint);
            //to draw an arrow, just lines needed, so style is only STROKE
            mpaint.setStyle(Paint.Style.STROKE);
            mpaint.setColor(Color.RED);
            mpaint.setStrokeWidth(5);

            //create a path to draw on
            Path arrowPath = new Path();
            //draw path on canvas
            canvas.drawPath(arrowPath, paint);
            float endx1 = endx + 25;
            float endy1 = endy - radius;

            float printingx1=endx1;
            float printingy1=endy1+25*samestate[result1];

            if(CodeIndex==1)
                canvas.drawText("" + t, printingx1 + 40, printingy1 + 20, painttext);
            else
            if(CodeIndex==2||CodeIndex==3)
                canvas.drawText(s, printingx1 + 40, printingy1 + 20, painttext);

            arrowPath.moveTo(endx1, endy1);//move back to the center
            arrowPath.lineTo(endx1 - 20, endy1 - 20);//draw the first arrowhead line to the left
            arrowPath.moveTo(endx1, endy1);//move back to the center
            arrowPath.lineTo(endx1 + 20, endy1 - 20);//draw the next arrowhead line to the right

            Matrix mMatrix = new Matrix();
            mMatrix.postRotate(180, endx1, endy1);
            arrowPath.transform(mMatrix);
            canvas.drawPath(arrowPath, paint);
            canvas.drawPath(arrowPath, mpaint);

        } else if (arr[result2][result1] == true || count[result1][result2] > 1 && count[result1][result2] <= 3) {
            Paint mpaint = new Paint();
            mpaint.setAntiAlias(true);
            mpaint.setStyle(Paint.Style.STROKE);
            mpaint.setStrokeWidth(7);
            mpaint.setColor(Color.rgb(255, 153, 51));

            if (count[result1][result2] == 3) {
                startx = startx + endx;
                endx = startx - endx;
                startx = startx - endx;

                starty = starty + endy;
                endy = starty - endy;
                starty = starty - endy;
            }

            final Path path = new Path();
            int midX = (int) (startx + ((endx - startx) / 2));
            int midY = (int) (starty + ((endy - starty) / 2));
            float xDiff = midX - startx;
            float yDiff = midY - starty;
            double angle = (Math.atan2(yDiff, xDiff) * (180 / Math.PI)) - 90;
            double angleRadians = Math.toRadians(angle);
            float pointX = (float) (midX + 200 * Math.cos(angleRadians));
            float pointY = (float) (midY + 200 * Math.sin(angleRadians));

            path.moveTo(startx, starty);
            path.cubicTo(startx, starty, pointX, pointY, endx, endy);
            canvas.drawPath(path, mpaint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.RED);
            paint.setStrokeWidth(7);

            Path arrowPath = new Path();

            Path initialarrow = new Path();
            initialarrow.moveTo(zerostatecoorX, zerostatecoorY);
            initialarrow.moveTo(zerostatecoorX - 100, zerostatecoorY);

            //draw path on canvas
            canvas.drawPath(arrowPath, paint);

            float endx1 = (float) ((startx + endx) / 2 + 100 * Math.cos(angleRadians));
            float endy1 = (float) ((starty + endy) / 2 + 100 * Math.sin(angleRadians));



            if (count[result1][result2] == 3) {
                startx = startx + endx;
                endx = startx - endx;
                startx = startx - endx;
                starty = starty + endy;
                endy = starty - endy;
                starty = starty - endy;
            }

            if (startx < endx) {

                arrowPath.moveTo(endx1, endy1);//move back to the center
                arrowPath.lineTo(endx1 - 20, endy1 - 20);//draw the first arrowhead line to the left
                arrowPath.moveTo(endx1, endy1);//move back to the center
                arrowPath.lineTo(endx1 + 20, endy1 - 20);//draw the next arrowhead line to the right

            } else {
                arrowPath.moveTo(endx1, endy1);//move back to the center
                arrowPath.lineTo(endx1 + 20, endy1 + 20);//draw the first arrowhead line to the left
                arrowPath.moveTo(endx1, endy1);//move back to the center
                arrowPath.lineTo(endx1 - 20, endy1 + 20);//draw the next arrowhead line to the right

            }
            // canvas.rotate((float) Math.toDegrees(Math.atan((endy-starty)/(endx-startx)))-90,endx1,endy1);
            Matrix mMatrix = new Matrix();
            mMatrix.postRotate((float) Math.toDegrees(Math.atan((endy - starty) / (endx - startx))) - 90, endx1, endy1);
            arrowPath.transform(mMatrix);
            if(CodeIndex==1)
            {
                canvas.drawText("" + t, endx1 + 20, endy1 + 20, painttext);
            }
            else
            if(CodeIndex==2||CodeIndex==3) {

                canvas.save();
                canvas.rotate((float) Math.toDegrees(Math.atan((endy - starty) / (endx - startx))), endx1, endy1);
                canvas.drawText(s, endx1 , endy1 + 50, painttext);
                canvas.restore();
            }


            canvas.drawPath(arrowPath, paint);
        } else if (count[result1][result2] == 1) {
            canvas.drawLine(startx, starty, endx, endy, paint);
            //to draw an arrow, just lines needed, so style is only STROKE
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.RED);
            paint.setStrokeWidth(7);
            //create a path to draw on
            Path arrowPath = new Path();
            //draw path on canvas
            canvas.drawPath(arrowPath, paint);
            float endx1 = (startx + endx) / 2;
            float endy1 = (starty + endy) / 2;


            if (startx < endx) {
                arrowPath.moveTo(endx1, endy1);//move back to the center
                arrowPath.lineTo(endx1 - 20, endy1 - 20);//draw the first arrowhead line to the left
                arrowPath.moveTo(endx1, endy1);//move back to the center
                arrowPath.lineTo(endx1 + 20, endy1 - 20);//draw the next arrowhead line to the right
            } else {
                arrowPath.moveTo(endx1, endy1);//move back to the center
                arrowPath.lineTo(endx1 + 20, endy1 + 20);//draw the first arrowhead line to the left
                arrowPath.moveTo(endx1, endy1);//move back to the center
                arrowPath.lineTo(endx1 - 20, endy1 + 20);//draw the next arrowhead line to the right
            }

            // canvas.rotate((float) Math.toDegrees(Math.atan((endy-starty)/(endx-startx)))-90,endx1,endy1);
            Matrix mMatrix = new Matrix();
            mMatrix.postRotate((float) Math.toDegrees(Math.atan((endy - starty) / (endx - startx))) - 90, endx1, endy1);
            arrowPath.transform(mMatrix);
            canvas.save();
            if(CodeIndex==1) {
                canvas.drawText("" + t, endx1 + 20, endy1 + 20, painttext);
            }
            else
            if(CodeIndex==2||CodeIndex==3) {
                canvas.save();
                canvas.rotate((float) Math.toDegrees(Math.atan((endy - starty) / (endx - startx))) , endx1, endy1);
                canvas.drawText(s, endx1 , endy1 + 50, painttext);
                canvas.restore();
            }
            canvas.drawPath(arrowPath, paint);

        }


    }

    public void saveautomata(String name) throws IOException {
        if(!name.equals("temporaryfile")) {
            Scanner scanner = null;
            int k = 0;
            sharedPreferences = getActivity().getSharedPreferences("Names", Context.MODE_PRIVATE);
            k = sharedPreferences.getInt("size", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            k++;
            editor.putInt("size", k);
            editor.commit();
            Log.d("k", "" + k);
            try {
                scanner = new Scanner(new File(getActivity().getFilesDir(), "Filenames"));
            } catch (FileNotFoundException e) {
                k = 0;
            }
            String names = "";
            names = names + name + "\n";
            int flag = 0;
            if (k != 0) {
                for (int i = 0; i < k - 1; i++) {
                    try {
                        names = names + scanner.nextLine() + "\n";
                        Log.d("JAtin", names);
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

            File namepath = new File(getActivity().getFilesDir(), "Filenames");
            FileOutputStream fileOutputStream = new FileOutputStream(namepath);
            fileOutputStream.write(names.getBytes());
            fileOutputStream.close();
        }

        File path = new File(getActivity().getFilesDir(), name);
        FileOutputStream outStream = new FileOutputStream(path);
        String s = "" + Undeterminedstates.size() + "\n";
        Iterator<Integer> itr = Undeterminedstates.iterator();
        while (itr.hasNext()) {
            s = s + " " + itr.next();
        }
        s = s + '\n';
        outStream.write(s.getBytes());
        String s1 = "";
        Iterator<Integer> itr2 = Undeterminedstates.iterator();
        while (itr2.hasNext()) {
            int i = itr2.next();
            s1 = s1 + state[i].getLeft() + " " + state[i].getTop() + " " + i + "\n";
        }
        outStream.write(s1.getBytes());

        String p = "" + acceptingStates.size() + " ";
        Iterator<Integer> itr3 = acceptingStates.iterator();
        while (itr3.hasNext()) {
            p = p + itr3.next() + " ";
        }
        p = p + "\n";
        outStream.write(p.getBytes());


        String transition = "" + transitionFunctionNFA.function.size() + "\n";
        for (Map.Entry<Pair<Integer, Character>, Set<Integer>> entry : transitionFunctionNFA.function.entrySet()) {
            transition = transition + entry.getKey().first + " " + entry.getKey().second + " " + entry.getValue().size() + " ";
            Iterator<Integer> itr1 = entry.getValue().iterator();
            while (itr1.hasNext()) {
                transition = transition + itr1.next() + " ";
            }
            transition = transition + "\n";
        }
        outStream.write(transition.getBytes());
        outStream.close();
        if (!name.equals("temporaryfile"))
            L.toast(getActivity(), "Automata Saved");
    }

    public void PDASave(String name) throws IOException {

        if(!name.equals("PDAtemporaryfile")) {
            Scanner scanner = null;
            int k = 0;
            sharedPreferences = getActivity().getSharedPreferences("PDANames", Context.MODE_PRIVATE);
            k = sharedPreferences.getInt("size", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            k++;
            editor.putInt("size", k);
            editor.commit();
            Log.d("k", "" + k);
            try {
                scanner = new Scanner(new File(getActivity().getFilesDir(), "PDAFilenames"));
            } catch (FileNotFoundException e) {
                k = 0;
            }
            String names = "";
            names = names + name + "\n";
            if (k != 0) {
                for (int i = 0; i < k - 1; i++) {
                    try {
                        names = names + scanner.nextLine() + "\n";
                    } catch (NoSuchElementException e) {
                        e.printStackTrace();
                        k = 0;
                        break;
                    }
                }
            } else
                k = 0;
            if (scanner != null)
                scanner.close();

            File namepath = new File(getActivity().getFilesDir(), "PDAFilenames");
            FileOutputStream fileOutputStream = new FileOutputStream(namepath);
            fileOutputStream.write(names.getBytes());
            fileOutputStream.close();
        }

        File path = new File(getActivity().getFilesDir(), name);
        FileOutputStream outStream = new FileOutputStream(path);
        String s = "" + Undeterminedstates.size() + "\n";
        Iterator<Integer> itr = Undeterminedstates.iterator();
        while (itr.hasNext()) {
            s = s + " " + itr.next();
        }
        s = s + '\n';
        outStream.write(s.getBytes());
        String s1 = "";
        Iterator<Integer> itr2 = Undeterminedstates.iterator();
        while (itr2.hasNext()) {
            int i = itr2.next();
            s1 = s1 + state[i].getLeft() + " " + state[i].getTop() + " " + i + "\n";
        }
        outStream.write(s1.getBytes());
        String p = "" + acceptingStates.size() + " ";
        Iterator<Integer> itr3 = acceptingStates.iterator();
        while (itr3.hasNext()) {
            p = p + itr3.next() + " ";
        }
        p = p + "\n";
        outStream.write(p.getBytes());
        String transsize=PDAtransitioncount+"\n";
        outStream.write(transsize.getBytes());
        String transition="";
        for(int i=0;i<PDAtransitioncount;i++)
        {
            int result1=pdaTransitionNode[i].getResult1();
            int result2=pdaTransitionNode[i].getResult2();
            String read=pdaTransitionNode[i].getRead();
            String pop =pdaTransitionNode[i].getPop();
            String push=pdaTransitionNode[i].getPush();
            transition+=result1+"\n";
            transition+=result2+"\n";
            transition=transition+read+"\n";
            transition=transition+pop+"\n";
            transition=transition+push+"\n";
        }
        Log.d("count",transition);
        outStream.write(transition.getBytes());
        outStream.close();
        if (!name.equals("PDAtemporaryfile"))
            L.toast(getActivity(), "Automata Saved");

    }

    public void TMSave(String name) throws IOException {


        if(!name.equals("TMtemporaryfile")) {
            Scanner scanner = null;
            int k = 0;
            sharedPreferences = getActivity().getSharedPreferences("TMNames", Context.MODE_PRIVATE);
            k = sharedPreferences.getInt("size", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            k++;
            editor.putInt("size", k);
            editor.commit();
            Log.d("k", "" + k);
            try {
                scanner = new Scanner(new File(getActivity().getFilesDir(), "TMFilenames"));
            } catch (FileNotFoundException e) {
                k = 0;
            }
            String names = "";
            names = names + name + "\n";
            if (k != 0) {
                for (int i = 0; i < k - 1; i++) {
                    try {
                        names = names + scanner.nextLine() + "\n";
                    } catch (NoSuchElementException e) {
                        e.printStackTrace();
                        k = 0;
                        break;
                    }
                }
            } else
                k = 0;
            if (scanner != null)
                scanner.close();

            File namepath = new File(getActivity().getFilesDir(), "TMFilenames");
            FileOutputStream fileOutputStream = new FileOutputStream(namepath);
            fileOutputStream.write(names.getBytes());
            fileOutputStream.close();
        }

        File path = new File(getActivity().getFilesDir(), name);
        FileOutputStream outStream = new FileOutputStream(path);
        String s = "" + Undeterminedstates.size() + "\n";
        Iterator<Integer> itr = Undeterminedstates.iterator();
        while (itr.hasNext()) {
            s = s + " " + itr.next();
        }
        s = s + '\n';
        outStream.write(s.getBytes());
        String s1 = "";
        Iterator<Integer> itr2 = Undeterminedstates.iterator();
        while (itr2.hasNext()) {
            int i = itr2.next();
            s1 = s1 + state[i].getLeft() + " " + state[i].getTop() + " " + i + "\n";
        }
        outStream.write(s1.getBytes());
        String p = "" + acceptingStates.size() + " ";
        Iterator<Integer> itr3 = acceptingStates.iterator();
        while (itr3.hasNext()) {
            p = p + itr3.next() + " ";
        }
        p = p + "\n";
        outStream.write(p.getBytes());

        if(CodeIndex==3)
        {
            String r = "" + rejectingStates.size() + " ";
            Iterator<Integer> itr4 = rejectingStates.iterator();
            while (itr4.hasNext()) {
                r = r + itr4.next() + " ";
            }
            r = r + "\n";
            outStream.write(r.getBytes());
        }

        String transsize=TMtransitioncount+"\n";
        outStream.write(transsize.getBytes());
        String transition="";
        for(int i=0;i<TMtransitioncount;i++)
        {
            int result1=turingMachineTransitionNodes[i].getResult1();
            int result2=turingMachineTransitionNodes[i].getResult2();
            String read=turingMachineTransitionNodes[i].getRead();
            String pop =turingMachineTransitionNodes[i].getWrite();
            String push=turingMachineTransitionNodes[i].getDirection();
            transition+=result1+"\n";
            transition+=result2+"\n";
            transition=transition+read+"\n";
            transition=transition+pop+"\n";
            transition=transition+push+"\n";
        }
        Log.d("count",transition);
        outStream.write(transition.getBytes());
        outStream.close();
        if (!name.equals("TMtemporaryfile"))
            L.toast(getActivity(), "Automata Saved");
    }

    public void loadautomata(String name) throws FileNotFoundException {

        clean();
        Scanner scanner = new Scanner(new File(getActivity().getFilesDir(), name));
        int p = scanner.nextInt();
        for (int i = 0; i < p; i++) {
            int k = scanner.nextInt();
            Undeterminedstates.add(k);
        }

        for (int i = 0; i < p; i++) {

            int a = scanner.nextInt();
            int b = scanner.nextInt();
            int index = scanner.nextInt();
            addstate(a, b, index);
        }

        int acceptingsize = scanner.nextInt();

        for (int i = 0; i < acceptingsize; i++) {
            addfinalstate(scanner.nextInt(), 1);
        }

        int transitionsize = scanner.nextInt();
        for (int i = 0; i < transitionsize; i++) {

            int c = scanner.nextInt();
            String ch = scanner.next();
            char a[] = ch.toCharArray();

            int setsize = scanner.nextInt();
            for (int j = 0; j < setsize; j++) {

                int u = scanner.nextInt();
                transitionadding(c, u, a[0], 1);
            }
        }
        scanner.close();
        new CreategraphAsynctask().execute();
        bitmap.eraseColor(Color.parseColor("#FFFFFFFF"));
    }

    public void PDALoad(String name) throws FileNotFoundException {

        PDAclean();
        Scanner scanner = new Scanner(new File(getActivity().getFilesDir(), name));
        int p = scanner.nextInt();
        for (int i = 0; i < p; i++) {
            int k = scanner.nextInt();
            Undeterminedstates.add(k);
        }

        for (int i = 0; i < p; i++) {

            int a = scanner.nextInt();
            int b = scanner.nextInt();
            int index = scanner.nextInt();
            addstate(a, b, index);
        }

        int acceptingsize = scanner.nextInt();

        for (int i = 0; i < acceptingsize; i++) {
            addfinalstate(scanner.nextInt(), 1);
        }


        int count  = scanner.nextInt();
        Log.d("count",""+count);
        try {
            for (int i = 0; i < count; i++) {
                String n1 = scanner.next();
                String n2 = scanner.next();
                Log.d("count", "n1-" + n1);
                Log.d("count", "n2-" + n2);
                int result1 = Integer.parseInt(n1);
                int result2 = Integer.parseInt(n2);
                String read = scanner.next();
                String pop = scanner.next();
                String push = scanner.next();
                transitionaddingPDA(result1, result2, read, pop, push, 1);
            }
        }
        catch (NoSuchElementException e)
        {
            Log.d("Error","Nosuch element");
        }

        scanner.close();
        new PDACreategraphAsynctask().execute();
        /*
        bitmap.eraseColor(Color.parseColor("#FFFFFFFF"));
        new PDACreategraphAsynctask().execute();
        bitmap.eraseColor(Color.parseColor("#FFFFFFFF"));
        */
    }

    public void TMLoad(String name) throws FileNotFoundException {


        TMclean();
        Scanner scanner = new Scanner(new File(getActivity().getFilesDir(), name));
        int p = scanner.nextInt();
        for (int i = 0; i < p; i++) {
            int k = scanner.nextInt();
            Undeterminedstates.add(k);
        }

        for (int i = 0; i < p; i++) {

            int a = scanner.nextInt();
            int b = scanner.nextInt();
            int index = scanner.nextInt();
            addstate(a, b, index);
        }

        int acceptingsize = scanner.nextInt();

        for (int i = 0; i < acceptingsize; i++) {
            addfinalstate(scanner.nextInt(), 1);
        }

        if(CodeIndex==3) {
            int rejectingSize = scanner.nextInt();

            for (int i = 0; i < rejectingSize; i++) {
                addrejectState(scanner.nextInt(), 1);
            }
        }




        int count  = scanner.nextInt();
        Log.d("count",""+count);
        try {
            for (int i = 0; i < count; i++) {
                String n1 = scanner.next();
                String n2 = scanner.next();
                Log.d("count", "n1-" + n1);
                Log.d("count", "n2-" + n2);
                int result1 = Integer.parseInt(n1);
                int result2 = Integer.parseInt(n2);
                String read = scanner.next();
                String pop = scanner.next();
                String push = scanner.next();
                transitionaddingTM(result1, result2, read, pop, push, 1);
            }
        }
        catch (NoSuchElementException e)
        {
            Log.d("Error","Nosuch element");
        }

        scanner.close();
        new TMCreategraphAsynctask().execute();

        bitmap.eraseColor(Color.parseColor("#FFFFFFFF"));
        /*
        new PDACreategraphAsynctask().execute();
        bitmap.eraseColor(Color.parseColor("#FFFFFFFF"));
        */
    }

    void recreategraph() {
        for (int i = 0; i < 1000; i++)
            Arrays.fill(count[i], 0);
        for (int i = 0; i < 100; i++)
            Arrays.fill(arr[i], false);
        if (state[0] == null) {
            return;
        }
        Arrays.fill(samestate,0);
        for (final Map.Entry<Pair<Integer, Character>, Set<Integer>> entry : transitionFunctionNFA.function.entrySet()) {
            final int result1 = entry.getKey().first;
            Set<Integer> s;
            s = entry.getValue();
            Iterator<Integer> itr = s.iterator();
            while (itr.hasNext()) {
                final int p = itr.next();
                //Log.d("Values",""+result1+" "+p);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        drawlines(result1, p, entry.getKey().second," ");
                    }
                });
            }
        }



        Iterator<Integer> itr = acceptingStates.iterator();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {


                edges = new ImageView(getActivity());
                relativeLayoutworking.addView(edges);
                edges.setImageBitmap(bitmap);
                final Canvas canvas = new Canvas(bitmap);
                final float radius = 75;
                final Paint mpaint = new Paint();
                mpaint.setStyle(Paint.Style.STROKE);
                mpaint.setStrokeWidth(5);
                mpaint.setColor(Color.RED);
                Path arrowPath = new Path();

                arrowPath.moveTo(zerostatecoorX, zerostatecoorY);//move back to the center
                arrowPath.moveTo(zerostatecoorX, zerostatecoorY);//move back to the center
                arrowPath.lineTo(zerostatecoorX - 50, zerostatecoorY - 50);//draw the first arrowhead line to the left
                arrowPath.moveTo(zerostatecoorX, zerostatecoorY);//move back to the center
                arrowPath.lineTo(zerostatecoorX + 50, zerostatecoorY - 50);//draw the next arrowhead line to the right
                Matrix mMatrix = new Matrix();
                mMatrix.postRotate(270, zerostatecoorX, zerostatecoorY);
                arrowPath.transform(mMatrix);
                canvas.drawPath(arrowPath, mpaint);
                canvas.drawLine(zerostatecoorX,zerostatecoorY,zerostatecoorX-100,zerostatecoorY,mpaint);

            }
        });


        if (!acceptingStates.isEmpty())
            while (itr.hasNext()) {


                final int v = itr.next();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        edges = new ImageView(getActivity());
                        relativeLayoutworking.addView(edges);
                        edges.setImageBitmap(bitmap);
                        final Canvas canvas = new Canvas(bitmap);
                        final float radius = 75;
                        final Paint mpaint = new Paint();
                        mpaint.setStyle(Paint.Style.STROKE);
                        mpaint.setStrokeWidth(5);
                        mpaint.setColor(Color.RED);
                        final int startx = state[v].getLeft() + state[v].getWidth() / 2;
                        final int starty = state[v].getTop() + state[v].getHeight() / 2;
                        canvas.drawCircle(startx, starty, radius, mpaint);
                    }
                });


            }

            /*
            Iterator<Integer> it=Undeterminedstates.iterator();

        while (it.hasNext()) {
            final int k=it.next();
            View view1 = state[k];
            if (view1 != null) {
                final ViewGroup parentViewGroup = (ViewGroup) view1.getParent();
                if (parentViewGroup != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            parentViewGroup.removeAllViews();
                            relativeLayoutworking.addView(state[k]);
                        }
                    });
                }
            }
        }
        */

    }

    void PDArecreategraph() {

        for (int i = 0; i < 1000; i++)
            Arrays.fill(count[i], 0);
        for (int i = 0; i < 100; i++)
            Arrays.fill(arr[i], false);
        if (state[0] == null) {
            return;
        }
        Arrays.fill(samestate,0);

        for (int i = 0; i < PDAtransitioncount; i++) {

            final int result1=pdaTransitionNode[i].getResult1();
            final int result2=pdaTransitionNode[i].getResult2();
            final String read=pdaTransitionNode[i].getRead();
            final String pop=pdaTransitionNode[i].getPop();
            final String push=pdaTransitionNode[i].getPush();


            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {


                    drawlines(result1,result2,'U',read+","+pop+","+push);
                }
            });

        }
        Iterator<Integer> itr = acceptingStates.iterator();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {


                edges = new ImageView(getActivity());
                relativeLayoutworking.addView(edges);
                edges.setImageBitmap(bitmap);
                final Canvas canvas = new Canvas(bitmap);
                final float radius = 75;
                final Paint mpaint = new Paint();
                mpaint.setStyle(Paint.Style.STROKE);
                mpaint.setStrokeWidth(5);
                mpaint.setColor(Color.RED);
                Path arrowPath = new Path();

                arrowPath.moveTo(zerostatecoorX, zerostatecoorY);//move back to the center
                arrowPath.moveTo(zerostatecoorX, zerostatecoorY);//move back to the center
                arrowPath.lineTo(zerostatecoorX - 50, zerostatecoorY - 50);//draw the first arrowhead line to the left
                arrowPath.moveTo(zerostatecoorX, zerostatecoorY);//move back to the center
                arrowPath.lineTo(zerostatecoorX + 50, zerostatecoorY - 50);//draw the next arrowhead line to the right
                Matrix mMatrix = new Matrix();
                mMatrix.postRotate(270, zerostatecoorX, zerostatecoorY);
                arrowPath.transform(mMatrix);
                canvas.drawPath(arrowPath, mpaint);
                canvas.drawLine(zerostatecoorX,zerostatecoorY,zerostatecoorX-100,zerostatecoorY,mpaint);

            }
        });


        if (!acceptingStates.isEmpty())
            while (itr.hasNext()) {


                final int v = itr.next();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        edges = new ImageView(getActivity());
                        relativeLayoutworking.addView(edges);
                        edges.setImageBitmap(bitmap);
                        final Canvas canvas = new Canvas(bitmap);
                        final float radius = 75;
                        final Paint mpaint = new Paint();
                        mpaint.setStyle(Paint.Style.STROKE);
                        mpaint.setStrokeWidth(5);
                        mpaint.setColor(Color.RED);
                        final int startx = state[v].getLeft() + state[v].getWidth() / 2;
                        final int starty = state[v].getTop() + state[v].getHeight() / 2;
                        canvas.drawCircle(startx, starty, radius, mpaint);
                    }
                });


            }

    }

    void TMrecreategraph(){

        for (int i = 0; i < 1000; i++)
            Arrays.fill(count[i], 0);
        for (int i = 0; i < 100; i++)
            Arrays.fill(arr[i], false);
        if (state[0] == null) {
            return;
        }
        Arrays.fill(samestate,0);

        for (int i = 0; i < TMtransitioncount; i++) {

            final int result1=turingMachineTransitionNodes[i].getResult1();
            final int result2=turingMachineTransitionNodes[i].getResult2();
            final String read=turingMachineTransitionNodes[i].getRead();
            final String pop=turingMachineTransitionNodes[i].getWrite();
            final String push=turingMachineTransitionNodes[i].getDirection();

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    drawlines(result1,result2,'U',read+","+pop+","+push);
                }
            });

        }
        Iterator<Integer> itr = acceptingStates.iterator();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {


                edges = new ImageView(getActivity());
                relativeLayoutworking.addView(edges);
                edges.setImageBitmap(bitmap);
                final Canvas canvas = new Canvas(bitmap);
                final float radius = 75;
                final Paint mpaint = new Paint();
                mpaint.setStyle(Paint.Style.STROKE);
                mpaint.setStrokeWidth(5);
                mpaint.setColor(Color.RED);
                Path arrowPath = new Path();

                arrowPath.moveTo(zerostatecoorX, zerostatecoorY);//move back to the center
                arrowPath.moveTo(zerostatecoorX, zerostatecoorY);//move back to the center
                arrowPath.lineTo(zerostatecoorX - 50, zerostatecoorY - 50);//draw the first arrowhead line to the left
                arrowPath.moveTo(zerostatecoorX, zerostatecoorY);//move back to the center
                arrowPath.lineTo(zerostatecoorX + 50, zerostatecoorY - 50);//draw the next arrowhead line to the right
                Matrix mMatrix = new Matrix();
                mMatrix.postRotate(270, zerostatecoorX, zerostatecoorY);
                arrowPath.transform(mMatrix);
                canvas.drawPath(arrowPath, mpaint);
                canvas.drawLine(zerostatecoorX,zerostatecoorY,zerostatecoorX-100,zerostatecoorY,mpaint);

            }
        });


        if (!acceptingStates.isEmpty())
            while (itr.hasNext()) {


                final int v = itr.next();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        edges = new ImageView(getActivity());
                        relativeLayoutworking.addView(edges);
                        edges.setImageBitmap(bitmap);
                        final Canvas canvas = new Canvas(bitmap);
                        final float radius = 75;
                        final Paint mpaint = new Paint();
                        mpaint.setStyle(Paint.Style.STROKE);
                        mpaint.setStrokeWidth(5);
                        mpaint.setColor(Color.RED);
                        final int startx = state[v].getLeft() + state[v].getWidth() / 2;
                        final int starty = state[v].getTop() + state[v].getHeight() / 2;
                        canvas.drawCircle(startx, starty, radius, mpaint);
                    }
                });


            }
        if(!rejectingStates.isEmpty()) {
            Iterator<Integer> it=rejectingStates.iterator();
            while (it.hasNext()) {


                final int v = it.next();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        edges = new ImageView(getActivity());
                        relativeLayoutworking.addView(edges);
                        edges.setImageBitmap(bitmap);
                        final Canvas canvas = new Canvas(bitmap);
                        final float radius = 75;
                        final Paint mpaint = new Paint();
                        mpaint.setStyle(Paint.Style.STROKE);
                        mpaint.setStrokeWidth(5);
                        mpaint.setColor(Color.BLUE);
                        final int startx = state[v].getLeft() + state[v].getWidth() / 2;
                        final int starty = state[v].getTop() + state[v].getHeight() / 2;
                        canvas.drawCircle(startx, starty, radius, mpaint);
                    }
                });

            }
        }
    }

    class CreategraphAsynctask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            recreategraph();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    class PDACreategraphAsynctask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            PDArecreategraph();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    class TMCreategraphAsynctask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            TMrecreategraph();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;

        }
    }

    void show(int no) {
        if (once[no] == 0 && movecnt[no] <= 8) {
            Vibrator vibrator;
            vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
            long[] once = {0, 100};
            vibrator.vibrate(once, -1);
            manager = getActivity().getSupportFragmentManager();
            trans = manager.beginTransaction();
            if(CodeIndex!=3) {
                frags.show(manager, "StateFragment");
                frags.stateno = no;
                frags.p = GraphDisplay.this;
            }
            else
            {
                fragstm.show(manager,"StateFragmetTM");
                fragstm.stateno=no;
                fragstm.p=GraphDisplay.this;
            }
            movecnt[no] = 0;
        }
        once[no]++;
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d("Hey","PauseFragment");
        try {
            if(CodeIndex==1)
                saveautomata("temporaryfile");
            else
            if(CodeIndex==2)
                PDASave("PDAtemporaryfile");
            else
            if(CodeIndex==3)
                TMSave("TMtemporaryfile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Hey","ResumeFragment");

        if(toremove==1) {

            Log.d("Hey","Removing");
            View view1 = horizontalScrollView;
            if (view1 != null) {
                ViewGroup parentViewGroup = (ViewGroup) view1.getParent();
                if (parentViewGroup != null) {
                    parentViewGroup.removeAllViews();
                }
            }
            View view2 = verticalscrollView;
            if (view2 != null) {
                ViewGroup parentViewGroup = (ViewGroup) view2.getParent();
                if (parentViewGroup != null) {
                    parentViewGroup.removeAllViews();
                }
            }

            View view3 = relativeLayoutworking;
            if (view3 != null) {
                ViewGroup parentViewGroup = (ViewGroup) view3.getParent();
                if (parentViewGroup != null) {
                    parentViewGroup.removeAllViews();
                }
            }

            for (int i = 0; i < Undeterminedstates.size(); i++) {

                View view = state[i];
                if (view != null) {

                    ViewGroup parentViewGroup = (ViewGroup) view.getParent();
                    if (parentViewGroup != null) {
                        parentViewGroup.removeAllViews();
                    }
                }
            }
            try {
                if(CodeIndex==1)
                    loadautomata("temporaryfile");
                else
                if(CodeIndex==2)
                    PDALoad("PDAtemporaryfile");
                else
                if(CodeIndex==3)
                    TMLoad("TMtemporaryfile");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        toremove=1;

        /*
        for (int i = 0; i < buttons.size(); i++) {
            ViewGroup v = (ViewGroup) buttons.get(i).getParent();
            if(v!=null)
            v.removeView(buttons.get(i));
            relativeLayoutworking.addView(buttons.get(i));
        }
        new CreategraphAsynctask().execute();
        */
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Hey","StopFragment");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void clean() {


        for (int i = 0; i < Undeterminedstates.size(); i++) {
            View view = state[i];
            if (view != null) {
                ViewGroup parentViewGroup = (ViewGroup) view.getParent();
                if (parentViewGroup != null) {
                    parentViewGroup.removeAllViews();
                }
            }
        }

        //state = new Button[30];
        Arrays.fill(state,null);
        transitioninfo.clear();
        edgecounter = 0;
        edgeinfo.clear();
        Arrays.fill(visited, 0);
        buttons.clear();
        transitionFunctionNFA = new TransitionFunctionNFA();
        acceptingStates.clear();
        Undeterminedstates.clear();
        for (int i = 0; i < 1000; i++)
            Arrays.fill(count[i], 0);
        for (int i = 0; i < 100; i++)
            Arrays.fill(arr[i], false);

        bitmap.eraseColor(Color.parseColor("#FFFFFFFF"));

    }

    public void PDAclean() {

        for (int i = 0; i < Undeterminedstates.size(); i++) {
            View view = state[i];
            if (view != null) {
                ViewGroup parentViewGroup = (ViewGroup) view.getParent();
                if (parentViewGroup != null) {
                    parentViewGroup.removeAllViews();
                }
            }
        }
        //state = new Button[30];
        Arrays.fill(state,null);
        transitioninfo.clear();
        edgecounter = 0;
        edgeinfo.clear();
        Arrays.fill(visited, 0);
        buttons.clear();
        pdaTransitionNode=new PDATransitionNode[100];
        PDAtransitioncount=0;
        acceptingStates.clear();
        Undeterminedstates.clear();
        for (int i = 0; i < 1000; i++)
            Arrays.fill(count[i], 0);
        for (int i = 0; i < 100; i++)
            Arrays.fill(arr[i], false);

        bitmap.eraseColor(Color.parseColor("#FFFFFFFF"));
    }

    public void TMclean(){

        for (int i = 0; i < Undeterminedstates.size(); i++) {
            View view = state[i];
            if (view != null) {
                ViewGroup parentViewGroup = (ViewGroup) view.getParent();
                if (parentViewGroup != null) {
                    parentViewGroup.removeAllViews();
                }
            }
        }
        //state = new Button[30];
        Arrays.fill(state,null);
        transitioninfo.clear();
        edgecounter = 0;
        edgeinfo.clear();
        Arrays.fill(visited, 0);
        buttons.clear();
        turingMachineTransitionNodes=new TuringMachineTransitionNode[100];
        TMtransitioncount=0;
        acceptingStates.clear();
        rejectingStates.clear();
        Undeterminedstates.clear();
        for (int i = 0; i < 1000; i++)
            Arrays.fill(count[i], 0);
        for (int i = 0; i < 100; i++)
            Arrays.fill(arr[i], false);

        bitmap.eraseColor(Color.parseColor("#FFFFFFFF"));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Hey","DestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Destroy1","Fragment destroyed");
    }


}







