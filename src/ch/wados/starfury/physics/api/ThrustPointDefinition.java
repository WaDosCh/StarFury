package ch.wados.starfury.physics.api;

import org.dyn4j.geometry.Vector2;

/**
 * Definition class for thrust points. A thrust point is a fixed point on an
 * entity a force in a fixed direction can be applied.
 * <p>
 * Using thrust points for recurring forces acting in a fixed direction at a
 * fixed location (in the local frame of reference) allows for some
 * preprocessing to separate the linear force component from the torque. This
 * can improve over-all physics performance, since many redundant vector
 * operations are avoided. Thrust point positions are provided relative to the
 * shape origin and not to the centre of mass. This allows for correct
 * positioning relative to the outline of the entity. The necessary corrections
 * are handled internally. An identifier allows for modification of
 * thrust-points later on. The identifier must be unique within an entity.
 * </p>
 * 
 * @author Andreas Wälchli
 * @version 1.1 - 2016/06/13
 * @since StarFury 0.0.1
 */
public final class ThrustPointDefinition {

	private final Vector2 position;
	private final Vector2 direction;
	private final String id;

	/**
	 * Creates a new ThrustPointDefinition instance from a given position,
	 * direction and identifier.
	 * 
	 * @param position
	 *            the thrust-point position. May not be {@code null}.
	 * @param direction
	 *            the thrust direction. May not be {@code null} or the zero
	 *            vector.
	 * @param id
	 *            an (optional) identifier
	 * @throws NullPointerException
	 *             if the {@code position} or {@code direction} is {@code null}.
	 * @throws IllegalArgumentException
	 *             if the {@code direction} vector is the zero vector.
	 */
	public ThrustPointDefinition(Vector2 position, Vector2 direction, String id) {
		if (position == null)
			throw new NullPointerException("position may not be null");
		if (direction == null)
			throw new NullPointerException("direction may not be null");
		if (direction.isZero())
			throw new IllegalArgumentException("direction may not be the zero vector");
		this.position = new Vector2(position);
		this.direction = direction.getNormalized();
		this.id = id;
	}

	/**
	 * Creates a new ThrustPointDefinition instance from a given position and
	 * direction.
	 * 
	 * @param position
	 *            the thrust-point position. May not be {@code null}.
	 * @param direction
	 *            the thrust direction. May not be {@code null} or the zero
	 *            vector.
	 * @throws NullPointerException
	 *             if the {@code position} or {@code direction} is {@code null}.
	 * @throws IllegalArgumentException
	 *             if the {@code direction} vector is the zero vector.
	 */
	public ThrustPointDefinition(Vector2 position, Vector2 direction) {
		this(position, direction, null);
	}

	public Vector2 getPosition() {
		return this.position;
	}

	public Vector2 getDirection() {
		return this.direction;
	}

	public String getIdentifier() {
		return this.id;
	}

}
