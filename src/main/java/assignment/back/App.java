package assignment.back;


import org.apache.maven.shared.utils.StringUtils;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {

        int num = 10;

        if (args != null && args.length > 0) {
            if (StringUtils.isNumeric(args[0])) {
                num = Integer.valueOf(args[0]);
            } else {
                System.out.println("Please input a number");
                return ;
            }
        }

        Utils.downloadDict();
        Utils.downloadChembl();

        Utils.printTopN(num);
    }
}
