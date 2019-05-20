package resourceFunctions;


import entities.Libri;

import java.util.List;

public class LibriRepository extends EntityManagerClass implements LibriInterface {


    @Override
    public void create(Libri l) {
        try {
            em.getTransaction().begin();
            em.persist(l);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void edit(Libri l) throws BibliotekaException {
        try {
            em.getTransaction().begin();
            em.merge(l);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new BibliotekaException(e.getMessage());
        }
    }

    @Override
    public void delete(Libri l) throws BibliotekaException {
        try {
            em.getTransaction().begin();
            em.remove(l);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new BibliotekaException(e.getMessage());
        }
    }

    @Override
    public void detach(Libri l) throws BibliotekaException {
        try {
            em.getTransaction().begin();
            em.detach(l);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new BibliotekaException(e.getMessage());
        }
    }

    @Override
    public List<Libri> findAll() throws BibliotekaException {
        try {
            return (List<Libri>) em.createNamedQuery("Libri.findAll").getResultList();
        } catch (Exception e) {
            throw new BibliotekaException(e.getMessage());
        }
    }

    @Override
    public Libri findById(Integer id) throws BibliotekaException {
        throw new UnsupportedOperationException("Not supported yet");
    }
}
