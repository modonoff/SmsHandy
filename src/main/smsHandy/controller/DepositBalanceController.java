package smsHandy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import smsHandy.Main;
import smsHandy.model.SmsHandy;

public class DepositBalanceController {
    @FXML
    private TextField amount;
    private SmsHandy smsHandy;

    private Main main;
    private SmshandyOverviewController controller;
    private Stage dialogStage;

    /**
     * Called when user clicks ok
     */
    @FXML
    private void handleOk() {
        if (amount.getText() != "") {
            Integer deposit = Integer.valueOf(amount.getText());
            if (deposit > 0) {
                smsHandy.getProvider().deposit(smsHandy.getNumber(), deposit);
            }
        }
        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setController(SmshandyOverviewController controller) {
        this.controller = controller;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setSmsHandy(SmsHandy smsHandy) {
        this.smsHandy = smsHandy;
    }
}
