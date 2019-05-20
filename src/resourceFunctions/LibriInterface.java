package resourceFunctions;


import entities.Libri;

import java.util.List;

public interface LibriInterface {

    void create(Libri l) throws BibliotekaException;

    void edit(Libri l) throws BibliotekaException;

    void delete(Libri l) throws BibliotekaException;

    void detach(Libri l) throws BibliotekaException;

    List<Libri> findAll() throws BibliotekaException;

    Libri findById(Integer id) throws BibliotekaException;
}
