package de.at.home.rubybot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.awt.Robot;
import java.awt.event.KeyEvent;

@SpringBootApplication
public class RubyBotApplication {

	private Robot robot;

	public static void main(String[] args)
	{
		SpringApplication.run(RubyBotApplication.class, args);
	}

	@PostConstruct
	public void init() throws Exception
	{
		// VM option "-Djava.awt.headless=false" setzen
		this.robot = new Robot();

		Thread.sleep(5000);
		zocken();
		zocken();
	}

	public void zocken() throws Exception
	{
		// 43 Sekunden für einen Durchlauf
		// in 10 Minuten ca. 14 Durchläufte
		// maximal 49 Durchläufe sind für einen vollen Geldbeutel nötig
		// 49 Durchläufe dauern ca. 35 Minuten

		Thread.sleep(2500);

		// Shop betreten
		key(KeyEvent.VK_UP,1300);
		key(KeyEvent.VK_RIGHT, 1000);

		// mit dem Betreiber sprechen
		key(KeyEvent.VK_C, 100);
		Thread.sleep(1500);
		key(KeyEvent.VK_C, 100);
		Thread.sleep(1500);
		key(KeyEvent.VK_C, 100);
		Thread.sleep(1500);
		key(KeyEvent.VK_C, 100);
		Thread.sleep(1500);
		key(KeyEvent.VK_C, 100);
		Thread.sleep(1500);
		key(KeyEvent.VK_C, 100);
		Thread.sleep(1500);
		key(KeyEvent.VK_C, 100);

		// Kran platzieren
		key(KeyEvent.VK_LEFT, 1400);
		key(KeyEvent.VK_X, 2300);
		Thread.sleep(4000);
		key(KeyEvent.VK_C, 100);

		// Preis einsammeln
		key(KeyEvent.VK_LEFT, 500);
		key(KeyEvent.VK_UP, 500);
		Thread.sleep(16000);
		key(KeyEvent.VK_C, 100);
		Thread.sleep(1500);
		key(KeyEvent.VK_C, 100);

		// Shop verlassen
		key(KeyEvent.VK_DOWN, 500);
		key(KeyEvent.VK_RIGHT, 1000);
		key(KeyEvent.VK_DOWN, 500);
	}

	public void key(int event, int duration) throws Exception
	{
		this.robot.keyPress(event);
		Thread.sleep(duration);
		this.robot.keyRelease(event);
	}
}

