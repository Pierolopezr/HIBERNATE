package Models;

import javax.persistence.Entity;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name="pokedex")

public class Pokedex implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;
    @Column(name = "peso", precision = 5, scale = 2)
    private BigDecimal peso;
    @Column(name = "misc")
    private String misc;

    public Pokedex(){} // OBLIGATORIO para que Hibernate lea filas de la BD y cree los objetos

    public Pokedex(String nome, BigDecimal peso, String misc){
        this.nome = nome;
        this.peso = peso;
        this.misc = misc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public String getMisc() {
        return misc;
    }

    public void setMisc(String misc) {
        this.misc = misc;
    }

    @Override
    public String toString() {
        return "pokedex{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", peso=" + peso +
                ", misc='" + misc + '\'' +
                '}';
    }
}

