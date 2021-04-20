package modelo;

import java.util.ArrayList;
import java.util.List;

public class Campo {

	private final int linha;
	private final int coluna;
	
	private boolean aberto = false;
	private boolean minado = false;
	private boolean marcado = false;

	private List<Campo> vizinhos = new ArrayList<>();
	private List<CampoObservedor> Observadores = new ArrayList<>(); 

	Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	
	public void resgitrarObservadores(CampoObservedor observador) {
		Observadores.add(observador);
	}
	
	public void notificarObservador(CampoEvento evento) {
		Observadores.stream().forEach(obs -> obs.eventoOcorreu(this, evento));
	}
	
	boolean adicionarVizinhos(Campo vizinho) {
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		
		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaLinha + deltaColuna;
		
		if (deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		}else if(deltaGeral == 2 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		}else {
			return false;
		}
	}
	
	public void alternarMarcacao() {
		if (!aberto) {
			marcado = !marcado;
			
			if (marcado) {
				notificarObservador(CampoEvento.MARCAR);
			}else {
				notificarObservador(CampoEvento.DESMARCAR);
			}
		}
	}
	
	public boolean abrir() {
		if (!aberto && !marcado) {
		   if(minado) {
			notificarObservador(CampoEvento.EXPLODIR);
			return true;
		   }
		
		setAberto(true);
		
		if (vizinhancasSegura()) {
				vizinhos.forEach( v -> v.abrir());
		}
			return true;
		} else {
			return false;
		  }
	}
	
	public boolean vizinhancasSegura(){	
		return vizinhos.stream().noneMatch(v -> v.minado);
	}
	
	public void minar() {
		minado = true;
	}
	
	public boolean isMinado() {
		return minado;
	}
	
	public boolean isMarcado() {
		return marcado;		
	}
	
	void setAberto(boolean aberto) {
		this.aberto = aberto;
		
		if (aberto) {
			notificarObservador(CampoEvento.ABRIR);
		}
	}

	public boolean isAberto() {
		return aberto;
	}
	
	public boolean isfechado() {
		return !isAberto();
	}

	public int getColuna() {
		return coluna;
	}

	public int getLinha() {
		return linha;
	}
	
    boolean objetivoAlcansado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}
	
	public int minasNaVizinhancas() {
		return (int) vizinhos.stream().filter(v -> v.minado).count();
	}
	
	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
		notificarObservador(CampoEvento.REINICIAR);
	}	
}
