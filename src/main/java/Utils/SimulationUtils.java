package Utils;

import MainModel.Main;
import View.Simulation;
import View.SimulationController;
import javafx.stage.FileChooser;

import java.io.*;

public class SimulationUtils {

    SimulationController controller;
    public SimulationUtils(SimulationController simulationController) {
        this.controller = simulationController;
    }

    /**
     * Methode, sodass eine andere serializable-Datei geöffnet werden kann, um andere Simulationen zu öffnen
     */
    public void openNewSimulation() throws IOException, ClassNotFoundException {
        if (controller.getSimulation() != null) {
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

            //neue Simulation laden
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
                controller.setSimulation((Simulation) inputStream.readObject());
                fileInputStream.close();
                inputStream.close();
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
