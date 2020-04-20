package com.example.curso.exemple.service.validation;

import com.example.curso.exemple.Resources.exception.FieldMessage;
import com.example.curso.exemple.domain.Cliente;
import com.example.curso.exemple.dto.ClienteDTO;
import com.example.curso.exemple.dto.ClienteNewDTO;
import com.example.curso.exemple.enums.TipoCliente;
import com.example.curso.exemple.repositories.ClienteRepository;
import com.example.curso.exemple.service.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

    @Autowired
    private HttpServletRequest request;


    @Autowired
    private ClienteRepository repo;

    @Override
    public void initialize(ClienteUpdate ann) {
    }

    @Override
    public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        Integer uriID = Integer.parseInt(map.get("id"));


        Cliente aux = repo.findByEmail(objDto.getEmail());
        if(aux!=null && !aux.getId().equals(uriID)){ //achou o email no banco e verifico se o email que está sendo inserido na atualização percente ao mesmo usuário
            list.add(new FieldMessage("email", "email ja existente"));
        }

// inclua os testes aqui, inserindo erros na lista
        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getField()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}