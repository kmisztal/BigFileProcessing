package bigfilecreator.Validate;

import bigfilecreator.FileSettings;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class NameValidator
{
    public static void isOK(TextField tf, Text ok, Text wrong, TextField other, FileSettings fs)
    {
        String value = tf.getText().trim();
        if(!ValidateSettings.checkNamePattern(value))
        {
            fs.setName("");
            ok.setText("");
            other.setText(fs.getAllName());
        }
        else
        {
            fs.setName(value);
            ok.setText("OK!");
            wrong.setText("");
            other.setText(fs.getAllName());
        }
    }

    public static void isWrong(boolean hasFocus, TextField tf, Text ok, Text wrong, TextField other, FileSettings fs)
    {
        if(!hasFocus)
        {
            String value = tf.getText().trim();
            if(!ValidateSettings.checkNamePattern(value))
            {
                fs.setName("");
                ok.setText("");
                wrong.setText("Podaj nazwę i rozszerzenie, maksymalnie " + ValidateSettings.maxNameLength + " znaków.");
                other.setText("");
            }
        }
    }
}