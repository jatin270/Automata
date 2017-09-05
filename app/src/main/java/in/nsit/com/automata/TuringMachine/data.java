package in.nsit.com.automata.TuringMachine;

/**
 * Created by ( Jatin Bansal ) on 26-03-2017.
 */

public class data {

    char writeSymbol;
    int nextState;
    char Direction;

    public data(char writeSymbol,int nextState,char Direction){

        this.writeSymbol=writeSymbol;
        this.nextState=nextState;
        this.Direction=Direction;
    }

}