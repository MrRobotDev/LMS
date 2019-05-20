package entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "referenca_huazime")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "ReferencaHuazime.findLexuesiID" , query = "select max(r.dataHuazimit) from ReferencaHuazime r where r.lexuesiId=:lexuesi"),
        @NamedQuery(name = "ReferencaHuazime.nextNumber" , query = "select max(r.referencaId) from ReferencaHuazime r"),
        @NamedQuery(name = "ReferencaHuazime.findLibriFromId", query = "select l from Libri l where l.libriId =:libri_id"),
        @NamedQuery(name = "ReferencaHuazime.findAll" , query = "select r from ReferencaHuazime r")
})

public class ReferencaHuazime {
    private int referencaId;
    private int libriId;
    private int lexuesiId;
    private Date dataHuazimit;
    private Date dataKthimit;

    @Id
    @Column(name = "referenca_id", nullable = false)
    public int getReferencaId() {
        return referencaId;
    }

    public void setReferencaId(int referencaId) {
        this.referencaId = referencaId;
    }

    @Basic
    @Column(name = "libri_id", nullable = false, length = 15)
    public int getLibriId() {
        return libriId;
    }

    public void setLibriId(int libriId) {
        this.libriId = libriId;
    }

    @Basic
    @Column(name = "lexuesi_id", nullable = false)
    public int getLexuesiId() {
        return lexuesiId;
    }

    public void setLexuesiId(int lexuesiId) {
        this.lexuesiId = lexuesiId;
    }

    @Basic
    @Column(name = "data_huazimit", nullable = false)
    public Date getDataHuazimit() {
        return dataHuazimit;
    }

    public void setDataHuazimit(Date dataHuazimit) {
        this.dataHuazimit = dataHuazimit;
    }

    @Basic
    @Column(name = "data_kthimit", nullable = false)
    public Date getDataKthimit() {
        return dataKthimit;
    }

    public void setDataKthimit(Date dataKthimit) {
        this.dataKthimit = dataKthimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReferencaHuazime that = (ReferencaHuazime) o;
        return referencaId == that.referencaId &&
                lexuesiId == that.lexuesiId &&
                libriId == that.libriId &&
                Objects.equals(dataHuazimit, that.dataHuazimit) &&
                Objects.equals(dataKthimit, that.dataKthimit);
    }

    @Override
    public int hashCode() {

        return Objects.hash(referencaId, libriId, lexuesiId, dataHuazimit, dataKthimit);
    }
}
