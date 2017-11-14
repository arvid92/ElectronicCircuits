package de.nordakademie.tests;

import de.nordakademie.simulation.protocol.GlitchDetection;
import de.nordakademie.simulation.protocol.GlitchDetectionProtocol;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Die ProtocolGlitchDetection-Klasse enthält Methoden um zu ueberpruefen, ob
 * und wie viele Glitches in einem (XML)Document enthalten sind. Des weiteren
 * wird ueberprueft, ob die in dem Glitch-Protokoll enthaltenen Belegungen vor
 * und nach dem Glitch mit denen aus dem Simulations-Protokoll, wo ein Glitch
 * aufgetreten ist, uebereinstimmen. Fuer diese Tests wurden
 * Beispiel-Simulations-Protokolle im Package exampleprotocols von Markus Jaehn
 * angelegt.
 *
 * @author Timo Schlatter
 */
public class TestGlitchDetection {

	private GlitchDetection glitchDetection;
	private Document protocolWithoutGlitch;
	private Document protocolWithOneGlitch;
	private Document protocolWithTwoGlitches;
	private final XmlFromPackageService xmlPckgService = new XmlFromPackageService();

	@Before
	public void setUp() {
		glitchDetection = new GlitchDetection();
		protocolWithoutGlitch = xmlPckgService.getDoc("withoutGlitch");
		protocolWithOneGlitch = xmlPckgService.getDoc("withOneGlitch");
		protocolWithTwoGlitches = xmlPckgService.getDoc("withTwoGlitches");
	}

	@Test
	public void testDetectNumberOfGlitches0() {
		GlitchDetectionProtocol glitchDetectionProtocol = glitchDetection.detectGlitches(protocolWithoutGlitch);

		// Ueberpruefe, ob kein Glitch-Element in dem Glitch-Detection-Protokoll
		// enthalten ist
		assertEquals(0, getNumberOfGlitchElements(glitchDetectionProtocol.getDocument()));
	}

	@Test
	public void testDetectNumberOfGlitches1() {
		GlitchDetectionProtocol glitchDetectionProtocol = glitchDetection.detectGlitches(protocolWithOneGlitch);
		Document glitchDetectionDocument = glitchDetectionProtocol.getDocument();

		// Ueberpruefe, ob ein Glitch-Element in dem Glitch-Detection-Protokoll
		// enthalten ist
		assertEquals(1, getNumberOfGlitchElements(glitchDetectionDocument));

		Node glitchNode = glitchDetectionDocument.getFirstChild().getFirstChild();

		// Ermittle die Belegungen vor dem Auftreten des Glitches
		List<Boolean> inputsBeforeGlitch = extractValuesFromNodeList(glitchNode.getFirstChild().getChildNodes());

		// Ermittle die Belegungen nach dem Auftreten des Glitches
		List<Boolean> inputsAfterGlitch = extractValuesFromNodeList(glitchNode.getLastChild().getChildNodes());

		// Vergleiche ob die Belegungen vor dem Auftreten des Glitches mit den
		// Belegungen aus dem withOneGlitch.xml
		// Protokoll uebereinstimmen
		assertEquals(Arrays.asList(false, false), inputsBeforeGlitch);

		// Vergleiche ob die Belegungen nach dem Auftreten des Glitches mit den
		// Belegungen aus dem withOneGlitch.xml
		// Protokoll uebereinstimmen
		assertEquals(Arrays.asList(false, true), inputsAfterGlitch);
	}

	@Test
	public void testDetectNumberOfGlitches2() {
		GlitchDetectionProtocol glitchDetectionProtocol = glitchDetection.detectGlitches(protocolWithTwoGlitches);
		Document glitchDetectionDocument = glitchDetectionProtocol.getDocument();

		// Überpruefe, ob zwei Glitch-Elemente in dem
		// Glitch-Detection-Protokoll enthalten ist
		assertEquals(2, getNumberOfGlitchElements(glitchDetectionProtocol.getDocument()));

		Node firstGlitchNode = glitchDetectionDocument.getFirstChild().getFirstChild();
		Node secondGlitchNode = glitchDetectionDocument.getFirstChild().getLastChild();

		// Ermittle die Belegungen vor und nach dem Auftreten des ersten
		// Glitches
		List<Boolean> inputsBeforeFirstGlitch = extractValuesFromNodeList(
				firstGlitchNode.getFirstChild().getChildNodes());
		List<Boolean> inputsAfterFirstGlitch = extractValuesFromNodeList(
				firstGlitchNode.getLastChild().getChildNodes());

		// Ermittle die Belegungen vor und nach dem Auftreten des zweiten
		// Glitches
		List<Boolean> inputsBeforeSecondGlitch = extractValuesFromNodeList(
				secondGlitchNode.getFirstChild().getChildNodes());
		List<Boolean> inputsAfterSecondGlitch = extractValuesFromNodeList(
				secondGlitchNode.getLastChild().getChildNodes());

		// Vergleiche ob die Belegungen vor dem Auftreten des Glitches mit den
		// Belegungen aus dem withOneGlitch.xml
		// Protokoll uebereinstimmen
		assertEquals(Arrays.asList(false, false), inputsBeforeFirstGlitch);
		assertEquals(Arrays.asList(true, false), inputsAfterFirstGlitch);

		// Vergleiche ob die Belegungen vor dem Auftreten des Glitches mit den
		// Belegungen aus dem withOneGlitch.xml
		// Protokoll uebereinstimmen
		assertEquals(Arrays.asList(false, false), inputsBeforeSecondGlitch);
		assertEquals(Arrays.asList(false, true), inputsAfterSecondGlitch);
	}

	@Test
	public void testGetNumberOfGlitchElements() throws Exception {
		Document document;
		DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		document = documentBuilder.newDocument();
		Element rootElement = document.createElement("test");
		document.appendChild(rootElement);

		for (int i = 0; i < 10; i++) {
			assertEquals(i, getNumberOfGlitchElements(document));
			rootElement.appendChild(document.createElement("glitch"));
		}
	}

	/**
	 * Ermittelt die Anzahl der Glitch-Elemente in einem uebergebenen Dokument
	 * 
	 * @param document
	 *            das zu untersuchende Dokument
	 * @return die Anzahl der Glitch-Elemente
	 */
	private int getNumberOfGlitchElements(Document document) {
		NodeList changeElements = document.getFirstChild().getChildNodes();
		for (int i = 0; i < changeElements.getLength(); i++) {
			assertEquals("glitch", changeElements.item(i).getNodeName());
		}
		return changeElements.getLength();
	}

	/**
	 * Konvertiert die Text-Inhalte einer uebergebenen NodeList in eine
	 * Boolean-List
	 * 
	 * @param xmlElements
	 *            die zu konvertierende NodeList
	 * @return die konvertierte Boolean-List
	 */
	private List<Boolean> extractValuesFromNodeList(NodeList xmlElements) {
		List<Boolean> values = new ArrayList<>();
		for (int i = 0; i < xmlElements.getLength(); i++) {
			values.add(Boolean.valueOf(xmlElements.item(i).getTextContent()));
		}
		return values;
	}

	@Test
	public void testExtractValuesFromNodeList() throws Exception {
		Document document;
		DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		document = documentBuilder.newDocument();
		Element rootElement = document.createElement("test");
		document.appendChild(rootElement);

		List<Boolean> values = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			values.add(i % 2 == 0);
			rootElement.appendChild(document.createTextNode(String.valueOf(values.get(i))));
			assertEquals(values, extractValuesFromNodeList(rootElement.getChildNodes()));
		}
	}
}
