package Utils;

import View.Simulation;
import View.SimulationController;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestSimulationUtils {
    //todo geat logisch net, i miaßat quasi olls mochn
    @Test
    public void openNewSimulation() throws IOException, ClassNotFoundException {
        Simulation simulation = new Simulation();
        SimulationController controller = new SimulationController();
        SimulationUtils simulationUtils = new SimulationUtils(controller);
        simulationUtils.openSimulation();
    }
}
