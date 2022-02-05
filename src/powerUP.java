import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class powerUP 
{
	//private variables
	private double x;
	private double y;
	private int r;

	private double dx;
	private double dy;
	private double rad;
	private int speed;

	private Color color;


	private int count;
	private boolean bouncing;
	private int type;





	public powerUP(int x , int y, int type)
	{

		count = 0;
		this.type = type;
		this.x = x;
		this.y = y;
		this.r = (int)(GamePanel.WIDTH/160);

		double angle = Math.random() * 140 +20;
		rad = Math.toRadians(angle);

		speed = 5;
		this.dx = Math.cos(rad) * speed;
		this.dy = Math.sin(rad) * speed;;

		bouncing = false;

		if(type == 1)
			color = Color.WHITE;
		else if (type == 2)
			color = Color.GREEN;
                else if (type == 3)
                {
                    color = Color.RED;
                }


	}


	public double getx()
	{
		return x;
	}
	public double gety()
	{
		return y;
	}
	public int getr()
	{
		return r;
	}
	public boolean isBouncing()
	{
		return bouncing;
	}






	public void update()
	{
            
		x += dx;
		y += dy;


		if(x<r && dx < 0)
		{
			dx = -dx;
			count++;
		}
		if(y < r && dy < 0)
		{
			dy = -dy;
			count++;
		}
		if(x > GamePanel.WIDTH -r && dx > 0){
			dx = -dx;
			count++;
		}
		if(y > GamePanel.HEIGHT -r && dy > 0)
		{
			dy = -dy;
			count++;
		}


		if(count == 3)
			bouncing = true;





	}




	public void draw(Graphics2D g)
	{
		g.setColor(color);
		g.fillRect((int)(x-r),(int)(y-r),(int)(r*2),(int)(r*2));
		g.setStroke(new BasicStroke(4));
		g.setColor(color.darker());
		g.drawRect((int)(x-r),(int)(y-r),(int)(r*2),(int)(r*2));
		g.setStroke(new BasicStroke(1));



	}













}
