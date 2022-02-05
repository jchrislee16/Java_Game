import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Coin 
{
	//private variable
	private double x;
	private double y;
	private int r;


	private int value;


	private double dx;
	private double dy;
	private double speed;

	private Color color1;

	public Coin(int x , int y , int value)
	{
		this.x = x;
		this.y = y;
		r = 14;
		this.value = value;
		color1 = new Color(224,151,112);
		speed = 10;

		double rand = Math.random()*360;
		double rad = Math.toRadians(rand);
		dx = Math.cos(rad) * speed;
		dy = Math.sin(rad)* speed;










	}


	//Start Getter
	public double getx()
	{return x;}
	public double gety()
	{return y;}
	public int getr()
	{return r;}
	public int getValue()
	{return value;}
	//End Getter





	public void update()
	{
		x += dx;
		y += dy;

		if(x<r && dx < 0)
		{
			dx = -dx;
		}
		if(y < r && dy < 0)
		{
			dy = -dy;
		}
		if(x > GamePanel.WIDTH -r && dx > 0){
			dx = -dx;
		}
		if(y > GamePanel.HEIGHT -r && dy > 0)
		{
			dy = -dy;
		}
	}



	public void draw(Graphics2D g)
	{
		g.setColor(color1);
                if(value >= 10)
                {
                    g.setColor(Color.GRAY.brighter());
                }
		g.fillOval((int)(x-r), (int)(y-r), r*2, r*2);
		g.setStroke(new BasicStroke(3));
		g.setColor(color1.brighter());
		g.drawOval((int)(x-r), (int)(y-r), r*2, r*2);
		g.setStroke(new BasicStroke(1));
		g.setFont(new Font("Times New Roman",Font.BOLD, (r*2 - 6)) );
		String s = "C";
		g.setColor(Color.WHITE);
		int Length = (int)(g.getFontMetrics().getStringBounds(s, g).getWidth());
		g.drawString(s, (int)(x-8), (int)(y+9));
		g.setStroke(new BasicStroke(2));
		g.setColor(new Color(43,46,51));
		g.drawString(s, (int)(x-8), (int)(y+8));
		g.setStroke(new BasicStroke(1));

	}









}
