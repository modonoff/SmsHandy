package smsHandy.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import smsHandy.Main;
import smsHandy.model.Provider;
import smsHandy.model.SmsHandy;

public class EditSmsHandyDialogController {
    @FXML
    private ChoiceBox<String> providers;

    private SmsHandy smsHandy;
    private Main main;
    private SmshandyOverviewController controller;
    private Stage dialogStage;

    @FXML
    private void initialize() {
        providers.setItems(FXCollections.observableArrayList("Provider-1", "Provider-2", "Provider-3", "Provider-4", "Provider-5", "Provider-6", "Provider-7"));
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void handleOk() {
        for (Provider provider : main.getProviders()) {
            if (provider.getName().equalsIgnoreCase(providers.getValue())) {
                provider.register(smsHandy);
                smsHandy.getProvider().unregister(smsHandy);
                controller.getProviderTable().refresh();
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

    public void setSmsHandy(SmsHandy smsHandy) {
        this.smsHandy = smsHandy;
    }
}
