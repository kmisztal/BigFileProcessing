package bigfilecreator.Validate;

import bigfilecreator.FileSettings;
import java.io.File;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class SizeValidator
{
    public static void isOK(TextField tf, Text ok, Text wrong, FileSettings fs)
    {
        String value = tf.getText().trim();
        double size = ValidateSettings.checkDouble(value);
        File f = fs.getPath();
        if(f != null && size*1024L*1024L > f.getFreeSpace()*0.975)
        {
            fs.setSize(0);
            ok.setText("");
            wrong.setText("Za mało miejsca na wskazanym dysku!\nPamiętaj, że wartość jest przybliżona i faktycznie może być większa.");
        }
        else if(size <= 0)
        {
            fs.setSize(0);
            ok.setText("");
            wrong.setText("Zawartość rozmiaru musi być nieujemną liczbą!");
        }
        else
        {
            fs.setSize(size);
            ok.setText("OK!");
            wrong.setText("");
        }
    }
    
    public static void isWrong(boolean hasFocus, TextField tf, Text wrong)
    {
        String value = tf.getText().trim();
        if(!hasFocus && value.length() <= 0) { wrong.setText("Nie podałeś liczby!"); }
    }
}