package br.meujogomodelo.app;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.w3c.dom.css.Rect;

public class Fase extends JPanel implements ActionListener {

	private Image fundo;
	private Player player;
	private Timer timer;
	private List<Enemy> enemy;
	private boolean emJogo;

	public Fase() {
		setFocusable(true);
		setDoubleBuffered(true);

		ImageIcon referencia = new ImageIcon("res//Blackground.jpg");
		fundo = referencia.getImage();

		player = new Player();
		player.load();

		addKeyListener(new TecladoAdapter());

		timer = new Timer(5, this);
		timer.start();

		inicializaEnemy();
		emJogo = true;

	}

	public void inicializaEnemy() {
		int condernadas[] = new int[100];
		enemy = new ArrayList<Enemy>();

		for (int i = 0; i < condernadas.length; i++) {
			int x = (int) (Math.random() * 8000 + 1024);
			int y = (int) (Math.random() * 650 + 30);
			enemy.add(new Enemy(x, y));
		}

	}

	public void paint(Graphics g) {
		Graphics2D graficos = (Graphics2D) g;
		if (emJogo == true) {

			graficos.drawImage(fundo, 0, 0, null);
			graficos.drawImage(player.getImagen(), player.getX(), player.getY(), this);

			List<Tiro> tiros = player.getTiros();
			for (int i = 0; i < tiros.size(); i++) {
				Tiro m = tiros.get(i);
				m.load();
				graficos.drawImage(m.getImagem(), m.getX(), m.getY(), this);
			}

			for (int o = 0; o < enemy.size(); o++) {
				Enemy in = enemy.get(o);
				in.load();
				graficos.drawImage(in.getImagem(), in.getX(), in.getY(), this);

			}

		} else {

			ImageIcon fimJogo = new ImageIcon("res//gamerover.png");
			graficos.drawImage(fimJogo.getImage(), 0, 0, null);
		}

		g.dispose();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		player.update();

		List<Tiro> tiros = player.getTiros();
		for (int i = 0; i < tiros.size(); i++) {
			Tiro m = tiros.get(i);
			if (m.isVisivel()) {
				m.update();
			} else {
				tiros.remove(i);
			}
		}

		for (int o = 0; o < enemy.size(); o++) {
			Enemy in = enemy.get(o);

			if (in.isVisivel()) {
				in.update();

			} else {

				enemy.remove(o);

			}
		}
		checarColisoes();

		repaint();
	}

	public void checarColisoes() {
		Rectangle formaNave = player.getBounds();
		Rectangle formaEmeny;
		Rectangle formaTiro;

		for (int i = 0; i < enemy.size(); i++) {
			Enemy tempEnemy = enemy.get(i);
			formaEmeny = tempEnemy.getBounds();
			if (formaNave.intersects(formaEmeny)) {
				player.setVisivel(false);
				tempEnemy.setVisivel(false);
				emJogo = false;

			}

		}
		List<Tiro> tiros = player.getTiros();
		for (int j = 0; j < tiros.size(); j++) {
			Tiro tempTiro = tiros.get(j);
			formaTiro = tempTiro.getBounds();
			for (int o = 0; o < enemy.size(); o++) {
				Enemy tempEnemy = enemy.get(o);
				formaEmeny = tempEnemy.getBounds();
				if (formaTiro.intersects(formaEmeny)) {
					tempEnemy.setVisivel(false);
					tempTiro.setVisivel(false);
					
				}
			}
		}

	}

	private class TecladoAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			player.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			player.keyRelease(e);
		}

	}

}
