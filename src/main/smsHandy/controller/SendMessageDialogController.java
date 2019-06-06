package smsHandy.controller;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import smsHandy.Main;
import smsHandy.exception.NotEnoughBalanceException;
import smsHandy.model.SmsHandy;

public class SendMessageDialogController {
    @FXML
    private TextField numberToField;
    @FXML
    private TextArea messageContentTextArea;

    private SmsHandy smsHandy;

    private SmshandyOverviewController controller;
    private Main main;
    private Stage dialogStage;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    @FXML
    private void handleSend() throws NotEnoughBalanceException, InterruptedException {
        if (!numberToField.getText().equals("") || !messageContentTextArea.getText().equals("")) {
            smsHandy.sendSms(numberToField.getText(), messageContentTextArea.getText());

            Pane canvas = new Pane();
            Scene scene = new Scene(canvas, 250, 100);
            Text ball = new Text("Sending SMS...");
            ball.relocate(0, 10);

            canvas.getChildren().add(ball);
            Stage stage = new Stage();
            stage.setTitle("Sending...");
            stage.setScene(scene);
            Bounds bounds = canvas.getBoundsInLocal();
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5),
                    new KeyValue(ball.layoutXProperty(), bounds.getMaxX()-ball.getX())));
            timeline.setCycleCount(5);
            timeline.play();
            stage.show();
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished( event -> stage.close() );
            delay.play();
            dialogStage.wait(1000);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("Fields are not filled in");
            alert.setContentText("Please type a number and message.");
            alert.showAndWait();
        }
        dialogStage.close();
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setController(SmshandyOverviewController controller) {
        this.controller = controller;
    }

    public void setSmsHandy(SmsHandy smsHandy) {
        this.smsHandy = smsHandy;
    }
}
