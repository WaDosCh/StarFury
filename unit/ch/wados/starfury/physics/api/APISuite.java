package ch.wados.starfury.physics.api;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Suite combining all physics API tests
 */
@RunWith(Suite.class)
@SuiteClasses({ ThrustDefTest.class, FixtureDefTest.class, PhysicsEntityDefTest.class })
public class APISuite {
}
