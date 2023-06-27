package com.pjfs.projetocna.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pjfs.projetocna.domain.Cliente;
import com.pjfs.projetocna.repositories.ClienteRepository;
import com.pjfs.projetocna.services.Exception.ObjectNotFoundException;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    public Cliente find(Integer id) {
	Optional<Cliente> obj = repo.findById(id);
	return obj.orElseThrow(() -> new ObjectNotFoundException(
        "Objecto não encontrado! Id:" + id + ", Tipo: " + Cliente.class.getName()));
    }
    
}
