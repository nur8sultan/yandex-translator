package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.awt.TextArea;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;
import javax.swing.JProgressBar;

import java.sql.*;
import sample.ControllerEdit;

import static sample.ControllerEdit.toSet;

/**
 * Created by nur8sultan on 20.12.16.
 */
public class ControllerTranslate implements Initializable {
    @FXML
    public ComboBox<String> comboBox1;
    @FXML
    public ComboBox<String> comboBox2;
    @FXML
    public TextField textField;
    @FXML
    public Label result;
    @FXML
    public ListView listView;
    @FXML
    public ProgressIndicator progressIndicator;
    @FXML
    public ProgressBar progressBar;
    @FXML
    public Button transBtn;
    @FXML
    private ControllerEdit editController;

    public static String lang1 = "";
    public static String lang2 = "";
    public static String tr = "";
    public static String str = "";
    public static String string = "";
    public static ObservableList<String> list2 = FXCollections.observableArrayList();
//    public static Stage stage1 = new Stage();

    public static File file = new File("way to your file");




    public static JSONObject obj = new JSONObject();
    public static JSONArray totrans = new JSONArray();
    public static JSONArray trans = new JSONArray();

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:/Users/nur8sultan/IdeaProjects/ProjectQ/test.db"; // change way to database
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public void fill(){
        String sql = "SELECT * FROM Words";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                list2.add(rs.getString("Word1")+rs.getString("Word2"));
//                System.out.println(rs.getString("Word1") +  "\t" +
//                        rs.getString("Word2"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void translate() {
        System.out.println("on the way");
        System.out.println(textField.getText());
        if (comboBox1.getValue().equals("Russian")){
            lang1 = "ru";
        }
        if (comboBox1.getValue().equals("English")){
            lang1 = "en";
        }
        if (comboBox1.getValue().equals("Kazakh")){
            lang1 = "kk";
        }
        if (comboBox1.getValue().equals("Turkish")){
            lang1 = "tr";
        }
        if (comboBox2.getValue().equals("Russian")){
            lang2 = "ru";
        }
        if (comboBox2.getValue().equals("English")){
            lang2 = "en";
        }
        if (comboBox2.getValue().equals("Kazakh")){
            lang2 = "kk";
        }
        if (comboBox2.getValue().equals("Turkish")){
            lang2 = "tr";
        }
        // You can also add another languages, add new if statements and add name of language to combobox
        tr = lang1+"-"+lang2;
        str = textField.getText();
        try {
            string = getJsonStringYandex(tr, str);
            System.out.println(string);
            result.setText(string);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

//        System.out.println(a);
    }

    public void delete(){
        final int selectedIdx = listView.getSelectionModel().getSelectedIndex();
        String qq = listView.getSelectionModel().getSelectedItem().toString();
        String[] q = qq.split(" ");
        System.out.println(q[0]);
        listView.getItems().remove(selectedIdx);
        String sql = "DELETE FROM Words " +
                "WHERE Word1='"+q[0]+"';";
        System.out.println(sql);
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                System.out.println(rs.getString(q[0]));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public void edit(){
        final int selectedIdx = listView.getSelectionModel().getSelectedIndex();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("edit.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(root1, 400, 200));
            stage1.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
        listView.getItems().set(selectedIdx, toSet);

    }
    public void add() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "INSERT INTO Words (Word1,Word2) " +
                    "VALUES ('"+str+"','"+string+"' );";
            list2.add(str+" - "+string);
            totrans.add(str);
            trans.add(string);
            obj.put("words1", totrans);
            obj.put("words2", trans);
            try {

                FileWriter file = new FileWriter("way to file");
                file.write(obj.toJSONString());
                file.flush();
                file.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        System.out.println("Records created successfully");

    }


    ObservableList<String> list = FXCollections.observableArrayList(
            "Kazakh", "Russian", "English", "Turkish"
    );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBox1.setItems(list);
        comboBox2.setItems(list);

        String sql = "SELECT * FROM Words";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            while (rs.next()) {
                list2.add(rs.getString("Word1")+" - "+rs.getString("Word2"));
//                System.out.println(rs.getString("Word1") +  "\t" +
//                        rs.getString("Word2"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        listView.setItems(list2);


    }

    public static String getJsonStringYandex(String trans, String text) throws IOException, ParseException {
        String apiKey = "API Key. Get it on yandex`s site";
        String requestUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?key="
                + apiKey + "&lang=" + trans + "&text=" + text;

        URL url = new URL(requestUrl);
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.connect();
        int rc = httpConnection.getResponseCode();
        System.out.println(rc);

        if (rc == 200) {
            String line = null;
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            StringBuilder strBuilder = new StringBuilder();
            while ((line = buffReader.readLine()) != null) {
                strBuilder.append(line + '\n');
            }
            return getTranslateFromJSON(strBuilder.toString());
        }
        return "Done";
    }

    public static String getTranslateFromJSON(String str) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(str);
        StringBuilder sb = new StringBuilder();
        JSONArray array = (JSONArray) object.get("text");
        for (Object s : array) {
            sb.append(s.toString() + "\n");
        }
        return sb.toString();
    }
}
