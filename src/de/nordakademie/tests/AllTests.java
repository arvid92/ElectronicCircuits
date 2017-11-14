package de.nordakademie.tests;

/**
 * Klasse fuehrt alle Testklassen aus
 * 
 * @author Jonas Jacobsen
 */
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.nordakademie.tests.circuitcomponenttests.TestAndGate;
import de.nordakademie.tests.circuitcomponenttests.TestInputComponent;
import de.nordakademie.tests.circuitcomponenttests.TestInverterGate;
import de.nordakademie.tests.circuitcomponenttests.TestMeasureComponent;
import de.nordakademie.tests.circuitcomponenttests.TestOrGate;
import de.nordakademie.tests.circuitcomponenttests.TestWire;
import de.nordakademie.tests.circuitcomponenttests.TestXorGate;

@RunWith(Suite.class)
@SuiteClasses({ TestAndGate.class, TestCircuit.class, TestGlitchDetection.class, TestInputComponent.class,
		TestInverterGate.class, TestMeasureComponent.class, TestOrGate.class, TestProtocol.class,
		TestSingleInputChangeBehaviour.class, TestTimeline.class, TestWire.class, TestWireFactory.class,
		TestXorGate.class })
public class AllTests {

}
