package modelo;

public class ManTenp {

	public static void main(String[] args) {

		Tabuleiro tabuleiro = new Tabuleiro(3, 3, 9);
		
		tabuleiro.registrarObservador(e -> {
			if (e.isGanhou()) {
				System.out.println("Ganhou...:)");
			}else {
				System.out.println("Perdeu..;(");
			}
		});
		
		tabuleiro.AlternaMarcacao(0, 0);
		tabuleiro.AlternaMarcacao(0, 1);
		tabuleiro.AlternaMarcacao(0, 2);
		tabuleiro.AlternaMarcacao(1, 0);
		tabuleiro.AlternaMarcacao(1, 1);
		tabuleiro.AlternaMarcacao(1, 2);
		tabuleiro.AlternaMarcacao(2, 0);
		tabuleiro.AlternaMarcacao(2, 1);
		tabuleiro.Abrir(2, 2);
//		tabuleiro.AlternaMarcacao(2, 2);
		
	}
}
