package Models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name="Adestrador")

public class Adestrador implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column(name = "Nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "Nacemento")
    @Temporal(TemporalType.DATE)
    private Date Nacemento;

    public  Adestrador(){

    }
    public Adestrador(String nome, Date Nacemento){
        this.nome = nome;
        this.Nacemento = Nacemento;
    }
    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getNacemento() {
        return Nacemento;
    }

    public void setNacemento(Date nacemento) {
        Nacemento = nacemento;
    }

    // mostrar los datos del entrenador limpiamente por consola
    @Override
    public String toString() {
        return "Adestrador{" +
                "id=" + Id +
                ", nome='" + nome + '\'' +
                ", misc='" + Nacemento + '\'' +
                '}';
    }
}
