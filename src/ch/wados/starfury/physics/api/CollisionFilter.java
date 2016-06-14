package ch.wados.starfury.physics.api;

/**
 * Collision filters are used to eliminate all collisions, that should be
 * ignored early in the broad-phase scan.
 * <p>
 * If any filter returns {@code false} for a given collision pair, that pair
 * will be ignored. Therefore by default {@code true} should be returned.
 * </p>
 * 
 * @author Andreas Wälchli
 * @version 1.1 - 2016/06/14
 * @since StarFury 0.0.1
 */
@FunctionalInterface
public interface CollisionFilter {

	/**
	 * Indicates if a collision check should be performed for the given
	 * combination.
	 * 
	 * @param entity0
	 *            the first entity
	 * @param fixture0
	 *            the fixture of the first entity
	 * @param entity1
	 *            the second entity
	 * @param fixture1
	 *            the fixture of the second entity
	 * @return {@code true} if the collision should be checked, {@code false}
	 *         otherwise. By default this should return {@code true}.
	 */
	public abstract boolean filterCollision(PhysicsEntity entity0, String fixture0, PhysicsEntity entity1,
			String fixture1);

}
