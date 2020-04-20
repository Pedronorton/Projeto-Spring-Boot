package com.example.curso.exemple.service.validation;

import com.example.curso.exemple.Resources.exception.FieldMessage;
import com.example.curso.exemple.domain.Cliente;
import com.example.curso.exemple.dto.ClienteNewDTO;
import com.example.curso.exemple.enums.TipoCliente;
import com.example.curso.exemple.repositories.ClienteRepository;
import com.example.curso.exemple.service.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    ClienteRepository repo;

    @Override
    public void initialize(ClienteInsert ann) {
    }

    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA) && BR.isValidCPF(objDto.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfouCnpj","CPF inválido"));
        }
        if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfouCnpj","CNPJ inválido"));
        }

        Cliente aux = repo.findByEmail(objDto.getEmail());
        if(aux!=null){ //achou o email no banco
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