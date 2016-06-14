package ch.wados.starfury.physics.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Vector2;

import ch.wados.starfury.physics.api.CollisionFilter;
import ch.wados.starfury.physics.api.CollisionListener;
import ch.wados.starfury.physics.api.PhysicsEntity;
import ch.wados.starfury.physics.api.PhysicsEntityDefinition;
import ch.wados.starfury.physics.api.PhysicsManager;
import ch.wados.starfury.physics.api.TimeOfImpactListener;
import ch.wados.starfury.physics.api.UpdateListener;

/**
 * Simple {@link PhysicsManager}
 * 
 * @author Andreas Wälchli
 * @version 1.1 - 2016/06/14
 * @since StarFury 0.0.1
 */
public final class SimplePhysicsManager implements PhysicsManager {

	private boolean initialised = false;
	private World world;

	private void assertInit() {
		if (!initialised)
			throw new IllegalStateException("world must be initialised");
	}

	private final List<CollisionFilter> collisionFilters = new ArrayList<>();
	private final List<CollisionListener> collisionListeners = new ArrayList<>();
	private final List<TimeOfImpactListener> toiListeners = new ArrayList<>();
	private final List<UpdateListener> updateListeners = new ArrayList<>();

	@Override
	public void addCollisionFilter(CollisionFilter filter) {
		Objects.requireNonNull(filter);
		if (!this.collisionFilters.contains(filter))
			this.collisionFilters.add(filter);
	}

	@Override
	public void addCollisionListener(CollisionListener listener) {
		Objects.requireNonNull(listener);
		if (!this.collisionListeners.contains(listener))
			this.collisionListeners.add(listener);
	}

	@Override
	public void addTimeOfImpactListener(TimeOfImpactListener listener) {
		Objects.requireNonNull(listener);
		if (!this.toiListeners.contains(listener))
			this.toiListeners.add(listener);
	}

	@Override
	public void addUpdateListener(UpdateListener listener) {
		Objects.requireNonNull(listener);
		if (!this.updateListeners.contains(listener))
			this.updateListeners.add(listener);
	}

	@Override
	public void removeCollisionFilter(CollisionFilter filter) {
		Objects.requireNonNull(filter);
		this.collisionFilters.remove(filter);
	}

	@Override
	public void removeCollisionListener(CollisionListener listener) {
		Objects.requireNonNull(listener);
		this.collisionListeners.remove(listener);
	}

	@Override
	public void removeTimeOfImpactListener(TimeOfImpactListener listener) {
		Objects.requireNonNull(listener);
		this.toiListeners.remove(listener);
	}

	@Override
	public void removeUpdateListener(UpdateListener listener) {
		Objects.requireNonNull(listener);
		this.updateListeners.remove(listener);
	}

	@Override
	public PhysicsEntity createEntity(PhysicsEntityDefinition definition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void despawnEntity(PhysicsEntity entity) {
		this.assertInit();
		// TODO Auto-generated method stub

	}

	@Override
	public Vector2 getGravity() {
		this.assertInit();
		return this.world.getGravity();
	}

	@Override
	public void initialiseWorld(Vector2 gravity, int initialCapacity) {
		if (this.initialised)
			throw new IllegalStateException("already initialised");
		// TODO Auto-generated method stub

	}

	@Override
	public void setGravity(Vector2 gravity) {
		this.assertInit();
		this.world.setGravity(gravity);
	}

	@Override
	public void spawnEntity(PhysicsEntity entity) {
		this.assertInit();
		Objects.requireNonNull(entity);
		if (!(entity instanceof SimpleEntity))
			throw new IllegalArgumentException("incompatible entity");
		this.world.addBody(((SimpleEntity) entity).body);
	}

	@Override
	public void stepWorld(double stepTime) {
		this.assertInit();
		// TODO Auto-generated method stub

	}

}
