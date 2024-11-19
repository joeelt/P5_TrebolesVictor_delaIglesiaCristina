package backtracking;
import estructura.Encreuades;
import estructura.PosicioInicial;

public class SolucioBacktracking {

	/* TODO
	 * cal definir els atributs necessaris
	 */
	private final Encreuades repte;

	
	public SolucioBacktracking(Encreuades repte) {
		this.repte = repte;
	}

	public char[][] getMillorSolucio() {
		return null; //TODO
	}

	public Runnable start(boolean optim)
	{
		/* TODO
		 * cal inicialitzar els atributs necessaris
		 */

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
	/* TODO
	 * Esquema recursiu que busca totes les solucions
	 * no cal utilitzar una variable booleana per aturar perquè busquem totes les solucions
	 * cal guardar una COPIA de la millor solució a una variable
	 */
	private void backMillorSolucio(int indexUbicacio) {

	}
	
	private boolean acceptable(int indexUbicacio, int indexItem) {
		return false; //TODO
	}
	
	private void anotarASolucio(int indexUbicacio, int indexItem) {
		//TODO
	}
	
	private void desanotarDeSolucio(int indexUbicacio, int indexItem) {
		//TODO
	}

	
	private boolean esSolucio(int index) {
		return false; //TODO
	}
	
	private int calcularFuncioObjectiu(char[][] matriu) {
		return 0; //TODO
	}
	
	private void guardarMillorSolucio() {
		// TODO - cal guardar un clone
	}
	
	public String toString() {
		String resultat = "";
		//TODO
		return resultat;
	}

}
