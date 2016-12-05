package bigfilecreator.Validate;

import bigfilecreator.FileSettings;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class HeaderValidator
{
    public static void isOK(TextField tf, Text ok, Text wrong, Text count, FileSettings fs)
    {
        int cc = fs.getColumnsCount();
        String value = tf.getText().trim();
        String[] heads = value.split(",");

        if(value.equals(""))
        {
            fs.setHeader("");
            ok.setText("OK!");
            wrong.setText("");
            count.setText("0/" + cc);
            count.setFill(Color.BLUE);
        }
        else if(cc == heads.length)
        {
            fs.setHeader(value);
            ok.setText("OK!");
            wrong.setText("");
            count.setText(heads.length + "/" + cc);
            count.setFill(Color.BLUE);
        }
        else
        {
            fs.setHeader("");
            ok.setText("");
            count.setText(heads.length + "/" + cc);
            count.setFill(Color.FIREBRICK);
        }
    }
    
    public static void isWrong(boolean hasFocus, TextField tf, Text ok, Text wrong, Text count, FileSettings fs)
    {
        if(!hasFocus)
        {
            int cc = fs.getColumnsCount();
            String value = tf.getText().trim();
            String[] heads = value.split(",");

            if(cc != heads.length)
            {
                fs.setHeader("");
                ok.setText("");
                wrong.setText("Musisz pozostawić to pole puste lub podać tyle nazw, ile zadeklarowano kolumn,\nchoć wybrane mogą pozostać puste np.: Nazwisko, Wiek, Płeć, , , Płaca");
                count.setText(heads.length + "/" + cc);
                count.setFill(Color.FIREBRICK);
            }
        }
    }
}