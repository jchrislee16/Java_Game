

import java.awt.Color;
import java.awt.Graphics2D;




public class Bullet {

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

    public Bullet(double angle, int x, int y) {
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

    public Bullet(double angle, int x, int y, int type) {
        this.x = x;
        this.y = y;
        r = (int) (GamePanel.WIDTH / 150);
        speed = 15;
        rad = Math.toRadians(angle);
        dx = Math.cos(rad) * speed;
        dy = Math.sin(rad) * speed;;
        damage = 1;

        this.type = type;
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
        if (type == 2) {
            for (int i = 0; i < GamePanel.enemies.size(); i++) {
                double ex = GamePanel.enemies.get(i).getx();
                double ey = GamePanel.enemies.get(i).gety();

                double bx = x;
                double by = y;
                double tan = Math.sqrt((ex - bx) * (ex - bx) + (ey - by) * (ey - by));
                if (tan < 200) {

                    dx = ((ex - bx) / tan) * speed;
                    dy = ((ey - by) / tan) * speed;
                }

            }
            for (int i = 0; i < GamePanel.bosses.size(); i++) {
                double ex = GamePanel.bosses.get(i).getx();
                double ey = GamePanel.bosses.get(i).gety();

                double bx = x;
                double by = y;

                double tan = Math.sqrt((ex - bx) * (ex - bx) + (ey - by) * (ey - by));
                if (tan < 200) {

                    dx = ((ex - bx) / tan) * speed;
                    dy = ((ey - by) / tan) * speed;
                }

            }
        }
        x += dx;
        y += dy;

        if (type == 4) {
            dx = dx * .5;
            dy = dy * .5;
            r++;
            damage++;
            // if(r >= 30)
            //  {
            //      r = 30;
            //   }
        }

        if (type == 3) {
            if ((x < r && dx < 0) || (x > GamePanel.WIDTH - r && dx > 0)) {
                dx = -dx;
                count++;
            }
            if ((y < r && dy < 0) || (y > GamePanel.HEIGHT - r && dy > 0)) {
                dy = -dy;
                count++;
            }
            if (count >= 3) {
                return true;
            }
        } else {
            if (x < -r || x > GamePanel.WIDTH + r || y < -r || y > GamePanel.HEIGHT + r) {
                return true;
            }
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
