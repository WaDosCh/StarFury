package ch.wados.starfury.util;

public class Math {

	/**
	 * Normalises an angle in radians into the range [0,2π)
	 * 
	 * @param angle
	 *            the raw angle in radians
	 * @return the normalised angle
	 */
	public static double normalizedAngle(double angle) {
		final double twopi = 2 * java.lang.Math.PI;
		while (angle >= twopi)
			angle -= twopi;
		while (angle < 0)
			angle += twopi;
		return angle;
	}

}
