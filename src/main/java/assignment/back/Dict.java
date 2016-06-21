package assignment.back;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by hua on 21/06/16.
 */

public class Dict extends Handler {

    private static Dict singleton;

    public void setNull() {
        singleton = null;
    }

    private Dict(){ }

    public static synchronized Dict getInstance(String filename) {
        if (singleton == null) {
            singleton = new Dict();
            singleton.words = new ArrayList<String>();
            handleLine(filename, singleton, false);
            Collections.sort(singleton.words, new WordComparator());
        }
        return singleton;
    }

    public List<String> getWords() {
        return words;
    }

    private List<String> words;

    @Override
    Object execute(String line) {
        words.add(line);
        return 0;
    }
}
class  WordComparator implements Comparator<String> {
    public int compare(String o1, String o2) {
        return o2.length() - o1.length();
    }
}
