package backtracking;
import estructura.Encreuades;
import estructura.PosicioInicial;

import java.util.Iterator;

public class SolucioBacktracking {

	/* TODO
	 * cal definir els atributs necessaris
	 */
	private char[][] solucio;
	private final Encreuades repte;

	
	public SolucioBacktracking(Encreuades repte) {
		this.repte = repte;
	}

	public char[][] getMillorSolucio() {
		this.solucio = this.repte.getPuzzle();
		Iterator<PosicioInicial> espaisDispo = this.repte.getEspaisDisponibles().iterator();
		int sumaChar, sumaCharAntic = 0;
		while (espaisDispo.hasNext()) {
			PosicioInicial posicio = espaisDispo.next();
			for(int i = 0; i < this.repte.getItemsSize(); i++) {
				sumaChar = 0;
				if(this.repte.getItem(i).length == posicio.getLength()) {
					for (int j = 0; j < this.repte.getItem(i).length; j++) {
						sumaChar = sumaChar + this.repte.getItem(i)[j];
					}
					if(sumaCharAntic < sumaChar) {
						sumaCharAntic = sumaChar;
						solucio[posicio.getInitRow()][posicio.getInitCol()] = this.repte.getItem(i)[0];
						for(int k = 1; k < this.repte.getItem(i).length; k++) {
							if(posicio.getDireccio() == 'H') {
								solucio[posicio.getInitRow()][k] = this.repte.getItem(i)[k];
							} else {
								solucio[k][posicio.getInitCol()] = this.repte.getItem(i)[k];
							}
						}
					}
				}
			}
		}
		return solucio; //TODO: Cris
	}

	public Runnable start(boolean optim)
	{
		/* TODO
		 * cal inicialitzar els atributs necessaris
		 */
		this.solucio = this.repte.getPuzzle();

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
		if (esSolucio(indexUbicacio)){

		}


	}
	
	private boolean acceptable(int indexUbicacio, int indexItem) {
		int espaiL = this.repte.getEspaisDisponibles().get(indexUbicacio).getLength();
		int itemL = this.repte.getItem(indexItem).length;
        return espaiL == itemL; //TODO: Cris
	}
	
	private void anotarASolucio(int indexUbicacio, int indexItem) {
		//TODO: Cris
		PosicioInicial posicio = this.repte.getEspaisDisponibles().get(indexUbicacio);
		int itemL = this.repte.getItem(indexItem).length;
		solucio[posicio.getInitRow()][posicio.getInitCol()] = this.repte.getItem(indexItem)[0];
		for(int i = 1; i < itemL; i++) {
			if(posicio.getDireccio() == 'H') {
				solucio[posicio.getInitRow()][i] = this.repte.getItem(indexItem)[i];
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
		return index == this.repte.getEspaisDisponibles().size(); // TODO: Cris
	}
	
	private int calcularFuncioObjectiu(char[][] matriu) {

		return 0; //TODO: Victor
	}
	
	private void guardarMillorSolucio() {
		// TODO - cal guardar un clone - Victor

		char[][] copiaSolucio = new char[solucio.length][solucio[0].length];

		for (int i = 0; i < solucio.length; i++){
			for (int j = 0; j < solucio[i].length; j++){
				copiaSolucio[i][j] = solucio[i][j];
			}
		}

		int puntuacioActual = calcularFuncioObjectiu(copiaSolucio);
		int puntacioMillor  = calcularFuncioObjectiu(this.solucio);

		if (puntuacioActual > puntacioMillor){
			solucio = copiaSolucio;
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
