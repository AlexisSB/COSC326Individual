package arithmetic;

public class ArithTree<T>{

    private T rootValue;
    private ArithTree<T> leftTree;
    private ArithTree<T> rightTree;

    public ArithTree(T value){
        this(value,null,null);
    }
    public ArithTree(){
        this(null,null,null);
    }

    public ArithTree(T value, ArithTree<T> leftTree, ArithTree<T> rightTree){
        this.rootValue = value;
        this.leftTree = leftTree;
        this.rightTree = rightTree;
    }

    public String toString(){
        return rootValue.toString();
    }

    public boolean isEmpty(){
        return rootValue == null;
    }

    public boolean addChildren(ArithTree<T> number){
        this.leftTree = number
    }
    public String solveProblem(long[] numbers, char option, long total){

    }





}
