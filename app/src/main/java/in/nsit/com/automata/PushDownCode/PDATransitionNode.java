package in.nsit.com.automata.PushDownCode;

/**
 * Created by ( Jatin Bansal ) on 10-03-2017.
 */

public class PDATransitionNode {

    private  int result1;
    private  int result2;
    private  String read,pop,push;

    public PDATransitionNode(int result1,int result2,String read,String pop,String push){
        this.result1=result1;
        this.result2=result2;
        this.read=read;
        this.pop=pop;
        this.push=push;
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

    public String getPop() {
        return pop;
    }

    public String getPush() {
        return push;
    }
}
