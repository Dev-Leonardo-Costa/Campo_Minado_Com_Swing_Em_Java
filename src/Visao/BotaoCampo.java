package Visao;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import modelo.Campo;
import modelo.CampoEvento;
import modelo.CampoObservedor;
// o visual de aquele que acontecer no campo
@SuppressWarnings("serial")
public class BotaoCampo extends JButton implements CampoObservedor, MouseListener{
	
	private final Color BG_PADRAO = new Color(100,149,237);
	private final Color BG_MARCADO = new Color(85,107,47);
	private final Color BG_EXPLODIR = new Color(255,0,0);
	private final Color BG_TEXTO_VERDE = new Color(34,139,34);
	
	private Campo campo;
	
	public BotaoCampo(Campo campo) {
		this.campo = campo;
		setBackground(BG_PADRAO);
		setOpaque(true);
		setBorder(BorderFactory.createBevelBorder(0));
			
		addMouseListener(this);
		campo.resgitrarObservadores(this);
	}

	@Override
	public void eventoOcorreu(Campo campo, CampoEvento evento) {
		switch (evento) {
		case ABRIR:
			AplicarAbrir();
			break;
		case MARCAR:
			MarcaStilo();
			break;			
		case EXPLODIR:
			ExplodirStilo();
			break;	
		default:
			StiloPadrao();	
		}
		
		SwingUtilities.invokeLater(()->{
			repaint();
			validate();
		});
	}
	
	private void MarcaStilo() {
		setBackground(BG_MARCADO);
		setText("M");
	}
	
	private void ExplodirStilo() {
		setBackground(Color.red);
		setForeground(Color.WHITE);
		setText("X");
	}
	
	private void StiloPadrao() {
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("");
	}
    // AS INTERFACES DO EVENTOS DO MAUSE	
	
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 1) {
			campo.abrir();
		}else {
			campo.alternarMarcacao();
		}
	}

	private void AplicarAbrir() {
		
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		if (campo.isMinado()) {
			  setBackground(BG_EXPLODIR);
			  return;
		}
		
		setBackground(BG_PADRAO);
	
		switch (campo.minasNaVizinhancas()) {
		case 1:
			setForeground(BG_TEXTO_VERDE);
			break;
		case 2:
			setForeground(Color.BLUE);
			break;
		case 3:
			// 3 minas perto do campo aberto
			setForeground(Color.YELLOW);
			break;
		case 4:
		case 5:
		case 6:
			setForeground(Color.RED);
			break;
		default:
			setForeground(Color.BLACK);
		}
		
		String valor = !campo.vizinhancasSegura() ?
				campo.minasNaVizinhancas() + " ":"";
				setText(valor);
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}
	
	public void mouseEntered(MouseEvent e) {
		
	}
	
	public void mouseExited(MouseEvent e) {
		
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
}
