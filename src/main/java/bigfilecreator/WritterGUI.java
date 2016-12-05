package bigfilecreator;

import java.io.File;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.text.Text;

public class WritterGUI extends Thread
{
    private final Text status;
    private final ObservableList<Node> btnList;
    private final ObservableList<Node> tfList;
    private final FileSettings fs;

    public WritterGUI(FileSettings settings, Text status, ObservableList<Node> btnList, ObservableList<Node> tfList)
    {
        this.fs = settings;
        this.status = status;
        this.btnList = btnList;
        this.tfList = tfList;
    }
    
    @Override
    public void run()
    {
        ObservableList<Node> temp;
        temp = tfList.filtered(tf -> tf.isDisabled() == false);
        temp.forEach(tf -> tf.setDisable(true));
        btnList.forEach(b -> b.setDisable(true));
        
        File file = new File(fs.getAllName());
        
        long currentSize = -1;
        long size = (long)(1024L*1024L*fs.getSize());
        while(currentSize < size)
        {
            double d = currentSize*100;
            d /= size;
            status.setText(String.format("%.2f", d) + "%");
            currentSize = file.length();
        }
        status.setText("100%");
        
        temp.forEach(tf -> tf.setDisable(false));
        btnList.forEach(b -> b.setDisable(false));
    }
}