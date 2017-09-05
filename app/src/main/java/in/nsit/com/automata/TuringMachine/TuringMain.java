package in.nsit.com.automata.TuringMachine;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by ( Jatin Bansal ) on 26-03-2017.
 */

public class TuringMain {


     Set<Integer> UndetermindedStates;
     Set<Integer> acceptingStates;
     Set<Integer> rejectingStates;
     Map<pair,ArrayList<data>> transition;
    TuringMachineTransitionNode[] transitionNodes;
    int TMtransitioncount;

    public String outputString="";
	public TuringMain(
	Set<Integer> UndetermindedStates,
	Set<Integer> acceptingStates,
	Set<Integer> rejectingStates,
    TuringMachineTransitionNode[] transitionNodes,
    int TMtransitioncount ){
		this.UndetermindedStates=UndetermindedStates;
		this.acceptingStates=acceptingStates;
		this.rejectingStates=rejectingStates;
        this.transitionNodes=transitionNodes;
        this.TMtransitioncount=TMtransitioncount;
	}

	int check=0;

     boolean process(String s,int currentpos,int currentState,int level) {

         System.out.println(currentpos+" "+s+" "+currentState+" "+level);

         if (acceptingStates.contains(currentState)) {
             check = 1;
             System.out.println(acceptingStates+"");
             outputString=outputString+"|-";
             if(currentpos>=s.length())
                 currentpos=s.length()-1;

             for(int j=0;j<=currentpos;j++)
                 outputString=outputString+s.charAt(j);
             outputString=outputString+" q"+currentState+" ";
             for(int j=currentpos+1;j<s.length();j++)
                 outputString=outputString+s.charAt(j);
             outputString=outputString+"\n";

             return true;
         }
         if (rejectingStates.contains(currentState))
         {
             outputString=outputString+"|-";
             if(currentpos>=s.length())
                 currentpos=s.length()-1;

             for(int j=0;j<=currentpos;j++)
                 outputString=outputString+s.charAt(j);

             outputString=outputString+" q"+currentState+" ";

             for(int j=currentpos+1;j<s.length();j++)
                 outputString=outputString+s.charAt(j);

             outputString=outputString+"\n";

             return false;
         }

         if(level>500)
            return false;

         if(check==1)
             return  true;


        int i;
         if(currentpos==s.length())
         {
             s=s+"_";
         }

        // outputString=outputString+"|-"+s.substring(0,currentpos+1)+" q"+currentState+" "+s.substring(currentpos+1,s.length())+"\n";
         outputString=outputString+"|-";

         for(int j=0;j<=currentpos;j++)
             outputString=outputString+s.charAt(j);

         outputString=outputString+" q"+currentState+" ";

         for(int j=currentpos+1;j<s.length();j++)
             outputString=outputString+s.charAt(j);

         outputString=outputString+"\n";


        for(Map.Entry<pair,ArrayList<data>> entry:transition.entrySet()){

            pair p=entry.getKey();

            try {
                if (p.read == s.charAt(currentpos) && p.current == currentState) {
                    //System.out.println(p.read+"-----"+p.current);

                    for (i = 0; i < transition.get(p).size(); i++) {
                        String pass;
                        data temp = transition.get(p).get(i);
                        char[] myNameChars = s.toCharArray();
                        myNameChars[currentpos] = temp.writeSymbol;
                        s = String.valueOf(myNameChars);

                        int  var=currentpos;
                        if (temp.Direction == 'R')
                            var++;
                        else
                        if(temp.Direction=='L')
                            var--;
                        else
                        if(temp.Direction=='-')
                            var=var;

/*
                        if (currentpos >= s.length()) {
                            pass = s + "_________________________________________________________________________________________________________________________________________________________";
                        } else
                            */

                            pass = s;

                        boolean k = process(pass, var, temp.nextState, level + 1);
                        System.out.println(k);
                        if (k == true)
                            return k;
                    }
                }
            }catch (IndexOutOfBoundsException e)
            {
                return  false;
            }
        }


        return false;
    }

    public boolean execute(String s) {
        String string;
        string=s;
        outputString=outputString+"|-q0 "+s+"\n";
        if(process(string,0,0,0))
        {
            System.out.println("Accepted");
            return true;
        }
        else
        {
            System.out.println("Rejected");
            return false;
        }
    }

    public void loadData(){

        transition=new HashMap<>();

        for(int i=0;i<TMtransitioncount;i++)
        {
             int result1;
             int result2;
             String read,write,direction;
             result1=transitionNodes[i].getResult1();
             result2=transitionNodes[i].getResult2();
             read=transitionNodes[i].getRead();
             write=transitionNodes[i].getWrite();
             direction=transitionNodes[i].getDirection();
             data temp=new data(write.charAt(0),result2,direction.charAt(0));
             pair p=new pair(result1,read.charAt(0));
            if(transition.containsKey(p))
            {
                transition.get(p).add(temp);
            }
            else
            {
                ArrayList<data> list=new ArrayList<>();
                list.add(temp);
                transition.put(p,list);
            }

        }

        System.out.println("-----------------------------------------------------");
        for(Map.Entry<pair,ArrayList<data>> entry:transition.entrySet()){

            int cur=entry.getKey().current;
            char read=entry.getKey().read;
            ArrayList<data> work=entry.getValue();

            for(int i=0;i<work.size();i++)
            {
                char write=work.get(i).writeSymbol;
                int next =work.get(i).nextState;
                char direction=work.get(i).Direction;
                System.out.println(cur+" "+read+" "+write+" "+next+" "+direction);
            }

        }

        System.out.println("-----------------------------------------------------");
    }

}
