package ztp.chinczyk.model;

import java.util.Random;

public class Dice {
	
	public static int rollDice() {
		Random r = new Random();
		return r.nextInt(6)+1;
	}

}
