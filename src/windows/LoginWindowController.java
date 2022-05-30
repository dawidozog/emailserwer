/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package windows;

import core.EmailService;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class LoginWindowController implements Initializable {

    static String attachPath;

    @FXML
    private TextArea recipients;

    @FXML
    private TextField topic;

    @FXML
    private TextArea content;
    
    public static String user = "user"; // Add user (gmail preferred)
    
    public static String password = "password"; // Add password

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
    }

    @FXML
    void addAttachment(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile.exists()) {
            attachPath = selectedFile.getAbsolutePath();
        }

    }

    @FXML
    void loginActionPerformed(ActionEvent event) {

        String[] splitedContent = content.getText().split("\n");

        StringBuilder builder = new StringBuilder();

        for (String splited1 : splitedContent) {

            builder.append(splited1).append("<br />");
        }

        String[] splited = recipients.getText().split("\n");
        for (String splited1 : splited) {
            odpalNaWatkuSendMail(splited1, builder.toString(), topic.getText(), attachPath);
        }

    }

    public static void odpalNaWatkuSendMail(String recepient, String msg, String topic, String path) {
        new Thread(new Runnable() {
            public void run() {

                EmailService sender = new EmailService("smtp.gmail.com", 587, user, password, recepient, topic, msg, path);
                try {
                    sender.sendMail();
                } catch (Exception ex) {

                }
            }
        }).start();

    }
}
