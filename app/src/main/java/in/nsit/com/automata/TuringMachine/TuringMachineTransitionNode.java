package in.nsit.com.automata.TuringMachine;

/**
 * Created by ( Jatin Bansal ) on 17-03-2017.
 */

public class TuringMachineTransitionNode {
    private  int result1;
    private  int result2;
    private  String read,write,direction;

    public TuringMachineTransitionNode(int result1,int result2,String read,String write,String direction){
        this.result1=result1;
        this.result2=result2;
        this.read=read;
        this.write=write;
        this.direction=direction;
    }

    public int getResult1() {
        return result1;
    }

    public int getResult2() {
        return result2;
    }

    public String getRead() {
        return read;
    }

    public String getWrite() {
        return write;
    }

    public String getDirection() {
        return direction;
    }
}
