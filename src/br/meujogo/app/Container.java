package br.meujogo.app;

import javax.swing.JFrame;

import br.meujogomodelo.app.Fase;

public class Container extends JFrame {

	public Container() {
		add(new Fase());
		setTitle("Guerra nas Estrelas");
		setSize(1024, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		this.setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Container();
	}

}
