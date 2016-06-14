package ch.wados.starfury.physics.api;

/**
 * Base Interface for all classes that physics engine listeners and filters can
 * be registered on. These are
 * </ul>
 * <li>{@link CollisionFilter}</li>
 * <li>{@link CollisionListener}</li>
 * <li>{@link TimeOfImpactListener}</li>
 * <li>{@link UpdateListener}</li>
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

	public abstract void addCollisionFilter(CollisionFilter filter);

	public abstract void addCollisionListener(CollisionListener listener);

	public abstract void addTimeOfImpactListener(TimeOfImpactListener listener);

	public abstract void addUpdateListener(UpdateListener listener);

	public abstract void removeCollisionFilter(CollisionFilter filter);

	public abstract void removeCollisionListener(CollisionListener listener);

	public abstract void removeTimeOfImpactListener(TimeOfImpactListener listener);

	public abstract void removeUpdateListener(UpdateListener listener);

}
