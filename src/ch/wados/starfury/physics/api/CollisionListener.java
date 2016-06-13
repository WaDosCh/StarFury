package ch.wados.starfury.physics.api;

/**
 * Whenever a collision is detected and resolved through dynamic collision
 * detection a {@link CollisionPoint} is created. All registered
 * CollisionListeners will be invoked. Since the invocation takes place during
 * the physics calculations, the world must not be altered. It is recommended to
 * buffer any required changes and to perform them through an
 * {@link UpdateListener}.
 * 
 * @author Andreas Wälchli
 * @version 1.1 - 2016/06/14
 * @since StarFury 0.0.1
 */
@FunctionalInterface
public interface CollisionListener {

	/**
	 * Called whenever a collision is detected and resolved through dynamic
	 * collision detection.
	 * 
	 * @param collision
	 *            the {@link CollisionPoint} data
	 */
	public abstract void collision(CollisionPoint collision);

}
