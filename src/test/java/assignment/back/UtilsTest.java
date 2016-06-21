package assignment.back;

import org.apache.maven.shared.utils.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Created by hua on 21/06/16.
 */
public class UtilsTest {
    @After
    public void removeTestFile() {
        File file= new File("input/test");
        file.delete();
    }

    @Test
    public void download() {
        Utils.downloadFile("https://raw.githubusercontent.com/smileyan/learning-scala/master/.travis.yml", "input/test");
    }

    @Before
    public void gzip() {
        Utils.gzip(ZIP_FILE);
    }

    public static final String ZIP_FILE = "input/chembl_21_chemreps_1000.txt";

    @Test
    public void gunzip() {
        File c = new File(ZIP_FILE);
        if (!c.exists())
            new Throwable("Should have a test file :" + ZIP_FILE);
        Utils.gunzip(ZIP_FILE + ".gz");
    }
}
