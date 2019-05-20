package resourceFunctions;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EntityManagerClass {
    public EntityManager em = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();
}
