package smsHandy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import smsHandy.Main;
import smsHandy.model.Provider;

public class AddProviderDialogController {
    @FXML
    private TextField providerNameField;
    private Main main;
    private SmshandyOverviewController controller;
    private Stage dialogStage;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (!providerNameField.getText().equals("")) {
            Provider provider = new Provider(providerNameField.getText());
            main.getProviders().add(provider);
        }
        controller.getProviderTable().refresh();
        dialogStage.close();
    }

    /**
     * Called when the user clicks Cancel
     */
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
}
