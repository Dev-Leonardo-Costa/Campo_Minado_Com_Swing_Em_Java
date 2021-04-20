package Visao;

import javax.swing.JFrame;

import modelo.Tabuleiro;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame{

	public TelaPrincipal() {
		Tabuleiro tabuleiroNIVELFACIL = new Tabuleiro(16, 20, 5);
		add( new PainelDoTabuleiro(tabuleiroNIVELFACIL));
		
		setTitle("Campo minado");
		setSize(690, 438);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	public static void main(String[] args) {	
		new TelaPrincipal();
	}
}
