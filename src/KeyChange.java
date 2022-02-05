import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyChange implements KeyListener{

	
	
	private static int newKey = 0;
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		newKey = e.getKeyCode();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		newKey = e.getKeyCode();
	}
	
	public static int change()
	{
		while(true)
		{
			System.out.println("press Please");
			if(newKey != 0)
			{
				System.out.println("key is pressed");
				break;
			}
		}
		
		return newKey;
	}
	
	
	

}
