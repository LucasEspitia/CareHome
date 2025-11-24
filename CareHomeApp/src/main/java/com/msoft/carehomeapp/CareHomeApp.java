package com.msoft.carehomeapp;

import com.msoft.carehomeapp.business.services.WellnessNotificationScheduler;
import com.msoft.carehomeapp.data.*;
import com.msoft.carehomeapp.data.implementation.*;
import com.msoft.carehomeapp.business.managers.*;
import com.msoft.carehomeapp.business.services.*;
import com.msoft.carehomeapp.ui.SceneSwitcher;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author lucas
 */
public class CareHomeApp extends Application  {

    @Override
    public void start(Stage stage) throws Exception {
        // DAOs
        IRecordsDAO recordsDAO = RecordsDAOImpl.getInstance();
        IPreferencesDAO prefsDAO = PreferencesDAOImpl.getInstance();
        
        //Bussiness Services
        MusicService musicService = new MusicService();
        LightningService lightningService = new LightningService();
        NotificationService notificationService = new NotificationService();
        WellnessNotificationScheduler scheduler = new WellnessNotificationScheduler(notificationService);

        
        //Managers
        PreferencesManager preferencesManager = new PreferencesManager(prefsDAO);
        RecordsManager recordsManager = new RecordsManager(recordsDAO);
        DeviceTestManager deviceTestManager = new DeviceTestManager();
        DeviceManager deviceManager = new DeviceManager(lightningService, musicService, deviceTestManager);
        NotificationsManager notificationsManager = new NotificationsManager(notificationService, scheduler);
        
        EmotionManager emotionManager =
                new EmotionManager(
                        recordsManager, preferencesManager, deviceManager);
        
        
        AppContext.init(
                emotionManager,
                preferencesManager,
                recordsManager,
                notificationsManager,
                deviceManager
        );
           
        SceneSwitcher.setMainStage(stage);
        SceneSwitcher.swichTo("HomeView.fxml");
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);       
    }
}