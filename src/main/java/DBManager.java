import model.Persona;
import persistenceModel.entity.PersonaEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private static DBManager instance = null;

    private static final String UNIT_NAME = "aplicacion";

    private EntityManagerFactory factory;
    private EntityManager manager;

    private DBManager() {
        this.factory = Persistence.createEntityManagerFactory(UNIT_NAME);
        this.manager = factory.createEntityManager();
    }

    public static DBManager getInstance() {
        if (instance == null)
            instance = new DBManager();

        return instance;
    }

    public boolean insert(Persona persona) {
        if (this.exists(persona.getId()))
            return false;

        //Mapeo la instancia de la persona creada en la aplicación web a la entidad propia de la BBDD.
        PersonaEntity personaEntity = new PersonaEntity(
                persona.getId(),
                persona.getNombre(),
                persona.getApellido(),
                persona.getDni(),
                persona.getEdad()
        );

        EntityTransaction trx = this.manager.getTransaction();
        trx.begin();
        this.manager.persist(personaEntity);
        trx.commit();

        return true;
    }

    public boolean exists(long id) {
        EntityTransaction trx = this.manager.getTransaction();

        Query query = this.manager.createQuery("SELECT COUNT(e) FROM PersonaEntity e WHERE e.id = :id");
        query.setParameter("id", id); //Completo el placeholder.
        long count = (Long)query.getSingleResult();
        return count > 0L;
    }

    public List<Persona> getPersonas() {
        List<PersonaEntity> personas = (List<PersonaEntity>) this.manager.createQuery("FROM PersonaEntity p").getResultList();
        List<Persona> ret = new ArrayList<Persona>();

        for (PersonaEntity p: personas) {
            ret.add(new Persona(
                    p.getId(),
                    p.getDni(),
                    p.getNombre(),
                    p.getApellido(),
                    p.getEdad()
            ));
        }

        return ret;
    }
}