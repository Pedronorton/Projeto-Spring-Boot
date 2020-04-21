package com.example.curso.exemple.service;


import com.example.curso.exemple.domain.PagamentoBoleto;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BoletoService {


    public void preencherPagamentoComBoleto(PagamentoBoleto pagamento, Date instantePedido){
        Calendar cal = Calendar.getInstance();
        cal.setTime(instantePedido);
        cal.add(Calendar.DAY_OF_MONTH,7);

        pagamento.setDataVencimento(cal.getTime());

    }
}
