package ch.wados.starfury.physics.simple;

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
public class SimplePhysicsManager implements PhysicsManager {

	@Override
	public void addCollisionFilter(CollisionFilter filter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addCollisionListener(CollisionListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addTimeOfImpactListener(TimeOfImpactListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addUpdateListener(UpdateListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeCollisionFilter(CollisionFilter filter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeCollisionListener(CollisionListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTimeOfImpactListener(TimeOfImpactListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeUpdateListener(UpdateListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public PhysicsEntity createEntity(PhysicsEntityDefinition definition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void despawnEntity(PhysicsEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public Vector2 getGravity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initialiseWorld(Vector2 gravity, int initialCapacity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setGravity(Vector2 gravity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void spawnEntity(PhysicsEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stepWorld(double stepTime) {
		// TODO Auto-generated method stub

	}

}
