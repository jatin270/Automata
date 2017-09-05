package in.nsit.com.automata.FiniteAutomata;

import android.util.Log;
import android.util.Pair;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by ( Jatin Bansal ) on 27-03-2017.
 */

public class NFADirect {

    Set<Integer> acceptingStates;
    public TransitionFunctionNFA transitionFunctionNFA;

    public  NFADirect(Set<Integer> acceptingStates,TransitionFunctionNFA transitionFunctionNFA){
        this.acceptingStates=acceptingStates;
        this.transitionFunctionNFA=transitionFunctionNFA;
    }

    public static final int ten1 = 10;

    public String outputString;

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
    static Set<Integer> NFA_finalStates = new HashSet<Integer>();
    static int N,M;
    static BitSet currentStates=new BitSet();
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

    static BitSet NFAmove(BitSet X,int A,BitSet Y){

        BitSet tmp=new BitSet();
        for(int i=0;i<N;i++){
            if(X.get(i)==true)
                NFAmove(i,A,tmp);
        }
        return Y=tmp;
    }

    public void loaddata(int n,int m){
        N=n;
        M=m;
        int F;
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

          //  Log.d("Transition", "" + entry.getKey().first + " " + entry.getKey().second);
            int c=(int)((int)entry.getKey().second-96);
            if( entry.getKey().second.equals("\\"))
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


    }
    public  boolean execute(String s){


        int symbol;

        currentStates.set(0,true);
        epilsonClosure(currentStates,currentStates);

        outputString="|-q 0 "+s+"\n";
        for(int i=0;i<s.length();i++)
        {
           // System.out.println(currentStates);
            symbol=s.charAt(i)-96;
           // System.out.println(""+symbol);
            if(symbol<=0||symbol>M)
            {
               // System.out.println("Invalid input symbol"+symbol);
                return false;
            }
            else{
                currentStates=NFAmove(currentStates,symbol,currentStates);
                epilsonClosure(currentStates,currentStates);
                for(int j=0;j<N;j++)
                {
                    if(currentStates.get(j)==true){
                        outputString=outputString+"|-q "+j+" "+s.substring(i+1)+" ";
                    }
                }
                outputString=outputString+"\n";
            }

        }
        int i;
        for(i=0;i<N;i++)
        {
            if(currentStates.get(i)==true&&NFA_finalStates.contains(i))
                break;
        }

        if(i<N)
        {
           // System.out.println("String Accepted");
            return  true;
        }
        else
        {
           // System.out.println("String Rejected");
            return  false;
        }


    }


}
