package com.pjfs.projetocna.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pjfs.projetocna.domain.Pedido;
import com.pjfs.projetocna.repositories.PedidoRepository;
import com.pjfs.projetocna.services.Exception.ObjectNotFoundException;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    public Pedido find(Integer id) {
	Optional<Pedido> obj = repo.findById(id);
	return obj.orElseThrow(() -> new ObjectNotFoundException(
        "Objecto não encontrado! Id:" + id + ", Tipo: " + Pedido.class.getName()));
    }
    
}
