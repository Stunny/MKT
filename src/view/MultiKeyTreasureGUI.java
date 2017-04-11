package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 * A window that shows a labyrinth.
 * 
 * @author  Programació avançada i estructura de dades - PAED
 * 		    La Salle - Universitat Ramon Llull
 * 
 * @version 1.0 01/12/2016
 */
public class MultiKeyTreasureGUI extends JFrame {
	private JLabel jlElapsedTime;
	private JLabel jlKeysCollected;
	private JLabel jlPathLength;
	private JLabel[][] labels;
	private JLabel entrance;
	private JLabel treasure;
	private Chronometer chronometer;

	private static final long serialVersionUID = 1L;
	public static final String ENTRANCE_REPRESENTATION = "E";
	public static final String TREASURE_REPRESENTATION = "T";
	public static final String WALL_REPRESENTATION = "-";
	private static final Color ENTRANCE_COLOR = Color.GREEN;
	private static final Color TREASURE_COLOR = Color.YELLOW;
	private static final Color WALL_COLOR = Color.DARK_GRAY;
	private static final Color COVERED_PATH = Color.CYAN;
	private static final Color UNCOVERED_PATH = Color.WHITE;

	/**
	 * Creates a new window that shows the initial state of the labyrinth.
	 * 
	 * @param width Width of the window in pixels.
	 * @param height Height of the window in pixels.
	 * @param title Title to be shown on the bar.
	 * @param state The state of each of the cells of the labyrinth.
	 */
	public MultiKeyTreasureGUI(int width, int height, String title, String[][] state) {
		super.getContentPane().add(createTopScoreboards(), BorderLayout.NORTH);
		super.getContentPane().add(createScenario(state), BorderLayout.CENTER);
		super.setSize(width, height);
		super.setTitle(title);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setVisible(true);

		chronometer = new Chronometer(this);
	}

	// Creates the upper scoreboards.
	private JPanel createTopScoreboards() {
		jlPathLength = new JLabel("");
		jlPathLength.setBorder(BorderFactory.createTitledBorder("Path length"));
		jlPathLength.setHorizontalAlignment(SwingConstants.CENTER);

		jlKeysCollected = new JLabel("");
		jlKeysCollected.setBorder(BorderFactory.createTitledBorder("Keys collected"));
		jlKeysCollected.setHorizontalAlignment(SwingConstants.CENTER);

		jlElapsedTime = new JLabel("");
		jlElapsedTime.setBorder(BorderFactory.createTitledBorder("Elapsed time"));
		jlElapsedTime.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel jpTop = new JPanel(new GridLayout(1, 3));
		jpTop.add(jlPathLength);
		jpTop.add(jlKeysCollected);
		jpTop.add(jlElapsedTime);

		return jpTop;
	}

	// Creates the board.
	private JPanel createScenario(String[][] state) {
		JPanel jpCenter = new JPanel(new GridLayout(state.length, state[0].length));
		jpCenter.setBorder(BorderFactory.createTitledBorder("Scenario"));
		labels = new JLabel[state.length][state[0].length];

		for (int i=0; i<state.length; i++) {
			for (int j=0; j<state[i].length; j++) {
				labels[i][j] = new JLabel();
				labels[i][j].setOpaque(true);
				labels[i][j].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
				labels[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				labels[i][j].setBackground(UNCOVERED_PATH);
				labels[i][j].setFont(labels[i][j].getFont().deriveFont(18f));
				jpCenter.add(labels[i][j]);
				updateScenario(i,j,state[i][j]);
			}
		}
		return jpCenter;
	}

	/**
	 * Changes the path length.
	 *
	 * @param length number to display.
	 */	
	public void setPathLength(int length) {
		jlPathLength.setText(String.valueOf(length));
	}

	/**
	 * Changes the value of the keys collected.
	 *
	 * @param keys number to display.
	 */
	public void setKeysCollected(int keys) {
		jlKeysCollected.setText(String.valueOf(keys));
	}

	/**
	 * Changes the elapsed time.
	 *
	 * @param time number to display.
	 */	
	protected void setElapsedTime(String time) {
		jlElapsedTime.setText(time);
	}

	/**
	 * Starts the chronometer to count the elapsed time.
	 */
	public void startChronometer() {
		chronometer.startChrono();
	}

	/**
	 * Stops the chronometer counting the elapsed time.
	 */
	public void stopChronometer() {
		chronometer.stopChrono();
	}

	/**
	 * Paints the chosen cell to indicate that it forms part of the path.
	 * 
	 * @param row Row of the board.
	 * @param column Column of the board.
	 */
	public void addToPath(int row, int column) {
		if (checkCell(row, column)) {
			if (!labels[row][column].getBackground().equals(WALL_COLOR)) {
				if (!labels[row][column].getBackground().equals(ENTRANCE_COLOR) &&
						!labels[row][column].getBackground().equals(TREASURE_COLOR)){
					labels[row][column].setBackground(COVERED_PATH);
				}
			} else {
				System.out.println("ERROR - (addToPath) There is a wall!");
			}
		} else {
			System.out.println("ERROR - (addToPath) [Row=" + row + ",Column=" + column + "] out of bounds.");
		}
	}

	/**
	 * Returns the chosen cell to its original color to indicate that it no longer forms part of the path.
	 * 
	 * @param row Row of the board.
	 * @param column Column of the board.
	 */
	public void deleteFromPath(int row, int column) {
		if (checkCell(row, column)) {
			if (!labels[row][column].getBackground().equals(WALL_COLOR)) {
				if (labels[row][column] == treasure){
					labels[row][column].setBackground(TREASURE_COLOR);
				} else if (labels[row][column] == entrance) {
					labels[row][column].setBackground(ENTRANCE_COLOR);
				} else {
					labels[row][column].setBackground(UNCOVERED_PATH);
				}
			} else {
				System.out.println("ERROR - (deleteFromPath) There is a wall!");
			}			
		} else {
			System.out.println("ERROR - (deleteFromPath) [Row=" + row + ",Column=" + column + "] out of bounds.");
		}
	}

	/**
	 * Removes the path.
	 * 
	 */
	public void deletePath() {
		for (int i=0; i<labels.length; i++) {
			for (int j=0; j<labels[i].length; j++) {
				deleteFromPath(i, j);
			}
		}
	}

	// Checks that the chosen cell is within the board limits.
	private boolean checkCell(int row, int column) {
		return row>=0 && row<labels.length && column>=0 && column<labels[row].length;
	}

	// Represents the function of the cell on its position.
	private void updateScenario(int row, int column, String value) {
		if (checkCell(row, column)) {
			switch(value) {
			case ENTRANCE_REPRESENTATION:
				entrance = labels[row][column];
				labels[row][column].setText(value);
				labels[row][column].setFont(labels[row][column].getFont().deriveFont(Font.BOLD));
				labels[row][column].setBackground(ENTRANCE_COLOR);
				break;
			case TREASURE_REPRESENTATION:
				treasure = labels[row][column];
				labels[row][column].setText(value);
				labels[row][column].setFont(labels[row][column].getFont().deriveFont(Font.BOLD));
				labels[row][column].setBackground(TREASURE_COLOR);
				break;
			case WALL_REPRESENTATION:
				labels[row][column].setBackground(WALL_COLOR);
				break;
			default: if (!value.equals("0")) labels[row][column].setText(value);
			}
		} else {
			System.out.println("ERROR - (updateScenario) [Row=" + row + ",Column=" + column + "] out of bounds.");
		}
		String aux = "dasdasd";
		aux.indexOf("\"\"");
	}

}
