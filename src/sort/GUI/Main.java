package sort.GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

class Main {
	private static JFrame frame; // main java swing frame

	// header object declarations
	private static JPanel headerPanel; // panel for header
	private static JLabel currentArrayLabel; // label to store current array
	// center object declarations
	// mainly used for showing the 'action', or the sorting process
	private static JPanel centerPanel; // panel for center

	private static JTextArea countingTextArea; // textArea to show counting sort
	private static JScrollPane countingScrollPane;
	private static JTextArea radixTextArea; // textArea to show radix sort
	private static JScrollPane radixScrollPane;
	// footer object declarations
	private static JPanel optionsPanel;
	private static JButton randomizeNumbersBtn; // create new random array of ints
	private static JButton sortBtn; // initiate sorting process

	private static int[] randData; // stores current array to be sorted

	public static void initializeGUI() {
		randData = new int[15];
		// Initializing random array
		for (int i = 0; i < randData.length; i++) {
			randData[i] = (int) (Math.random() * randData.length);
		}
		System.out.println("Random data generated!"); // debug message

		// Initialize GUI
		frame = new JFrame("Sorting Algorithms");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationByPlatform(true);
		frame.setSize(900, 500);
		frame.setResizable(false);
		System.out.println("GUI Initialized!"); // debug

		// Create panel for top
		headerPanel = new JPanel();
		// Label for displaying the current array
		currentArrayLabel = new JLabel(String.format("<html><div WIDTH=%d>%s %s</div></html>", frame.getWidth() - 50,
				"Current Array: ", Arrays.toString(randData)), SwingConstants.CENTER);
		headerPanel.add(currentArrayLabel);

		// Create panel for center
		centerPanel = new JPanel(new GridLayout(2, 2));
		centerPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),
				BorderFactory.createLoweredBevelBorder()));

		// TextArea for displaying output of counting sort
		countingTextArea = new JTextArea();
		countingTextArea.setEditable(false);
		// Scroll pane to store counting sort text area
		countingScrollPane = new JScrollPane(countingTextArea);
		countingTextArea.setText("Counting Sort: \n");
		countingTextArea.setLineWrap(true);
		countingTextArea.setWrapStyleWord(true);
		centerPanel.add(countingScrollPane);

		// TextArea for displaying output of radix sort
		radixTextArea = new JTextArea();
		radixTextArea.setEditable(false);
		// Scroll pane to store radix sort text area
		radixScrollPane = new JScrollPane(radixTextArea);
		radixTextArea.setText("Radix Sort: \n");
		radixTextArea.setLineWrap(true);
		radixTextArea.setWrapStyleWord(true);
		centerPanel.add(radixScrollPane);

		// Creating menu panel at bottom
		optionsPanel = new JPanel();

		// Generate new set of random numbers
		randomizeNumbersBtn = new JButton("Randomize Numbers");
		randomizeNumbersBtn.setActionCommand("Randomize");
		randomizeNumbersBtn.addActionListener(new ButtonClickListener());

		// Start sorting array
		sortBtn = new JButton("Sort!");
		sortBtn.setActionCommand("Sort");
		sortBtn.addActionListener(new ButtonClickListener());

		// Add buttons to options panel
		optionsPanel.add(randomizeNumbersBtn);
		optionsPanel.add(sortBtn);

		// align and add panes to frame
		frame.getContentPane().add(BorderLayout.NORTH, headerPanel);
		frame.getContentPane().add(BorderLayout.SOUTH, optionsPanel);
		frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
		// turn on frame
		frame.setVisible(true);
	}

	private static void countingSort(int arr[]) {
		int n = arr.length;

		int counting[] = new int[100];

		int output[] = new int[n];

		int x = 0;
		while (x < 100) {
			counting[x] = 0;
			x++;
		}

		int i = 0;
		while (i < n) {
			++counting[arr[i]];
			i++;
		}

		for (int c = 1; c < 100; ++c) {
			counting[c] += counting[c - 1];
		}

		for (int v = 0; v < n; ++v) {
			output[counting[arr[v]] - 1] = arr[v];
			--counting[arr[v]];
		}

		for (int b = 0; b < n; ++b) { // put sorted output array back into passed array
			arr[b] = output[b];
		}

	}

	/*
	 * Insert Radix Sort Here
	 */

	public static void main(String[] args) {
		// calls initializeGUI to initialize the window and components
		initializeGUI();
	}

	/*
	 * ButtonClickListener is a class that allows for event handlers to be captured
	 * from the functional buttons. This class is designed to be used with the
	 * JFrame and JTextArea in the main class, thus needs the references to those
	 * objects
	 */
	private static class ButtonClickListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				String command = e.getActionCommand();
				// Switch on command passed
				switch (command) {
				// Generate a new array to sort for both algorithms
				case "Randomize":
					System.out.println("Randomize button pressed");
					// Display message asking user how many elements they want
					String result = (String) JOptionPane.showInputDialog(frame,
							"How many random numbers would you like?", null, JOptionPane.PLAIN_MESSAGE, null, null,
							null);
					// Check if they pressed cancel on the dialog box
					if (result != null) {
						// if result is able to be parsed, then parse
						int resultNum = Integer.parseInt(result);
						if (resultNum > 0 && resultNum < 250) {
							// create new array of size resultNum
							randData = new int[resultNum];
							// populate array with random numbers
							for (int i = 0; i < randData.length; i++) {
								randData[i] = (int) (Math.random() * randData.length);
							}
							// display message indicating array has be created
							JOptionPane.showMessageDialog(frame, "New array generated!\n");
							// update currentArray label with correct formatting
							currentArrayLabel.setText(String.format("<html><div WIDTH=%d>%s %s</div></html>",
									frame.getWidth() - 50, "Current Array: ", Arrays.toString(randData)));
						} else {
							// number too small or too big
							JOptionPane.showMessageDialog(frame,
									"Please choose a number greater than 0 and less than 250!");
						}
					}
					// Otherwise, they cancelled, and nothing happens
					break;
				case "Sort":
					// Sort button has been pressed, thus the algorithms will
					// sort the current array
					System.out.println("Sort button pressed");
					// clears text areas for new text
					countingTextArea.setText("Counting Sort: \n\n");
					radixTextArea.setText("Quick Sort: \n\n");
					// counting sort called
					// starts timing counting sort
					long startTime = System.nanoTime();
					// sleeps to align timer
					TimeUnit.SECONDS.sleep(1);
					countingSort(Arrays.copyOf(randData, randData.length));
					// stops timer
					long endTime = System.nanoTime();
					// calculates elapsed time for counting sort to finish
					countingTextArea.append("Counting Sort took " + ((endTime - startTime) / 1000000) + "ms to sort "
							+ randData.length + " items.\n");

					// radix sort called
					// starts new timer
					startTime = System.nanoTime();
					// new timer alignment
					TimeUnit.SECONDS.sleep(1);
					radixSort(Arrays.copyOf(randData, randData.length), 0, randData.length - 1);
					// stops timer
					endTime = System.nanoTime();
					// calculates elapsed time for radix sort to finish
					radixTextArea.append("Radix Sort took " + ((endTime - startTime) / 1000000) + "ms to sort "
							+ randData.length + " items.\n");
					break;
				}
			} catch (NumberFormatException nfe) {
				// Catch the user not inputting a number in the dialog box
				JOptionPane.showMessageDialog(null, "Please enter a valid number!");
			} catch (InterruptedException ie) {
				// Catch interrupted execution from sleep function
				System.err.print("Interrupted during execution!");
			}
		}
	}
}