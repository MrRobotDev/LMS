package entities;

import  javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Entity
public class Autentifikimi {
    private int punetori;
    private String user;
    private String password;

    @Id
    @Column(name = "punetori", nullable = false)
    public int getPunetori() {
        return punetori;
    }

    public void setPunetori(int punetori) {
        this.punetori = punetori;
    }

    @Basic
    @Column(name = "user", nullable = false, length = 50)
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 20)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autentifikimi that = (Autentifikimi) o;
        return punetori == that.punetori &&
                Objects.equals(user, that.user) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(punetori, user, password);
    }
}
