package bg.tu_varna.sit.ps.project24621627;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

public class AddController {
    public TextField placeNumber;
    public TextField placeFloor;
    public TextArea notes;
    public ComboBox<SpaceType> placeTypeCombo;
    public RadioButton cardAccessRadio;
    public ToggleGroup accessToggle;
    public RadioButton distanceAccessRadio;
    public CheckBox busyCheckBox;
    public Button saveButton;
    public Button cancelButton;

    private ParkingSpace parkingSpace;
    private boolean saveClicked = false;

    private ParkingSpaceDAO parkingSpaceDAO;
    private int oldNumber; // Запомня оригиналния ключ

    public void initialize() {
        placeTypeCombo.setItems(FXCollections.observableArrayList(SpaceType.values()));
    }


    public void setParkingSpace(ParkingSpace space) {
        this.parkingSpace = space;
        this.oldNumber = space.getNumber(); // Запазваме стария номер при забиване на редакция

        placeNumber.setText(String.valueOf(space.getNumber()));
        placeFloor.setText(String.valueOf(space.getFloor()));
        notes.setText(space.getNotes());
        placeTypeCombo.setValue(space.getType());
        busyCheckBox.setSelected(space.isBusy());

        if ("Карта".equals(space.getAccess())) {
            cardAccessRadio.setSelected(true);
        } else if ("Дистанционно".equals(space.getAccess())) {
            distanceAccessRadio.setSelected(true);
        }
    }

    public void handleNewParkingSpace(ActionEvent actionEvent) {
        if (isInputValid()) {
            int number = Integer.parseInt(placeNumber.getText().trim());
            int floor = Integer.parseInt(placeFloor.getText().trim());
            SpaceType type = placeTypeCombo.getValue();
            String access = ((RadioButton) accessToggle.getSelectedToggle()).getText();
            boolean busy = busyCheckBox.isSelected();
            String noteText = notes.getText();

            if (parkingSpace == null) {
                parkingSpace = new ParkingSpace(number, floor, type, access, busy, noteText);
                parkingSpaceDAO.save(parkingSpace);
            } else {
                parkingSpace.setNumber(number);
                parkingSpace.setFloor(floor);
                parkingSpace.setType(type);
                parkingSpace.setAccess(access);
                parkingSpace.setBusy(busy);
                parkingSpace.setNotes(noteText);

                parkingSpaceDAO.update(parkingSpace, oldNumber);
            }

            saveClicked = true;
            handleCancel();
        }
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    public ParkingSpace getParkingSpace() {
        return parkingSpace;
    }

    public void handleCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (placeNumber.getText() == null || placeNumber.getText().trim().isEmpty()) {
            errorMessage += "Невалиден номер!\n";
        } else {
            try {
                int inputNumber = Integer.parseInt(placeNumber.getText().trim());

                if (parkingSpaceDAO != null) {
                    if (parkingSpace == null) {
                        if (parkingSpaceDAO.existsByNumber(inputNumber)) {
                            errorMessage += "Паркомясто с този номер вече съществува в базата данни!\n";
                        }
                    }
                    else if (parkingSpace != null && inputNumber != oldNumber) {
                        if (parkingSpaceDAO.existsByNumber(inputNumber)) {
                            errorMessage += "Паркомясто с този нов номер вече съществува в базата данни!\n";
                        }
                    }
                }

            } catch (NumberFormatException e) {
                errorMessage += "Номерът трябва да е число!\n";
            }
        }

        if (placeFloor.getText() == null || placeFloor.getText().trim().isEmpty()) {
            errorMessage += "Невалиден етаж!\n";
        } else {
            try {
                Integer.parseInt(placeFloor.getText().trim());
            } catch (NumberFormatException e) {
                errorMessage += "Етажът трябва да е число!\n";
            }
        }

        if (placeTypeCombo.getValue() == null) {
            errorMessage += "Не е избран тип място!\n";
        }
        if (accessToggle.getSelectedToggle() == null) {
            errorMessage += "Не е избран тип достъп!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlert(Alert.AlertType.ERROR, "Грешка при въвеждане", "Моля, коригирайте полетата!", errorMessage);
            return false;
        }
    }

    public void setParkingSpaceDao(ParkingSpaceDAO dao) {
        this.parkingSpaceDAO = dao;
    }

    private Optional<ButtonType> showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait();
    }
}
