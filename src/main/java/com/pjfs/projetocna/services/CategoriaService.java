package com.pjfs.projetocna.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.pjfs.projetocna.domain.Categoria;
import com.pjfs.projetocna.repositories.CategoriaRepository;
import com.pjfs.projetocna.services.Exception.DataIntegrityException;
import com.pjfs.projetocna.services.Exception.ObjectNotFoundException;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Categoria find(Integer id) {
	Optional<Categoria> obj = repo.findById(id);
	return obj.orElseThrow(() -> new ObjectNotFoundException(
        "Objecto não encontrado! Id:" + id + ", Tipo: " + Categoria.class.getName()));
    }
    
    public Categoria insert(Categoria obj) {
    	obj.setId(null);
    	return repo.save(obj);
    }
    
    public Categoria update(Categoria obj) {
    	find(obj.getId());
    	return repo.save(obj);
    }
    
    public void delete(Integer id) {
    	find(id);
    	try {
    	repo.deleteById(id);
    	}
    	catch(DataIntegrityViolationException e) {
    		throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos");
    	}
    }
    
}
