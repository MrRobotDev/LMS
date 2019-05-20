package resourceFunctions;

import entities.Lexuesi;

import java.util.List;

public class LexuesiRepository extends EntityManagerClass implements LexuesiInterface {


    @Override
    public void create(Lexuesi l) throws BibliotekaException {
        try {
            em.getTransaction().begin();
            em.persist(l);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void edit(Lexuesi l) throws BibliotekaException {
        try {
            em.getTransaction().begin();
            em.merge(l);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new BibliotekaException(e.getMessage());
        }
    }

    @Override
    public void delete(Lexuesi l) throws BibliotekaException {
        try {
            em.getTransaction().begin();
            em.remove(l);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new BibliotekaException(e.getMessage());
        }
    }

    @Override
    public void detach(Lexuesi l) throws BibliotekaException {
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
