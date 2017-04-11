package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import javax.swing.Timer;


public class Chronometer implements ActionListener{
	private Timer timer;
	private MultiKeyTreasureGUI view;
	private DecimalFormat formatter2D;
	private DecimalFormat formatter3D;
	private long startedAt;
	
	private static final int DELAY = 100;
	
	
	/**
	 * Creates a new chronometer.
	 * 
	 * @param view Object to update.
	 */
	public Chronometer(MultiKeyTreasureGUI view) {
		this.formatter2D = new DecimalFormat("00");
		this.formatter3D = new DecimalFormat("000");
		this.view = view;
		updateView(false);
	}
	
	/**
	 * Method executed every DELAY milliseconds. It updates the elapsed time ans the view.
	 */	
	public void actionPerformed(ActionEvent e) {
		updateView(true);
	}
	
	// Updates the view according to the elapsed time.
	private void updateView(boolean isOn) {
		long elapsedTime = isOn?System.nanoTime() - startedAt:0;
		
		view.setElapsedTime(formatter2D.format(TimeUnit.NANOSECONDS.toHours(elapsedTime) % 24) + ":" +
				formatter2D.format(TimeUnit.NANOSECONDS.toMinutes(elapsedTime) % 60) + ":" +
				formatter2D.format(TimeUnit.NANOSECONDS.toSeconds(elapsedTime) % 60) +"." +
				formatter3D.format(TimeUnit.NANOSECONDS.toMillis(elapsedTime) % 1000));
	}
	
	/**
	 * Starts the chronometer.
	 */
	public void startChrono() {
		startedAt = System.nanoTime();
		if (timer == null) timer = new Timer(DELAY, this);
		if (!timer.isRunning()) timer.start();
	}
	
	/**
	 * Stops the chronometer.
	 */
	public void stopChrono() {
		if (timer.isRunning()) timer.stop();
	}

}
