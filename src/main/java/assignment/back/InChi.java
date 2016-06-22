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
        int i = 0;
        for (String pattern: dict) {
            for (InChiOb object: objects) {
                if (isMatch(object.getStandard_inchi_key(), pattern)) {
                    if (i >= num) return;
                    i++;
                    mr.add(new MatchResult(pattern, object));
                }
            }
        }
    }

    public static synchronized InChi getInstance(String filename, int num) {
        if (singleton == null) {
            singleton = new InChi();
            singleton.num = num;
            singleton.inchi_filename = filename;
            handleLine(filename, singleton, true);
        }
        return singleton;
    }

    private List<InChiOb> objects = new ArrayList<InChiOb>();

    @Override
    Object execute(String line) {
        InChiOb inChi = new InChiOb(line);
        if(!inChi.isValid()) {
            return -1;
        } else {
            objects.add(inChi);
            return 0;
        }

    }

    Object execute1(String line) {
        int added = 0;
        List<MatchResult> mr_temp = new ArrayList<MatchResult>();


        InChiOb inChi = new InChiOb(line);
        if(!inChi.isValid()) {
            return -1;
        }

        for (String pattern : dict) {
            if (added >= num) {
                return 0;
            }

            if (mr.size() > 0 && (pattern.length() < mr.get(mr.size() - 1).getPattern().length())) {
                return 0;
            }

            if (isMatch(inChi.getStandard_inchi_key(), pattern)) {
                mr_temp.add(new MatchResult(pattern, inChi));
                added++;
                //Collections.sort(mr_temp, new MatchResultComparator());
            }
        }
        mr.addAll(mr_temp);
        Collections.sort(mr, new MatchResultComparator());
        return 0;
    }

    private boolean isMatch(String text, String pattern) {
        int po = BoyerMoore.match(text, pattern);
        return po + pattern.length() < text.length();
    }
    public List<MatchResult> getMr() {
        return mr;
    }

}
class MatchResultComparator implements Comparator<MatchResult> {
    public int compare(MatchResult o1, MatchResult o2) {
        return o2.getPattern().length() - o1.getPattern().length();
    }
}