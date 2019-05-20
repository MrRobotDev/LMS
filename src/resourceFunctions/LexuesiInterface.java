package resourceFunctions;

import entities.Lexuesi;

import java.util.List;

public interface LexuesiInterface {

    void create(Lexuesi l) throws BibliotekaException;

    void edit(Lexuesi l) throws BibliotekaException;

    void delete(Lexuesi l) throws BibliotekaException;

    void detach(Lexuesi l) throws BibliotekaException;

    List<Lexuesi> findAll() throws BibliotekaException;

    Lexuesi findById(Integer id) throws BibliotekaException;


}
