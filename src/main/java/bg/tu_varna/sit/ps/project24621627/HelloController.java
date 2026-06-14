package bg.tu_varna.sit.ps.project24621627;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class HelloController {

    @FXML
    public TableView<ParkingSpace> parkingTable;
    @FXML
    public TableColumn colNumber;
    @FXML
    public TableColumn colFloor;
    @FXML
    public TableColumn colType;
    @FXML
    public TableColumn colAccess;
    @FXML
    public TableColumn colBusy;
    @FXML
    public Label selectedPlaceInfo;

    private final ParkingSpaceDAO parkingSpaceDAO = new ParkingSpaceDAO();

    private final ObservableList<ParkingSpace> parkingData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        DatabaseInitializer.initDatabase();
        colNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        colFloor.setCellValueFactory(new PropertyValueFactory<>("floor"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colAccess.setCellValueFactory(new PropertyValueFactory<>("access"));
        colBusy.setCellValueFactory(new PropertyValueFactory<>("busy"));

        parkingTable.setItems(parkingData);

        parkingTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedPlaceInfo.setText("Бележки: " + newValue.getNotes());
            } else {
                selectedPlaceInfo.setText("Информация за паркомясто");
            }
        });

        loadDevelopersFromDatabase();
    }

    public void handleNewParkingSpace(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("add-view.fxml"));
        Parent root = loader.load();

        AddController addController = loader.getController();
        addController.setParkingSpaceDao(parkingSpaceDAO); // Подаваме DAO обекта

        Stage dialogStage = new Stage();
        dialogStage.setScene(new Scene(root));
        dialogStage.setTitle("Добавяне на паркомясто");
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.showAndWait();

        if (addController.isSaveClicked()) {
            parkingData.add(addController.getParkingSpace());
            showAlert(Alert.AlertType.INFORMATION, "Успешно", null, "Данните са записани успешно в базата.");
        }
    }

    public void handleExit(ActionEvent actionEvent) {
        Optional<ButtonType> result = showAlert(Alert.AlertType.CONFIRMATION, "Изход", "Затваряне на приложението",
                "Сигурни ли сте, че искате да затворите приложението?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage stage = (Stage) parkingTable.getScene().getWindow();
            stage.close();
        }

    }

    @FXML
    private void handleEditParkingSpace() throws IOException {
        ParkingSpace selectedSpace = parkingTable.getSelectionModel().getSelectedItem();

        if (selectedSpace == null) {
            showAlert(Alert.AlertType.ERROR, "Грешка", "Не сте избрали запис", "Моля изберете ред за редактиране");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("edit-view.fxml"));
        BorderPane root = loader.load();

        AddController formController = loader.getController();
        formController.setParkingSpace(selectedSpace);
        formController.setParkingSpaceDao(parkingSpaceDAO);

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Редактиране на паркомясто");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(parkingTable.getScene().getWindow());
        dialogStage.setScene(new Scene(root));
        dialogStage.showAndWait();

        if (formController.isSaveClicked()) {
            parkingTable.refresh();
            showAlert(Alert.AlertType.INFORMATION, "Успешно", null, "Данните са редактирани успешно.");
        }

    }

    public void handleDelete(ActionEvent actionEvent) {
        ParkingSpace selectedSpace = parkingTable.getSelectionModel().getSelectedItem();
        if (selectedSpace != null) {
            Optional<ButtonType> result = showAlert(Alert.AlertType.CONFIRMATION,"Потвърждение за изтриване", null, "Сигурни ли сте, че искате да изтриете избраното паркомясто?");
            if (result.isPresent() && result.get() == ButtonType.OK) {
                parkingSpaceDAO.deleteByNumber(selectedSpace.getNumber());
                parkingData.remove(selectedSpace);
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Грешка", "Не сте избрали запис", "Моля изберете ред за изтриване");
        }
    }

    public void handleAbout(ActionEvent actionEvent){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("about-view.fxml"));
            BorderPane root = fxmlLoader.load();

            Stage aboutStage = new Stage();
            aboutStage.setTitle("Относно");
            aboutStage.initModality(Modality.WINDOW_MODAL);
            aboutStage.initOwner(parkingTable.getScene().getWindow());

            Scene scene = new Scene(root);
            aboutStage.setScene(scene);
            aboutStage.showAndWait();

        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Грешка", "Неуспешно зареждане", e.getMessage());
        }
    }


    private Optional<ButtonType> showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait();
    }

    private void loadDevelopersFromDatabase() {
        parkingData.clear();
        parkingData.addAll(parkingSpaceDAO.findAll());
    }


}
