package in.nsit.com.automata;

/**
 * Created by ( Jatin Bansal ) on 24-03-2017.
 */

public class ResultPair {

    private String output;
    private boolean ans;

    public ResultPair(String output,boolean ans)
    {
        this.output=output;
        this.ans=ans;
    }


    public String getOutput() {
        return output;
    }

    public boolean isAns() {
        return ans;
    }

}
