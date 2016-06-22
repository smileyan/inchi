package assignment.back;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Collections;
import java.util.List;

/**
 * Created by hua on 21/06/16.
 */
public class Utils {

    private static final Log LOG = LogFactory.getLog(Utils.class);

    public static final String DICT_FILE = "input/dictionary.txt";
    public static final String IN_CHI_FILE = "input/chembl_21_chemreps.txt";

    public static final String DICT_URL = "https://raw.githubusercontent.com/jonbcard/scrabble-bot/master/src/dictionary.txt";
    public static final String CHEMBL_URL = "http://ftp.ebi.ac.uk/pub/databases/chembl/ChEMBLdb/latest/chembl_21_chemreps.txt.gz";

    public static void downloadDict() {
        downloadFile(DICT_URL, DICT_FILE);
    }

    public static void downloadChembl() {
        downloadFile(CHEMBL_URL, IN_CHI_FILE + ".gz");
        gunzip(IN_CHI_FILE + ".gz");
    }

    public static void gzip(String filename) {
        try {
            Runtime.getRuntime().exec("gzip " + filename);
        } catch (IOException ioe) {
            LOG.warn(ioe.getMessage());
        }
    }

    public static void gunzip(String filename) {
        try {
            File target = new File(filename);
            if (target.exists()) {
                return;
            } else {
                System.out.println("gunzip " + filename + " ...");
            }
            Runtime.getRuntime().exec("gunzip " + filename);
        } catch (IOException ioe) {
            LOG.warn(ioe.getMessage());
        }
    }

    public static void downloadFile(String from, String to) {
        URL website;
        FileOutputStream fos = null;

        try {
            File target = new File(to);
            if (target.exists()) {
                return;
            } else {
                System.out.println("Downloading " + from + " ...");
            }

            website = new URL(from);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            fos = new FileOutputStream(to);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (MalformedURLException mue) {
            LOG.warn(mue.getMessage());
        } catch (FileNotFoundException fnfe) {
            LOG.warn(fnfe.getMessage());
        } catch (IOException ioe) {
            LOG.warn(ioe.getMessage());
        } finally {
            if (fos != null)
                try {
                    fos.close();
                } catch (IOException ioe) {
                    LOG.warn(ioe.getMessage());
                }
        }
    }


    public static void printTopN(int num) throws Exception {
        InChi chem = InChi.getInstance(Utils.IN_CHI_FILE, num);
        chem.setDict();
        chem.match();

        List<MatchResult> mr = chem.getMr();
        Collections.sort(mr, new MatchResultComparator());

        print(mr, num);
    }

    public static void print(List<MatchResult> mr, int num) {
        int i = 0;
        for (MatchResult result : mr) {
            if (i == num) return;
            System.out.println(result.getInChiOb().getStandard_inchi_key() + ", " + result.getPattern() + ", " + result.getInChiOb().getChembl_id());
            i ++;
        }
    }
}
