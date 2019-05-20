package entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Punetori {
    private int punetoriId;
    private int departmenti;
    private String emriP;
    private String mbiemriP;
    private String nrTelefonit;
    private String email;
    private Date dataLindjes;
    private int autori_id;

    @Id
    @Column(name = "punetori_id", nullable = false)
    public int getPunetoriId() {
        return punetoriId;
    }

    public void setPunetoriId(int punetoriId) {
        this.punetoriId = punetoriId;
    }

    @Basic
    @Column(name = "departmenti", nullable = false)
    public int getDepartmenti() {
        return departmenti;
    }

    public void setDepartmenti(int departmenti) {
        this.departmenti = departmenti;
    }

    @Basic
    @Column(name = "emri_p", nullable = false, length = 30)
    public String getEmriP() {
        return emriP;
    }

    public void setEmriP(String emriP) {
        this.emriP = emriP;
    }

    @Basic
    @Column(name = "mbiemri_p", nullable = false, length = 30)
    public String getMbiemriP() {
        return mbiemriP;
    }

    public void setMbiemriP(String mbiemriP) {
        this.mbiemriP = mbiemriP;
    }

    @Basic
    @Column(name = "nrTelefonit", nullable = false, length = 20)
    public String getNrTelefonit() {
        return nrTelefonit;
    }

    public void setNrTelefonit(String nrTelefonit) {
        this.nrTelefonit = nrTelefonit;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 40)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "data_lindjes", nullable = false)
    public Date getDataLindjes() {
        return dataLindjes;
    }

    public void setDataLindjes(Date dataLindjes) {
        this.dataLindjes = dataLindjes;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Punetori punetori = (Punetori) o;
        return punetoriId == punetori.punetoriId &&
                departmenti == punetori.departmenti &&
                Objects.equals(emriP, punetori.emriP) &&
                Objects.equals(mbiemriP, punetori.mbiemriP) &&
                Objects.equals(nrTelefonit, punetori.nrTelefonit) &&
                Objects.equals(email, punetori.email) &&
                Objects.equals(dataLindjes, punetori.dataLindjes);
    }

    @Override
    public int hashCode() {

        return Objects.hash(punetoriId, departmenti, emriP, mbiemriP, nrTelefonit, email, dataLindjes);
    }
}
