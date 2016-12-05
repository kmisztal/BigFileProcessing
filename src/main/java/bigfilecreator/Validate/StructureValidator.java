package bigfilecreator.Validate;

import bigfilecreator.FileSettings;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class StructureValidator
{
    public static void isOK(TextField tf, Text ok, Text wrong, Text delm, FileSettings fs)
    {
        String value = tf.getText().trim();
        char delimiter = ValidateSettings.checkStructurePattern(value);
        if(delimiter == '#')
        {
            fs.setStructure("");
            ok.setText("");
            delm.setText("");
        }
        else
        {
            fs.setStructure(value);
            ok.setText("OK!");
            wrong.setText("");
            delm.setText("Delimiter: \" " + ((delimiter == 't') ? "tab" : delimiter) + " \".");
        }
    }

    public static void isWrong(boolean hasFocus, TextField tf, Text ok, Text wrong, Text delm, FileSettings fs)
    {
        if(!hasFocus)
        {
            String value = tf.getText().trim();
            char delimiter = ValidateSettings.checkStructurePattern(value);
            if(delimiter == '#')
            {
                fs.setStructure("");
                ok.setText("");
                wrong.setText("Użyj \",\" lub \";\" lub \" t \" jako separatora.\nMożliwe typy danych, to:\nboolean, int, long, float, double, String, Timestamp, state.");
                delm.setText("");
            }
        }
    }
}