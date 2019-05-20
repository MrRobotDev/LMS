package resourceFunctions;


import entities.Lexuesi;

import java.util.List;

public interface ObjectInterface {

    void create(Object l) throws BibliotekaException;

    void edit(Object l) throws BibliotekaException;

    void delete(Object l) throws BibliotekaException;

    void detach(Object l) throws BibliotekaException;

    List <Lexuesi> findAll() throws BibliotekaException;

    Object findById(Integer id) throws BibliotekaException;


}
