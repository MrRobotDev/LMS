package resourceFunctions;

import entities.Libri;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class LibriTableModel extends AbstractTableModel {

    List<Libri> lista;
    String[] kolonat = {"ID", "Titulli", "ISBN", "Kategoria", "Pershkrimi", "Publikuesi", "DataPublikimit", "DataRegjistrimit", "Nr.Kopjeve"};

    public LibriTableModel() {

    }

    public LibriTableModel(List<Libri> lista) {
        this.lista = lista;
    }

    public void add(List<Libri> lista) {
        this.lista = lista;
    }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    public Libri getLibri(int pozita) {
        return lista.get(pozita);
    }

    public String getColumnName(int column) {
        return kolonat[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Libri l = getLibri(rowIndex);
        switch (columnIndex) {
            case 0:
                return l.getLibriId();
            case 1:
                return l.getEmri();
            case 2:
                return l.getIsbn();
            case 3:
                return l.getKategoria();
            case 4:
                return l.getPershkrimi();
            case 5:
                return l.getPublikuesi();
            case 6:
                return l.getDataPublikimit();
            case 7:
                return l.getDataRegjistrimit();
            case 8:
                return l.getNumriKopjeve();
            default:
                return null;
        }
    }
}
