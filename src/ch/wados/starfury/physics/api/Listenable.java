package ch.wados.starfury.physics.api;

/**
 * Base Interface for all classes that physics engine listeners can be
 * registered on. These are
 * </ul>
 * <li>{@link CollisionListener}</li>
 * <li>{@link TimeOfImpactListener}</li>
 * <li>{@link UpdateListener}</li>
 * </ul>
 * 
 * @author Andreas Wälchli
 * @version 1.1 - 2016/06/14
 * @since StarFury 0.0.1
 */
public interface Listenable {

	public abstract void addCollisionListener(CollisionListener listener);

	public abstract void addTimeOfImpactListener(TimeOfImpactListener listener);

	public abstract void addUpdateListener(UpdateListener listener);

	public abstract void removeCollisionListener(CollisionListener listener);

	public abstract void removeTimeOfImpactListener(TimeOfImpactListener listener);

	public abstract void removeUpdateListener(UpdateListener listener);

}
