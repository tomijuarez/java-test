package persistenceModel.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@TableGenerator(name = "PERSONA", uniqueConstraints = {@UniqueConstraint(columnNames = "ID")})
public class PersonaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;
    @Column(name = "NOMBRE", unique = false, nullable = false, length = 100)
    private String nombre;
    @Column(name = "APELLIDO", unique = false, nullable = false, length = 100)
    private String apellido;
    @Column(name = "DNI", unique = false, nullable = false, length = 100)
    private String dni;
    @Column(name = "EDAD", unique = false, nullable = false)
    private int edad;

    public PersonaEntity() {

    }

    public PersonaEntity(Long id, String nombre, String apellido, String dni, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Long getId() {
        return this.id;
    }

    public String getDni() {
        return dni;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getEdad() {
        return this.edad;
    }
}