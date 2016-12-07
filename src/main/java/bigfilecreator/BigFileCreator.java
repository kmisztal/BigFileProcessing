package bigfilecreator;

import bigfilecreator.Validate.*;
import java.io.File;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Konrad Mędra. Programowanie w Java 2016/2017, UJ.
 */
public class BigFileCreator extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        GridBuilder gb = new GridBuilder(4);
        
        //Line 1.       
        gb.add(new Text("Wybierz ustawienia swojego pliku:"), 4);
        
        //Line 2.
        int minWidthMainCol = 350;
        gb.add(new Label("Nazwa pliku:"));
        gb.add(new Text());
        gb.add(new TextField());
        gb.add(null);
        
        //Line 3.
        gb.add(new Text(), 4);

        //Line 4.
        gb.add(new Label("Lokalizacja pliku:"));
        gb.add(new Text());
        gb.add(new TextField());
        gb.add(new Button("Wybierz..."));
        
        //Line 5.
        gb.add(new Text(), 4);
        
        //Line 6.
        gb.add(new Label("Wielkość pliku:"));
        gb.add(new Text());
        gb.add(new TextField());
        gb.add(new Text("~ MB"));
        
        //Line 7.
        gb.add(new Text(), 4);
        
        //Line 8.
        gb.add(new Label("Struktura pliku:"));
        gb.add(new Text());
        gb.add(new TextField());
        gb.add(new Text());
        
        //Line 9.
        gb.add(new Text(), 4);
        
        //Line 10.
        gb.add(new Label("Typy stanowe:"));
        gb.add(new Text());
        gb.add(new TextField());
        gb.add(new Text());
        
        //Line 11.
        gb.add(new Text(), 4);
        
        //Line 12.
        gb.add(new Label("Nazwy kolumn:"));
        gb.add(new Text());
        gb.add(new TextField());
        gb.add(new Text());
        
        //Line 13.
        gb.add(new Text(), 4);
        
        //Line 14.
        gb.add(null, 2);
        gb.add(new Button("Buduj plik"));
        gb.add(new Text());
        
        gb.add(new Text(), 4);

        //All.
        FileSettings fs = new FileSettings();
        ObservableList<Node> allList = gb.getGrid().getChildren();
        ObservableList<Node> tList = allList.filtered(n -> (n instanceof Text));
        ObservableList<Node> tfList = allList.filtered(n -> (n instanceof TextField));
        ObservableList<Node> btnList = allList.filtered(n -> (n instanceof Button));    
 
        Text t;
        TextField tf;
        Button btn;
        
        //Label-----------------------------
        
        t = (Text)tList.get(0);
        t.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        //Name-----------------------------
        
        ((Text)tList.get(1)).setFill(Color.GREEN);
        ((Text)tList.get(2)).setFill(Color.FIREBRICK);
        tf = (TextField)tfList.get(0);
        tf.setPromptText("Maksymalnie " + ValidateSettings.maxNameLength + " znaków z rozszerzeniem");
        tf.setMinWidth(minWidthMainCol);
                
        tf.textProperty().addListener
        (
            (observable) ->
            {
                NameValidator.isOK((TextField)tfList.get(0), (Text)tList.get(1), (Text)tList.get(2), (TextField)tfList.get(1), fs);
                setButton(fs, (Button)btnList.get(1));
            }
        );
        
        tf.focusedProperty().addListener
        (
            (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) ->
                    NameValidator.isWrong(newValue, (TextField)tfList.get(0), (Text)tList.get(1), (Text)tList.get(2), (TextField)tfList.get(1), fs)
        );
        
        //Path-----------------------------
        
        ((Text)tList.get(3)).setFill(Color.GREEN);
        ((Text)tList.get(4)).setFill(Color.FIREBRICK);
        tf = (TextField)tfList.get(1);
        tf.setDisable(true);
        btn = (Button)btnList.get(0);
        
        btn.setOnAction
        (
            (ActionEvent e) ->
            {
                PathValidator.isOK((TextField)tfList.get(1), (Text)tList.get(3), (Text)tList.get(4), primaryStage, fs);
                if(fs.getSize() > 0) SizeValidator.isOK((TextField)tfList.get(2), (Text)tList.get(5), (Text)tList.get(7), fs);
                setButton(fs, (Button)btnList.get(1));
            }
        );
        
        //Size-----------------------------
        
        ((Text)tList.get(5)).setFill(Color.GREEN);
        ((Text)tList.get(7)).setFill(Color.FIREBRICK);
        tf = (TextField)tfList.get(2);
        tf.setPromptText("np. 10,0 lub 10.0, lub 10");
        
        tf.textProperty().addListener
        (
            (observable) ->
            {
                SizeValidator.isOK((TextField)tfList.get(2), (Text)tList.get(5), (Text)tList.get(7), fs);
                setButton(fs, (Button)btnList.get(1));
            }
        );
        
        tf.focusedProperty().addListener
        (
            (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) ->
                    SizeValidator.isWrong(newValue, (TextField)tfList.get(2), (Text)tList.get(7))
        );
        
        //Structure-----------------------------
        
        ((Text)tList.get(8)).setFill(Color.GREEN);
        ((Text)tList.get(9)).setFill(Color.BLUE);
        ((Text)tList.get(10)).setFill(Color.FIREBRICK);
        tf = (TextField)tfList.get(3);
        tf.setPromptText("np. \"int, int\" LUB \"int; int\", LUB \"int t int\"");
        tf.setFont(Font.font(12));
        tf.setMinHeight(tf.getPrefHeight());
        
        tf.textProperty().addListener
        (
            (observable) ->
            {
                StructureValidator.isOK((TextField)tfList.get(3), (Text)tList.get(8), (Text)tList.get(10), (Text)tList.get(9), fs);
                StateValidator.isOK((TextField)tfList.get(4), (Text)tList.get(11), (Text)tList.get(13), (Text)tList.get(12), fs);
                HeaderValidator.isOK((TextField)tfList.get(5), (Text)tList.get(14), (Text)tList.get(16), (Text)tList.get(15), fs);
                setButton(fs, (Button)btnList.get(1));
            }
        );
        
        tf.focusedProperty().addListener
        (
            (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) ->
            {
                TextField temp;
                StructureValidator.isWrong(newValue, (TextField)tfList.get(3), (Text)tList.get(8), (Text)tList.get(10), (Text)tList.get(9), fs);
                temp = (TextField)tfList.get(4);
                if(fs.getStatesCount() > 0 && !temp.getText().equals("")) { StateValidator.isWrong(newValue, temp, (Text)tList.get(11), (Text)tList.get(13), (Text)tList.get(12), fs); }
                temp = (TextField)tfList.get(5);
                if(!temp.getText().equals("")) { HeaderValidator.isWrong(newValue, temp, (Text)tList.get(14), (Text)tList.get(16), (Text)tList.get(15), fs); }
            }
        );
        
        //State-----------------------------
        
        ((Text)tList.get(11)).setFill(Color.GREEN);
        ((Text)tList.get(12)).setFill(Color.BLUE);
        ((Text)tList.get(13)).setFill(Color.FIREBRICK);
        tf = (TextField)tfList.get(4);
        tf.setFont(Font.font(12));
        tf.setDisable(true);
        tf.setMinHeight(tf.getPrefHeight());
        tf.setPromptText("Tyle ile typów state, np.: (Kobieta|Mężczyzna), (A|B|C|D|Żadna)");
        
        tf.textProperty().addListener
        (
            (observable) ->
            {
                StateValidator.isOK((TextField)tfList.get(4), (Text)tList.get(11), (Text)tList.get(13), (Text)tList.get(12), fs);
                setButton(fs, (Button)btnList.get(1));
            }
        );
        
        tf.focusedProperty().addListener
        (
            (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) ->
                    StateValidator.isWrong(newValue, (TextField)tfList.get(4), (Text)tList.get(11), (Text)tList.get(13), (Text)tList.get(12), fs)
        );
        
        //Header-----------------------------
        
        ((Text)tList.get(14)).setFill(Color.GREEN);
        ((Text)tList.get(15)).setFill(Color.BLUE);
        ((Text)tList.get(16)).setFill(Color.FIREBRICK);
        
        tf = (TextField)tfList.get(5);
        tf.setFont(Font.font(12));
        tf.setMinHeight(tf.getPrefHeight());
        tf.setPromptText("Puste lub tyle ile kolumn, np.: Nazwisko, Wiek, Płeć, , , Płaca");
        
        tf.textProperty().addListener
        (
            (observable) ->
            {
                HeaderValidator.isOK((TextField)tfList.get(5), (Text)tList.get(14), (Text)tList.get(16), (Text)tList.get(15), fs);
                setButton(fs, (Button)btnList.get(1));
            }
        );
        
        tf.focusedProperty().addListener
        (
            (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) ->
            {
                TextField temp = (TextField)tfList.get(5);
                if(!temp.getText().equals("")) { HeaderValidator.isWrong(newValue, temp, (Text)tList.get(14), (Text)tList.get(16), (Text)tList.get(15), fs); }
            }
        );
        
        //Build-----------------------------

        ((Text)tList.get(17)).setFill(Color.GREEN);
        ((Text)tList.get(18)).setFill(Color.FIREBRICK);
        btn = (Button)btnList.get(1);
        btn.setDisable(true);
        btn.setMaxWidth(minWidthMainCol);
        
        btn.setOnAction(e -> {
            if(!fs.isReady())
            {
                ((Text)tList.get(17)).setText("");
                ((Text)tList.get(18)).setText("Nie można zbudować pliku, wypełnij poprawnie wszystkie pola.");
            }
            else
            {
                ((Text)tList.get(17)).setText("");
                ((Text)tList.get(18)).setText("");

                String path = fs.getAllName();
                File file = new File(path);
                if(file.exists()) { file.delete(); }
                Writer t1 = new Writer(fs);
                WriterGUI t2 = new WriterGUI(fs, (Text)tList.get(17), btnList, tfList);
                t1.start();
                t2.start();
            }
        });
        
        //-----------------------------
                
        GridPane gp = gb.getGrid();
        gp.getColumnConstraints().add(0, new ColumnConstraints());
        gp.getColumnConstraints().add(1, new ColumnConstraints(30));
        for(Node n : tfList) { ((TextField)n).setMinHeight(40); }
        gp.setPrefHeight(700);
        gp.setMinHeight(700);
        
        Scene scene = new Scene(gp, 750, 750);
        primaryStage.setScene(scene);
        primaryStage.setTitle("BigFileCreator");
        primaryStage.setX(250);
        primaryStage.setY(150);
        primaryStage.show();
                
        primaryStage.setOnCloseRequest
        (
            (WindowEvent e) ->
            {
                Platform.exit();
                System.exit(0);
            }
        );
    }
    
    private void setButton(FileSettings fs, Button btn)
    {
        if(fs.isReady()) { btn.setDisable(false); }
        else { btn.setDisable(true); }
    }

    public static void main(String[] args) { launch(args); }   
}
