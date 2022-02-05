
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class Player {

    //private Variables
    private int x;
    private int y;
    private int r;
    //direction and moving
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private int dx;
    private int dy;
    private int speed;
    private int direction;
    //lives and colors
    private int lives;
    private Color color1;
    private Color color2;

    private boolean recovering;
    private long recoveryTimer;

    private int damage;
    private int Choose;
    private int maxBullets;
    private int bulletSpeed;
    //Bullets Firing
    private boolean firing;
    private long firingTimer;
    private long firingDelay;
    //Coins
    private int money;

    public Player() {
        x = GamePanel.WIDTH / 2;
        y = GamePanel.HEIGHT / 2;
        r = (int) (GamePanel.WIDTH / 80);

        dx = 0;
        dy = 0;
        speed = 10;

        lives = 3;
        color1 = Color.WHITE;
        color2 = Color.RED.darker();

        firing = false;
        firingTimer = System.nanoTime();
        firingDelay = 500;
        direction = 270;
        //Upgrade
        damage = 1;
        Choose = 0;
        maxBullets = 5;
        bulletSpeed = 15;

        recovering = false;
        recoveryTimer = 0;

    }

    //Functions
    //Start Setters
    public void setLeft(boolean b) {
        left = b;
    }

    public void setRight(boolean b) {
        right = b;
    }

    public void setUp(boolean b) {
        up = b;
    }

    public void setDown(boolean b) {
        down = b;
    }

    public void addDamage() {
        damage++;
        if (damage >= 50) {
            damage = 50;
        }
    }

    public void addDamage(int i) {
        damage = damage + i;
        if (damage >= 50) {
            damage = 50;
        }
    }

    public void addSpeed() {
        speed++;
        if (speed >= 25) {
            speed = 25;
        }
    }

    public void addMaxBullet() {
        maxBullets++;
        if (maxBullets >= 15) {
            maxBullets = 15;
        }
    }

    public void rapidFire() {
        firingDelay -= 50;
        if (firingDelay <= 100) {
            firingDelay = 100;
        }
    }

    public void addBulletSpeed() {
        bulletSpeed++;
        if (bulletSpeed >= 50) {
            bulletSpeed = 50;
        }
    }

    public void loseLife() {
        lives--;
        recovering = true;
        recoveryTimer = System.nanoTime();
    }

    public void addCoins(int coins) {
    }

    public void addChoose() {
        Choose++;
        if (Choose >= 5) {
            Choose = 5;
        }
    }

    public void Upgrade(int choose) {
        if (choose == 1) {
            addDamage();
            Choose = 0;
        }
        if (choose == 2) {
            addSpeed();
            Choose = 0;
        }
        if (choose == 3) {
            addMaxBullet();
            Choose = 0;
        }
        if (choose == 4) {
            rapidFire();
            Choose = 0;
        }
        if (choose == 5) {
            addBulletSpeed();
            Choose = 0;
        }
    }

    public void pickupMoney(int money) {
        this.money += money;
    }
    //End Setter

    //All Getters
    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }

    public int getr() {
        return r;
    }

    public int getLives() {
        return lives;
    }

    public boolean isRecovering() {
        return recovering;
    }

    public int getDamage() {
        return damage;
    }

    public int getMaxBullets() {
        return maxBullets;
    }

    public int getChoose() {
        return Choose;
    }

    public int getSpeed() {
        return speed;
    }

    public int getBulletSpeed() {
        return bulletSpeed;
    }

    public int getBulletDelay() {
        return (int) firingDelay;
    }

    public int getMoney() {
        return money;
    }
    //End Getters

    public void setFiring(boolean b) {
        firing = b;
    }

    public void update() {
        if (left) {
            dx = -speed;
            direction = 180;
        }
        if (right) {
            dx = speed;
            direction = 0;
        }
        if (up) {
            dy = -speed;
            direction = 270;
        }
        if (down) {
            dy = speed;
            direction = 90;
        }

        x += dx;
        y += dy;

        if (x < r) {
            x = r;
        }
        if (y < r) {
            y = r;
        }
        if (x > GamePanel.WIDTH - r) {
            x = GamePanel.WIDTH - r;
        }
        if (y > GamePanel.HEIGHT - r) {
            y = GamePanel.HEIGHT - r;
        }

        dx = 0;
        dy = 0;

        if (firing) {
            long elapsed = (System.nanoTime() - firingTimer) / 1000000;
            if (elapsed > firingDelay) {
                if (GamePanel.bullets.size() >= maxBullets) {
                } else {
                    Bullet bullet = new Bullet(direction, x, y, GamePanel.getKey );
                    bullet.addSpeed(bulletSpeed);
                    GamePanel.bullets.add(bullet);

                    firingTimer = System.nanoTime();
                    /* Activating this will let the player rapid firing within given time of delay*/

                }
            }
            /*This stops from rapid firing
			  If you put this somewhere else, then all of 
			  bullets come out at the same time, 
			  creating a straight line
             */
            //firingTimer = System.nanoTime();
        }

        long elapsed = (System.nanoTime() - recoveryTimer) / 1000000;
        if (elapsed > 2000) {
            recovering = false;
            recoveryTimer = 0;
        }

    }

    public void draw(Graphics2D g) {

        if (recovering) {
            g.setColor(color2);
            g.fillOval(x - r, y - r, 2 * r, 2 * r);
            g.setStroke(new BasicStroke(3));
            g.setColor(color2.darker());
            g.drawOval(x - r, y - r, 2 * r, 2 * r);
            g.setStroke(new BasicStroke(1));

        } else {
            g.setColor(color1);
            g.fillOval(x - r, y - r, 2 * r, 2 * r);
            g.setStroke(new BasicStroke(3));
            g.setColor(color1.darker());
            g.drawOval(x - r, y - r, 2 * r, 2 * r);
            g.setStroke(new BasicStroke(1));
        }
        g.setColor(Color.BLUE);
        g.setStroke(new BasicStroke(3));
        if (direction == 90) {
            g.drawOval(x - r + 15, y - r + 30, 5, 5);
        }
        if (direction == 180) {
            g.drawOval(x - r + 3, y - r + 15, 5, 5);
        }
        if (direction == 270) {
            g.drawOval(x - r + 15, y - r + 3, 5, 5);
        }
        if (direction == 0) {
            g.drawOval(x - r + 28, y - r + 15, 5, 5);
        }
        g.setStroke(new BasicStroke(1));
    }

}
