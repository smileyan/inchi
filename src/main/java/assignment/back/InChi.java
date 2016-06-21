package assignment.back;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by hua on 21/06/16.
 */

public class InChi extends Handler {

    private int num;
    private List<String> dict;
    private String inchi_filename;
    private List<MatchResult> mr = new ArrayList<MatchResult>();

    private static InChi singleton;

    private InChi() {}

    public void setDict() {
        setDict(Utils.DICT_FILE);
    }

    public void setNull() {
        singleton.getDict().setNull();
        singleton = null;
    }

    public Dict getDict() {
        return singleton._dict;
    }

    private Dict _dict;

    // calling the method after getInstance.
    public void setDict(String dict_filename) {
        singleton._dict = Dict.getInstance(dict_filename);
        singleton.dict = _dict.getWords();
    }

    // calling the method after setDict.
    public void match() {
        handleLine(inchi_filename, singleton, true);
    }

    public static synchronized InChi getInstance(String filename, int num) {
        if (singleton == null) {
            singleton = new InChi();
            singleton.num = num;
            singleton.inchi_filename = filename;
        }
        return singleton;
    }

    @Override
    Object execute(String line) {
        InChiOb inChi = new InChiOb(line);
        if(!inChi.isValid()) {
            return -1;
        }

        for (String pattern : dict){
            if (!enough(pattern)) {
                int po = BoyerMoore.match(inChi.getStandard_inchi_key(), pattern);
                if (po + pattern.length() < inChi.getStandard_inchi_key().length()) {
                    if (mr.size() >= num) {
                        mr.set(num - 1, new MatchResult(pattern, inChi));
                    } else {
                        mr.add(new MatchResult(pattern, inChi));
                    }
                    Collections.sort(mr, new MatchResultComparator());
                }
            }
        }
        return 0;
    }

    public List<MatchResult> getMr() {
        return mr;
    }

    private boolean enough(String pattern) {
        if (num > mr.size()) {
            return false;
        } else {
            if (pattern.length() >= mr.get(num - 1).getPattern().length()) {
                return false;
            } else {
                return true;
            }
        }
    }


}
class MatchResultComparator implements Comparator<MatchResult> {
    public int compare(MatchResult o1, MatchResult o2) {
        return o2.getPattern().length() - o1.getPattern().length();
    }
}