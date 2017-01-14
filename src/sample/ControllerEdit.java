package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;

import static sample.ControllerTranslate.lang1;
//import static sample.ControllerTranslate.listView;
//import static sample.ControllerTranslate.selectedIdx;

/**
 * Created by nur8sultan on 21.12.16.
 */
public class ControllerEdit {

    @FXML
    public TextField textField1;
    @FXML
    public TextField textField2;
    @FXML
    public static Button cancelBtn;


    public static String word11 = "";
    public static String word22= "";
    public void edit(){
        if (textField1.getText() != ""){
            word11 = textField1.getText();
        }
        if (textField2.getText() != ""){
            word22 = textField2.getText();
        }
        toSet = word11+" - "+word22;
        System.out.println(toSet);
//        listView.getItems().set(selectedIdx, toSet);
    }
    public static String toSet = word11+" - "+word22;
}
