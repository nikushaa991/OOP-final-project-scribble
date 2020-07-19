package utils;

/* immutable pair class */
public class Pair<A, B> {
    private final A first;
    private final B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    /* getters */
    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }
}
