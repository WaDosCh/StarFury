package ch.wados.starfury;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ch.wados.starfury.physics.PhysicsSuite;

@RunWith(Suite.class)
@SuiteClasses(PhysicsSuite.class)
public class FullSuite {
}
