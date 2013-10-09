package com.example.tipcalculator;

/**
 * Model class for tip calculation
 * @author akailaje
 *
 */
public class TipCalculator {

	/**
	 * Get tip amount given the total amount and the tip percentage
	 * @param totalAmount total amount to be tipped on
	 * @param tipPercent tip percentage to apply
	 * @return tip amount
	 */
	public static double getTipAmount(double totalAmount, double tipPercent) {
		if (totalAmount <= 0.0d) {
			return 0.0d;
		}
		
		if (tipPercent <= 0.0d) {
			return 0.0d;
		}
		
		return ((tipPercent / 100) * totalAmount);
	}
}
