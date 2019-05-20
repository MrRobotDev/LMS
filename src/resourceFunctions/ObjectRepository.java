package resourceFunctions;

import entities.Lexuesi;

import java.util.List;

public class ObjectRepository extends EntityManagerClass implements ObjectInterface {


    @Override
    public void create(Object l) throws BibliotekaException {
        try {
            em.getTransaction().begin();
            em.persist(l);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void edit(Object l) throws BibliotekaException {
        try {
            em.getTransaction().begin();
            em.merge(l);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new BibliotekaException(e.getMessage());
        }
    }

    @Override
    public void delete(Object l) throws BibliotekaException {
        try {
            em.getTransaction().begin();
            em.remove(l);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new BibliotekaException(e.getMessage());
        }
    }

    @Override
    public void detach(Object l) throws BibliotekaException {
        try {
            em.getTransaction().begin();
            em.detach(l);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new BibliotekaException(e.getMessage());
        }
    }

    @Override
    public List<Lexuesi> findAll() throws BibliotekaException {
        try {
            return (List<Lexuesi>) em.createNamedQuery("Lexuesi.findAll").getResultList();
        } catch (Exception e) {
            throw new BibliotekaException(e.getMessage());
        }
    }

    @Override
    public Lexuesi findById(Integer id) throws BibliotekaException {
        return null;
    }
}
