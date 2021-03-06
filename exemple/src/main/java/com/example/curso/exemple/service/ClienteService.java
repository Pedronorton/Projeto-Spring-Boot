package com.example.curso.exemple.service;

//ONDE IRÁ TER AS OPERAÇÕES DE CONSULTA NO DB


import com.amazonaws.services.s3.AmazonS3;
import com.example.curso.exemple.domain.*;
import com.example.curso.exemple.domain.Cliente;
import com.example.curso.exemple.dto.ClienteDTO;
import com.example.curso.exemple.dto.ClienteNewDTO;
import com.example.curso.exemple.enums.Perfil;
import com.example.curso.exemple.enums.TipoCliente;
import com.example.curso.exemple.repositories.CidadeRepository;
import com.example.curso.exemple.repositories.ClienteRepository;
import com.example.curso.exemple.repositories.EnderecoRepository;
import com.example.curso.exemple.security.UserSS;
import com.example.curso.exemple.service.exception.AuthorizationException;
import com.example.curso.exemple.service.exception.DataIntegrityException;
import com.example.curso.exemple.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired //Automaticamente instanciada
    private ClienteRepository repo;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private ImageService imageService;

    @Value("${img.prefix.client.profile}")
    private String prefix;

    public Cliente buscar (Integer id){

        UserSS user = UserService.authenticated(); // retorna o usuário autenticado

        if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())){
            throw new AuthorizationException("Acesso negado");
        }

        Optional<Cliente> obj = repo.findById(id);

        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado| Id: "+ id+", Tipo: "+Cliente.class.getName()));
    }

    public Cliente update(Cliente obj){

        Cliente newObj = buscar(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    private void updateData(Cliente newObj, Cliente obj){ // obj são os valores que eu inseri para mudar e newObj serão atualizados no banco
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }

    @Transactional
    public Cliente insert (Cliente obj){
        obj.setId(null);
        obj = repo.save(obj);
        for (Endereco x : obj.getEnderecos()){
            enderecoRepository.save(x);
        }
        return obj;
    }

    public void delete(Integer id){
        buscar(id);
        try{
            repo.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível excluir pois há pedidos associados ao cliente");
        }
    }

    public List<Cliente> findAll(){
        return repo.findAll();
    }

    public Page<Cliente> findPage(Integer pager, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(pager, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO objDto){
        return new Cliente(objDto.getNome(), objDto.getEmail(), null , null,null);
    }

    public Cliente fromDTO(ClienteNewDTO objDto){
        Cliente cli = new Cliente(objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()) );
        Cidade cid = new Cidade(objDto.getCidade_id(), null,null);

        Endereco end = new Endereco(objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);

        cli.getEnderecos().add(end);

        cli.getTelefones().add(objDto.getTelefone1());
        if(objDto.getTelefone2()!=null){
            cli.getTelefones().add(objDto.getTelefone2());
        }
        if(objDto.getTelefone3()!=null){
            cli.getTelefones().add(objDto.getTelefone2());
        }

        return cli;
    }

    public URI uploadFilePicture(MultipartFile multipartFile) throws IOException, URISyntaxException {

        UserSS user = UserService.authenticated();
        if(user == null){
            throw new RuntimeException("Cliente inexistente");
        }

        BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
        String fileName = prefix+ user.getId() + "jpg";

        URI uri = s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");

        Optional<Cliente> cli = repo.findById(user.getId());

        cli.get().setImageUrl(uri.toString());

        repo.save(cli.get());
        return uri;
    }

}
