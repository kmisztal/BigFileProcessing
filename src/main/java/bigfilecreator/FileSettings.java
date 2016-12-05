package bigfilecreator;

import bigfilecreator.Validate.ValidateSettings;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FileSettings
{
    private double size;
    private File path;
    private String name, pathName, structure;
    private int columnsCount;
    private String states;
    private int statesCount;
    private String header;
    private char delimiter;
    
    private final Map<String, Boolean> statusMap = new HashMap<>();
    
    public FileSettings()
    {
        size = 0;
        path = null;
        name = pathName = structure = "";
        columnsCount = 0;
        
        states = null;
        statesCount = 0;
        header = null;
        delimiter = '#';
        
        statusMap.put("size", Boolean.FALSE);
        statusMap.put("path", Boolean.FALSE);
        statusMap.put("name", Boolean.FALSE);
        statusMap.put("pathName", Boolean.FALSE);
        statusMap.put("structure", Boolean.FALSE);
        statusMap.put("columnsCount", Boolean.FALSE);
        statusMap.put("states", Boolean.FALSE);
        statusMap.put("statesCount", Boolean.FALSE);
        statusMap.put("header", Boolean.FALSE);
        statusMap.put("delimiter", Boolean.FALSE);
    }
    
    public void setName(String name)
    {
        if(!ValidateSettings.checkNamePattern(name))
        {
            this.name = "";
            statusMap.replace("name", Boolean.FALSE);
        }
        else
        {
            this.name = name.trim();
            statusMap.replace("name", Boolean.TRUE);
        }
    }
           
    public void setPath(File path)
    {
        if(!ValidateSettings.checkPath(path))
        {
            pathName = "";
            statusMap.replace("path", Boolean.FALSE);
            statusMap.replace("pathName", Boolean.FALSE);
        }
        else
        {
            this.path = path;
            pathName = path.getAbsolutePath();
            statusMap.replace("path", Boolean.TRUE);
            statusMap.replace("pathName", Boolean.TRUE);
        }
    }
    
    public void setStructure(String structure)
    {
        char delm = ValidateSettings.checkStructurePattern(structure);
        if(delm != ',' && delm != ';' && delm != 't')
        {
            statusMap.replace("structure", Boolean.FALSE);
            statusMap.replace("delimiter", Boolean.FALSE);
            statusMap.replace("columnsCount", Boolean.FALSE);
            statusMap.replace("statesCount", Boolean.FALSE);
            statesCount = columnsCount = 0;
        }
        else
        {
            this.structure = structure.trim();
            delimiter = delm;
            statusMap.replace("structure", Boolean.TRUE);
            statusMap.replace("delimiter", Boolean.TRUE);
            
            String[] cols = this.structure.split("" + ((delimiter == 't') ? " t " : delimiter));
            columnsCount = cols.length;
            statusMap.replace("columnsCount", Boolean.TRUE);
            
            statesCount = 0;
            for(String s : cols) if(s.trim().equals("state")) { statesCount++; }
            statusMap.replace("statesCount", Boolean.TRUE);
        }
    }
    
    public void setStates(String states)
    {
        String[] statesArray = states.trim().split(",");
        int s = 0;
        boolean flag = (statesArray.length == 1 && statesArray[0].trim().equals(""));
        if(!flag) for(String state : statesArray)
        {
            if(ValidateSettings.checkState(state)) { s++; }
            else { return; }
        }
        if(!flag && (s != statesCount || s != statesArray.length)) { return; }
        this.states = "";
        if(!flag)
        {
            for(String state : statesArray) { this.states += state.trim() + ", "; }
        }
        statusMap.replace("states", Boolean.TRUE);
    }
    
    public void setHeader(String header)
    {
        String[] headsArray = header.trim().split(",");
        if(headsArray.length != columnsCount) { return; }
        this.header = "";
        if(!(headsArray.length == 1 && headsArray[0].trim().equals("")))
        {
            for(String head : headsArray) { this.header += head.trim() + ((delimiter == 't') ? " t " : delimiter); }
            this.header = this.header.substring(0, (delimiter == 't') ? this.header.length()-3 : this.header.length()-1);
        }
        statusMap.replace("header", Boolean.TRUE);
    }
    
    public void setSize(double size)
    {
        if(size <= 0)
        {
            statusMap.replace("size", Boolean.FALSE);
        }
        else
        {
            this.size = size;
            statusMap.replace("size", Boolean.TRUE);
        }
    }
    
    public String getName() { return name; }
    
    public File getPath() { return path; }
    
    public String getPathName() { return pathName; }
    
    public String getAllName() { return (pathName.equals("") && name.equals("")) ? "" : pathName + File.separator + name; }
    
    public String getStructure() { return structure; }
    
    public int getColumnsCount() { return columnsCount; }
    
    public String getStates() { return states; }
    
    public ArrayList<ArrayList<String>> getStatesArray()
    {
        ArrayList<ArrayList<String>> states = new ArrayList<>();
        for(String s1 : getStates().split(","))
        {
            s1 = s1.trim();
            if(!s1.equals(""))
            {
                s1 = s1.substring(1, s1.length()-1); //Brackets cut (K|M).
                ArrayList<String> al = new ArrayList<>();
                for(String s2 : s1.trim().split("\\|"))
                {
                    s2 = s2.trim();
                    al.add(s2);
                }
                states.add(al);
            }
        }
        return states;
    }
    
    public int getStatesCount() { return statesCount; }
    
    public String getHeader() { return header; }
    
    public char getDelimiter() { return delimiter; }
    
    public double getSize() { return size; }
        
    @Override
    public String toString()
    {
        String str = "Name: " + name + "\n;";
        str += "Path name: " + pathName + "\n;";
        str += "Location: " + getAllName() + "\n;";
        str += "Structure: " + structure + "\n;";
        str += "Delimiter: " + delimiter + "\n;";
        str += "Size: " + size + "\n.";

        return str;
    }
    
    public boolean isReady()
    {
        return !statusMap.containsValue(Boolean.FALSE);
    }
}