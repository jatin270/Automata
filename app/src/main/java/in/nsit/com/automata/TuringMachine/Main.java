package in.nsit.com.automata.TuringMachine;

/**
 * Created by ( Jatin Bansal ) on 14-03-2017.
 */



public class Main
{

    public boolean execute(String s,TuringMachine turingMachine)
    {

        boolean done = turingMachine.Run(s, false);
        if (done==true)
        {
            System.out.println("The input was accepted.");
        }
        else
        {
            System.out.println("The input was rejected.");
        }
        return  done;
    }

}
