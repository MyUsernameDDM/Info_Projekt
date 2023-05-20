package Utils;

import MainModel.Main;
import View.Simulation;
import View.SimulationController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.ContentHandler;
import java.util.Optional;

/**
 * SimulationsUtils dient zum laden und speichern von Simulationen und Simulationsdateien
 */
public class SimulationUtils {

    SimulationController controller;
    public SimulationUtils(SimulationController simulationController) {
        this.controller = simulationController;
    }

    /**
     * Methode beim starten einer neuen Simulation
     */
    public void newSimulation(){
        checkIfSimSaved();
        //neue Simulation erstellen
        controller.setSimulation(new Simulation());
        //Geldmenge eingeben
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Simulation");
        dialog.setHeaderText("Geben Sie die Startgeldmenge für die Simulation an:");

        // Dialog anzeigen und Benutzereingabe abrufen
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(value -> {
            controller.getSimulation().setMoneyAv(Integer.parseInt(value));
            controller.getWalletView().getMoneyShow().setText(value);
        });
    }

    /**
     * Methode, sodass eine andere serializable-Datei geöffnet werden kann, um andere Simulationen zu öffnen
     */
    public void openSimulation() throws IOException, ClassNotFoundException {

        checkIfSimSaved();
        //Simulation öffnen
        FileChooser fileChooser = new FileChooser();

        //neue Simulation laden
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            FileInputStream fileInputStream = new FileInputStream(selectedFile);
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            controller.setSimulation((Simulation) inputStream.readObject());
            fileInputStream.close();
            inputStream.close();
        }

        //die Simulation in die Anzeigen laden
        controller.getWalletView().reloadSimulation();

    }

    //Methode ruft ein PopUpFenster auf, in dem der Nutzer angibt, ob er die aktuelle Simulation speichern moechte
    private void checkIfSimSaved() {
        if(controller.getSimulation() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Simulation Speichern");
            alert.setHeaderText("Möchten Sie die Simulation speichern?");
            alert.setContentText("Klicken Sie auf OK, um die Simulation zu speichern.");

            // Benutzeraktion überprüfen
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
            if (result == ButtonType.OK) {
                saveCurrentSimulation();
            }
        }
    }

    public void saveCurrentSimulation(){
        //aktuelle Simulation öffnen
        FileChooser fileChooser = new FileChooser();

        //automatisch .ser als Dateiendung vorschlagen
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Serialized objects (*.ser)", "*.ser");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                // Serialize Simulation in das File
                FileOutputStream fileOut = new FileOutputStream(file);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(controller.getSimulation());
                out.close();
                fileOut.close();

                System.out.println("Object serialized and saved to file: " + file.getAbsolutePath());

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
