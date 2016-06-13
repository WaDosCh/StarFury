package ch.wados.starfury.physics.api;

/**
 * A TimeOfImpact is an event that is dispatched whenever a collision has been
 * found through CCD.
 * <p>
 * Note that any collision found through CCD will also produce a normal
 * collision event in the next physics tick.
 * </p>
 * 
 * @author Andreas Wälchli
 * @version 1.1 - 2016/06/13
 * @since StarFury 0.0.1
 */
public final class TimeOfImpact {

	/**
	 * The first entity involved in the collision
	 */
	public final PhysicsEntity entity0;
	/**
	 * The second entity involved in the collision
	 */
	public final PhysicsEntity entity1;
	/**
	 * The identifier of the involved fixture of {@code entity0}. If the fixture
	 * has no identifier, this holds {@code null}.
	 */
	public final String fixture0;
	/**
	 * The identifier of the involved fixture of {@code entity1}. If the fixture
	 * has no identifier, this holds {@code null}.
	 */
	public final String fixture1;
	/**
	 * The time of impact in the range [0,1], where 0 is the beginning of the
	 * time step and 1 is the end. (linear scaling in between).
	 */
	public final double toi;

	/**
	 * Instantiates a new TimeOfImpact event. This constructor should not be
	 * invoked from outside of the physic module. Since it should only be used
	 * internally null-checks are omitted for performance.
	 * 
	 * @param entity0
	 * @param entity1
	 * @param fixture0
	 * @param fixture1
	 * @param toi
	 */
	public TimeOfImpact(PhysicsEntity entity0, PhysicsEntity entity1, String fixture0, String fixture1, double toi) {
		this.entity0 = entity0;
		this.entity1 = entity1;
		this.fixture0 = fixture0;
		this.fixture1 = fixture1;
		this.toi = toi;
	}

}
