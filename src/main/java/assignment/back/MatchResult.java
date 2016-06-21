package assignment.back;

/**
 * Created by hua on 21/06/16.
 */
public class MatchResult{

    private String pattern;

    public String getPattern() {
        return pattern;
    }

    public InChiOb getInChiOb() {
        return inChiOb;
    }

    private InChiOb inChiOb;

    public MatchResult(String pattern, InChiOb inChiOb) {
        this.pattern = pattern;
        this.inChiOb = inChiOb;
    }
}
