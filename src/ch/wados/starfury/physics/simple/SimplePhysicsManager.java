package ch.wados.starfury.physics.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.dyn4j.collision.continuous.TimeOfImpact;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.TimeOfImpactAdapter;
import org.dyn4j.dynamics.World;
import org.dyn4j.dynamics.contact.ContactAdapter;
import org.dyn4j.dynamics.contact.SolvedContactPoint;
import org.dyn4j.geometry.Vector2;

import ch.wados.starfury.physics.api.CollisionFilter;
import ch.wados.starfury.physics.api.CollisionListener;
import ch.wados.starfury.physics.api.CollisionPoint;
import ch.wados.starfury.physics.api.PhysicsEntity;
import ch.wados.starfury.physics.api.PhysicsEntityDefinition;
import ch.wados.starfury.physics.api.PhysicsManager;
import ch.wados.starfury.physics.api.TimeOfImpactListener;
import ch.wados.starfury.physics.api.UpdateListener;

/**
 * Simple {@link PhysicsManager}
 */
public final class SimplePhysicsManager implements PhysicsManager {

	private boolean initialised = false;
	private World world;

	public SimplePhysicsManager() {
	}

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
		return new SimpleEntity(definition);
	}

	@Override
	public synchronized void despawnEntity(PhysicsEntity entity) {
		this.assertInit();
		Objects.requireNonNull(entity);
		if (!(entity instanceof SimpleEntity))
			throw new IllegalArgumentException("incompatible entity");
		if (this.world.getBodies().contains(((SimpleEntity) entity).getBody()))
			this.world.removeBody(((SimpleEntity) entity).getBody());
		else
			throw new IllegalArgumentException("entity does not exist");
	}

	@Override
	public Vector2 getGravity() {
		this.assertInit();
		return this.world.getGravity();
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
		this.world.addBody(((SimpleEntity) entity).getBody());
	}

	@Override
	public synchronized void initialiseWorld(Vector2 gravity) {
		if (this.initialised)
			throw new IllegalStateException("already initialised");
		Objects.requireNonNull(gravity);
		this.initialised = true;
		this.world = new World();
		this.world.setGravity(new Vector2(gravity));
		// register listeners
		this.world.addListener(new ContactListener());
		this.world.addListener(new TOIListener());
	}

	@Override
	public void stepWorld(double stepTime) {
		this.assertInit();
		this.world.updatev(stepTime);
		this.updateListeners.forEach(UpdateListener::update);
	}

	class ContactListener extends ContactAdapter {
		@Override
		public void postSolve(SolvedContactPoint point) {
			CollisionPoint cp = new CollisionPoint(
					(SimpleEntity) point.getBody1().getUserData(),
					(String) point.getFixture1().getUserData(),
					(SimpleEntity) point.getBody2().getUserData(),
					(String) point.getFixture2().getUserData(),
					point.getPoint().copy(), point.getNormal().copy(),
					point.getNormalImpulse(), point.getTangentialImpulse());
			collisionListeners.forEach(cl -> cl.collision(cp));
		}
	}

	class TOIListener extends TimeOfImpactAdapter {

		@Override
		public boolean collision(Body body1, BodyFixture fixture1, Body body2,
				BodyFixture fixture2, TimeOfImpact toi) {
			PhysicsEntity entity_0 = ((SimpleEntity) body1.getUserData());
			PhysicsEntity entity_1 = ((SimpleEntity) body2.getUserData());
			String fixture_0 = ((String) fixture1.getUserData());
			String fixture_1 = ((String) fixture2.getUserData());
			double tau = toi.getTime();
			boolean result = true;
			for (TimeOfImpactListener l : toiListeners)
				result &= l.collision(entity_0, fixture_0, entity_1, fixture_1,
						tau);

			return result;
		}

	}

}
