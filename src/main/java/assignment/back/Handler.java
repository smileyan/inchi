package assignment.back;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by hua on 21/06/16.
 */
public abstract class Handler {
    private static final Log LOG = LogFactory.getLog(Handler.class);

    abstract Object execute(String line);

    public static void handleLine(String file, Handler handler, boolean hasTitle) {
        String line = "";
        InputStream fis = null;
        try {
            fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);


            if (hasTitle) br.readLine();
            while ((line = br.readLine()) != null) {
                handler.execute(line);
            }
        } catch (IOException ioe) {
            System.out.println(line);
            LOG.warn(ioe.getMessage());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException ioe) {
                    System.out.println(line);
                    LOG.warn(ioe.getMessage());
                }
            }
        }
    }
}
