package ch.wados.starfury.physics.simple;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Triangle;
import org.dyn4j.geometry.Vector2;

import ch.wados.starfury.physics.api.EntityType;
import ch.wados.starfury.physics.api.FixtureDefinition;
import ch.wados.starfury.physics.api.PhysicsEntity;
import ch.wados.starfury.physics.api.PhysicsEntityDefinition;
import ch.wados.starfury.physics.api.PhysicsManager;

/**
 * Demo
 * 
 * Hold A,S,D,F to control direction of gravity
 */
public class Demo {

	static boolean left, right, up, down, gravUpdate;

	static void spawn(PhysicsManager m, PhysicsEntityDefinition d) {
		PhysicsEntity e = m.createEntity(d);
		System.out.println(((SimpleEntity) e).getBody().getMass().toString());
		m.spawnEntity(e);
	}

	static PhysicsManager manager;

	static void customUpdate() {
		if (!gravUpdate)
			return;
		gravUpdate = false;

		int x = 0, y = 0;

		if (left)
			x--;
		if (right)
			x++;
		if (up)
			y++;
		if (down)
			y--;

		manager.setGravity(new Vector2(x * 9.81, y * 9.81));
		manager.getSpawnedEntities().forEach(PhysicsEntity::wakeUp);
	}

	public static void main(String[] args) {

		System.out.println("DEMO");
		System.out.println("==============================");
		System.out.println("Hold [ASDF] to control gravity");

		SandboxController b = new SandboxController(800, 600, 45) {
			@Override
			protected void worldInit(PhysicsManager world) {

				manager = world;

				world.initialiseWorld(new Vector2(0, 0));

				Rectangle floorRect = new Rectangle(15.0, 1.0);

				PhysicsEntityDefinition floor = new PhysicsEntityDefinition(
						EntityType.STATIC)
								.addFixture(new FixtureDefinition(floorRect))
								.setPosition(new Vector2(0, -6.5));
				PhysicsEntityDefinition roof = new PhysicsEntityDefinition(
						floor).setPosition(new Vector2(0, 6.5));

				Rectangle sideRect = new Rectangle(1.0, 15.0);

				PhysicsEntityDefinition left = new PhysicsEntityDefinition(
						EntityType.STATIC)
								.addFixture(new FixtureDefinition(sideRect))
								.setPosition(new Vector2(-8, 0));
				PhysicsEntityDefinition right = new PhysicsEntityDefinition(
						left).setPosition(new Vector2(8, 0));

				spawn(world, floor);
				spawn(world, roof);
				spawn(world, left);
				spawn(world, right);

				world.addUpdateListener(Demo::customUpdate);

				// create a triangle object
				Triangle triShape = new Triangle(new Vector2(0.0, 0.5),
						new Vector2(-0.5, -0.5), new Vector2(0.5, -0.5));

				PhysicsEntityDefinition triangle = new PhysicsEntityDefinition(
						EntityType.DEFAULT)
								.addFixture(new FixtureDefinition(triShape)
										.setRestitutionCoefficient(1)
										.setFrictionCoefficient(0))
								.setPosition(new Vector2(-1, 2));

				spawn(world, triangle);

				// create a circle Circle cirShape = new Circle(0.5);
				// ColoredBody circle = new ColoredBody();
				// circle.addFixture(cirShape); circle.setMass(MassType.NORMAL);
				// circle.translate(2.0, 2.0); // test adding some force
				// circle.applyForce(new Vector2(-100.0, 0.0)); // set some
				// linear damping to simulate rolling friction
				// circle.setLinearDamping(0.05); world.addBody(circle);
				//
				// try a rectangle
				Rectangle rectShape = new Rectangle(1.0, 1.0);

				PhysicsEntityDefinition rectangle = new PhysicsEntityDefinition(
						EntityType.DEFAULT)
								.addFixture(new FixtureDefinition(rectShape))
								.setPosition(new Vector2(0, 2));

				spawn(world, rectangle);
			}

		};

		b.getFrame().getCanvas().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_A:
					left = true;
					break;
				case KeyEvent.VK_S:
					down = true;
					break;
				case KeyEvent.VK_D:
					right = true;
					break;
				case KeyEvent.VK_W:
					up = true;
					break;
				case KeyEvent.VK_Q:
					System.exit(0);
				default:
					return;
				}
				gravUpdate = true;
			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_A:
					left = false;
					break;
				case KeyEvent.VK_S:
					down = false;
					break;
				case KeyEvent.VK_D:
					right = false;
					break;
				case KeyEvent.VK_W:
					up = false;
					break;
				default:
					return;
				}
				gravUpdate = true;
			}
		});

		b.start();

	}

}
