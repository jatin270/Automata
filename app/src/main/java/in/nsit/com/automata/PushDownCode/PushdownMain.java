package in.nsit.com.automata.PushDownCode;

/**
 * Created by ( Jatin Bansal ) on 17-03-2017.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class PushdownMain {

    /*
    Set<Integer> UndeterminedStates;
    Set<Integer> finalStates;
    PDATransitionNode[] transitionNodes;
    int PDAtransitioncount;
    */
    Automata npda=new Automata();

    String myInitialState="";
    String myStackStartSymbol="";
    public String outputString="";


    public boolean execute(String s) throws FileNotFoundException    {

            boolean ans=false;
            npda.displayNPDA();

            String myString=s;
            npda.clearThePathVector();
            if(myString.isEmpty())
            {
                tuple myTuple=new tuple(myInitialState,"",myStackStartSymbol);
                if(npda.process(myTuple)==true)
                {
                    npda.printPath();
                    outputString=npda.outputString;
                    ans=true;
                    System.out.println("Accepted");
                }
                else
                {
                    outputString=npda.outputString2;
                    System.out.println("Rejected");
                }

            }
            else
            {
                tuple myTuple=new tuple(myInitialState,myString,myStackStartSymbol);
                if(npda.process(myTuple)==true)
                {
                    npda.printPath();
                    outputString=npda.outputString;
                    ans=true;
                    System.out.println("Accepted");
                }
                else
                {
                    outputString=npda.outputString2;
                    System.out.println("Rejected");
                }
            }
            return ans;
    }


    public  void load( Set<Integer> UndeterminedStates, Set<Integer> finalStates, PDATransitionNode[] transitionNodes, int PDAtransitioncount)
    {
        String startState;
        String inputAlphabet;
        String startStack;
        String endState;
        String endStack;

        Iterator<Integer> itr;
        itr=UndeterminedStates.iterator();
        while (itr.hasNext()){
            String s="q"+itr.next();
            npda.addState(s);
        }


        for(int i=0;i<190;i++)
        {
            char temp= (char) ('0'+i);
            String s=""+temp;
            npda.addSymbol(s);
        }

        for(int i=0;i<190;i++)
        {
            char temp= (char) ('0'+i);
            String s=""+temp;
            npda.addAllStackAlphabets(s);
        }
        for(int i=0;i<PDAtransitioncount;i++)
        {
            startState="q"+transitionNodes[i].getResult1();
            inputAlphabet=transitionNodes[i].getRead();
            startStack =transitionNodes[i].getPop();
            endState ="q"+transitionNodes[i].getResult2();
            endStack =transitionNodes[i].getPush();

            if(inputAlphabet.equals("\\"))
                inputAlphabet="*";

            if(startStack.equals("\\"))
                startStack="*";

            if(endStack.equals("\\"))
                endStack="*";
            npda.addTransition(startState, inputAlphabet, startStack, endState, endStack);
        }

            String s="q0";
            myInitialState=s;
            npda.setInitialState(s);

             s="0";
            myStackStartSymbol=s;
            npda.setStackStartSymbol(s);

        itr=finalStates.iterator();
         while (itr.hasNext())
        {
            s="q"+itr.next();
            npda.addFinalState(s);
        }


    }



}
