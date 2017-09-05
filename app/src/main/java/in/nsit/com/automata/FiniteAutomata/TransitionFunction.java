package in.nsit.com.automata.FiniteAutomata;

/**
 * Created by ( Jatin Bansal ) on 10-01-2017.
 */

import android.util.Log;
import android.util.Pair;
import java.util.HashMap;
import java.util.Map;

public class TransitionFunction {


    Pair<Integer,Character> p;
    public Map<Pair<Integer,Character>,Integer > function = new HashMap<>();

    public void setTransition(Integer startState, Integer goalState, char character)
    {
       p=p.create(startState,character);
        Log.d("Check",""+p.first+" "+p.second);
        /*
        if(!function.containsKey(startState))
            function.put(startState,new HashMap<Character, Integer>());
        else
            function.get(startState).put(character, goalState);
        */

        function.put(p,goalState);
    }

    public Integer process(Integer startState, char character) {

       p= p.create(startState,character);
        if (!function.containsKey(p)) {
            return null;
        }

        return function.get(p);
    }
    public  void show() {

        for(Map.Entry<Pair<Integer,Character>,Integer> entry:function.entrySet()){

            Log.d("Transition",""+entry.getKey().first+" "+entry.getKey().second);
            Log.d("Transition",""+entry.getValue());

        }

    }
}

