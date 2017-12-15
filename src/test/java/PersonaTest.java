import model.Persona;
import org.junit.Test;
import org.junit.*;

import static junit.framework.TestCase.assertEquals;

public class PersonaTest {

    /**
     * Este test tiene como objetivo probar la correcta inserción en condiciones normales.
     */
    @Test
    public void testSimpleInsert() {
        DBManager db = DBManager.getInstance();

        Persona p1 = new Persona(1L, "38670882", "Tomás", "Juárez", 22);

        //El usuario p1 debe ser insertado normalmente.
        assertEquals(db.insert(p1),true);
    }

    /**
     * Este test tiene como objetivo probar la correcta inserción en condiciones normales (inserción de p1)
     *    y además la inserción en el caso de la violación de la restricción de clave primaria, es decir,
     *    la inserción de un usuario con id ya insertado (usuario p2).
     */
    @Test
    public void testRepeatedInsert() {
        DBManager db = DBManager.getInstance();

        Persona p1 = new Persona(1L, "38670882", "Tomás", "Juárez", 22);
        Persona p2 = new Persona(1L, "35698789", "Ragnar", "Lothbrok", 42);

        //El usuario p1 debe ser insertado normalmente.
        assertEquals(db.insert(p1),true);
        //La inserción debe ser falsa ya que se está violando la restricción de la clave primaria.
        //El usuario con id 1 fue insertado previamente.
        assertEquals(db.insert(p2),false);
    }

    @Test
    public void testInsertAndGetPersonList() {
        DBManager db = DBManager.getInstance();

        Persona p1 = new Persona(1L, "38670882", "Tomás", "Juárez", 22);
        Persona p2 = new Persona(2L, "35698789", "Ragnar", "Lothbrok", 42);
        Persona p3 = new Persona(3L, "18698758", "Bjorn", "Lothbrok", 42);
        Persona p4 = new Persona(4L, "20598796", "Ivar", "Lothbrok", 42);

        //El usuario p1, p2, p3 y p4 debe ser insertado normalmente.
        assertEquals(db.insert(p1),true);
        assertEquals(db.insert(p2),true);
        assertEquals(db.insert(p3),true);
        assertEquals(db.insert(p4),true);

        //Deben existir cuatro usuarios.
        assertEquals(db.getPersonas().size(), 4);
    }
}
