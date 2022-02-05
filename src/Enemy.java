
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Enemy {

    //private variables
    private double x;
    private double y;
    private double r;

    private double dx;
    private double dy;
    private double speed;
    private double rad;

    public int health;
    private int maxHealth;
    private int maxHealthParameter;
    private int type;
    private int rank;

    private long timer;

    private Color color1;
    private Color color2;
    private Color color3;
    private boolean ready;
    private boolean dead;
    private boolean hitted;
    private long hittedTimer;

    public Enemy(int type, int rank) {
        this.type = type;
        this.rank = rank;
        /*Rank 1 = normal
		 * Rank 2 = Getting Bigger
		 * Rank 3 = Shooting monster
		 * Rank 4 = multiples
                 * Rank 5 = laser
                 * Rank 6 = 
                 * Rank 7 = 
         */
        if (type == 1) {
            color1 = Color.BLUE;
            color2 = new Color(0, 0, 255, 128);
            color3 = Color.WHITE;
            if (rank == 1) {
                speed = 5;
                r = (int) (GamePanel.WIDTH / 80);
                health = 3;
            }
            if (rank == 2) {
                speed = 5;
                r = (int) (GamePanel.WIDTH / 80);
                health = (int) r;
            }
            if (rank == 3) {
                speed = 25
;                r = (int) (GamePanel.WIDTH / 80);
                health = 15;
                timer = System.nanoTime();
            }
        }

        maxHealth = health;

        x = Math.random() * GamePanel.WIDTH / 2 + GamePanel.WIDTH / 4;
        y = -r;

        double angle = Math.random() * 140 + 20;

        rad = Math.toRadians(angle);

        dx = Math.cos(rad) * speed;
        dy = Math.sin(rad) * speed;

        hitted = false;
        hittedTimer = System.nanoTime();
        ready = false;
        dead = false;

    }

    //function
    public double getx() {
        return x;
    }

    public double gety() {
        return y;
    }

    public double getr() {
        return r;
    }

    public boolean isDead() {
        return dead;
    }

    public void hit(int damage) {
        hitted = true;
        hittedTimer = System.nanoTime();
        if (rank == 2) {
            r -= damage;
            health = health - damage;
            if (health <= (int) (GamePanel.WIDTH / 80)) {
                dead = true;
            }
        }
        health = health - damage;
        if (health <= 0) {
            dead = true;
        }
    }

    public void update() {
        x += dx;
        y += dy;
        if (rank == 2) {
            r += 0.2;
            health = (int) r - (int) (GamePanel.WIDTH / 80);
            if (health > maxHealth) {
                maxHealth = health;
            }
            if (r > 100) {
                r = 100;
            }
        }
        if (rank == 3) {
            if ((System.nanoTime() - timer)/1000000 > 2500) {
                //Don't forget to make this fire bullets.  
                EnemyBullet bullets1 = new EnemyBullet(0, (int)x, (int)y, type);
                EnemyBullet bullets2 = new EnemyBullet(90, (int)x, (int)y, type);
                EnemyBullet bullets3 = new EnemyBullet(180, (int)x, (int)y, type);
                EnemyBullet bullets4 = new EnemyBullet(270, (int)x, (int)y, type);
                GamePanel.enemybullets.add(bullets1);
                GamePanel.enemybullets.add(bullets2);
                GamePanel.enemybullets.add(bullets3);
                GamePanel.enemybullets.add(bullets4);
                timer = System.nanoTime();
            }
            else
            {
                
            }

        }

        if (!ready) {
            if (x > r && x < GamePanel.WIDTH - r && y > r && y < GamePanel.HEIGHT - r) {
                ready = true;
            }
        }

        if ((x < r && dx < 0) || (x > GamePanel.WIDTH - r && dx > 0)) {
            dx = -dx;
        }
        if ((y < r && dy < 0) || (y > GamePanel.HEIGHT - r && dy > 0)) {
            dy = -dy;
        }

        long elapsed = (System.nanoTime() - hittedTimer) / 1000000;
        if (elapsed > 200) {
            hitted = false;
            hittedTimer = 0;
        }

    }

    public void draw(Graphics2D g) {
        if (hitted) {

            g.setColor(color2);

            g.fillOval((int) (x - r), (int) (y - r), (int) (2 * r), (int) (2 * r));
            g.setStroke(new BasicStroke(3));
            g.setColor(color1.darker());
            g.drawOval((int) (x - r), (int) (y - r), (int) (2 * r), (int) (2 * r));
            g.setStroke(new BasicStroke(1));
            g.setColor(Color.BLACK.brighter());
            g.fillRect((int) (x - r), (int) (y - r - r / 4), (int) (2 * r) * health / maxHealth, (int) (r / 5));

        } else {
            g.setColor(color1);
            g.fillOval((int) (x - r), (int) (y - r), (int) (2 * r), (int) (2 * r));
            g.setStroke(new BasicStroke(3));
            g.setColor(color1.darker());
            g.drawOval((int) (x - r), (int) (y - r), (int) (2 * r), (int) (2 * r));
            g.setStroke(new BasicStroke(1));
            g.setColor(Color.BLACK.brighter());
            g.fillRect((int) (x - r), (int) (y - r - r / 4), (int) (2 * r) * health / maxHealth, (int) (r / 5));

        }

    }

}
