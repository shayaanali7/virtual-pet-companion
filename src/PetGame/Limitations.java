package PetGame;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;

/**
 * The {@code Limitations} class handles parental control restrictions
 * by allowing parents to set restricted hours during which the game cannot be played.
 * These settings are persistent across sessions and prevent starting or loading games
 * during blocked times when enabled.
 *
 * @author Ishaan Misra
 */
public class Limitations extends MainController {

    /** Indicates whether the playtime limitation feature is enabled. */
    public static boolean enabled = false;

    /** The hour (in 24-hour format) when playtime restrictions start. */
    public static int startHour = 0; 

    /** The hour (in 24-hour format) when playtime restrictions end. */
    public static int endHour = 0;    

    /** The filename used to persist limitation settings in JSON format. */
    private static final String FILE_NAME = "limitations.json";

    /** ComboBox for selecting the start hour of the playtime block. */
    @FXML
    public ComboBox<String> startHourCombo;

    /** ComboBox for selecting the end hour of the playtime block. */
    @FXML
    public ComboBox<String> endHourCombo;

    /** Checkbox to enable or disable playtime limitations. */
    @FXML
    private CheckBox playtimeCheckbox;

    /**
     * Loads saved settings into the UI controls
     */
    @FXML
    public void initialize() {
        for(int i = 0; i < 24; i++) {
            String period = (i < 12) ? "AM" : "PM";
            int displayHour = (i % 12 == 0) ? 12 : i % 12;
            String label = displayHour + " " + period;
            startHourCombo.getItems().add(label);
            endHourCombo.getItems().add(label);
        }

        /**Set initial values */
        startHourCombo.setValue(format12Hour(startHour));
        endHourCombo.setValue(format12Hour(endHour));
        playtimeCheckbox.setSelected(enabled);
    }

    /**
     * Called when the parent clicks "Save" to update the limitations settings.
     */
    @FXML
    private void handleSaveLimitations() {
        try {
            enabled = playtimeCheckbox.isSelected();
            startHour = convertTo24Hour(startHourCombo.getValue());
            endHour = convertTo24Hour(endHourCombo.getValue());

            save();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Limitations Saved");
            alert.setHeaderText(null);
            alert.setContentText("Parental limitations have been saved.");
            alert.showAndWait();

        } 
        catch (Exception e) {
            e.printStackTrace();
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("Could not save settings.");
            error.setContentText("Please ensure both times are selected.");
            error.showAndWait();
        }
    }

    /**
     * Returns true if the current time falls within the blocked range.
     *
     * @return true if access is currently restricted
     */
    public static boolean isBlockedTimeNow() {
        if(!enabled) return false;

        LocalTime now = LocalTime.now();
        LocalTime start = LocalTime.of(startHour, 0);
        LocalTime end = LocalTime.of(endHour, 0);

        if(startHour < endHour) {
            return now.isAfter(start) && now.isBefore(end);
        } 
        else {
            return now.isAfter(start) || now.isBefore(end);
        }
    }

    /**
    * Converts a 12-hour format time string (e.g., "3 PM", "12 AM") to 24-hour format.
    *
    * @param time the time string in the format "h AM" or "h PM"
    * @return the corresponding hour in 24-hour format (0–23)
    */
    private int convertTo24Hour(String time) {
        String[] parts = time.split(" ");
        int hour = Integer.parseInt(parts[0]);
        String meridian = parts[1];

        if(meridian.equals("AM")) {
            return (hour == 12) ? 0 : hour;
        } 
        else {
            return (hour == 12) ? 12 : hour + 12;
        }
    }

    /**
    * Converts a 24 hour format hour into a 12 hour format string with AM/PM.
    *
    * @param hour24 the hour in 24-hour format (0–23)
    * @return the formatted time as a string in 12-hour format (e.g., "3 PM", "12 AM")
    */
    private String format12Hour(int hour24) {
        String period = (hour24 < 12) ? "AM" : "PM";
        int displayHour = (hour24 % 12 == 0) ? 12 : hour24 % 12;
        return displayHour + " " + period;
    }

    /**
     * Loads the saved limitation settings from disk.
     */
    public static void load() {
        try(FileReader reader = new FileReader(FILE_NAME)) {
            Gson gson = new Gson();
            LimitationsData data = gson.fromJson(reader, LimitationsData.class);
            enabled = data.enabled;
            startHour = data.startHour;
            endHour = data.endHour;
        } 
        catch (IOException e) {
            /**First run so file may not exist */
        }
    }

    /**
     * Saves the current limitation settings to disk.
     */
    public static void save() {
        try(FileWriter writer = new FileWriter(FILE_NAME)) {
            Gson gson = new Gson();
            gson.toJson(new LimitationsData(enabled, startHour, endHour), writer);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Private data holder for serialization to JSON.
     */
    private static class LimitationsData {
        boolean enabled;
        int startHour;
        int endHour;

        LimitationsData(boolean enabled, int startHour, int endHour) {
            this.enabled = enabled;
            this.startHour = startHour;
            this.endHour = endHour;
        }
    }

    /**
     * Called when the user clicks "Back" to return to the Parental Controls screen.
     */
    @FXML
    private void handleCancel(javafx.event.ActionEvent event) throws IOException {
        switchToParentalControls(event);
    }
}
