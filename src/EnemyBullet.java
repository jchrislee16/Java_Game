

import java.awt.Color;
import java.awt.Graphics2D;

public class EnemyBullet 
{



    //private variables
    private double x;
    private double y;
    private int r;

    private double dx;
    private double dy;
    private double rad;
    private double speed;
    private int type;
    private int count;

    private Color color1;
    private int size;
    private int damage;

    public EnemyBullet(double angle, int x, int y) {
        this.x = x;
        this.y = y;
        r = (int) (GamePanel.WIDTH / 150);
        speed = 15;
        rad = Math.toRadians(angle);
        dx = Math.cos(rad) * speed;
        dy = Math.sin(rad) * speed;;
        damage = 1;

        color1 = Color.BLACK;
    }

    public EnemyBullet(double angle, int x, int y, int type) {
        this.x = x;
        this.y = y;
        r = (int) (GamePanel.WIDTH / 150);
        speed = 15;
        rad = Math.toRadians(angle);
        dx = Math.cos(rad) * speed;
        dy = Math.sin(rad) * speed;;
        damage = 1;

        this.type = type;
        if(type == 1)
        {
            color1 = Color.GRAY;
        }
        if (type == 2) {
            color1 = Color.GREEN.darker();
        }
        if (type == 3) {
            color1 = Color.RED.darker();
            count = 0;
        }
    }

    //function
    public double getDamage() {
        return damage;
    }

    public double getx() {
        return x;
    }

    public double gety() {
        return y;
    }

    public double getr() {
        return r;
    }

    public void addSpeed(int bulletSpeed) {
        speed = bulletSpeed;
        if (speed >= 50) {
            speed = 50;
        }
    }

    public boolean update() {
        /*if (type == 2) {
            for (int i = 0; i < GamePanel.enemies.size(); i++) {
                double ex = GamePanel.enemies.get(i).getx();
                double ey = GamePanel.enemies.get(i).gety();

                double bx = x;
                double by = y;

                if (Math.sqrt((ex - bx) * (ex - bx) + (ey - by) * (ey - by)) < 500) {
                       double rate = 0;
                    if (Math.abs(ex - bx) > Math.abs(ey - by)) {
                        rate = Math.abs(ex - bx);
                    } else {
                        rate = Math.abs(ey - by);
                    }
                    dx = (ex - bx) / rate * (speed - 4);
                    dy = (ey - by) / rate * (speed - 4);
                }

            }
            for (int i = 0; i < GamePanel.bosses.size(); i++) {
                double ex = GamePanel.bosses.get(i).getx();
                double ey = GamePanel.bosses.get(i).gety();

                double bx = x;
                double by = y;

                if (Math.sqrt((ex - bx) * (ex - bx) + (ey - by) * (ey - by)) < 500) {
                    double rate = 0;
                    if (Math.abs(ex - bx) > Math.abs(ey - by)) {
                        rate = Math.abs(ex - bx);
                    } else {
                        rate = Math.abs(ey - by);
                    }
                    dx = (ex - bx) / rate * (speed - 4);
                    dy = (ey - by) / rate * (speed - 4);
                }

            }
        }*/
        x += dx;
        y += dy;

      
        
            if (x < -r || x > GamePanel.WIDTH + r || y < -r || y > GamePanel.HEIGHT + r) {
                return true;
            }
        return false;
    }

    public void draw(Graphics2D g) {
        g.setColor(color1);
        g.fillOval((int) (x - r), (int) (y - r), 2 * r, 2 * r);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int i) {
        size = i;
    }

    public void addSize() {
        size++;
    }

}



