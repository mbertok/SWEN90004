package Ext;

public class Simulator {

	public static void main(String[] args) {
		for (int i = 1; i <= Params.SIMULATION_NO_OF_RUNS; i++) {
			Driver driver = new Driver();
			driver.drive(Params.NUMBER_OF_TICKS, i, Params.DATA_DUMP_FOLDER);
		}
	}
}