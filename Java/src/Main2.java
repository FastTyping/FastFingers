import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Main2 {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);
		
		
		JLabel text = new JLabel("This is random on which this for being his her to on is with his the the on the with sometimes");
		frame.add(text);
		
		JTextArea input = new JTextArea();
		input.setSize(600, 50);
		frame.add(input);
		
		frame.setVisible(true);
		
	}

}
