import java.util.*;

/**
Minglish lesson
===============
Welcome to the lab, minion. Henceforth you shall do the bidding of Professor
Boolean. Some say he's mad, trying to develop a zombie serum and all... but we
think he's brilliant!
First things first - Minions don't speak English, we speak Minglish. Use the
Minglish dictionary to learn! The first thing you'll learn is how to use the
dictionary.
Open the dictionary. Read the page numbers, figure out which pages come before
others. You recognize the same letters used in English, but the order of letters
is completely different in Minglish than English (a < b < c < ...).
Given a sorted list of dictionary words (you know they are sorted because you
can read the page numbers), can you find the alphabetical order of the Minglish
alphabet?
For example, if the words were ["z", "yx", "yz"] the alphabetical order would be
"xzy," which means x < z < y. The first two words tell you that z < y, and the
last two words tell you that x < z.
Write a function answer(words) which, given a list of words sorted
alphabetically in the Minglish alphabet, outputs a string that contains each
letter present in the list of words exactly once; the order of the letters in
the output must follow the order of letters in the Minglish alphabet.
The list will contain at least 1 and no more than 50 words, and each word will
consist of at least 1 and no more than 50 lowercase letters [a-z].
It is guaranteed that a total ordering can be developed from the input provided,
(i.e. given any two distinct letters, you can tell which is greater),
and so the answer will exist and be unique.
*/
public class Answer {
    Map<String, List<String>> graph;
    Set<String> leftValues;
    Set<String> rightValues;
    String alphabet;

    public Answer() {
        graph = new HashMap<String, List<String>>();
        leftValues = new HashSet<String>();
        rightValues = new HashSet<String>();
    }

    public static String answer(String[] words) {
        Answer answer = new Answer();
        
        answer.buildDAG(Arrays.asList(words));
        if (answer.graph != null && answer.graph.size() == 0) {
            return words[0].substring(0,1);
        } else {
            return answer.evaluateDAG();
        }
        
    }

    private void buildDAG(List<String> words) {
        String lastWord = words.get(0);
        wordsLoop:
        for (String thisWord:words) {
            wordloop:
            for (int i = 0 ; i < lastWord.length() ; i++) {
                if (i > thisWord.length()) {
                    break wordloop;
                }
                
                String letterFromLastWord = lastWord.substring(i,i+1);
                String letterFromThisWord = thisWord.substring(i,i+1);
                if (!letterFromLastWord.equals(letterFromThisWord)) {
                    addToGraph(letterFromLastWord, letterFromThisWord.substring(0,1));
                    break wordloop;
                }
            }
            lastWord = thisWord;
        }
    }

    private void addToGraph(String node1, String node2) {
        leftValues.add(node1);
        rightValues.add(node2);
        if (graph.containsKey(node1)) {
            List<String> node1List = graph.get(node1);
            node1List.add(node2);
        } else {
            List<String> nextLetters = new ArrayList<String>();
            nextLetters.add(node2);
            graph.put(node1, nextLetters);
        }
    }

    private String evaluateDAG() {
        Set<String> lettersWithNoAncestor = new HashSet<String>(leftValues);
        lettersWithNoAncestor.removeAll(rightValues);
        
        if (lettersWithNoAncestor.size() != 1) {
            // This is an unexpected condition
            throw new IllegalArgumentException("Wrong number of ancestors");
            // return ""; //
        } else {
            String firstLetter = (String)lettersWithNoAncestor.toArray()[0];
            return findDAGPaths(firstLetter);
        }
    }

    private String findDAGPaths(String currentAlphabet) {
        Set<String> allLetters = new HashSet<String>(leftValues);
        allLetters.addAll(rightValues);

        if (currentAlphabet == null) {
            currentAlphabet = "";
        }
        String currentLetter = currentAlphabet.substring(currentAlphabet.length() - 1);
        
        if (!graph.containsKey(currentLetter)) {
            return currentAlphabet;
        } else {
            for (String nextLetter : graph.get(currentLetter)) {
                String nextAlphabet = findDAGPaths(currentAlphabet + nextLetter);
                if (allLetters.size() == nextAlphabet.length()) {
                    return nextAlphabet;
                }
            }
        }
        
        return "";
    }


}
