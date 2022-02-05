
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Boss {

    //private variables
    private double x;
    private double y;
    private double r;

    private double dx;
    private double dy;
    private double speed;
    private double rad;

    private int health;
    private int maxHealth;
    private int maxHealthParameter;
    private int type;
    private int rank;
    private String name;

    private Color color1;
    private Color color2;
    private Color color3;
    private boolean ready;
    private boolean dead;
    private boolean hitted;
    private long hittedTimer;
    private long VectorTimer;

    public Boss(int type, int rank, String name) {
        this.type = type;
        this.rank = rank;
        this.name = name;
        color1 = Color.BLUE;

        x = GamePanel.WIDTH / 2;
        y = -r;
        r = 50;

        health = 100;
        maxHealth = 100;
        speed = 10;
        
        double angle = Math.random() * 140 + 20;
        rad = Math.toRadians(angle);
        dx = Math.cos(rad) * speed;
        dy = Math.sin(rad) * speed;
        
        VectorTimer = System.nanoTime();
    }
    
    public double getx()
    {
        return x;
    }
    
    public double gety()
    {
        return y;
    }
    
    public double getr()
    {
        return r;
    }
    public boolean isDead()
    {
        return dead;
    }
    
    
    
    
    
    public void hit(int Damage)
    {


        hitted = true;
        hittedTimer = System.nanoTime();
        health = health - Damage;
        if(health <= 0)
            dead = true;
        
        



        
    }
    
    
    
    
    

    public void update() {
    	long elapsed = (System.nanoTime() - VectorTimer)/1000000;
		if(elapsed > 3000)
		{
	        double angle = Math.random() * 360;
	        rad = Math.toRadians(angle);
	        dx = Math.cos(rad) * speed;
	        dy = Math.sin(rad) * speed;
			VectorTimer = System.nanoTime();
        }
    	
    	
    	
        x += dx;
        y += dy;

        if (x < r && dx < 0) {
            dx = -dx;
        }
        if (y < r && dy < 0) {
            dy = -dy;
        }
        if (x > GamePanel.WIDTH - r && dx > 0) {
            dx = -dx;
        }
        if (y > GamePanel.HEIGHT - r && dy > 0) {
            dy = -dy;
        }
        
        long elapsed2 = (System.nanoTime() - hittedTimer)/1000000;
		if(elapsed2 > 200)
		{
			hitted = false;
			hittedTimer = 0;
                }
        
    }

    public void draw(Graphics2D g) {
        g.setFont(new Font("Gothic", Font.PLAIN, GamePanel.WIDTH/70));
        g.setColor(Color.GRAY.brighter().brighter());
        int length = (int) g.getFontMetrics().getStringBounds(name, g).getWidth();
       g.drawString(name, (int)(x-length /2), (int)(y-(r*1.5)));

        if(hitted)
        {
         g.setColor(Color.RED.brighter());   
        }
        else
        {
        g.setColor(color1);
        }
        g.fillOval((int) (x - r), (int) (y - r), (int) (2 * r), (int) (2 * r));
        g.setStroke(new BasicStroke(3));
        g.setColor(color1.darker());
        g.drawOval((int) (x - r), (int) (y - r), (int) (2 * r), (int) (2 * r));
        g.setStroke(new BasicStroke(1));
        g.setColor(Color.BLACK.brighter());
        g.fillRect((int) (x - r), (int) (y - r - r / 4), (int) (2 * r) * health / maxHealth, (int) (r / 5));
        g.drawRect((int) (x - r), (int) (y - r - r / 4), (int) (2 * r) , (int) (r / 5));
    }

}
