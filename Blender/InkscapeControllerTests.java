package org.local.test;

import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.junit.Test;

/*
Beim Exportieren des UV-Layouts nach SVG, werden SVG-Objekt mit Konturen (Rändern) erzeugt.
Wenn man diese Objekte in Inkscape mit einer Füllung versieht und die Kontur entfernt, dann verringert sich die Größe der Objekte um die Dicke der Kontur.

Umwandeln der Kontur-Objekte in konturfreie Objekte der selben Größe:
- alle Objekte markieren
- Kontur in Pfad umwandeln
- die folgenden Schritte sind auf jedes Objekt einzeln anzuwenden
  - 'Tab' (nächstes Objekt auswählen)
  - 'Strg + L' (Pfad vereinfachen)
  - 'Shift + Strg + K' (Subpfade in separate Objekte zerlegen)
  - 'Strg + +' (Objekte vereinigen)
  
Die Schritte, die auf jedes Objekt einzeln angewendet werden müssen, können mit dieser Klasse automatisiert werden.
Voraussetzungen:
- in den anfänglichen Wartesekunden wird der Fokus auf Inkscape gelegt (worin das UV-Layout geladen ist)
- die Konturen wurden in Pfade umgewandelt
- das Objekt-Werkzeug (F1) wurde ausgewählt
- es ist kein Objekt markiert
 */
public class InkscapeControllerTests
{
	private Robot robot;
	
	public InkscapeControllerTests()
	{
		try
		{
			this.robot = new Robot();
		}
		catch (Exception e) {/* ignore */}
	}
	
	@Test
	public void strokeToObjectProcessorTest() throws Exception
	{
		int waitForNextInput = 300;
		int polygonCount = 234;
		
		String duration = DurationFormatUtils.formatDuration(waitForNextInput * 4 * polygonCount, "HH:mm:ss,SSS");
		System.out.println(duration);
		
		Thread.sleep(8000);
		
		for (int i = 0 ; i < polygonCount ; i++)
		{
			// 'Tab' (nächstes Objekt auswählen)
			this.robot.keyPress(KeyEvent.VK_TAB);
			this.robot.keyRelease(KeyEvent.VK_TAB);
			Thread.sleep(waitForNextInput);
			
			// 'Strg + L' (Pfad vereinfachen)
			this.robot.keyPress(KeyEvent.VK_CONTROL);
			this.robot.keyPress(KeyEvent.VK_L);
			this.robot.keyRelease(KeyEvent.VK_L);
			this.robot.keyRelease(KeyEvent.VK_CONTROL);
			Thread.sleep(waitForNextInput);
			
			// 'Shift + Strg + K' (Subpfade in separate Objekte zerlegen)
			this.robot.keyPress(KeyEvent.VK_SHIFT);
			this.robot.keyPress(KeyEvent.VK_CONTROL);
			this.robot.keyPress(KeyEvent.VK_K);
			this.robot.keyRelease(KeyEvent.VK_K);
			this.robot.keyRelease(KeyEvent.VK_CONTROL);
			this.robot.keyRelease(KeyEvent.VK_SHIFT);
			Thread.sleep(waitForNextInput);
			
			// 'Strg + +' (Objekte vereinigen)
			this.robot.keyPress(KeyEvent.VK_CONTROL);
			this.robot.keyPress(KeyEvent.VK_PLUS);
			this.robot.keyRelease(KeyEvent.VK_PLUS);
			this.robot.keyRelease(KeyEvent.VK_CONTROL);
			Thread.sleep(waitForNextInput);
		}
	}
}