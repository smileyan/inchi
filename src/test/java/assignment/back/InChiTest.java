package assignment.back;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hua on 21/06/16.
 */
public class InChiTest {

    @Test
    public void test2000_1000(){
        test(2000,1000);
    }

    @Test
    public void test4000_1000(){
        test(4000,1000);
    }
    @Test
    public void test6000_1000(){
        test(6000, 1000);
    }

    @Test
    public void test8000_1000(){
        test(8000, 1000);
    }
    @Test
    public void test1000_2000(){
        test(1000, 2000);
    }
    @Test
    public void test1000_4000(){
        test(1000, 4000);
    }

    @Test
    public void test1000_6000(){
        test(1000, 6000);
    }

    @Test
    public void test1000_8000(){
        test(1000, 8000);
    }

    List<String> messages;
    @Before
    public void setUp() {
        messages = new ArrayList<String>();
    }
    public void test(int n, int m) {
        long begin = System.currentTimeMillis();
        InChi chem = InChi.getInstance("test/in_" + n + ".txt", 10);
        chem.setDict("test/dict_"+m+".txt");
        chem.match();

        messages.add("n = " + n + " , m = " + m+ ", time: " + (System.currentTimeMillis() - begin));

        chem.getDict().setNull();
        chem.setNull();
    }

    @After
    public void tearDown() {
        Collections.sort(messages);
        for (String m : messages) {
            System.out.println(m);
        }
    }
}
