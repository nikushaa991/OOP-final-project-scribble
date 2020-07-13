package game;

import java.util.ArrayList;

public class WordsList {
    //words and difficulty
    /* public static ArrayList<Pair<String, Integer>> wordsList = new ArrayList<Pair<String, Integer>>(){{
        add(new Pair("dog", 1));
        add(new Pair("cat", 1));
        add(new Pair("mouse", 1));
        add(new Pair("ae", 2));
        add(new Pair("rip", 2));
        add(new Pair("ops", 2));
        add(new Pair("hand", 1));
        add(new Pair("oop", 3));
        add(new Pair("nand2tetris", 3));
        add(new Pair("macs", 3));
        add(new Pair("money", 1));
        add(new Pair("water", 4));
        add(new Pair("hamburger", 2));
        add(new Pair("scribble", 4));
        add(new Pair("cyclops", 3));
    }}; */

    //just words
    //TODO: add some lexicon from the internet instead of random words, difficulty not needed.
    //TODO: maybe store the words in database?
    public static ArrayList<String> wordsList = new ArrayList<String>(){{
        add("Dog");
        add("Cat");
        add("Frog");
        add("Calculator");
        add("Book");
        add("Computer");
        add("Beach");
        add("Space");
    }};
}
