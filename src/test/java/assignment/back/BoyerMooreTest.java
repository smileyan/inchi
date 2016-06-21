package assignment.back;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
/**
 * Created by hua on 21/06/16.
 */
public class BoyerMooreTest {

    @Test
    public void testMatch() {
        int i = BoyerMoore.match("WZIDQXDNXDLXAC-METISSESSA-N,", "METISSES");
        Assert.assertThat(i, Is.is(15));

        i = BoyerMoore.match("GQHBSDPLDCAZAI-METISSESSA-N", "METISSES");
        Assert.assertThat(i, Is.is(15));

        i = BoyerMoore.match("ZSNAKILYSGAMRM-UHFFFAOYSA-N", "SNAKILY");
        Assert.assertThat(i, Is.is(1));

        i = BoyerMoore.match("PPGDECVBLABBED-REOHCLBHSA-N", "BLABBED");
        Assert.assertThat(i, Is.is(7));
    }

}
