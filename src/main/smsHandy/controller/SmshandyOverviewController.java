package smsHandy.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import smsHandy.Main;
import smsHandy.model.*;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;

public class SmshandyOverviewController {
    @FXML
    private TableView<Provider> providerTable;
    @FXML
    private TableColumn<Provider, String> nameColumn;
    @FXML
    private TableColumn<Provider, String> quantityColumn;
    @FXML
    private TableView<SmsHandy> smshandyTable;
    @FXML
    private TableColumn<SmsHandy, String> numberColumn;
    @FXML
    private Label numberOfSmshandy;
    @FXML
    private Label typeOfSmshandy;
    @FXML
    private Label restOfBalance;
    @FXML
    private TableView<Message> sentMessageTable;
    @FXML
    private TableColumn<Message, String> toColumn;
    @FXML
    private TableColumn<Message, String> contentColumn1;
    @FXML
    private TableColumn<Message, String> dateColumn1;
    @FXML
    private TableView<Message> receivedMessageTable;
    @FXML
    private TableColumn<Message, String> fromColumn;
    @FXML
    private TableColumn<Message, String> contentColumn2;
    @FXML
    private TableColumn<Message, String> dateColumn2;

    private Main main;

    public SmshandyOverviewController() {}

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityOfPhones());
        showSmshandyOfProvider(null);
        providerTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showSmshandyOfProvider(newValue));
        showSmshandyDetails(null);
        smshandyTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showSmshandyDetails(newValue));
    }

    public void setMain(Main main) {
        this.main = main;
        providerTable.setItems(main.getProviders());
    }

    /**
     * Fills all columns of table to show details about provider's phones.
     * If the specified provider is null, all text fields are cleared.
     *
     * @param provider the provider or null
     */
    private void showSmshandyOfProvider(Provider provider) {
        if (provider != null) {
            ObservableList<SmsHandy> phones = FXCollections.observableArrayList(provider.getSubscriber().values());
            smshandyTable.setItems(phones);
            numberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty());
        }
    }

    private void showSmshandyDetails(SmsHandy smsHandy) {
        if (smsHandy != null) {
            numberOfSmshandy.setText(smsHandy.getNumber());
            System.out.println(smsHandy.getSent());
            typeOfSmshandy.setText(smsHandy.getClass().getName().substring(15));
            sentMessageTable.setItems(FXCollections.observableArrayList(smsHandy.getSent()));
            toColumn.setCellValueFactory(cellData -> cellData.getValue().toProperty());
            contentColumn1.setCellValueFactory(cellData -> cellData.getValue().contentProperty());
            Format formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            dateColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(formatter.format(cellData.getValue().getDate())));
            receivedMessageTable.setItems(FXCollections.observableArrayList(smsHandy.getReceived()));
            fromColumn.setCellValueFactory(cellData -> cellData.getValue().fromProperty());
            contentColumn2.setCellValueFactory(cellData -> cellData.getValue().contentProperty());
            dateColumn2.setCellValueFactory(cellData -> new SimpleStringProperty(formatter.format(cellData.getValue().getDate())));
            if (smsHandy instanceof PrepaidSmsHandy) {
                restOfBalance.setText(String.valueOf(smsHandy.getProvider().getCreditForSmsHandy(smsHandy.getNumber())) + " Euro");
            }
            if (smsHandy instanceof TariffPlanSmsHandy) {
                restOfBalance.setText(String.valueOf(((TariffPlanSmsHandy) smsHandy).getRemainingFreeSms()) + " Free SMS");
            }
        } else {
            numberOfSmshandy.setText("");
            typeOfSmshandy.setText("");
            restOfBalance.setText("");
        }
    }

    @FXML
    private void handleAddProvider() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/AddProviderDialog.fxml"));
            AnchorPane layout = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("New Provider");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(layout);
            dialogStage.setScene(scene);

            AddProviderDialogController controller = loader.getController();
            controller.setMain(main);
            controller.setDialogStage(dialogStage);
            controller.setController(this);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddSmsHandy() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/AddSmsHandyDialog.fxml"));
            AnchorPane layout = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("New Phone");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(layout);
            dialogStage.setScene(scene);

            AddSmsHandyDialogController controller = loader.getController();
            controller.setMain(main);
            controller.setDialogStage(dialogStage);
            controller.setController(this);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteProvider() {
        int selectedIndex = providerTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Provider provider = providerTable.getItems().get(selectedIndex);
            Provider.getProviderList().remove(provider);
            providerTable.getItems().remove(provider);
            smshandyTable.getItems().removeAll(provider.getSubscriber().values());
            smshandyTable.refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Provider Selected");
            alert.setContentText("Please select a provider in the table.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleDeleteSmsHandy() {
        int selectedIndex = smshandyTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            SmsHandy phone = smshandyTable.getItems().get(selectedIndex);
            smshandyTable.getItems().remove(selectedIndex);
            phone.getProvider().unregister(phone);
            providerTable.refresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Phone Selected");
            alert.setContentText("Please select a phone in the table.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleEditSmsHandy() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/EditSmsHandyDialog.fxml"));
            AnchorPane layout = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Change provider");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(layout);
            dialogStage.setScene(scene);

            EditSmsHandyDialogController controller = loader.getController();
            controller.setMain(main);
            controller.setDialogStage(dialogStage);
            controller.setController(this);
            controller.setSmsHandy(smshandyTable.getItems().get(smshandyTable.getSelectionModel().getSelectedIndex()));

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSendSms() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/SendMessageDialog.fxml"));
            AnchorPane layout = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Send SMS");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(layout);
            dialogStage.setScene(scene);

            SendMessageDialogController controller = loader.getController();
            controller.setMain(main);
            controller.setDialogStage(dialogStage);
            controller.setController(this);
            controller.setSmsHandy(smshandyTable.getItems().get(smshandyTable.getSelectionModel().getSelectedIndex()));

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDepositSmsHandy() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/DepositBalanceDialog.fxml"));
            AnchorPane layout = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Deposit");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(layout);
            dialogStage.setScene(scene);

            DepositBalanceController controller = loader.getController();
            controller.setMain(main);
            controller.setDialogStage(dialogStage);
            controller.setController(this);
            controller.setSmsHandy(smshandyTable.getItems().get(smshandyTable.getSelectionModel().getSelectedIndex()));

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TableView<Provider> getProviderTable() {
        return providerTable;
    }

    public TableView<SmsHandy> getSmshandyTable() {
        return smshandyTable;
    }
}
