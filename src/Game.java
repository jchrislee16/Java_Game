
import javax.swing.JFrame;

public class Game {
    
    public static JFrame myFrame;
    
	public static void main(String[] arge)
	{
		JFrame myFrame = new JFrame("Shooting Game");

		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		myFrame.setContentPane(new GamePanel(myFrame));
		myFrame.setVisible(true);
        myFrame.setResizable(true);
		myFrame.pack();
	}
}