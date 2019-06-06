package smsHandy;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import smsHandy.controller.SmshandyOverviewController;
import smsHandy.exception.NotEnoughBalanceException;
import smsHandy.model.*;


import java.io.IOException;
import java.util.Date;

public class Main extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Provider> providers = FXCollections.observableArrayList();

    /**
     * Initializes new providers, phones
     */
    public Main() {
        Provider provider1 = new Provider("Provider-1");
        Provider provider2 = new Provider("Provider-2");
        Provider provider3 = new Provider("Provider-3");
        Provider provider4 = new Provider("Provider-4");
        Provider provider5 = new Provider("Provider-5");
        Provider provider6 = new Provider("Provider-6");
        Provider provider7 = new Provider("Provider-7");
        SmsHandy phone1 = new PrepaidSmsHandy("13", provider3);
        SmsHandy phone2 = new PrepaidSmsHandy("23", provider3);
        SmsHandy phone3 = new PrepaidSmsHandy("33", provider3);
        SmsHandy phone4 = new PrepaidSmsHandy("41", provider1);
        SmsHandy phone5 = new TariffPlanSmsHandy("57", provider7);
        SmsHandy phone6 = new TariffPlanSmsHandy("66", provider6);
        SmsHandy phone7 = new TariffPlanSmsHandy("76", provider6);
        SmsHandy phone8 = new TariffPlanSmsHandy("85", provider5);
        providers.add(provider1);
        providers.add(provider2);
        providers.add(provider3);
        providers.add(provider4);
        providers.add(provider5);
        providers.add(provider6);
        providers.add(provider7);
        phone1.getSent().add(new Message("Hello!", "13", "23", new Date()));
        phone1.getReceived().add(new Message("How are you?", "23", "13", new Date()));
        try {
            phone1.sendSms("23", "Test");
            phone2.sendSms("33", "Do you go to the cinema?");
            phone3.sendSms("41", "Test");
            phone4.sendSms("33", "Test");
            phone5.sendSms("66", "Test");
        } catch (NotEnoughBalanceException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Provider> getProviders() {
        return providers;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SmsHandy Project");

        initRootLayout();
        showSmshandyOverview();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSmshandyOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/SmshandyOverview.fxml"));
            AnchorPane smshandyOverview = loader.load();

            rootLayout.setCenter(smshandyOverview);

            SmshandyOverviewController controller = loader.getController();
            controller.setMain(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
