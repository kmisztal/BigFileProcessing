package bigfilecreator.Validate;

import bigfilecreator.FileSettings;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class StateValidator
{
    public static void isOK(TextField tf, Text ok, Text wrong, Text count, FileSettings fs)
    {
        int sc = fs.getStatesCount();
        if(sc <= 0)
        {
            tf.setDisable(true);
            fs.setStates("");
            ok.setText("");
            wrong.setText("");
            count.setText("0/0");
            count.setFill(Color.GRAY);
            return;
        }
        else
        {
            tf.setDisable(false);
        }
                
        String value = tf.getText().trim();
        String[] states = value.split(",");
        int c = 0;
        for(String state : states) if(ValidateSettings.checkState(state)) { c++; }
        
        if(sc == c && c == states.length)
        {
            fs.setStates(value);
            ok.setText("OK!");
            wrong.setText("");
            count.setText(c + "/" + sc);
            count.setFill(Color.BLUE);
        }
        else
        {
            fs.setStates("");
            ok.setText("");
            count.setText(c + "/" + sc);
            count.setFill(Color.FIREBRICK);
        }
    }
    
    public static void isWrong(boolean hasFocus, TextField tf, Text ok, Text wrong, Text count, FileSettings fs)
    {
        if(!hasFocus)
        {
            int sc = fs.getStatesCount();

            String value = tf.getText().trim();
            String[] states = value.split(",");
            int c = 0;
            for(String state : states) if(ValidateSettings.checkState(state)) { c++; }

            if(sc != c || c != states.length)
            {
                fs.setStates("");
                ok.setText("");
                wrong.setText("Musisz podać tyle typów stanowych, ile zadeklarowano, np.: (Kobieta|Mężczyzna), (A|B|C|D|Żadna)");
                count.setText(c + "/" + sc);
                count.setFill(Color.FIREBRICK);
            }
        }
    }
}