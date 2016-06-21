package assignment.back;

import java.util.HashMap;

/**
 * Created by hua on 21/06/16.
 */
public class BoyerMoore {
    final static int CARD_CHAR_SET = 256;

    public static int match(String text, String pattern) {
        int[] BC = buildBC(pattern);
        int[] GS = buildGS(pattern);

        int i = 0;
        while (text.length() - pattern.length() >= i) {
            int j = pattern.length() - 1;
            while (pattern.charAt(j) == text.charAt(i+j)) {
                if (0 > --j) break;
            }
            if (0 > j){
                break;
            } else {
                i += max(GS[j], j - BC[text.charAt(i+j)]);
            }
        }
        return i;
    }

    private static HashMap<String, int[]> bcMap = new HashMap<String, int[]>();

    protected static int[] buildBC(String p) {
        if (bcMap.containsKey(p)) {
            return bcMap.get(p);
        }

        int[] BC = new int[CARD_CHAR_SET];
        int	j;

        for (j = 0; j < CARD_CHAR_SET; j++) {
            BC[j] = -1;
        }

        for (j = 0; j < p.length(); j++) {
            BC[p.charAt(j)] = j;
        }

        bcMap.put(p, BC);
        return BC;
    }

    protected static int[] computeSuffixSize(String P) {
        int	m = P.length();
        int[] SS = new int[m];
        int	s, t;
        int	j;

        SS[m-1]	=	m;

        s	=	m-1;	t = m-2;
        for (j = m-2; j >= 0; j--) {
            if ((j > s) && (j-s > SS[(m-1-t)+j]))
                SS[j]	=	SS[(m-1-t)+j];
            else {
                t = j;
                s = min(s, j);
                while ((0 <= s) && (P.charAt(s) == P.charAt((m-1-t)+s)))
                    s--;
                SS[j] = t-s;
            }
        }
        return(SS);
    }


    static HashMap<String, int[]> gsMap = new HashMap<String, int[]>();

    protected static int[] buildGS(String p) {

        if (gsMap.containsKey(p)) {
            return gsMap.get(p);
        }

        int	m = p.length();
        int[] SS = computeSuffixSize(p);

        int[] GS = new int[m];//Good Suffix Index
        int j;

        for (j = 0; j < m; j++)	GS[j] = m;

        int	i = 0;
        for (j = m-1; j >= -1; j--)
            if (-1 == j || j+1 == SS[j])
                for (; i < m-j-1; i++)
                    if (GS[i] == m)
                        GS[i] = m-j-1;

        for (j = 0; j < m-1; j++)
            GS[m-SS[j]-1] = m-j-1;

        gsMap.put(p, GS);
        return(GS);
    }

    protected static int max(int a, int b)
    { return (a>b) ? a : b; }

    protected static int min(int a, int b)
    { return (a<b) ? a : b; }
}
