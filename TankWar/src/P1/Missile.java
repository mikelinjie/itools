package P1;

import java.awt.*;
import java.util.List;

public class Missile {
	static final int X_SPEED = 10, Y_SPEED = 10;
	static final int WIDTH = 10, HEIGHT = 10;
	int x, y;
	Direction dir;
	private boolean good;
	TankClient tc;

	private boolean live = true;

	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isLive() {
		return live;
	}

	public Missile(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}

	public Missile(int x, int y, boolean good, Direction dir, TankClient tc) {
		this(x, y, dir);
		this.good = good;
		this.tc = tc;
	}

	public void draw(Graphics g) {
		if (!this.isLive()) {
			tc.missiles.remove(this);
			return;
		}

		Color c = g.getColor();
		if (this.good)
			g.setColor(Color.red);
		else
			g.setColor(Color.black);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		move();
	}

	public void move() {

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

		}

		if (x < 0 || y < 0 || x > TankClient.GAME_WIDTH
				|| y > TankClient.GAME_HIGHT) {
			live = false;

		}

	}

	public Rectangle getRec() {
		return new Rectangle(x, y, this.WIDTH, this.HEIGHT);
	}

	public boolean hitTank(Tank t) {
		if (this.isLive() && this.getRec().intersects(t.getRec()) && t.isLive()
				&& this.good != t.isGood()) {
			if (t.isGood()) {
				t.setLife(t.getLife() - 20);
				if (t.getLife() <= 0)
					t.setLive(false);
				;

			} else {
				t.setLive(false);
			}

			this.setLive(false);
			Explode e = new Explode(x, y, tc);
			tc.explodes.add(e);
			return true;
		}

		return false;

	}

	public boolean hitTanks(List<Tank> tanks) {
		for (int i = 0; i < tanks.size(); i++) {
			if (hitTank(tanks.get(i))) {
				return true;
			}
		}
		return false;
	}

	public boolean hitWall(Wall w) {
		if (this.live && this.getRec().intersects(w.getRec())) {
			this.live = false;
			return true;
		}
		return false;
	}

}
