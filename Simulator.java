
public class Simulator {

	public static void main(String[] args) {
		for(int i=1;i<=50;i++){
			Driver driver = new Driver();
			driver.drive(Params.NUMBER_OF_TICKS, i, "Newdata");
		}
	}

}
