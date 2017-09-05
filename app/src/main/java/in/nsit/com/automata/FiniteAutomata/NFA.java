package in.nsit.com.automata.FiniteAutomata;

/**
 * Created by ( Jatin Bansal ) on 27-01-2017.
 */


import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.util.Pair;

import java.io.IOException;
import java.util.*;

import in.nsit.com.automata.ResultPair;

public class NFA {

    public static final int ten1 = 10;
    public TransitionFunctionNFA transitionFunctionNFA;
    private final int startState;
    private final Set<Integer> acceptingStates;


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public NFA(TransitionFunctionNFA transitionFunctionNFA, int startState, Set<Integer> acceptingStates) {

        this.transitionFunctionNFA = Objects.requireNonNull(transitionFunctionNFA, "Transition function is null.");
        this.startState = startState;
        this.acceptingStates =
                Objects.requireNonNull(acceptingStates,
                        "Accepting state set is null.");
    }


    static class NFAstate{
        int[][] transitions;
        NFAstate()
        {
            transitions=new int[ten1][ten1];
            for(int i=0;i<ten1;i++)
                for(int j=0;j<ten1;j++)
                    transitions[i][j]=-1;
        }
    }
    static NFAstate[] NFAstates;

    static class DFAstate
    {
        boolean finalstate;
        BitSet constituentNFAstates;
        BitSet[] transitions;
        int[] symbolicTransitions;


        DFAstate()
        {
            constituentNFAstates=new BitSet();

            transitions=new BitSet[ten1];
            for(int i=0;i<ten1;i++)
                transitions[i]=new BitSet();
            symbolicTransitions=new int[ten1];
        }
    }
    static Set<Integer> NFA_finalStates = new HashSet<Integer>();
    static ArrayList<Integer> DFA_Finalstates=new ArrayList<Integer>();
    static ArrayList<DFAstate> DFAstates=new ArrayList<DFAstate>();
    static Queue<Integer> incompleteDFAstates = new LinkedList<Integer>();
    static int N;
    static int M;

    static void epilsonClosure(int state,BitSet closure){

        for(int i=0;i<N && NFAstates[state].transitions[0][i]!=-1;i++)
        {
            //System.out.println(NFAstates[state].transitions[0][i]);
            if(closure.get(NFAstates[state].transitions[0][i])==false)
            {
                closure.flip(NFAstates[state].transitions[0][i]);
                epilsonClosure(NFAstates[state].transitions[0][i],closure);
            }
        }
    }

    static void epilsonClosure(BitSet state, BitSet closure){

        //	System.out.println(state);
        for(int i=0;i<N;i++)
            if(state.get(i)==true)
            {
                epilsonClosure(i,closure);
            }
    }

    static void NFAmove(int X,int A,BitSet Y){

        for(int i=0;i<N && NFAstates[X].transitions[A][i]!=-1;i++){
            Y.set(NFAstates[X].transitions[A][i],true);
        }
    }

    static void NFAmove(BitSet X,int A,BitSet Y){

        //	System.out.println(X);
        for(int i=0;i<N;i++){
            if(X.get(i)==true)
                NFAmove(i,A,Y);
        }
    }
//----------------------------------------------------------------------------------------------------------------------------------------------------
// Main converting function for nfa to dfa
    DFA dfa;
    public void convertdfatonfa(int n,int m) throws IOException {
        int i,j,X,Y,A,T,F,D;

        N=n;
        M=m;

        NFAstates=new NFAstate[N];
        for(int p=0;p<N;p++)
            NFAstates[p]=new NFAstate();

        F=acceptingStates.size();
        Iterator<Integer> itr = acceptingStates.iterator();

        while(itr.hasNext())
        {
            int y=itr.next();
            NFA_finalStates.add(y);
        }

        for(Map.Entry<Pair<Integer, Character>, Set<Integer> > entry:transitionFunctionNFA.function.entrySet()) {

            Log.d("Transition", "" + entry.getKey().first + " " + entry.getKey().second);
            int c=(int)((int)entry.getKey().second-96);
            if( entry.getKey().second.equals("/"))
            {
                c=0;
            }
            if((c>150)||(c<0))
                c=0;
            Iterator<Integer> itr1 = entry.getValue().iterator();
            int k=0;
            while(itr1.hasNext())
            {
                int qto=itr1.next();
                System.out.println(entry.getKey().first+" "+c+" "+k+" "+qto);
                NFAstates[entry.getKey().first].transitions[c][k]=qto;
                k++;
            }
        }

        //construct the corresponding DFA

        D=1;
        DFAstates.add(new DFAstate());
        DFAstates.get(0).constituentNFAstates.set(0,true);
        epilsonClosure(0,DFAstates.get(0).constituentNFAstates);

        for(j=0;j<N;j++){

            if(DFAstates.get(0).constituentNFAstates.get(j)==true &&
                    NFA_finalStates.contains(j)){

                DFAstates.get(0).finalstate=true;
                DFA_Finalstates.add(0);
                break;

            }
        }

        incompleteDFAstates.add(0);
        while(!incompleteDFAstates.isEmpty()){

            X=incompleteDFAstates.peek();
            incompleteDFAstates.remove();
            for(i=1;i<=M;i++){

                NFAmove(DFAstates.get(X).constituentNFAstates,
                        i,DFAstates.get(X).transitions[i]);

                epilsonClosure(DFAstates.get(X).transitions[i],
                        DFAstates.get(X).transitions[i]);

                for(j=0;j<D;j++){

                    if(DFAstates.get(X).transitions[i].equals
                            (DFAstates.get(j).constituentNFAstates)
                            ){

                        DFAstates.get(X).symbolicTransitions[i]=j;
                        break;
                    }
                }

                if(j==D){

                    DFAstates.get(X).symbolicTransitions[i]=D;
                    DFAstates.add(new DFAstate());
                    DFAstates.get(D).constituentNFAstates= DFAstates.get(X).transitions[i];

                    for(j=0;j<N;j++){

                        if(DFAstates.get(D).constituentNFAstates.get(j)==true
                                && NFA_finalStates.contains(j)
                                ){

                            DFAstates.get(D).finalstate=true;
                            DFA_Finalstates.add(D);
                            break;
                        }

                    }
                    incompleteDFAstates.add(D);
                    D++;
                }


            }
        }

        // write out the corresponding DFA

        String string="";
        string=string+D+" "+M+" "+DFA_Finalstates.size();

        Set<Integer> finalstates=new HashSet<Integer>();
        System.out.println(D+" "+M);
        System.out.println(DFA_Finalstates.size());

        String g="";

        for(int l=0;l<DFA_Finalstates.size();l++ ) {

            finalstates.add(DFA_Finalstates.get(l));
            g=g+DFA_Finalstates.get(l)+" ";

        }
        System.out.println(g);

        TransitionFunction function=new TransitionFunction();
        for(i=0;i<D;i++) {

            for(j=1;j<=M;j++){

                String h="";
                function.setTransition(i,DFAstates.get(i).symbolicTransitions[j],(char)(j+96));
                h=h+i+" "+j+" "+DFAstates.get(i).symbolicTransitions[j];
                System.out.println(h);

            }
        }
        dfa=new DFA(function,0,finalstates);


    }



    public ResultPair solvenfa(String s){
        boolean ans=dfa.solve(s);
        Log.d("Answer",""+ans);
        ResultPair temp=new ResultPair(dfa.string,ans);
        return  temp;
    }
}
