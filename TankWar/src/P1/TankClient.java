package P1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TankClient extends Frame {

	Wall w1 = new Wall(100, 200, 20, 150, this);

	Wall w2 = new Wall(300, 100, 300, 20, this);

	public static final int GAME_WIDTH = 800;
	public static final int GAME_HIGHT = 600;
	Tank myTank = new Tank(50, 50, true, Direction.STOP, this);

	List<Explode> explodes = new ArrayList<Explode>();
	List<Missile> missiles = new ArrayList<Missile>();
	List<Tank> tanks = new ArrayList<Tank>();
	Blood b = new Blood();
	Image offScreenImage = null;

	public static void main(String[] args) {
		TankClient t1 = new TankClient();
		t1.launchFrame();

	}

	public void launchFrame() {
		

		int initTankCount = Integer.parseInt(propertymgr.getProperty("initTankCount"));
		
		for (int i = 0; i < initTankCount; i++) {
			tanks.add(new Tank(50 + 40 * (i + 1), 50, false, Direction.D, this));
		}

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(screenSize.width / 2 - GAME_WIDTH / 2, screenSize.height
				/ 2 - GAME_HIGHT / 2, GAME_WIDTH, GAME_HIGHT);
		this.setTitle("TankWar");
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

		});
		this.setResizable(false);
		this.addKeyListener(new KeyMonitor());
		this.setVisible(true);
		new Thread(new PaintThread()).start();

	}

	// using iterator maybe better
	public void paint(Graphics g) {

		if (tanks.size() == 0) {
			for (int i = 0; i < 3; i++) {
				tanks.add(new Tank(50 + 40 * (i + 1), 50, false, Direction.D,
						this));
			}
		}

		for (int i = 0; i < missiles.size(); i++) {
			Missile m = missiles.get(i);
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.hitWall(w1);
			m.hitWall(w2);
			m.draw(g);
		}
		for (int i = 0; i < explodes.size(); i++) {
			Explode e = explodes.get(i);
			e.draw(g);
		}

		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			t.collidesWithWall(w1);
			t.collidesWithWall(w2);
			t.collidesWithTanks(tanks);
			t.draw(g);
		}
		myTank.draw(g);
		myTank.eat(b);
		g.drawString("Missile Count:  " + missiles.size(), 60, 60);
		g.drawString("Explodes Count:  " + explodes.size(), 60, 70);
		g.drawString("Tanks Count:  " + tanks.size(), 60, 80);

		w1.draw(g);
		w2.draw(g);
		b.draw(g);

	}

	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HIGHT);
		}

		Graphics gOffScreen = this.offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.green);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	private class PaintThread implements Runnable {

		public void run() {

			while (true) {
				repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}

	private class KeyMonitor extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}
	}
}
