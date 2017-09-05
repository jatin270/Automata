package in.nsit.com.automata.FiniteAutomata;

import android.util.Log;
import android.util.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by ( Jatin Bansal ) on 27-01-2017.
 */

public class TransitionFunctionNFA {

    Pair<Integer,Character> p;

    public Map<Pair<Integer,Character>,Set<Integer>> function = new HashMap<Pair<Integer,Character>,Set<Integer>>();

    public void setTransition(Integer startState, Integer goalState, char character)
    {
        p=p.create(startState,character);

        //Log.d("Check",""+p.first+" "+p.second);

        if(function.containsKey(p))
        {
            function.get(p).add(goalState);
        }
        else
        {
            function.put(p,new HashSet<Integer>());
            function.get(p).add(goalState);
        }
    }

    public Set<Integer> process(Integer startState, char character) {

        p= p.create(startState,character);

        if (!function.containsKey(p)) {
            return null;
        }
        return function.get(p);
    }

    public  void show() {

        for(Map.Entry<Pair<Integer, Character>, Set<Integer>> entry:function.entrySet()){

            Log.d("Transition",""+entry.getKey().first+" "+entry.getKey().second);
            Log.d("Transition",""+entry.getValue());

        }

    }


}
