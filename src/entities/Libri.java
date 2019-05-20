package entities;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "libri")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Libri.findAll", query = "SELECT l FROM Libri l")
        , @NamedQuery(name = "Libri.findByEmri", query = "SELECT l FROM Libri l WHERE l.emri = :emri")
        , @NamedQuery(name = "Libri.findByKategoria", query = "SELECT l FROM Libri l WHERE l.kategoria = :kategoria")
        , @NamedQuery(name = "Libri.findByDataPublikimit", query = "SELECT l FROM Libri l WHERE l.dataPublikimit = :dataPublikimit")
        , @NamedQuery(name = "Libri.findByDataRegjistrimit", query = "SELECT l FROM Libri l WHERE l.dataRegjistrimit = :dataRegjistrimit")
        , @NamedQuery(name = "Libri.findByNumriKopjeve", query = "SELECT l FROM Libri l WHERE l.numriKopjeve = :numriKopjeve")
        , @NamedQuery(name = "Libri.findByPershkrimi", query = "SELECT l FROM Libri l WHERE l.pershkrimi = :pershkrimi")
        , @NamedQuery(name = "Libri.findByLibriId", query = "SELECT l FROM Libri l WHERE l.libriId = :libriId")
        , @NamedQuery(name = "Libri.findByIsbn", query = "SELECT l FROM Libri l WHERE l.isbn = :isbn")
        , @NamedQuery(name = "Libri.findByAutori_id" , query = "SELECT l from Libri l where l.autori_id = :autori_id")})



public class Libri {
    private String emri;
    private String kategoria;
    private Date dataPublikimit;
    private Byte numriKopjeve;
    private String pershkrimi;
    private int libriId;
    private String isbn;
    private String publikuesi;
    private Date dataRegjistrimit;
    private int autori_id;

    public Libri() {

    }

    public Libri(int libriId, String emri, String isbn, String kategoria, String pershkrimi, String publikuesi, Date dataPublikimit, Date dataRegjistrimit, Byte numriKopjeve) {
        this.libriId = libriId;
        this.emri = emri;
        this.isbn = isbn;
        this.kategoria = kategoria;
        this.pershkrimi = pershkrimi;
        this.publikuesi = publikuesi;
        this.dataPublikimit = dataPublikimit;
        this.dataRegjistrimit = dataRegjistrimit;
        this.numriKopjeve = numriKopjeve;
    }

    @Basic
    @Column(name = "emri", nullable = false, length = 40)
    public String getEmri() {
        return emri;
    }

    public void setEmri(String emri) {
        this.emri = emri;
    }

    @Basic
    @Column(name = "kategoria", nullable = true, length = 20)
    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

    @Basic
    @Column(name = "dataPublikimit", nullable = true)
    public Date getDataPublikimit() {
        return dataPublikimit;
    }

    public void setDataPublikimit(Date dataPublikimit) {
        this.dataPublikimit = dataPublikimit;
    }

    @Basic
    @Column(name = "numri_kopjeve", nullable = true)
    public Byte getNumriKopjeve() {
        return numriKopjeve;
    }

    public void setNumriKopjeve(Byte numriKopjeve) {
        this.numriKopjeve = numriKopjeve;
    }

    @Basic
    @Column(name = "pershkrimi", nullable = true, length = 50)
    public String getPershkrimi() {
        return pershkrimi;
    }

    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
    }

    @Id
    @Column(name = "libri_id", nullable = false)
    public int getLibriId() {
        return libriId;
    }

    public void setLibriId(int libriId) {
        this.libriId = libriId;
    }

    @Basic
    @Pattern(regexp = "[\\p{Digit}&&[123456789]]+")
    @Column(name = "ISBN", nullable = true, length = 15)
    public String getIsbn() {
        return isbn;
    }

    @Pattern(regexp = "[\\p{Digit}&&[123456789]]+")
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Basic
    @Column(name = "publikuesi", nullable = false, length = 20)
    public String getPublikuesi() {
        return publikuesi;
    }

    public void setPublikuesi(String publikuesi) {
        this.publikuesi = publikuesi;
    }

    @Basic
    @Column(name = "dataRegjistrimit", nullable = true)
    public Date getDataRegjistrimit() {
        return dataRegjistrimit;
    }

    public void setDataRegjistrimit(Date dataRegjitrimit) {
        this.dataRegjistrimit = dataRegjitrimit;
    }

    @Basic
    @Column(name = "autori_id" , nullable = false)
    public int getAutori_id(){
        return autori_id;
    }

    public void setAutori_id(int autori_id){
        this.autori_id = autori_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libri libri = (Libri) o;
        return libriId == libri.libriId &&
                Objects.equals(emri, libri.emri) &&
                Objects.equals(kategoria, libri.kategoria) &&
                Objects.equals(pershkrimi, libri.pershkrimi) &&
                Objects.equals(isbn, libri.isbn) &&
                Objects.equals(publikuesi, libri.publikuesi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emri, kategoria, dataPublikimit, numriKopjeve, pershkrimi, libriId, isbn, publikuesi, dataRegjistrimit);
    }
}
