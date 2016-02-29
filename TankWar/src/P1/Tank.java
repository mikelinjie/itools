package P1;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.List;

public class Tank {

	private int x, y;
	private int oldX, oldY;
	static final int X_SPEED = 5, Y_SPEED = 5;
	static final int WIDTH = 30, HEIGHT = 30;
	TankClient tc = null;

	private int life = 100;
	private BloodBar bb = new BloodBar();

	public void setLife(int life) {
		this.life = life;
	}

	public int getLife() {
		return life;
	}

	private boolean good;

	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	private static Random r = new Random();

	private boolean live = true;

	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isLive() {
		return live;
	}

	private boolean bL = false, bR = false, bU = false, bD = false;

	/*
	 * enum Direction { L, LU, U, RU, R, RD, D, LD, STOP };
	 */
	private Direction dir = Direction.STOP;
	private Direction ptDir = Direction.D;

	private int step = r.nextInt(12) + 3;

	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.good = good;
		this.oldX = x;
		this.oldY = y;
	}

	public Tank(int x, int y, boolean good, Direction dir, TankClient tc) {
		this(x, y, good);
		this.dir = dir;
		this.tc = tc;
	}

	public void draw(Graphics g) {
		if (!live) {
			if (!good) {
				tc.tanks.remove(this);
			}
			return;
		}

		Color c = g.getColor();
		if (this.good)
			g.setColor(Color.red);
		else
			g.setColor(Color.blue);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(Color.black);

		if (good)
			bb.draw(g);

		switch (ptDir) {

		case L:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y
					+ Tank.HEIGHT / 2);
			break;
		case LU:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y);
			break;
		case U:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH
					/ 2, y);
			break;
		case RU:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH,
					y);
			break;
		case R:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH,
					y + Tank.HEIGHT / 2);
			break;
		case RD:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH,
					y + Tank.HEIGHT);
			break;
		case D:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x + Tank.WIDTH
					/ 2, y + Tank.HEIGHT);
			break;
		case LD:
			g.drawLine(x + Tank.WIDTH / 2, y + Tank.HEIGHT / 2, x, y
					+ Tank.HEIGHT);
			break;
		}
		g.setColor(c);
		move();
	}

	public void move() {
		// 记录上一步的位置
		this.oldX = x;
		this.oldY = y;

		switch (dir) {
		case L:
			x -= X_SPEED;
			break;
		case LU:
			x -= X_SPEED;
			y -= Y_SPEED;
			break;
		case U:
			y -= Y_SPEED;
			break;
		case RU:
			x += X_SPEED;
			y -= Y_SPEED;
			break;
		case R:
			x += X_SPEED;
			break;
		case RD:
			x += X_SPEED;
			y += Y_SPEED;
			break;
		case D:
			y += Y_SPEED;
			break;
		case LD:
			x -= X_SPEED;
			y += Y_SPEED;
			break;
		case STOP:
			break;

		}

		if (dir != Direction.STOP)
			ptDir = dir;
		if (x < 0)
			x = 0;
		if (y < 30)
			y = 30;
		if (x + Tank.WIDTH > TankClient.GAME_WIDTH)
			x = TankClient.GAME_WIDTH - Tank.WIDTH;
		if (y + Tank.HEIGHT > TankClient.GAME_HIGHT)
			y = TankClient.GAME_HIGHT - Tank.HEIGHT;

		if (!good) {
			if (step == 0) {
				step = r.nextInt(12) + 3;
				Direction[] dirs = Direction.values();
				int rn = r.nextInt(dirs.length);
				dir = dirs[rn];
			}
			step--;
			if (r.nextInt(40) > 35)
				fire();
		}

	}

	public void stay() {
		x = oldX;
		y = oldY;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_F2:
			if (!live) {
				this.live = true;
				this.life = 100;
			}
			break;
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		case KeyEvent.VK_CONTROL:
			break;

		}

		locateDirection();
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		case KeyEvent.VK_CONTROL:
			fire();
			break;
		case KeyEvent.VK_A:
			this.superFire();
			break;
		}
		locateDirection();
	};

	public void locateDirection() {

		if (bL && !bU && !bR && !bD)
			dir = Direction.L;
		else if (bL && bU && !bR && !bD)
			dir = Direction.LU;
		else if (!bL && bU && !bR && !bD)
			dir = Direction.U;
		else if (!bL && bU && bR && !bD)
			dir = Direction.RU;
		else if (!bL && !bU && bR && !bD)
			dir = Direction.R;
		else if (!bL && !bU && bR && bD)
			dir = Direction.RD;
		else if (!bL && !bU && !bR && bD)
			dir = Direction.D;
		else if (bL && !bU && !bR && bD)
			dir = Direction.LD;
		else if (!bL && !bU && !bR && !bD)
			dir = Direction.STOP;

	}

	public Missile fire() {
		if (!live)
			return null;
		int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
		Missile m = new Missile(x, y, good, ptDir, this.tc);
		tc.missiles.add(m);
		return m;
	}

	public Missile fire(Direction dir) {
		if (!live)
			return null;
		int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
		int y = this.y + Tank.HEIGHT / 2 - Missile.HEIGHT / 2;
		Missile m = new Missile(x, y, good, dir, this.tc);
		tc.missiles.add(m);
		return m;
	}

	public Rectangle getRec() {
		return new Rectangle(x, y, this.WIDTH, this.HEIGHT);
	}

	public boolean collidesWithWall(Wall w) {
		if (this.live && this.getRec().intersects(w.getRec())) {
			this.stay();
			return true;
		}
		return false;
	}

	public boolean collidesWithTanks(List<Tank> tanks) {
		for (int i = 0; i < tanks.size(); i++) {
			Tank t = tanks.get(i);
			if (this != t) {
				if (this.live && t.isLive()
						&& this.getRec().intersects(t.getRec())) {
					this.stay();
					t.stay();
					return true;
				}
			}
		}
		return false;
	}

	public void superFire() {
		Direction[] dirs = Direction.values();
		for (int i = 0; i < 8; i++) {
			fire(dirs[i]);
		}
	}

	private class BloodBar {
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.red);
			int w = WIDTH * life / 100;
			g.fillRect(x, y - 10, w, 10);
			g.drawRect(x, y - 10, WIDTH, 10);
			g.setColor(c);
		}
	}

	public boolean eat(Blood b) {
		if (this.live && b.isLive() && this.getRec().intersects(b.getRec())) {
			this.life = 100;
			b.setLive(false);
			return true;
		}
		return false;
	}
}
