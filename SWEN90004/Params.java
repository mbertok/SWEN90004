package SWEN90004;

public class Params {
	// The dimension of the world
	public final static int DIMENSION = 51;

	// The mutation rate of the world
	public final static double MUTATION_RATE = 0.005;

	// The death rate of the world
	public final static double DEATH_RATE = 0.10;

	// The cost of giving of an agent in the world
	public final static double COST_OF_GIVING = 0.001;

	// The gain of receiving of an agent in the world
	public final static double GAIN_OF_RECEIVING = 0.003;

	// The immigrant chance to cooperate with agents of the same color as him
	public final static double IMMIGRANT_CHANCE_TO_COOP_WITH_SAME = 0.50;

	// The immigrant chance to cooperate with agents of different color than him
	public final static double IMMIGRANT_CHANCE_TO_COOP_WITH_DIFFERENT = 0.50;

	// The number of ethnicities in the world
	public final static int NO_OF_ETHNICITIES = 4;

	// The initial potential to reproduce of an agent
	public final static double INITIAL_PTR = 0.12;

	// The number of immigrants allowed in the world per day
	public final static int IMMIGRANTS_PER_DAY = 1;

	// The number of ticks to run the simulation for
	public final static int NUMBER_OF_TICKS = 500;

}
