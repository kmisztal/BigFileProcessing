package bigfilecreator.Validate;

import bigfilecreator.FileSettings;
import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateSettings
{
    public static final int maxNameLength = 35;
    private static Scanner scan;

    private static final String namePtrnStr = "(.+)[.](.+)";
    
    private static final String typeCommaPtrnStr =
    //Po każdym musi być przecinek:
    "^(" +
    "boolean((,|, ){1})|" +
    "int((,|, ){1})|" +
    "long((,|, ){1})|" +
    "float((,|, ){1})|" +
    "double((,|, ){1})|" +
    "(String|string)((,|, ){1})|" +
    "(Timestamp|timestamp)((,|, ){1})|" +
    "state((,|, ){1})|" + 
    ")*" + //Powyższe wyrażenie może wystąpić wiele razy.
    //Na koncu nie musi byc przecinka:
    "(" +
    "boolean((,|, ){0,1})|" +
    "int((,|, ){0,1})|" +
    "long((,|, ){0,1})|" +
    "float((,|, ){0,1})|" +
    "double((,|, ){0,1})|" +
    "(String|string)((,|, ){0,1})|" +
    "(Timestamp|timestamp)((,|, ){0,1})|" +
    "state((,|, ){0,1})|" +
    "){1}$"; //Takie wyrażenie może wystąpić tylko raz.

    private static final String typeSemicPtrnStr =
    "^(" +
    "boolean((;|; ){1})|" +
    "int((;|; ){1})|" +
    "long((;|; ){1})|" +
    "float((;|; ){1})|" +
    "double((;|; ){1})|" +
    "(String|string)((;|; ){1})|" +
    "(Timestamp|timestamp)((;|; ){1})|" +
    "state((;|; ){1})|" +
    ")*" +
    "(" +
    "boolean((;|; ){0,1})|" +
    "int((;|; ){0,1})|" +
    "long((;|; ){0,1})|" +
    "float((;|; ){0,1})|" +
    "double((;|; ){0,1})|" +
    "(String|string)((;|; ){0,1})|" +
    "(Timestamp|timestamp)((;|; ){0,1})|" +
    "state((;|; ){0,1})|" +
    "){1}$";

    private static final String typeTabPtrnStr =
    "^(" +
    "boolean(X{1})|" +
    "int(X{1})|" +
    "long(X{1})|" +
    "float(X{1})|" +
    "double(X{1})|" +
    "(String|string)(X{1})|" +
    "(Timestamp|timestamp)(X{1})|" +
    "state(X{1})|" +
    ")*" +
    "(" +
    "boolean(X{0,1})|" +
    "int(X{0,1})|" +
    "long(X{0,1})|" +
    "float(X{0,1})|" +
    "double(X{0,1})|" +
    "(String|string)(X{0,1})|" +
    "(Timestamp|timestamp)(X{0,1})|" +
    "state(X{0,1})|" +
    "){1}$";
    
    private static final String statePtrnStr = "^(\\(.{1,}((\\|(.{1,})){0,})\\))$";
    private static final Pattern namePtrn = Pattern.compile(namePtrnStr);
    private static final Pattern typeCommaPtrn = Pattern.compile(typeCommaPtrnStr);
    private static final Pattern typeSemicPtrn = Pattern.compile(typeSemicPtrnStr);
    private static final Pattern typeTabPtrn = Pattern.compile(typeTabPtrnStr.replace("X", "( t )"));
    private static final Pattern statePtrn = Pattern.compile(statePtrnStr);
    
    public static boolean checkNamePattern(String v)
    {
        if(v == null) { return false; }
        v = v.trim();
        if(v.length() <= 0 || v.length() > maxNameLength) { return false; }

        Matcher m = namePtrn.matcher(v.trim());
        return m.find();
    }
    
    public static boolean checkPath(File f)
    {
        return (f != null && f.exists());
    }
    
    public static boolean checkFile(File f)
    {
        return (f != null && f.exists() && f.canWrite());
    }
    
    public static double checkDouble(String value)
    {
        double size;
        try { size = Double.valueOf(value); }
        catch(NumberFormatException ex)
        {
            try
            {
                scan = new Scanner(value);
                size = scan.nextDouble();
            }
            catch(Exception ex2) { return Double.NEGATIVE_INFINITY; }
        }
        return size;
    }
        
    public static char checkStructurePattern(String value)
    {
        if(value == null) { return '#'; }
        value = value.trim();
        if(value.length() <= 0) { return '#'; }
        
        Matcher m;
                
        m = typeCommaPtrn.matcher(value);
        if(m.find()) { return ','; }
        
        m = typeSemicPtrn.matcher(value);
        if(m.find()) { return ';'; }

        m = typeTabPtrn.matcher(value);
        if(m.find()) { return 't'; }
        
        return '#';
    }
    
    public static boolean checkState(String value)
    {
        if(value == null) { return false; }
        value = value.trim();
        Matcher m = statePtrn.matcher(value);
        if(!m.find()) { return false; }
        return true;
    }
}