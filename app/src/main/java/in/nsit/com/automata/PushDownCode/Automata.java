package in.nsit.com.automata.PushDownCode;

/**
 * Created by ( Jatin Bansal ) on 17-03-2017.
 */


import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public  class  Automata {

    ArrayList<String> allStates=new ArrayList<String>();
    ArrayList<String> allSymbols=new ArrayList<String>();
    ArrayList<String> allFinalStates=new ArrayList<String>();
    ArrayList<String> allStackAlphabets=new ArrayList<String>();
    String initialState;
    String currentState;
    String stackStartSymbol;
    HashMap<tuple,ArrayList<pair>> allTransitionsMap=new HashMap<>();
    ArrayList<tuple> pathVector=new ArrayList<tuple>();


    String outputString="";
    String outputString2="";


    void addState(String state){
        allStates.add(state);
    }
    void addSymbol(String symbol){

        allSymbols.add(symbol);
    }
    void addFinalState(String finalState){

        allFinalStates.add(finalState);
    }
    void addAllStackAlphabets(String stackSymbol){

        allStackAlphabets.add(stackSymbol);
    }
    void setInitialState(String myInitialState){
        initialState=myInitialState;
        currentState=myInitialState;
    }
    void setStackStartSymbol(String stackSymbol){
        stackStartSymbol=stackSymbol;
    }
    boolean isFinalState(String state){

        boolean isFound=false;
        int i=0;
        while(i<allFinalStates.size() && !isFound)
        {
            if(allFinalStates.get(i).equals(state))
            {
                isFound=true;
            }
            i++;
        }
        return isFound;
    }
    void addTransition(String startState,String inputAlphabet,String startStack,String endState,String endStack){

        String myInputAlphabet=inputAlphabet;
        if(myInputAlphabet.equals("*"))
        {
            myInputAlphabet="";

        }
        tuple ketTuple=new tuple(startState,myInputAlphabet,startStack);

        String myEndStack=endStack;
        if(myEndStack.equals("*")){
            myEndStack="";
        }
        pair myPairValue=new pair(endState,myEndStack);
        if(allTransitionsMap.containsKey(ketTuple))
        {
            allTransitionsMap.get(ketTuple).add(myPairValue);
        }
        else
        {
            ArrayList<pair> temp=new ArrayList<>();
            temp.add(myPairValue);
            allTransitionsMap.put(ketTuple,temp);
        }


    }
    ArrayList<tuple> transitionToNextState(tuple urTuple){

        ArrayList<tuple> vectorOfTuple = new ArrayList<tuple>();
        tuple myTuple=urTuple;

        String startState=myTuple.getFirst();
        String inputString=myTuple.getSecond();
        String stackString=myTuple.getThird();

        String newInputString="";
        char myCharSymbol='\0';
        if(!inputString.equals(""))
        {
				/*Can be a point of error*/
            if(inputString.length()==1)
            {
                newInputString="";
            }
            else
                newInputString =inputString.substring(1);


            myCharSymbol=inputString.charAt(0);
        }
        String inputSymbol=""+myCharSymbol;
        char myStackChar='\0';
        if(!stackString.equals(""))
        {
            myStackChar=stackString.charAt(0);
        }
        String stackTop=""+myStackChar;
        if(stackTop.equals(""+'\0'))
            stackTop="";

        if(inputSymbol.equals(""+'\0'))
            inputSymbol="";

        tuple myKeyTuple=new tuple(startState,inputSymbol,stackTop);
        //    System.out.println(myKeyTuple.getFirst()+" "+myKeyTuple.getSecond()+" "+myKeyTuple.getThird());

        ArrayList<pair> myValueVector=null;
        for(Map.Entry<tuple,ArrayList<pair>> entry:allTransitionsMap.entrySet()){
            tuple a=entry.getKey();
            if(a.getFirst().equals(myKeyTuple.getFirst()))
            {
                if(a.getSecond().equals(myKeyTuple.getSecond()))
                {
                    if(a.getThird().equals(myKeyTuple.getThird()))
                    {
                        myValueVector=entry.getValue();

                    }
                }
            }
        }


        //	ArrayList<pair> myValueVector=allTransitionsMap.get(myKeyTuple);

        String newStackString="";
        String newState="";
        String myStack="";

        if(myValueVector!=null) {
            for (int i = 0; i < myValueVector.size(); i++) {

                pair myPairValue = myValueVector.get(i);
                newState = myPairValue.getFirst();
                myStack = myPairValue.getSecond();
                if (!stackString.equals("")) {
                    newStackString = stackString.substring(1);
                }

                newStackString = myStack + newStackString;

                tuple myNewTuple = new tuple(newState, newInputString, newStackString);
                vectorOfTuple.add(myNewTuple);
            }

            tuple myKeyTuple2 = new tuple(startState, "", stackTop);
            for (Map.Entry<tuple, ArrayList<pair>> entry : allTransitionsMap.entrySet()) {
                tuple a = entry.getKey();


                if (a.getFirst().equals(myKeyTuple2.getFirst())) {
                    if (a.getSecond().equals(myKeyTuple2.getSecond())) {
                        if (a.getThird().equals(myKeyTuple2.getThird())) {
                            String newStackString2 = "";
                            String newState2 = "";
                            String myStack2 = "";
                            ArrayList<pair> myValueVector2;

                            myValueVector2 = entry.getValue();

                            for (int i = 0; i < myValueVector2.size(); i++) {

                                pair myPairValue2 = myValueVector2.get(i);
                                newState2 = myPairValue2.getFirst();
                                myStack2 = myPairValue2.getSecond();

                                if (!stackString.equals("")) {
                                    newStackString2 = stackString.substring(1);
                                }
                                newStackString2 = myStack2 + newStackString2;

                                tuple myNewTuple2 = new tuple(newState2, inputString, newStackString2);
                                vectorOfTuple.add(myNewTuple2);
                            }
                        }
                    }
                }
            }
        }

		/*
			if(allTransitionsMap.containsKey(myKeyTuple2)){
				String newStackString2="";
				String newState2="";
				String myStack2="";
				ArrayList<pair> myValueVector2=allTransitionsMap.get(myKeyTuple2);

				for(int i=0;i<myValueVector2.size();i++){

					pair myPairValue2=myValueVector2.get(i);
					newState2=myPairValue2.getFirst();
					myStack2=myPairValue2.getSecond();

					if(stackString!=""){
						newStackString2=stackString.substring(1);
					}
					newStackString2=myStack2+newStackString2;

					tuple myNewTuple2=new tuple(newState2,inputString,newStackString2);
					vectorOfTuple.add(myNewTuple2);
				}

			}
			*/


        return vectorOfTuple;
    }
    boolean process(tuple urTuple){

        tuple myTuple=urTuple;
        pathVector.add(urTuple);
        String startState = myTuple.getFirst();
        String inputString2 = myTuple.getSecond();
        String stackString = myTuple.getThird();

        if(inputString2.equals(""))
        {
            inputString2=" ";
        }
        if(stackString.equals(""))
        {
            stackString=" ";
        }

        outputString2=outputString2+"|-"+startState+","+inputString2+","+stackString+"\n";
        Log.d("String",outputString2);

        String state=myTuple.getFirst();
        String inputString=myTuple.getSecond();


        if(isFinalState(state)&&inputString.equals("")){
            return true;
        }

        System.out.println(state+" "+inputString+" "+myTuple.getThird());

        ArrayList<tuple> vectorOfTuple=transitionToNextState(urTuple);

        for(int i=0;i<vectorOfTuple.size();i++){

            if(process(vectorOfTuple.get(i))==true)
            {
                return true;
            }

        }
			/*can be a point of error*/
        pathVector.remove(pathVector.size()-1);
      //  System.out.println("-------------");
        return false;
    }

    void displayNPDA(){

        System.out.println("------------N P D A----------");
        System.out.println("<states>");
        for(int i=0;i<allStates.size();i++)
        {
            System.out.print(allStates.get(i)+" ");
        }

        System.out.println("");

        System.out.println("<alphabet>");
        for(int i=0;i<allSymbols.size();i++){
            System.out.print(allSymbols.get(i)+" ");
        }
        System.out.println("");
        System.out.println("<stack>");
        for(int i=0;i<allStackAlphabets.size();i++){

            System.out.print(allStackAlphabets.get(i)+" ");
        }
        System.out.println("");
        System.out.println("<transition>");


        for(Map.Entry<tuple,ArrayList<pair>> entry:allTransitionsMap.entrySet()){

            tuple a=entry.getKey();
            ArrayList<pair> b=entry.getValue();

            for(int i=0;i<b.size();i++)
            {
                pair p=b.get(i);
                String startState=a.getFirst();
                String inputAlphabet=a.getSecond();
                String startStack=a.getThird();
                String endState=p.getFirst();
                String endStack=p.getSecond();

                if(inputAlphabet.equals(""))
                {
                    inputAlphabet = "*";
                }
                if( endStack.equals(""))
                {
                    endStack = "*";
                }


                System.out.println("("+startState+","+inputAlphabet+","+startStack+")"+"->("+endState+","+endStack+")");

            }
        }
        System.out.println("<initial state>");
        System.out.println(initialState);
        System.out.println("<stack start>");
        System.out.println(stackStartSymbol);
        System.out.println("<final states>");
        for(int i=0;i<allFinalStates.size();i++)
        {
            System.out.print(allFinalStates.get(i)+" ");
        }
        System.out.println("");
        System.out.println("-------------------------------------------");



    }

    void clearThePathVector(){
        pathVector.clear();
    }

    void printPath(){

        tuple myTuple=pathVector.get(0);
        String startState=myTuple.getFirst();
        String inputString=myTuple.getSecond();
        String stackString=myTuple.getThird();


        if(inputString.equals(""))
        {
            inputString=" ";
        }
        if(stackString.equals(""))
        {
            stackString=" ";
        }

        outputString=outputString+"|-"+startState+","+inputString+","+stackString+"\n";
        System.out.println("|-("+startState+","+inputString+","+stackString+")");

        for(int i=1;i<pathVector.size();i++)
        {
            myTuple=pathVector.get(i);
            startState=myTuple.getFirst();
            inputString=myTuple.getSecond();
            stackString=myTuple.getThird();



            if(inputString.equals(""))
            {
                inputString=" ";
            }
            if(stackString.equals(""))
            {
                stackString=" ";
            }

            outputString=outputString+"|-"+startState+","+inputString+","+stackString+"\n";
            System.out.println("|-("+startState+","+inputString+","+stackString+")");

        }
    }
}
