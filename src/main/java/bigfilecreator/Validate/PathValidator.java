package bigfilecreator.Validate;

import bigfilecreator.FileSettings;
import java.io.File;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javax.swing.filechooser.FileSystemView;

public class PathValidator
{
    private static final DirectoryChooser dirChooser = new DirectoryChooser();
    private static File homePath = FileSystemView.getFileSystemView().getHomeDirectory();
    
    public static void isOK(TextField tf, Text ok, Text wrong, Stage s, FileSettings fs)
    {
        dirChooser.setTitle("Lokalizacja pliku");
        dirChooser.setInitialDirectory(homePath);
        File f = dirChooser.showDialog(s);

        if(ValidateSettings.checkPath(f))
        {
            homePath = f;
            fs.setPath(f);
            ok.setText("OK!");
            wrong.setText("");
            tf.setText(fs.getAllName());
        }
        else if(fs.getPath() == null)
        {
            fs.setPath(null);
            ok.setText("");
            wrong.setText("Nie wybrano lokalizacji.");
            tf.setText(fs.getAllName());
        }
    }
}