package ch.wados.starfury.physics.api;

/**
 * Base Interface for all classes that physics engine listeners and filters can
 * be registered on. These listeners and filters are
 * </ul>
 * <li>{@link CollisionFilter} for broad-phase elimination of unwanted collisions</li>
 * <li>{@link CollisionListener} for detecting collisions resolved through the narrow-phase</li>
 * <li>{@link TimeOfImpactListener} for detection of collisions resolved through CCD</li>
 * <li>{@link UpdateListener} for performing updates directly after a physics step</li>
 * </ul>
 * <p>
 * If any method is invoked with a {@code null} parameter, a
 * {@link NullPointerException} should be thrown. If a {@code remove} method is
 * invoked with a listener that is not registered, the call should be ignored.
 * </p>
 * 
 * @author Andreas Wälchli
 * @version 1.1 - 2016/06/14
 * @since StarFury 0.0.1
 */
public interface Listenable {

	void addCollisionFilter(CollisionFilter filter);

	void addCollisionListener(CollisionListener listener);

	void addTimeOfImpactListener(TimeOfImpactListener listener);

	void addUpdateListener(UpdateListener listener);

	void removeCollisionFilter(CollisionFilter filter);

	void removeCollisionListener(CollisionListener listener);

	void removeTimeOfImpactListener(TimeOfImpactListener listener);

	void removeUpdateListener(UpdateListener listener);

}
