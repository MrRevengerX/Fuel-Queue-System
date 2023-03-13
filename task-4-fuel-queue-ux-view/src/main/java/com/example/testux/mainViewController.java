package com.example.testux;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class mainViewController {
    @FXML
    private TextField searchField;
    @FXML
    private TableColumn<QueuePassenger, String> firstNameCol;

    @FXML
    private TableColumn<QueuePassenger, String> lastNameCol;

    @FXML
    private TableColumn<QueuePassenger, Integer> litreCol;

    @FXML
    private TableColumn<QueuePassenger, String> queueCol;

    @FXML
    private TableView<QueuePassenger> tableView;

    @FXML
    private TableColumn<QueuePassenger, String> vehicleNoCol;

    static File logFile = new File("fuel-queue-log.txt");

    @FXML
    public void initialize() {

        firstNameCol.setCellValueFactory(new PropertyValueFactory<QueuePassenger, String>("passengerFirstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<QueuePassenger, String>("passengerLastName"));
        vehicleNoCol.setCellValueFactory(new PropertyValueFactory<QueuePassenger, String>("vehicleNumber"));
        litreCol.setCellValueFactory(new PropertyValueFactory<QueuePassenger, Integer>("noOfLiters"));
        queueCol.setCellValueFactory(new PropertyValueFactory<QueuePassenger, String>("queueName"));

        ObservableList<QueuePassenger> data = FXCollections.observableArrayList();
        System.out.println(" " + Main.waitingList.getSize());
        try {
            Path file = Path.of("fuel-queue-log.txt");
            int lineNumber = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = lineNumber; j < 6 + lineNumber; j++) {
                    String[] lineData = Files.readAllLines(file).get(j).split(",");
                    if (lineData.length > 1) {
                        data.add(new QueuePassenger(lineData[0], lineData[1], lineData[2], Integer.parseInt(lineData[3]), ("Pump " + String.valueOf(i + 1))));
                    }
                }
                lineNumber += 6;

            }
            for (int i = 32; i < 38; i++) {
                String[] lineData = Files.readAllLines(file).get(i).split(",");
                if (lineData.length > 1) {
                    data.add(new QueuePassenger(lineData[0], lineData[1], lineData[2], Integer.parseInt(lineData[3]), "waiting"));
                }
            }
            System.out.println("Data loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error occurred.");
        }

        FilteredList<QueuePassenger> filteredData = new FilteredList<>(data, b -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(QueuePassenger -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (QueuePassenger.getPassengerFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (QueuePassenger.getPassengerLastName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (QueuePassenger.getVehicleNumber().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else
                    return false;
            });
        });

        SortedList<QueuePassenger> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedData);
    }

}
