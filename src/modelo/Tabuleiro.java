package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tabuleiro implements CampoObservedor{
	
	private final int linhas;
	private final int colunas;
	private final int minas;
	
	private final List<Campo> campos = new ArrayList<>();
	private final List<Consumer<ResultadoDoEvento>> observadores = new ArrayList<>();

	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
	
		gerarCampos();
		asssociarVizinhos();
		SortearMinas();
	}
	
	public void registrarObservador(Consumer<ResultadoDoEvento> observador) {
		observadores.add(observador);
	}
	
	public void notificarObservador(boolean resultado) {
		observadores.stream().forEach(result -> result.accept(new ResultadoDoEvento(resultado)));
	}
	
	public void Abrir(int linha, int coluna) {
		campos.parallelStream()
		.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
		.findFirst()
		.ifPresent(c -> c.abrir());
	}
	
	public void AlternaMarcacao(int linha, int coluna) {
		campos.parallelStream()
		.filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
		.findFirst()
		.ifPresent(c -> c.alternarMarcacao());
	}

	private void gerarCampos() {
		for (int linha = 0; linha < linhas; linha++) {
			for (int coluna = 0; coluna < colunas; coluna++) {
				Campo campo = new Campo(linha, coluna);
				campo.resgitrarObservadores(this);	
				campos.add(campo);	
			}
		}
	}
	
	private void asssociarVizinhos() {
		for(Campo c1: campos) {
			for(Campo c2: campos) {
				c1.adicionarVizinhos(c2);
			}
		}
	}
	
	private void SortearMinas() {
		long minasArmada = 0;
		Predicate<Campo> minado = c -> c.isMinado();
		
		do {
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmada = campos.stream().filter(minado).count();
	
		} while (minasArmada < minas);
	}	
	
	public boolean objetivoAlcansado() {
		return campos.stream().allMatch(c -> c.objetivoAlcansado());
	}
	
	public void reiniciar() {
		campos.stream().forEach(c -> c.reiniciar());
		SortearMinas();
	}
	
	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}

	@Override
	public void eventoOcorreu(Campo campo, CampoEvento evento) {
		if (evento == CampoEvento.EXPLODIR) {
			mostrarMinas();
			notificarObservador(false);
		}else if(objetivoAlcansado()){
			notificarObservador(true);
		 }	
	}
	
	private void mostrarMinas() {
		campos.stream()
		.filter(c -> c.isMinado())
		.filter(c -> c.isMarcado())		
		.forEach(c -> c.setAberto(true));
	}

	public void paraCadaCampo(Consumer<Campo> funcao) {
		campos.forEach(funcao);
	}
}
