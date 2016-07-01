package ch.wados.starfury.physics.api;

/**
 * Whenever a collision is detected through CCD a TimeOfImpact event is created.
 * All registered TimeOfImpactListeners will be invoked. Since the invocation
 * takes place during the physics calculations, the world must not be altered.
 * It is recommended to buffer any required changes and to perform them through
 * an {@link UpdateListener}.
 * <p>
 * All TimeOfImpactListeners also act as collision detection filters for CCD. If
 * any filter returns {@code false} the collision will not be resolved. This
 * does however not prevent the discrete collision detection to resolve the
 * collision if it finds it. Resolved TimeOfImpact events will be detected by
 * the discrete collision detection in the subsequent collision step.
 * </p>
 * 
 * @see UpdateListener
 * @see TimeOfImpact
 */
@FunctionalInterface
public interface TimeOfImpactListener {

	/**
	 * Called whenever an impact is detected through CCD. Indicates if the
	 * collision should be resolved. The default return value should be
	 * {@code true}.
	 * 
	 * @param entity0
	 *            the first entity involved in the collision.
	 * @param fixture0
	 *            the identifier of the involved fixture of {@code entity0}.
	 * @param entity1
	 *            the second entity involved in the collision.
	 * @param fixture1
	 *            the identifier of the involved fixture of {@code entity1}.
	 * 
	 * @param toi
	 *            The time of impact in the range [0,1], where 0 is the
	 *            beginning of the time step and 1 is the end. (linear scaling
	 *            in between).
	 * @return {@code true} if the collision should be resolved, {@code false}
	 *         if it should be ignored. By default {@code true} should be
	 *         returned.
	 */
	boolean collision(PhysicsEntity entity0, String fixture0,
			PhysicsEntity entity1, String fixture1, double toi);

}
