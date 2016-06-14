package ch.wados.starfury.physics.api;

import org.dyn4j.geometry.Vector2;

/**
 * A collision point holds all relevant data of a collision between two
 * {@link PhysicsEntity PhysicsEntities} detected and resolved through discrete
 * collision handling. Besides real collisions this class is also used to
 * represent sensor collisions. For these sensor collisions the resolution
 * parameters (impulses) are zeroed.
 * 
 * @author Andreas Wälchli
 * @version 1.1 - 2016/06/14
 * @since StarFury 0.0.1
 * 
 * @see FixtureDefinition
 * @see CollisionListener
 */
public final class CollisionPoint {

	/**
	 * The first entity involved in the collision.
	 */
	public final PhysicsEntity entity0;
	/**
	 * The second entity involved in the collision.
	 */
	public final PhysicsEntity entity1;
	/**
	 * The identifier of the involved fixture of {@code entity0}.
	 */
	public final String fixture0;
	/**
	 * The identifier of the involved fixture of {@code entity1}.
	 */
	public final String fixture1;
	/**
	 * The contact point in the world frame of reference.
	 */
	public final Vector2 contactPoint;
	/**
	 * the contact normal. This is the vector along which the normal impulse is
	 * transferred. This vector is normalised.
	 */
	public final Vector2 contactNormal;
	/**
	 * the impulse transferred along the normal vector. For sensor collisions
	 * this is zero.
	 */
	public final double normalImpulse;
	/**
	 * the impulse transferred tangentially to the contact (friction). For
	 * sensor collisions this is zero.
	 */
	public final double tangentialImpulse;

	/**
	 * creates a new CollisionPoint instance. For internal use of the physics
	 * engine only! Since it should only be used internally, null checks and
	 * input validation is omitted for performance.
	 * 
	 * @param entity0
	 * @param fixture0
	 * @param entity1
	 * @param fixture1
	 * @param contactPoint
	 * @param contactNormal
	 * @param normalImpulse
	 * @param tangentialImpulse
	 */
	public CollisionPoint(PhysicsEntity entity0, String fixture0, PhysicsEntity entity1, String fixture1,
			Vector2 contactPoint, Vector2 contactNormal, double normalImpulse, double tangentialImpulse) {
		super();
		this.entity0 = entity0;
		this.fixture0 = fixture0;
		this.entity1 = entity1;
		this.fixture1 = fixture1;
		this.contactPoint = contactPoint;
		this.contactNormal = contactNormal.getNormalized();
		this.normalImpulse = normalImpulse;
		this.tangentialImpulse = tangentialImpulse;
	}

}
