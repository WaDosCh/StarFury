package ch.wados.starfury.math;

public class Vector4f {

	public final float[] comps;

	/**
	 * Creates a default 4-tuple vector with all values set to 0.
	 */
	public Vector4f() {
		this.comps = new float[] { 0f, 0f, 0f, 0f };
	}

	/**
	 * Creates a 4-tuple vector with specified values.
	 *
	 * @param x
	 *            x value
	 * @param y
	 *            y value
	 * @param z
	 *            z value
	 * @param w
	 *            w value
	 */
	public Vector4f(float x, float y, float z, float w) {
		this.comps = new float[] { x, y, z, w };
	}

	/**
	 * Calculates the squared length of the vector.
	 *
	 * @return Squared length of this vector
	 */
	public float lengthSquared() {
		return this.comps[0] * this.comps[0] + this.comps[1] * this.comps[1]
				+ this.comps[2] * this.comps[2] + this.comps[3] * this.comps[3];
	}

	/**
	 * Calculates the length of the vector.
	 *
	 * @return Length of this vector
	 */
	public float length() {
		return (float) Math.sqrt(lengthSquared());
	}

	/**
	 * Normalizes the vector.
	 *
	 * @return Normalized vector
	 */
	public Vector4f normalize() {
		float length = length();
		return divide(length);
	}

	/**
	 * Adds this vector to another vector.
	 *
	 * @param other
	 *            The other vector
	 *
	 * @return Sum of this + other
	 */
	public Vector4f add(Vector4f other) {
		float x = this.comps[0] + other.comps[0];
		float y = this.comps[1] + other.comps[1];
		float z = this.comps[2] + other.comps[2];
		float w = this.comps[3] + other.comps[3];
		return new Vector4f(x, y, z, w);
	}

	/**
	 * Negates this vector.
	 *
	 * @return Negated vector
	 */
	public Vector4f negate() {
		return scale(-1f);
	}

	/**
	 * Subtracts this vector from another vector.
	 *
	 * @param other
	 *            The other vector
	 *
	 * @return Difference of this - other
	 */
	public Vector4f subtract(Vector4f other) {
		return this.add(other.negate());
	}

	/**
	 * Multiplies a vector by a scalar.
	 *
	 * @param scalar
	 *            Scalar to multiply
	 *
	 * @return Scalar product of this * scalar
	 */
	public Vector4f scale(float scalar) {
		float x = this.comps[0] * scalar;
		float y = this.comps[1] * scalar;
		float z = this.comps[2] * scalar;
		float w = this.comps[3] * scalar;
		return new Vector4f(x, y, z, w);
	}

	/**
	 * Divides a vector by a scalar.
	 *
	 * @param scalar
	 *            Scalar to multiply
	 *
	 * @return Scalar quotient of this / scalar
	 */
	public Vector4f divide(float scalar) {
		return scale(1f / scalar);
	}

	/**
	 * Calculates the dot product of this vector with another vector.
	 *
	 * @param other
	 *            The other vector
	 *
	 * @return Dot product of this * other
	 */
	public float dot(Vector4f other) {
		return this.comps[0] * other.comps[0] + this.comps[1] * other.comps[1]
				+ this.comps[2] * other.comps[2]
				+ this.comps[3] * other.comps[3];
	}

	/**
	 * Calculates a linear interpolation between this vector with another
	 * vector.
	 *
	 * @param other
	 *            The other vector
	 * @param alpha
	 *            The alpha value, must be between 0.0 and 1.0
	 *
	 * @return Linear interpolated vector
	 */
	public Vector4f lerp(Vector4f other, float alpha) {
		return this.scale(1f - alpha).add(other.scale(alpha));
	}

}
