package smsHandy.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import javafx.scene.control.TextField;
import smsHandy.Main;
import smsHandy.model.PrepaidSmsHandy;
import smsHandy.model.Provider;
import smsHandy.model.SmsHandy;
import smsHandy.model.TariffPlanSmsHandy;


public class AddSmsHandyDialogController {
    @FXML
    private TextField smshandyNameField;
    @FXML
    private ChoiceBox typeOfPhone;

    private Main main;
    private SmshandyOverviewController controller;
    private Stage dialogStage;

    /**
     * Initializes ChoiceBox with type of tariffPlans
     */
    @FXML
    private void initialize() {
        typeOfPhone.setItems(FXCollections.observableArrayList("PrepaidSmsHandy", "TariffPlanSmsHandy"));
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        int selected = controller.getProviderTable().getSelectionModel().getSelectedIndex();
        Provider provider = main.getProviders().get(selected);
        if (typeOfPhone.getValue().toString().equalsIgnoreCase("PrepaidSmsHandy") && !smshandyNameField.getText().equals("")) {
            SmsHandy phone = new PrepaidSmsHandy(smshandyNameField.getText(), provider);
        }
        if (typeOfPhone.getValue().toString().equalsIgnoreCase("TariffPlanSmsHandy") && !smshandyNameField.getText().equals("")) {
            SmsHandy phone = new TariffPlanSmsHandy(smshandyNameField.getText(), provider);
        }
        controller.getProviderTable().refresh();
        controller.getSmshandyTable().refresh();
        dialogStage.close();
    }

    /**
     * Called when the user clicks cancel.
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
