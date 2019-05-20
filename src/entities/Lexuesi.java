package entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Entity
@Table(name = "lexuesi")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Lexuesi.findAll", query = "SELECT l FROM Lexuesi l")
        , @NamedQuery(name = "Lexuesi.findByEmri", query = "SELECT l FROM Lexuesi l WHERE l.emri = :emri")
        , @NamedQuery(name = "Lexuesi.findByMbiemri", query = "SELECT l FROM Lexuesi l WHERE l.mbiemri = :mbiemri")
        , @NamedQuery(name = "Lexuesi.findByEmriDheMbiemri" , query = "SELECT l from Lexuesi l where l.emri =:emri and l.mbiemri =:mbiemri")
        , @NamedQuery(name = "Lexuesi.findByAdresa", query = "SELECT l FROM Lexuesi l WHERE l.adresa = :adresa")
        , @NamedQuery(name = "Lexuesi.findByDataRegjistrimit", query = "SELECT l FROM Lexuesi l WHERE l.dataRegjistrimit = :dataRegjistrimit")
        , @NamedQuery(name = "Lexuesi.findByKontakti", query = "SELECT l FROM Lexuesi l WHERE l.kontakti = :kontakti")
        , @NamedQuery(name = "Lexuesi.findByPaguar", query = "SELECT l FROM Lexuesi l WHERE l.paguar = :paguar")
        , @NamedQuery(name = "Libri.findByVitiLindjes", query = "SELECT l FROM Lexuesi l WHERE l.vitiLindjes = :vitiLindjes")})

public class Lexuesi {
    private int lexuesiId;
    private String emri;
    private String mbiemri;
    private String adresa;
    private Date dataRegjistrimit;
    private String kontakti;
    private Date vitiLindjes;
    private boolean paguar;

    public Lexuesi(){

    }

    public Lexuesi(int lexuesiId , String emri , String mbiemri , String adresa , Date dataRegjistrimit , String kontakti , Date vitiLindjes  , boolean paguar){
        this.lexuesiId = lexuesiId;
        this.emri = emri;
        this.mbiemri = mbiemri;
        this.adresa = adresa;
        this.dataRegjistrimit = dataRegjistrimit;
        this.kontakti = kontakti;
        this.vitiLindjes = vitiLindjes;
        this.paguar = paguar;
    }

    @Id
    @Column(name = "lexuesi_id", nullable = false)
    public int getLexuesiId() {
        return lexuesiId;
    }

    public void setLexuesiId(int lexuesiId) {
        this.lexuesiId = lexuesiId;
    }

    @Basic
    @Column(name = "emri", nullable = false, length = 15)
    public String getEmri() {
        return emri;
    }

    public void setEmri(String emri) {
        this.emri = emri;
    }

    @Basic
    @Column(name = "mbiemri", nullable = false, length = 15)
    public String getMbiemri() {
        return mbiemri;
    }

    public void setMbiemri(String mbiemri) {
        this.mbiemri = mbiemri;
    }

    @Basic
    @Column(name = "adresa", nullable = false, length = 20)
    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    @Basic
    @Column(name = "dataRegjistrimit", nullable = false)
    public Date getDataRegjistrimit() {
        return dataRegjistrimit;
    }

    public void setDataRegjistrimit(Date dataRegjistrimit) {
        this.dataRegjistrimit = dataRegjistrimit;
    }

    @Basic
    @Column(name = "kontakti", nullable = false, length = 50)
    public String getKontakti() {
        return kontakti;
    }

    public void setKontakti(String kontakti) {
        this.kontakti = kontakti;
    }

    @Basic
    @Column(name = "paguar", nullable = false)
    public boolean isPaguar() {
        return paguar;
    }

    public void setPaguar(boolean paguar) {
        this.paguar = paguar;
    }

    @Basic
    @Column(name = "viti_lindjes", nullable = false)
    public Date getVitiLindjes() {
        return vitiLindjes;
    }

    public void setVitiLindjes(Date vitiLindjes) {
        this.vitiLindjes = vitiLindjes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lexuesi lexuesi = (Lexuesi) o;
        return lexuesiId == lexuesi.lexuesiId &&
                paguar == lexuesi.paguar &&
                Objects.equals(emri, lexuesi.emri) &&
                Objects.equals(mbiemri, lexuesi.mbiemri) &&
                Objects.equals(adresa, lexuesi.adresa) &&
                Objects.equals(dataRegjistrimit, lexuesi.dataRegjistrimit) &&
                Objects.equals(kontakti, lexuesi.kontakti) &&
                Objects.equals(vitiLindjes, lexuesi.vitiLindjes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lexuesiId, emri, mbiemri, adresa, dataRegjistrimit, kontakti, paguar, vitiLindjes);
    }
}
