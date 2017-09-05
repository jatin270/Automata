package in.nsit.com.automata.FiniteAutomata;

/**
 * Created by ( Jatin Bansal ) on 10-01-2017.
 */


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class DFA {

    public TransitionFunction transitionFunction;
    private final int startState;
    private final Set<Integer> acceptingStates;
    public String string="";


    public DFA(TransitionFunction transitionFunction, int startState, Set<Integer> acceptingStates) {
        this.transitionFunction = Objects.requireNonNull(transitionFunction, "Transition function is null.");
        this.startState = startState;
        this.acceptingStates =
                Objects.requireNonNull(acceptingStates,"Accepting state set is null.");
    }

    public boolean matches(String text) {

        Integer currentState = startState;

        int i=0;
        for (char c : text.toCharArray()) {

            Log.d("Test",""+currentState+" "+c);
            string=string+"|-q"+currentState+text.substring(i);
            i++;
            currentState = transitionFunction.process(currentState, c);
            if (currentState == null) {
                return false;
            }
        }

        Log.d("State",""+currentState);
        return acceptingStates.contains(currentState);
    }



    public boolean solve(String s) {

        Iterator<Integer> itr=acceptingStates.iterator();

        while(itr.hasNext()){
            Log.d("Accepting States",""+itr.next());
        }
            String line =s;
            return matches(line);
    }

}