package com.pjfs.projetocna.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pjfs.projetocna.domain.Cliente;
import com.pjfs.projetocna.domain.Cidade;
import com.pjfs.projetocna.domain.Endereco;
import com.pjfs.projetocna.domain.enums.TipoCliente;
import com.pjfs.projetocna.dto.ClienteDTO;
import com.pjfs.projetocna.dto.ClienteNewDTO;
import com.pjfs.projetocna.repositories.ClienteRepository;
import com.pjfs.projetocna.repositories.EnderecoRepository;
import com.pjfs.projetocna.services.Exception.DataIntegrityException;
import com.pjfs.projetocna.services.Exception.ObjectNotFoundException;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;
    
    @Autowired
    private EnderecoRepository enderecoRepository;

    public Cliente find(Integer id) {
	Optional<Cliente> obj = repo.findById(id);
	return obj.orElseThrow(() -> new ObjectNotFoundException(
        "Objecto não encontrado! Id:" + id + ", Tipo: " + Cliente.class.getName()));
    }
    
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
    
    public Cliente update(Cliente obj) {
    	Cliente newObj = find(obj.getId());
    	updateData(newObj, obj);
    	return repo.save(newObj);
    }
    
    public void delete(Integer id) {
    	find(id);
    	try {
    	repo.deleteById(id);
    	}
    	catch(DataIntegrityViolationException e) {
    		throw new DataIntegrityException("Não é possivel excluir porque há entidades relacionadas");
    	}
    }
    public List<Cliente> findAll(){
    	return repo.findAll();
    }
    
    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
    	PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
    	return repo.findAll(pageRequest);
    }
    
    public Cliente fromDTO(ClienteDTO objDto) {
    	return new Cliente(objDto.getId(), objDto.getNome(),objDto.getEmail(), null, null);
    }
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
    
    private void updateData(Cliente newObj, Cliente obj) {
    	newObj.setNome(obj.getNome());
    	newObj.setEmail(obj.getEmail());
    }
    
}
