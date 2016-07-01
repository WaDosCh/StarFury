package ch.wados.starfury.physics.simple;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Suite combining all simplePhysics tests
 */

@RunWith(Suite.class)
@SuiteClasses({ ThrusterTest.class, ThrusterSystemTest.class })
public class SimpleSuite {
}