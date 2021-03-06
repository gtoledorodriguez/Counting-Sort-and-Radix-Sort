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

		int counting[] = new int[100]; // array for counting variables

		int output[] = new int[n]; // array that will be the sorted array

		int x = 0;
		while (x < 100) { // initializing array values to zero
			counting[x] = 0;
			x++;
		}

		int i = 0;
		while (i < n) { // stores count of all numbers
			++counting[arr[i]];
			i++;
		}

		for (int c = 1; c < 100; ++c) { // changing count to actual position
			counting[c] += counting[c - 1];
			countingTextArea.append("Counting array: " + Arrays.toString(counting) + "\n\n");
		}

		for (int v = 0; v < n; ++v) { // filling values of output array
			output[counting[arr[v]] - 1] = arr[v];
			--counting[arr[v]];
			countingTextArea.append("Counting array: " + Arrays.toString(counting) + "\n\n");
		}

		for (int b = 0; b < n; ++b) { // put sorted output array back into passed array
			arr[b] = output[b];
		}

		countingTextArea.append("Sorted array: " + Arrays.toString(output) + "\n\n");
	}

	private static int getMax(int arr[], int n) { // Gets the max value in array
		int max = arr[0];
		for (int i = 1; i < n; i++) {
			if (arr[i] > max) {
				max = arr[i];
			}
		}
		return max;
	}

	private static void radixCountSort(int arr[], int n, int exp) {// Does counting sort on the array according to the
																	// digit represented by exp
		// Can not use the one from the other file because
		// we need 3 inputs
		// Will use the code from CountingSort as a base

		int counting[] = new int[100];

		int output[] = new int[n];

		int i;

		for (i = 0; i < n; i++) { // counts the number of occurrences in counting[]
			counting[(arr[i] / exp) % 10]++;
		}

		for (i = 1; i < 10; i++) { // count will contain the real position of the digit
			counting[i] += counting[i - 1];
		}

		for (i = n - 1; i >= 0; i--) { // Builds the output array
			output[counting[(arr[i] / exp) % 10] - 1] = arr[i];
			counting[(arr[i] / exp) % 10]--;
		}

		for (int b = 0; b < n; ++b) { // put sorted output array back into passed array
			arr[b] = output[b]; // contains sorted numbers according to current digit
		}

	}

	private static void radixSort(int[] arr) {
		int n = arr.length;
		int max = getMax(arr, n); // Used to find the max # to know how many digits it contains
		radixTextArea.append("Max: " + max + "\n");
		for (int exp = 1; max / exp > 0; exp *= 10) { // Do counting sort for every digit
			radixCountSort(arr, n, exp); // exp is 10^i where i the the current digit number
			radixTextArea.append(Arrays.toString(arr) + "\n\n");
		}

		radixTextArea.append("Sorted Array: " + Arrays.toString(arr) + "\n\n");
	}

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
						if (resultNum > 0 && resultNum < 101) {
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
									"Please choose a number greater than 0 and less than 101!");
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
					radixTextArea.setText("Radix Sort: \n\n");

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
					radixSort(Arrays.copyOf(randData, randData.length));
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