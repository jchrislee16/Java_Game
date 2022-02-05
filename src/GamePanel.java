

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    //public variables
    public static int WIDTH = 800;
    public static int HEIGHT = 600;
    public static Player player;
    public static ArrayList<Bullet> bullets;
    public static ArrayList<Enemy> enemies;
    public static ArrayList<powerUP> powerups;
    public static ArrayList<Coin> coins;
    public static ArrayList<Boss> bosses;
    public static ArrayList<EnemyBullet> enemybullets;
    //private variables
    private boolean running;
    private boolean GameOver;
    private boolean Title;
    private boolean retry;
    private boolean textBlinking;
    private boolean Settings;
    private boolean Resolution;
    private boolean Game_Control;
    private Thread thread;
    private BufferedImage image;
    private Graphics2D g;
    private listofnames bossName;
    private JFrame frame;

    private int FPS = 30;
    private double averageFPS;

    private long waveStartTimer;
    private long waveStartTimerDiff;
    private int waveNumber;
    private boolean waveStart;
    private long waveDelay = 2000;
    //Keys 
    private int LeftKey;
    private int RightKey;
    private int UpKey;
    private int DownKey;
    private int AttackKey;
    private int UpgradeKey;

    private String[] text;
    private String[] text2;
    private String[] text3;
    private String[] text4;
    private String[] text5;
    private static int[] text6;
    private long textTimer;
    private long textTimerDelay = 800;
    private int select;
    private int maxSelect;
    
    //Music
    private AudioPlayer bgm;
    private HashMap<String, AudioPlayer> SFX;

    public static int getKey;

    //Constructor
    public GamePanel(JFrame frame) {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
        this.frame = frame;
    }

    //Functions
    public void addNotify() {
        super.addNotify();
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
        addKeyListener(this);
    }

    public void run() {

        text = new String[2];
        text2 = new String[3];
        text3 = new String[3];
        text4 = new String[5];
        text5 = new String[6];
        text6 =  new int[6];
        text[0] = "yes";
        text[1] = "no";
        text2[0] = "start";
        text2[1] = "setting";
        text2[2] = "exit";
        text3[0] = "Resolution";
        text3[1] = "Game Control";
        text3[2] = "back";
        text4[0] = "800 x 600";
        text4[1] = "1280 x 800";
        text4[2] = "1440 x 900";
        text4[3] = "1920 x 1200";
        text4[4] = "back";
        text5[0] = "UpKey";
        text5[1] = "DownKey";
        text5[2] = "LeftKey";
        text5[3] = "RightKey";
        text5[4] = "AttackKey";
        text5[5] = "back";
        bossName = new listofnames();


        maxSelect = 2;
        player = new Player();
        bullets = new ArrayList<Bullet>();
        enemies = new ArrayList<Enemy>();
        powerups = new ArrayList<powerUP>();
        coins = new ArrayList<Coin>();
        bosses = new ArrayList<Boss>();
        enemybullets = new ArrayList<EnemyBullet>();
        running = true;
        GameOver = false;
        Title = true;
        retry = false;
        Settings = false;
        Resolution = false;
        Game_Control = false;
        select = 0;
        getKey = 1;
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        SFX = new HashMap<String, AudioPlayer>();
        SFX.put("Attack", new AudioPlayer("/Attack/Attack.wav"));
        SFX.put("Attack", new AudioPlayer("/Attack/Attack2.wav"));
        SFX.put("Attack", new AudioPlayer("/Attack/Attack3.wav"));
        SFX.put("Coin", new AudioPlayer("/Coin/Coin.wav"));
        SFX.put("Coin", new AudioPlayer("/Coin/Coin2.wav"));
        SFX.put("Explosion", new AudioPlayer("/Explosion/Explosion.wav"));
        SFX.put("Hurt", new AudioPlayer("/Hurt/Hurt.wav"));
        SFX.put("Hurt", new AudioPlayer("/Hurt/Hurt2.wav"));
        
        
        //bgm = new AudioPlayer("/Attack/Attack.wav");//gotta fix it
        
        
        
        waveStartTimer = 0;
        waveStartTimerDiff = 0;
        waveStart = true;
        waveNumber = 0;
        RightKey = KeyEvent.VK_D;
        LeftKey = KeyEvent.VK_A;
        UpKey = KeyEvent.VK_W;
        DownKey = KeyEvent.VK_S;
        AttackKey = KeyEvent.VK_K;
        text6[0] = UpKey;
        text6[1] = DownKey;
        text6[2] = LeftKey;
        text6[3] = RightKey;
        text6[4] = AttackKey;
        text6[5] = 0;
        
        

        //Time
        long startTime;
        long URDTimeMillis;
        long waitTime;
        long totalTime = 0;

        int frameCount = 0;
        int maxFrameCount = 30;

        long targetTime = 1000 / FPS;

        //LOOP
        while (running) {
            while (Title) {
                g.setColor(Color.YELLOW.darker());
                g.fillRect(0, 0, WIDTH, HEIGHT);
                g.setFont(new Font("Times New Roman", Font.PLAIN, WIDTH / 25));
                String s = " W E L C O M E   T O";
                int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
                g.setColor(Color.WHITE);
                g.drawString(s, WIDTH / 2 - length / 2, HEIGHT / 2);
                g.setFont(new Font("Times New Roman", Font.PLAIN, WIDTH / 25));
                s = "S H O O T I N G   A R C A D E ";
                length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
                g.drawString(s, WIDTH / 2 - length / 2, HEIGHT / 2 + 100);
                maxSelect = 2;
                s = text2[select];
                length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
                g.drawString(s, WIDTH / 2 - length / 2, HEIGHT / 2 + 200);

                gameDraw();

                while (Settings) {
                    g.setColor(Color.YELLOW.darker());
                    g.fillRect(0, 0, WIDTH, HEIGHT);
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Times New Roman", Font.PLAIN, WIDTH / 20));
                    maxSelect = 2;
                    s = text3[select];
                    length = (int) g.getFontMetrics().getStringBounds("Settings", g).getWidth();
                    g.drawString("Settings", WIDTH /2 - length /2 , HEIGHT /4);
                    g.setFont(new Font("Times New Roman", Font.PLAIN, WIDTH / 25));
                    length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
                    g.drawString(s, WIDTH / 2 - length / 2, HEIGHT / 2 + HEIGHT /4);

                    gameDraw();
                
                
                while(Resolution)
                {
                    g.setColor(Color.YELLOW.darker());
                    g.fillRect(0, 0, WIDTH, HEIGHT);
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Times New Roman", Font.PLAIN, WIDTH / 25));
                    maxSelect = 4;
                    s = text4[select];
                    length = (int) g.getFontMetrics().getStringBounds("Resolution", g).getWidth();
                    g.drawString("Resolution", WIDTH /2 - length /2 , HEIGHT /4);
                    length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
                    g.drawString(s, WIDTH / 2 - length / 2, HEIGHT / 2 + HEIGHT /4);

                    gameDraw();
                } 
                
                while(Game_Control)
                {
                     g.setColor(Color.YELLOW.darker());
                    g.fillRect(0, 0, WIDTH, HEIGHT);
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Times New Roman", Font.PLAIN, WIDTH / 25));
                    maxSelect = 5;
                    s = text5[select];
                    length = (int) g.getFontMetrics().getStringBounds("Game Control", g).getWidth();
                    g.drawString("Game Control", WIDTH /2 - length /2 , HEIGHT /4);
                    if(select !=5)
                    {
                            length = (int) g.getFontMetrics().getStringBounds(s + ": " + KeyEvent.getKeyText(text6[select]), g).getWidth();
                            g.drawString(s + ": " + KeyEvent.getKeyText(text6[select]), WIDTH / 2 - length / 2, HEIGHT / 2 + HEIGHT /4);
                    }
                    else
                    {
                        length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
                        g.drawString(s, WIDTH / 2 - length / 2, HEIGHT / 2 + HEIGHT /4);
                    }
                    gameDraw();
                }
                
                }

            }//End of Intro

            startTime = System.nanoTime();
            gameUpdate();
            gameRender();
            gameDraw();
            URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - URDTimeMillis;

            try {
                Thread.sleep(waitTime);
            } catch (Exception e) {
            }
            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == maxFrameCount) {
                averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
            }
            while (GameOver) {
                textBlinking = true;
                g.setColor(Color.YELLOW.darker());
                g.fillRect(0, 0, WIDTH, HEIGHT);
                String s = "";
                int length = 0;

                long elapsed = (System.nanoTime() - textTimer) / 1000000;
                if (elapsed > 400) {
                    textBlinking = false;
                    if (elapsed > textTimerDelay) {
                        textTimer = System.nanoTime();
                        textBlinking = true;
                    }
                }

                if (textBlinking) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.RED);
                }
                g.setFont(new Font("Times New Roman", Font.PLAIN, WIDTH / 25));
                s = " G A M E O V E R";
                length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
                g.drawString(s, WIDTH / 2 - length / 2, HEIGHT / 2);
                g.setFont(new Font("Times New Roman", Font.PLAIN, WIDTH / 25));
                s = "Would you want to play this game again?";
                length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
                g.drawString(s, WIDTH / 2 - length / 2, HEIGHT / 2 + 100);
                maxSelect = 1;
                s = text[select];
                
                length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
                g.drawString(s, WIDTH / 2 - length / 2, HEIGHT / 2 + 200);

                gameDraw();

                
                
                
                
                if (retry) {
                    GameOver = false;
                    player = new Player();
                    bullets = new ArrayList<Bullet>();
                    enemies = new ArrayList<Enemy>();
                    bosses = new ArrayList<Boss>();
                    enemybullets = new ArrayList<EnemyBullet>();
                    coins = new ArrayList<Coin>();
                    waveNumber = 0;
                }

            }//End of Game Over
        }//End of Loops

    }//End of Run

    //UPDATE
    private void gameUpdate() {

        //new wave
        if (waveStartTimer == 0 && enemies.size() == 0 && bosses.size() == 0) {
            waveNumber++;
            waveStart = false;
            waveStartTimer = System.nanoTime();
        } else {
            waveStartTimerDiff = (System.nanoTime() - waveStartTimer) / 1000000;
            if (waveStartTimerDiff > waveDelay) {
                waveStart = true;
                waveStartTimer = 0;
                waveStartTimerDiff = 0;
            }
        }

        //create enemies
        if (waveStart && enemies.size() == 0 && bosses.size() == 0) {
            createNewEnemies();
        }

        //Player
        player.update();

        //Boss
        for (int i = 0; i < bosses.size(); i++) {
            bosses.get(i).update();
        }

        //Bullet
        for (int i = 0; i < bullets.size(); i++) {
            boolean remove = bullets.get(i).update();
            if (remove) {
                bullets.remove(i);
                i--;
            }
        }

        //EnemyBullet
        for (int i = 0; i < enemybullets.size(); i++) {
            boolean remove = enemybullets.get(i).update();
            if (remove) {
                enemybullets.remove(i);
                i--;
            }
        }

        //Enemy
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();
        }

        //Bullet-Enemy Collision
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            double bx = b.getx();
            double by = b.gety();
            double br = b.getr();
            for (int j = 0; j < enemies.size(); j++) {
                Enemy e = enemies.get(j);
                double ex = e.getx();
                double ey = e.gety();
                double er = e.getr();
                double dx = bx - ex;
                double dy = by - ey;
                double dist = Math.sqrt(dx * dx + dy * dy);
                if (dist < br + er) {
                    e.hit(player.getDamage());
                    bullets.remove(i);
                    i--;
                    break;
                }

            }
        }
        //Bullet-Boss Collision
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            double bx = b.getx();
            double by = b.gety();
            double br = b.getr();
            for (int j = 0; j < bosses.size(); j++) {
                Boss e = bosses.get(j);
                double ex = e.getx();
                double ey = e.gety();
                double er = e.getr();

                double dx = bx - ex;
                double dy = by - ey;
                double dist = Math.sqrt(dx * dx + dy * dy);
                if (dist < br + er) {
                    e.hit((int) bullets.get(i).getDamage());
                    bullets.remove(i);
                    i--;
                    break;
                }

            }
        }

        //player-EnemyBullet Collision
        for (int i = 0; i < enemybullets.size(); i++) {
            EnemyBullet b = enemybullets.get(i);
            double bx = b.getx();
            double by = b.gety();
            double br = b.getr();

            double ex = player.getx();
            double ey = player.gety();
            double er = player.getr();

            double dx = bx - ex;
            double dy = by - ey;
            double dist = Math.sqrt(dx * dx + dy * dy);
            if (dist < br + er) {
                player.loseLife();
                enemybullets.remove(i);
                i--;
                break;

            }
        }

        {
            int px = player.getx();
            int py = player.gety();
            int pr = player.getr();

            for (int i = 0; i < coins.size(); i++) {
                Coin c = coins.get(i);
                double cx = c.getx();
                double cy = c.gety();
                double cr = c.getr();

                double dx = px - cx;
                double dy = py - cy;
                double dist = Math.sqrt(dx * dx + dy * dy);

                if (dist < pr + cr) {
                    player.pickupMoney(c.getValue());
                    SFX.get("Coin").play();
                    coins.remove(i);
                    i--;
                }

            }
        }

        //Player-Enemy Collision
        if (!player.isRecovering()) {
            int px = player.getx();
            int py = player.gety();
            int pr = player.getr();
            for (int i = 0; i < enemies.size(); i++) {
                Enemy e = enemies.get(i);
                double ex = e.getx();
                double ey = e.gety();
                double er = e.getr();

                double dx = px - ex;
                double dy = py - ey;
                double dist = Math.sqrt(dx * dx + dy * dy);

                if (dist < pr + er) {
                    player.loseLife();
                    SFX.get("Hurt").play();
                    break;
                }
            }

        }

        //Player-Boss Collision
        if (!player.isRecovering()) {
            int px = player.getx();
            int py = player.gety();
            int pr = player.getr();
            for (int i = 0; i < bosses.size(); i++) {
                Boss e = bosses.get(i);
                double ex = e.getx();
                double ey = e.gety();
                double er = e.getr();

                double dx = px - ex;
                double dy = py - ey;
                double dist = Math.sqrt(dx * dx + dy * dy);

                if (dist < pr + er) {
                    player.loseLife();
                    break;
                }
            }

        }

        //Game Over
        if (player.getLives() <= 0) {
            GameOver = true;
        }

        //Enemy Dead? Also create powerups
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).isDead()) {
                coins.add(new Coin((int) enemies.get(i).getx(), (int) enemies.get(i).gety(), 2));
                powerups.add(new powerUP((int) enemies.get(i).getx(), (int) enemies.get(i).gety(), 2));
                SFX.get("Explosion").play();
                enemies.remove(i);
                i--;
            }
        }

        //Boss Dead?
        for (int i = 0; i < bosses.size(); i++) {
            if (bosses.get(i).isDead()) {
                coins.add(new Coin((int) bosses.get(i).getx(), (int) bosses.get(i).gety(), 10));
                powerups.add(new powerUP((int) bosses.get(i).getx(), (int) bosses.get(i).gety(), 2));
                powerups.add(new powerUP((int) bosses.get(i).getx(), (int) bosses.get(i).gety(), 2));
                powerups.add(new powerUP((int) bosses.get(i).getx(), (int) bosses.get(i).gety(), 2));
                powerups.add(new powerUP((int) bosses.get(i).getx(), (int) bosses.get(i).gety(), 2));
                powerups.add(new powerUP((int) bosses.get(i).getx(), (int) bosses.get(i).gety(), 2));
                bosses.remove(i);
                i--;
            }
        }

        //powerups update
        for (int i = 0; i < powerups.size(); i++) {
            powerups.get(i).update();
            if (powerups.get(i).isBouncing()) {
                powerups.remove(i);
                i--;
            }
        }

        //powerups-player collision
        for (int i = 0; i < powerups.size(); i++) {
            double px = player.getx();
            double py = player.gety();
            double pr = player.getr();
            double hypot = 0;

            double ppx = powerups.get(i).getx();
            double ppy = powerups.get(i).gety();
            double ppr = powerups.get(i).getr();
            if (Math.abs(px - ppx) > Math.abs(py - ppy)) {
                double ty = (powerups.get(i).getr() * Math.abs(py - ppy)) / Math.abs(px - ppx);
                hypot = Math.sqrt(ty * ty + ppr * ppr);

            } else {
                double ty = (powerups.get(i).getr() * Math.abs(px - ppx)) / Math.abs(py - ppy);
                hypot = Math.sqrt(ty * ty + ppr * ppr);
            }

            double dx = px - ppx;
            double dy = py - ppy;
            double dist = Math.sqrt(dx * dx + dy * dy);

            if (dist < pr + hypot) {
                powerups.remove(i);
                i--;
                player.addChoose();
            }
        }

        //coin update
        for (int i = 0; i < coins.size(); i++) {
            coins.get(i).update();

        }

    }//End of Game Update

    private void gameRender() {
        //Background
        g.setColor(Color.YELLOW.darker());
        g.fillRect(0, 0, WIDTH, HEIGHT);

        //Texts
        {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Times New Roman", Font.PLAIN, WIDTH / 40));
            String s = "Damage: " + player.getDamage();
            int Length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
            g.drawString(s, 0 + 20, HEIGHT - 50);
            s = "Bullets: " + bullets.size() + " / " + player.getMaxBullets();
            Length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
            g.drawString(s, 0 + 20, 200);
            s = "Speed: " + player.getSpeed();
            Length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
            g.drawString(s, 0 + 20, HEIGHT - 85);
            s = "Bullet Speed: " + player.getBulletSpeed();
            Length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
            g.drawString(s, 0 + 20, HEIGHT - 120);
            s = "Bullet Intervals: " + player.getBulletDelay() + "Milli-Seconds";
            Length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
            g.drawString(s, 0 + 20, HEIGHT - 155);
            s = "Money: $ " + player.getMoney();
            Length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
            g.drawString(s, 0 + 20, 250);
            //Power Bars

            if (player.getChoose() >= 1) {
                g.setColor(new Color(229, 182, 112));
                g.fillRect((WIDTH / 4) + (0 * 80), HEIGHT - 60, 80, 50);
            }
            if (player.getChoose() >= 2) {
                g.setColor(new Color(84, 165, 87));
                g.fillRect((WIDTH / 4) + (1 * 80), HEIGHT - 60, 80, 50);
            }
            if (player.getChoose() >= 3) {
                g.setColor(new Color(56, 63, 124));
                g.fillRect((WIDTH / 4) + (2 * 80), HEIGHT - 60, 80, 50);
            }
            if (player.getChoose() >= 4) {
                g.setColor(new Color(120, 73, 160));
                g.fillRect((WIDTH / 4) + (3 * 80), HEIGHT - 60, 80, 50);
            }
            if (player.getChoose() >= 5) {
                g.setColor(new Color(206, 35, 72));
                g.fillRect((WIDTH / 4) + (4 * 80), HEIGHT - 60, 80, 50);
            } else {
            }
            for (int i = 0; i < 5; i++) {
                g.setColor(new Color(205, 210, 216));
                g.setStroke(new BasicStroke(6));
                g.drawRect((WIDTH / 4) + (i * 80), HEIGHT - 60, 80, 50);
                g.setStroke(new BasicStroke(1));
            }
        }

        //bullets
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(g);

        }

        //enemybullets
        for (int i = 0; i < enemybullets.size(); i++) {
            enemybullets.get(i).draw(g);

        }

        //coin draw
        for (int i = 0; i < coins.size(); i++) {
            coins.get(i).draw(g);

        }

        //enemies
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }

        for (int i = 0; i < powerups.size(); i++) {
            powerups.get(i).draw(g);

        }

        //player
        player.draw(g);

        //wave number
        if (waveStartTimer != 0) {
            g.setFont(new Font("Times New Roman", Font.PLAIN, WIDTH / 25));
            String s = "- W A V E    " + waveNumber + "   -";
            int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
            int alpha = (int) (255 * Math.sin(3.14 * waveStartTimerDiff / waveDelay));
            if (alpha > 255) {
                alpha = 255;
            }
            g.setColor(new Color(255, 255, 255, alpha));
            g.drawString(s, WIDTH / 2 - length / 2, HEIGHT / 2);
        }

        //player lives
        for (int i = 0; i < player.getLives(); i++) {
            g.setColor(Color.WHITE);
            g.fillOval(player.getr() * 2 + (player.getr() * 3 * i), 30, player.getr() * 2, player.getr() * 2);
            g.setStroke(new BasicStroke(3));
            g.setColor(Color.WHITE.darker());
            g.drawOval(player.getr() * 2 + (player.getr() * 3 * i), 30, player.getr() * 2, player.getr() * 2);

        }

        //draw Boss
        for (int i = 0; i < bosses.size(); i++) {
            bosses.get(i).draw(g);
        }

    }//End of Render

    private void gameDraw() {
        Graphics g2 = this.getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }//End of Draw

    private void createNewEnemies() {
        enemies.clear();
        Enemy e;
        if (waveNumber > 20) {
            for (int i = 20; i < waveNumber; i++) {
                enemies.add(new Enemy(1, 3));
            }
        }
       else if (waveNumber > 10) {
            for (int i = 10; i < waveNumber; i++) {
                enemies.add(new Enemy(1, 2));
            }
        }
       else if (waveNumber > 0) {
            for (int i = 0; i < waveNumber; i++) {
                enemies.add(new Enemy(1, 1));
            }
        }
        
        if(waveNumber % 1 == 0)
        {
        	bosses.add(new Boss(1,1,bossName.oneNameOnly()));
        }

    }//End of Create New Enemies

    public void keyTyped(KeyEvent key) {

    }//End of keyTyped

    public void keyPressed(KeyEvent key) {
        int keyCode = key.getKeyCode();
        if (keyCode == KeyEvent.VK_1) {
            getKey = 1;
        }
        if (keyCode == KeyEvent.VK_2) {
            getKey = 2;
        }
        if (keyCode == KeyEvent.VK_3) {
            getKey = 3;
        }
        if (keyCode == KeyEvent.VK_4) {
            getKey = 4;
        }
        if (keyCode == LeftKey) {
            player.setLeft(true);
        }
        if (keyCode == RightKey) {
            player.setRight(true);
        }
        if (keyCode == UpKey) {
            player.setUp(true);
        }
        if (keyCode == DownKey) {
            player.setDown(true);
        }
        if (keyCode == AttackKey) {
            player.setFiring(true);
        }
        if (keyCode == KeyEvent.VK_ENTER) {
            retry = true;
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            player.Upgrade(player.getChoose());
        }
        if (keyCode == KeyEvent.VK_UP) {
            if (select == 0) {
                select = maxSelect;
            } else {
                select--;
            }
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            if (select == maxSelect) {
                select = 0;
            } else {
                select++;
            }
        }
        if (keyCode == KeyEvent.VK_ENTER) {
            if (!Title && GameOver && !Settings) {
                if (select == 0) {
                    retry = true;
                } else if (select == 1) {
                    Title = true;
                    GameOver = false;
                    select = 0;
                }
            }//Game Over enter keys
            else if (Title && !Settings ) {
                if (select == 0) {
                    Title = false;
                } else if (select == 1) {
                    Settings = true;
                    select = 0;
                } else if (select == 2) {
                    System.exit(0);
                }

            }//Title Enter keys
            else if (Settings && Title && !Resolution && !Game_Control) {
                if (select == 2) {
                    Settings = false;
                    select = 0;
                }
                else if (select == 0) {
                   Resolution = true;
                   select = 0;
                            }
                else if (select == 1) 
                {
                Game_Control = true;
                select = 0;
                }
            }//Settings enter keys
            else if (Resolution)
            {
                if( select == 4)
                {
                    Resolution = false;
                    select = 0;
                }
                else if (select == 0)
                {
                     WIDTH = 800;
                    HEIGHT = 600;
                    setPreferredSize(new Dimension(WIDTH, HEIGHT));
                    frame.pack();
                    requestFocus();
                   System.out.println("A");
                    image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
                    g = (Graphics2D) image.getGraphics();

                    this.setSize(new Dimension(WIDTH,HEIGHT));
                }
                else if(select == 1)
                {
                 WIDTH = 1280;
                    HEIGHT = 800;
                    setPreferredSize(new Dimension(WIDTH, HEIGHT));
                    frame.pack();
                    requestFocus();
                    image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
                    g = (Graphics2D) image.getGraphics();

                    this.setSize(new Dimension(WIDTH,HEIGHT));
                }
                if(select == 2)
                {
                     WIDTH = 1440;
                    HEIGHT = 900;
                    setPreferredSize(new Dimension(WIDTH, HEIGHT));
                    frame.pack();
                    requestFocus();
                    image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
                    g = (Graphics2D) image.getGraphics();

                    this.setSize(new Dimension(WIDTH,HEIGHT));
                }
                else if(select == 3)
                {
                     WIDTH = 1920  ;
                    HEIGHT = 1200;
                    setPreferredSize(new Dimension(WIDTH, HEIGHT));
                    frame.pack();
                    requestFocus();
                    image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
                    g = (Graphics2D) image.getGraphics();

                    this.setSize(new Dimension(WIDTH,HEIGHT));
                }
            }
            else if (Game_Control)
            {
                if( select == 5)
                {
                    Game_Control = false;
                    select = 0;
                }
                else if (select == 0)
                {
                	
                	//change(key)
                }
                else if(select == 1)
                {
                }
                else if(select == 2)
                {
                }
                else if(select == 3)
                {
                }
                else if(select == 4)
                {
                	
                }
            }//Game_Control Enter Keys
        }
    }//End of Key Pressed

    public void keyReleased(KeyEvent key) {
        int keyCode = key.getKeyCode();
        if (keyCode == LeftKey) {
            player.setLeft(false);
        }
        if (keyCode == RightKey) {
            player.setRight(false);
        }
        if (keyCode == UpKey) {
            player.setUp(false);
        }
        if (keyCode == DownKey) {
            player.setDown(false);
        }
        if (keyCode == AttackKey) {
            player.setFiring(false);
        }
        if (keyCode == KeyEvent.VK_ENTER) {
            retry = false;
        }
    }//End of Key Released    
    

}
