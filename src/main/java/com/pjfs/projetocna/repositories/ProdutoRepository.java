package com.pjfs.projetocna.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pjfs.projetocna.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{
    
}
