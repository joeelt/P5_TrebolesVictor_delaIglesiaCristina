package backtracking;
import estructura.Encreuades;
import estructura.PosicioInicial;

import java.util.Iterator;

public class SolucioBacktracking {

	/* TODO
	 * cal definir els atributs necessaris
	 */
	private char[][] solucio;
	private char[][] solucioMillor;
	private final Encreuades repte;
	private boolean[] markatge;
	private int numMillorSol = -1;

	
	public SolucioBacktracking(Encreuades repte) {
		this.repte = repte;
	}

	public char[][] getMillorSolucio() {
		return solucio; //TODO: Cris
	}

	public Runnable start(boolean optim)
	{
		/* TODO
		 * cal inicialitzar els atributs necessaris
		 */
		markatge = new boolean[this.repte.getItemsSize()];
		for (int i = 0; i < this.repte.getItemsSize(); i++) {
			markatge[i] = false;
		}

		this.solucio = this.repte.getPuzzle();
		solucioMillor = this.repte.getPuzzle();

		if(!optim) {
			if (!this.backUnaSolucio(0))
				throw new RuntimeException("solució no trobada");
			guardarMillorSolucio();

		}else
			this.backMillorSolucio(0);
		return null;
	}

	/* esquema recursiu que troba una solució
	 * utilitzem una variable booleana (que retornem)
	 * per aturar el recorregut quan haguem trobat una solució
	 */
	private boolean backUnaSolucio(int indexUbicacio) {
		boolean trobada = false;
		// iterem sobre els possibles elements
		for(int indexItem = 0; indexItem < this.repte.getItemsSize() && !trobada; indexItem++) {
			//mirem si l'element es pot posar a la ubicació actual
			if(acceptable( indexUbicacio, indexItem)) {
				//posem l'element a la solució actual
				anotarASolucio(indexUbicacio, indexItem);

				if(esSolucio(indexUbicacio)) { // és solució si totes les ubicacions estan plenes
					return true;
				} else
					trobada = this.backUnaSolucio(indexUbicacio+1); //inserim la següent paraula
				if(!trobada)
					// esborrem la paraula actual, per després posar-la a una altra ubicació
					desanotarDeSolucio(indexUbicacio, indexItem);
			}
		}
		return trobada;
	}
	/* TODO: Victor
	 * Esquema recursiu que busca totes les solucions
	 * no cal utilitzar una variable booleana per aturar perquè busquem totes les solucions
	 * cal guardar una COPIA de la millor solució a una variable
	 */
	private void backMillorSolucio(int indexUbicacio) {
		//posible solucion
		for (int indexItem = 0; indexItem < this.repte.getItemsSize(); indexItem++) {
			if (acceptable(indexUbicacio, indexItem)){
				anotarASolucio(indexUbicacio, indexItem);
				if (esSolucio(indexUbicacio)){
					guardarMillorSolucio();
				} else {
					backMillorSolucio(indexUbicacio);
				}
				desanotarDeSolucio(indexUbicacio, indexItem);
			}
		}
	}

	private boolean acceptable(int indexUbicacio, int indexItem) {
		if(indexUbicacio >= this.repte.getEspaisDisponibles().size()) return false;
		char[] item = this.repte.getItem(indexItem);
		PosicioInicial posicio = this.repte.getEspaisDisponibles().get(indexUbicacio);

		// TODO item i posicio mateixa longitud
		if (item.length != posicio.getLength()) return false;
		// todo marcatge per no repetir la paraula
		return true;

	}
	
	private void anotarASolucio(int indexUbicacio, int indexItem) {
		//TODO: Cris
		PosicioInicial posicio = this.repte.getEspaisDisponibles().get(indexUbicacio);
		int itemL = this.repte.getItem(indexItem).length;
		solucio[posicio.getInitRow()][posicio.getInitCol()] = this.repte.getItem(indexItem)[0];
		for(int i = 0; i < itemL; i++) {
			if(posicio.getDireccio() == 'H') {
				solucio[posicio.getInitRow()][posicio.getInitCol()+i] = this.repte.getItem(indexItem)[i];
			} else {
				solucio[i][posicio.getInitCol()] = this.repte.getItem(indexItem)[i];
			}
		}
	}

	private void desanotarDeSolucio(int indexUbicacio, int indexItem) {
		//TODO: Cris
		PosicioInicial posicio = this.repte.getEspaisDisponibles().get(indexUbicacio);
		for(int i = 0; i < posicio.getLength(); i++) {
			if(posicio.getDireccio() == 'H') {
				if(esPotEliminar(posicio.getInitRow(), i, 'H')) {
					solucio[posicio.getInitRow()][i] = ' ';
				}
			} else {
				if(esPotEliminar(i, posicio.getInitCol(), 'V')) {
					solucio[i][posicio.getInitCol()] = ' ';
				}
			}
		}
	}

	private boolean esPotEliminar(int x, int y, char direccio) {
		if(direccio == 'H') {
			return  (x == this.solucio.length || '▪' == this.solucio[y][x+1]) && (x == 0 || '▪' == this.solucio[y][x-1]);
		} else {
			return (y == this.solucio.length || '▪' == this.solucio[y+1][x]) && (x == 0 || '▪' == this.solucio[y-1][x]);
		}
	}

	
	private boolean esSolucio(int index) {
		return index+1 == this.repte.getEspaisDisponibles().size(); // TODO: Cris
	}

	//
	private int calcularFuncioObjectiu(char[][] matriu) { // TODO: Cris
		int valor = 0;
		for(int i = 0; i < matriu.length; i++) {
			for(int j = 0; j < matriu.length; j++) {
				valor += matriu[i][j];
			}
		}
		return valor;
	}
	
	private void guardarMillorSolucio() {
		// TODO - cal guardar un clone - Cris
		int puntuacioActual = calcularFuncioObjectiu(this.solucio);

		if (puntuacioActual > numMillorSol){
			for (int i = 0; i < solucio.length; i++){
				for (int j = 0; j < solucio[i].length; j++){
					solucioMillor[i][j] = solucio[i][j];
				}
			}
			numMillorSol = puntuacioActual;
		}
	}
	
	public String toString() {
		String resultat = "";
		//TODO: Cris
		for(int i = 0; i < solucio.length; i++) {
			for(int j = 0; j < solucio[i].length; j++) {
				resultat += solucio[i][j] + " ";
			}
			resultat += "\n";
		}
		return resultat;
	}

}
