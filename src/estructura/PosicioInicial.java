package estructura;

public class PosicioInicial implements Comparable<PosicioInicial>{
    private final int initRow;
    private final int initCol;
    private final int length;
    private final char direccio;

    public PosicioInicial(int initRow, int initCol, int length, char direccio) {
        this.initRow = initRow;
        this.initCol = initCol;
        this.length = length;
        this.direccio = direccio;
    }

    // Getters per cada atribut
    public int getInitRow() {
        return initRow;
    }

    public int getInitCol() {
        return initCol;
    }

    public int getLength() {
        return length;
    }

    public char getDireccio() {
        return direccio;
    }

    @Override
    public String toString() {
        return "PosicioInicial{" +
                "initRow=" + initRow +
                ", initCol=" + initCol +
                ", length=" + length +
                ", direccio=" + direccio +
                '}';
    }

    @Override
    public int compareTo(PosicioInicial other) {
        return Integer.compare(this.length, other.length);
    }
}
