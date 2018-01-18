package arithmetic;
import java.util.ArrayList;

public class NormalNode extends Node{
    public long nodeValue;

    ArrayList<Long> runningTotalList;

    public NormalNode(long value){
        this.nodeValue = value;
    }


}
