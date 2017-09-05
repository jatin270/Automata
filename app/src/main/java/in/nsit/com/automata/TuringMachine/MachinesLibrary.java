package in.nsit.com.automata.TuringMachine;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by ( Jatin Bansal ) on 14-03-2017.
 */

public class MachinesLibrary
{
    Set<Integer> UndetermindeStates;
    Set<Integer> acceptingStates;
    Set<Integer> rejectState;
    TuringMachineTransitionNode[] transitionNodes;
    int TMtransitioncount;
    public MachinesLibrary(
            Set<Integer> UndetermindeStates,
            Set<Integer> acceptingStates,
            Set<Integer> rejectState,
            TuringMachineTransitionNode[] transitionNodes,
            int TMtransitioncount) {

        this.UndetermindeStates=UndetermindeStates;
        this.acceptingStates=acceptingStates;
        this.rejectState=rejectState;
        this.transitionNodes=transitionNodes;
        this.TMtransitioncount=TMtransitioncount;
    }

    public  TuringMachine load()
    {
        TuringMachine newTM = new TuringMachine();
        Iterator<Integer> itr;

        itr=UndetermindeStates.iterator();

        while (itr.hasNext())
        {
            newTM.addState("q"+itr.next());
        }
        /*
        newTM.addState("q0");
        newTM.addState("q1");
        newTM.addState("q2");
        newTM.addState("q3");
        */
        itr=acceptingStates.iterator();
        while (itr.hasNext())
        {
            newTM.setAcceptState("q"+itr.next());
        }
        newTM.setStartState("q0");

        if(rejectState!=null) {
            itr = rejectState.iterator();
            while (itr.hasNext()) {
                newTM.setRejectState("q" + itr.next());
            }
        }
        for(int i=0;i<TMtransitioncount;i++)
        {
            String current="q"+transitionNodes[i].getResult1();
            String temp=transitionNodes[i].getRead();
            char read[] = temp.toCharArray();
            String next="q"+transitionNodes[i].getResult2();
            String temp2=transitionNodes[i].getWrite();
            char write[]=temp2.toCharArray();
            boolean direction;
            if(transitionNodes[i].getDirection().equals("L"))
                direction=false;
            else
                direction=true;
            newTM.addTransition(current,read[0],next,write[0],direction);
        }

        return newTM;
    }

}
