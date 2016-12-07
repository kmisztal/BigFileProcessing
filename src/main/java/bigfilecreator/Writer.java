package bigfilecreator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Writer extends Thread
{
    private final FileSettings fs;
    
    public Writer(FileSettings settings) { this.fs = settings; }
        
    @Override
    public void run()
    {
        try
        {
            String path = fs.getAllName();
            File file = new File(fs.getAllName());
            PrintWriter writer = new PrintWriter(path, "UTF-8");
                     
            String structure = fs.getStructure();
            String delimiter = "" + fs.getDelimiter();
            if(delimiter.equals("t"))
            {
                delimiter = "\t";
                structure = structure.replace(" t ", " \t ");
            }
            String[] splitArray = structure.split(delimiter);
                    
            String type, toWrite = fs.getHeader().replace(" t ", " \t ");
            if(toWrite.length() > 0) { writer.println(toWrite); }
            Random rand = new Random();
            int stateIt = 0;
            ArrayList<ArrayList<String>> states = fs.getStatesArray();
            long currentSize = 0;
            long size = (long)(1024L*1024L*fs.getSize());
            while(currentSize < size)
            {
                toWrite = "";
                for (String aSplitArray : splitArray) {
                    type = aSplitArray.trim();
                    switch (type) {
                        case "boolean":
                            toWrite += rand.nextBoolean() + delimiter;
                            break;

                        case "int":
                            toWrite += rand.nextInt() + delimiter;
                            break;

                        case "long":
                            toWrite += rand.nextLong() + delimiter;
                            break;

                        case "float": {
                            float f = 1;
                            long pow = rand.nextInt(38);
                            for (int j = 0; j < pow; j++) {
                                f *= 10L;
                            }
                            f *= rand.nextFloat();
                            toWrite += f + delimiter;
                            break;
                        }

                        case "double": {
                            double d = 1;
                            long pow = rand.nextInt(308);
                            for (int j = 0; j < pow; j++) {
                                d *= 10L;
                            }
                            d *= rand.nextDouble();
                            toWrite += d + delimiter;
                            break;
                        }

                        case "string":
                        case "String": {
                            String s = "";
                            int len = rand.nextInt(20) + 1;
                            char c;
                            String alpha = "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                            for (int j = 0; j < len; j++) {
                                c = alpha.charAt(rand.nextInt(52));
                                s += c;
                            }
                            toWrite += "\"" + s + "\"" + delimiter;
                            break;
                        }

                        case "timestamp":
                        case "Timestamp": {
                            //1900-01-01 00:00:00.0 -> 2100-12-31 23:59:59.999999999
                            Timestamp tmp = new Timestamp(rand.nextInt(201), rand.nextInt(12), rand.nextInt(29), rand.nextInt(24), rand.nextInt(60), rand.nextInt(60), rand.nextInt(1000000000));
                            Timestamp t = new Timestamp(tmp.getTime() + rand.nextInt(4) * 86400000);
                            toWrite += t.toString().substring(0, 16) + delimiter;
                            break;
                        }

                        case "state": {
                            ArrayList<String> temp = states.get(stateIt);
                            toWrite += temp.get(rand.nextInt(temp.size())) + delimiter;
                            stateIt++;
                            stateIt %= states.size();
                            break;
                        }

                        default: {
                            break;
                        }
                    }
                }
                writer.println(toWrite.substring(0, toWrite.length()-1));
                currentSize = file.length();
            }
            writer.close();
        }
        catch(FileNotFoundException | UnsupportedEncodingException ex)
        {
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
