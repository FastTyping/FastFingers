import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class GameUI extends JFrame implements KeyListener {

	private JPanel contentPane;
	private static JTextField textField = new JTextField();
	private JTextPane textPane;
	private static int currentWord = 0;
	private static int counter = 0;
	
	private static String text = Text.gererateRandomText();
	private static String[] words = text.split("[ ]+");
	
	private double startTime = -1;
	private boolean isTimeStarted = false;
	private double endTime = -1;
	
	private Color green = new Color(76, 192, 111);
	private Highlighter highlighter;
	private HighlightPainter gray = new DefaultHighlighter.DefaultHighlightPainter(new Color(219, 218, 217));
	private Object a;
	private int index1 = 0;
	private int index2 = words[0].length();
	
	private static String[] origin = { "name", "macAddress", "result", "words", "ready" };
	private static String[] send = new String[5];
	private String[] macAddress = { "macAddress" };
	private String[] sendMacAddress = new String[1];
	private static String[] randomText = { "words2" };
	private static String[] sendRandomText = new String[1];
	
	private static String username = null;
	
	private static String post = "http://46.229.197.244:8080/example/test.php";
	private static String textGet = "http://46.229.197.244:8080/example/words.php";
	
	private static JProgressBar progressBar;
	private static JProgressBar progressBar2;	
	private static JProgressBar progressBar3;
	private static JProgressBar progressBar4;
	
	private int counterWords = 0;
	private int indexToDelete;
	private static JProgressBar[] progressBars;
	private static String[] playerResults;
	private static int[] playerWPM;
	private static int[] playerWords;
	private static String[] playerNames;
	private static JLabel[] wPMLabels;
	
	private static boolean isReady = true;
	private static boolean isGameFinished = true;
	
	private static JLabel lblUser1;
	private static JLabel lblUser2;
	private static JLabel lblUser3;
	private static JLabel lblUser4;
	
	private JButton btnReady;
	
	private JLabel lblWords4;
	private JLabel lblWords3;
	private JLabel lblWords2;
	private JLabel lblWords1;

	/**
	 * Launch the application.
	 */
	public static void play() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
					getRandomText();
					System.out.println(text);
					
					GameUI frame = new GameUI();
					frame.setTitle("Fast Typer");
					frame.setVisible(true);

					try {
						initSendValues();
						httpPost(post, origin, send);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					getPlayerResults();
					
					showProgressBars();

					Timer sendInformations = new Timer();
					sendInformations.schedule(new TimerTask() {

						@Override
						public void run() {
							getPlayerResults();
						}
					}, 1000, 1000);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GameUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 829, 685);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textPane = new JTextPane();
		textPane.setText(text);
		textPane.setBounds(144, 208, 552, 263);
		textPane.setFont(new Font("Calibri", 1, 20));
		textPane.setFocusable(false);
		contentPane.add(textPane);

		textField = new JTextField();
		textField.setBounds(144, 502, 552, 39);
		textField.addKeyListener(this);
		textField.setFont(new Font("Calibri", 1, 20));
		textField.setFocusable(false);
		contentPane.add(textField);
		textField.setColumns(10);

		progressBar = new JProgressBar();
		progressBar.setBounds(144, 164, 552, 21);
		progressBar.setVisible(false);
		contentPane.add(progressBar);

		progressBar2 = new JProgressBar();
		progressBar2.setBounds(144, 132, 552, 21);
		progressBar2.setVisible(false);
		contentPane.add(progressBar2);

		btnReady = new JButton("Ready");
		btnReady.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (btnReady.getText().equals("Ready")) {
					send[4] = "Yes";
					btnReady.setEnabled(false);			
					try {
						httpPost(post, origin, send);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					startNewGame();
				}
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event){
				try {
					sendMacAddress[0] = getMacAddress();
					httpPost("http://46.229.197.244:8080/example/cleartest.php", macAddress, sendMacAddress);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btnReady.setBounds(596, 552, 104, 33);
		contentPane.add(btnReady);

		progressBar3 = new JProgressBar();
		progressBar3.setBounds(144, 100, 552, 21);
		progressBar3.setVisible(false);
		contentPane.add(progressBar3);

		progressBar4 = new JProgressBar();
		progressBar4.setBounds(144, 68, 552, 21);
		progressBar4.setVisible(false);
		contentPane.add(progressBar4);

		progressBars = new JProgressBar[] { progressBar, progressBar2, progressBar3, progressBar4 };

		lblUser1 = new JLabel();
		lblUser1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUser1.setBounds(33, 164, 101, 21);
		lblUser1.setFont(new Font("Calibri", 1, 20));
		contentPane.add(lblUser1);

		lblUser2 = new JLabel();
		lblUser2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUser2.setBounds(33, 132, 101, 21);
		lblUser2.setFont(new Font("Calibri", 1, 20));
		contentPane.add(lblUser2);

		lblUser3 = new JLabel();
		lblUser3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUser3.setBounds(33, 100, 101, 21);
		lblUser3.setFont(new Font("Calibri", 1, 20));
		contentPane.add(lblUser3);

		lblUser4 = new JLabel();
		lblUser4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUser4.setBounds(33, 68, 101, 21);
		lblUser4.setFont(new Font("Calibri", 1, 20));
		contentPane.add(lblUser4);
		
		lblWords1 = new JLabel();
		lblWords1.setBounds(716, 164, 57, 21);
		lblWords1.setFont(new Font("Calibri", 1, 20));
		contentPane.add(lblWords1);
		
		lblWords2 = new JLabel();
		lblWords2.setBounds(716, 132, 57, 21);
		lblWords2.setFont(new Font("Calibri", 1, 20));
		contentPane.add(lblWords2);
		
		lblWords3 = new JLabel();
		lblWords3.setBounds(716, 100, 57, 21);
		lblWords3.setFont(new Font("Calibri", 1, 20));
		contentPane.add(lblWords3);
		
		lblWords4 = new JLabel();
		lblWords4.setBounds(716, 68, 57, 21);
		lblWords4.setFont(new Font("Calibri", 1, 20));
		contentPane.add(lblWords4);
		
		wPMLabels = new JLabel[] { lblWords1, lblWords2, lblWords3, lblWords4 };
		try {
			highlighter = textPane.getHighlighter();
			a = highlighter.addHighlight(index1, index2, gray);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	private void startNewGame() {
		btnReady.setText("Ready");
		send[4] = "Not";			
		currentWord = 0;
		counter = 0;
		
		getRandomText();
		
		index1 = 0;
		index2 = words[0].length();
		try {
			highlighter.changeHighlight(a, index1, index2);
			initSendValues();
			httpPost(post, origin, send);
		} catch (BadLocationException | IOException e1) {
			e1.printStackTrace();
		}
		
		isTimeStarted = false;
		counterWords = 0;
		textPane.setText(text);
		textField.setText(null);
		textField.setFocusable(false);
	}
	
	private static void getRandomText(){
		try {
			if(httpGet(textGet).equals("")){
				sendRandomText[0] = Text.gererateRandomText();
				httpPost(textGet, randomText, sendRandomText);
			}
			text = httpGet(textGet);
		} catch (IOException e) {
			e.printStackTrace();
		}
		words = text.split("[ ]+");
	}
	
	private static void initSendValues() throws UnknownHostException, SocketException {
		if(username == null){
			username = JOptionPane.showInputDialog("Enter your name : ");
		}
		send[0] = username;
		send[1] = getMacAddress();
		send[2] = "0 WPM";
		send[3] = "0 Woords";
		send[4] = "Not";
	}
	
	private static void showProgressBars(){
		int numberOfPlayers = playerResults.length - 1;
		switch (numberOfPlayers) {
		case 4:
			progressBar4.setVisible(true);
			lblUser4.setText(playerNames[3]);
		case 3:
			progressBar3.setVisible(true);
			lblUser3.setText(playerNames[2]);
		case 2:
			progressBar2.setVisible(true);
			lblUser2.setText(playerNames[1]);
		case 1:
			progressBar.setVisible(true);
			lblUser1.setText(playerNames[0]);
		default:
			break;
		}
	}

	private static void getPlayerResults() {
		String result = "";
		try {
			result = httpGet(post);
		} catch (IOException e) {
			e.printStackTrace();
		}
		result = result.replaceFirst(send[0], "You");
		result = result.replaceAll("<br>", "\n");

		playerResults = result.split("[@]+");
		playerWords = new int[playerResults.length];
		playerNames = new String[playerResults.length];
		playerWPM = new int[playerResults.length];
		isReady = true;
		isGameFinished = true;
		
		for (int i = 0; i < playerResults.length - 1; i++) {
			initPlayerInfo(i);
		}
		for (int i = playerResults.length - 1; i >= 0; i--) {
			progressBars[i].setValue(playerWords[i] * 100 / 99);
		}
		startGameIfReady();
		clearDataIfFinished();	
		showProgressBars();
	}

	private static void clearDataIfFinished() {
		if (isGameFinished) {
			clearData(origin, send);
			try {
				sendRandomText[0] = "";
				httpPost(textGet, randomText, sendRandomText);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void startGameIfReady() {
		if (isReady) {
			if(currentWord != words.length){
				textField.setFocusable(true);
				textField.requestFocusInWindow();
			} else {
				textField.setFocusable(false);
			}
		}
	}
	
	private static void initPlayerInfo(int i){
		int endIndexOfName = playerResults[i].indexOf("Result");
		int endIndexOfWPM = playerResults[i].indexOf("WPM");
		int startIndexOfWords = playerResults[i].indexOf("Words");
		int endIndexOfWords = playerResults[i].indexOf("Woords");
		int startIndexOfReady = playerResults[i].indexOf("Ready");
		
		playerWPM[i] = Integer.valueOf(playerResults[i].substring(endIndexOfName + 8, endIndexOfWPM - 1));
		wPMLabels[i].setText(String.valueOf(playerWPM[i]));
		playerNames[i] = playerResults[i].substring(7, endIndexOfName - 2);
		playerWords[i] = Integer.valueOf(playerResults[i].substring(startIndexOfWords + 7, endIndexOfWords - 1));
		
		String ready = playerResults[i].substring(startIndexOfReady + 7, startIndexOfReady + 10);
		if (ready.equals("Not")) {
			isReady = false;
		}
		if (playerWords[i] < 100) {
			isGameFinished = false;
		}
	}

	private void appendToPane(JTextPane text, String str, Color c, int index) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
		aset = sc.addAttribute(aset, new Font("Calibri", 1, 20), "Lucida Console");
		text.setSelectionStart(index1);
		text.setSelectionEnd(index2);
		text.setSelectedTextColor(c);
		text.setCharacterAttributes(aset, false);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!isTimeStarted) {
			startTime = System.currentTimeMillis();
			isTimeStarted = true;
		}
		if (e.getKeyChar() == ' ') {
			indexToDelete = textField.getText().length();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		double time;
		if (e.getKeyChar() == ' ') {
			counterWords++;
			moveToNextWord();
			
			postProgress();
			
			highlightNextWord();
		}
		// Text has ended
		if (currentWord == words.length) {
			textField.setFocusable(false);
			endTime = System.currentTimeMillis();
			time = (endTime - startTime) / 1000;
			textField.setText("" + time + " seconds, " + (int) ((counter / time) * 60) + "WPM!");
			btnReady.setText("Play Again");
			btnReady.setEnabled(true);
		}
	}

	private void highlightNextWord() {
		if (currentWord != words.length) {
			index1 = textPane.getText().indexOf(words[currentWord], index2);
			index2 = index1 + words[currentWord].length();
			try {
				highlighter.changeHighlight(a, index1, index2);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void postProgress() {
		double time;
		int progress = (counterWords * 100 / 99);
		try {
			endTime = System.currentTimeMillis();
			time = (endTime - startTime) / 1000;
			int wPM = ((int) ((counter / time) * 60));
			send[2] = (wPM + " WPM");
			send[3] = String.valueOf(progress) + " Woords";
			httpPost(post, origin, send);

		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	private void moveToNextWord() {
		if (currentWord == 0) {
			if (textField.getText().substring(0, indexToDelete).equals(words[currentWord])) {
				counter++;
				appendToPane(textPane, words[currentWord], green, index1);
			} else {
				appendToPane(textPane, words[currentWord], Color.RED, index1);
			}
		} else if (textField.getText().substring(0, indexToDelete).equals(" " + words[currentWord])) {
			counter++;
			appendToPane(textPane, words[currentWord], green, index1);
		} else {
			appendToPane(textPane, words[currentWord], Color.RED, index1);
		}
		textField.setText(textField.getText().substring(indexToDelete));
		currentWord++;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public static String getMacAddress() throws UnknownHostException, SocketException {
		InetAddress ip = InetAddress.getLocalHost();

		NetworkInterface network = NetworkInterface.getByInetAddress(ip);
		byte[] mac = network.getHardwareAddress();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++) {
			sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
		}
		return sb.toString();
	}

	public static void clearData(String[] origin, String[] send) {
		try {
			httpPost("http://46.229.197.244:8080/example/cleartest.php", origin, send);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public static String httpGet(String urlStr) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		if (conn.getResponseCode() != 200) {
			throw new IOException(conn.getResponseMessage());
		}

		// Buffer the result into a string
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();

		conn.disconnect();
		return sb.toString();
	}

	public static void httpPost(String urlStr, String[] paramName, String[] paramVal) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setUseCaches(false);
		conn.setAllowUserInteraction(false);
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		// Create the form content
		OutputStream out = conn.getOutputStream();
		Writer writer = new OutputStreamWriter(out, "UTF-8");
		// writer.write("?");
		for (int i = 0; i < paramName.length; i++) {
			writer.write(paramName[i]);
			writer.write("=");
			writer.write(URLEncoder.encode(paramVal[i], "UTF-8"));
			writer.write("&");
		}
		writer.close();
		out.close();

		if (conn.getResponseCode() != 200) {
			throw new IOException(conn.getResponseMessage());
		}
		// Buffer the result into a string
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();

		conn.disconnect();
		// return sb.toString();
	}
}
