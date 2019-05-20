package entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "kategoria_punetorit", schema = "library", catalog = "")
public class KategoriaPunetorit {
    private int kategoriaId;
    private String emriDepartmentit;
    private String pershkrim;

    @Id
    @Column(name = "kategoria_id", nullable = false)
    public int getKategoriaId() {
        return kategoriaId;
    }

    public void setKategoriaId(int kategoriaId) {
        this.kategoriaId = kategoriaId;
    }

    @Basic
    @Column(name = "emri_departmentit", nullable = false, length = 30)
    public String getEmriDepartmentit() {
        return emriDepartmentit;
    }

    public void setEmriDepartmentit(String emriDepartmentit) {
        this.emriDepartmentit = emriDepartmentit;
    }

    @Basic
    @Column(name = "pershkrim", nullable = false, length = 50)
    public String getPershkrim() {
        return pershkrim;
    }

    public void setPershkrim(String pershkrim) {
        this.pershkrim = pershkrim;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KategoriaPunetorit that = (KategoriaPunetorit) o;
        return kategoriaId == that.kategoriaId &&
                Objects.equals(emriDepartmentit, that.emriDepartmentit) &&
                Objects.equals(pershkrim, that.pershkrim);
    }

    @Override
    public int hashCode() {

        return Objects.hash(kategoriaId, emriDepartmentit, pershkrim);
    }
}
