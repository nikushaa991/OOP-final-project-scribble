package utils;

/* immutable pair class */
public class Pair<A, B> {
    private A first;
    private B second;

    public Pair(A first, B second){
        this.first = first;
        this.second = second;
    }

    /* getters */
    public A getFirst(){
        return first;
    }

    public B getSecond(){
        return second;
    }
}
