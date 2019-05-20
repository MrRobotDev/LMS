package entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Autori {
    private int autoriId;
    private String emri;
    private String mbiemri;
    private int libriId;

    @Id
    @Column(name = "autori_ID", nullable = false)
    public int getAutoriId() {
        return autoriId;
    }

    public void setAutoriId(int autoriId) {
        this.autoriId = autoriId;
    }

    @Basic
    @Column(name = "emri", nullable = false, length = 20)
    public String getEmri() {
        return emri;
    }

    public void setEmri(String emri) {
        this.emri = emri;
    }

    @Basic
    @Column(name = "mbiemri", nullable = false, length = 20)
    public String getMbiemri() {
        return mbiemri;
    }

    public void setMbiemri(String mbiemri) {
        this.mbiemri = mbiemri;
    }

    @Basic
    @Column(name = "libri_id", nullable = false)
    public int getLibriId() {
        return libriId;
    }

    public void setLibriId(int libriId) {
        this.libriId = libriId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autori autori = (Autori) o;
        return autoriId == autori.autoriId &&
                libriId == autori.libriId &&
                Objects.equals(emri, autori.emri) &&
                Objects.equals(mbiemri, autori.mbiemri);
    }

    @Override
    public int hashCode() {

        return Objects.hash(autoriId, emri, mbiemri, libriId);
    }
}
