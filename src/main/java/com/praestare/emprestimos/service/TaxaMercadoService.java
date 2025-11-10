package com.praestare.emprestimos.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaxaMercadoService {

    private final RestTemplate restTemplate;

    private static final String BASE_URL = "https://olinda.bcb.gov.br/olinda/servico/taxaJuros/versao/v2/odata/";

    public Double buscarTaxaMensalPorBanco(String banco, String mes) {
        String url = BASE_URL +
            "TaxasJurosMensalPorMes?$filter=InstituicaoFinanceira eq '" + banco +
            "' and ModalidadeCredito eq 'Empréstimo pessoal não consignado' and Mes eq '" + mes + "'&$format=json";

        try {
            ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
            JsonNode taxaNode = response.getBody().get("value");

            if (taxaNode != null && taxaNode.size() > 0) {
                return taxaNode.get(0).get("TaxaJuros").asDouble();
            }
        } catch (Exception e) {
            System.out.println("Erro ao consultar taxa BACEN: " + e.getMessage());
        }
        return null;
    }

    public boolean avaliarTaxa(String banco, double valor, double taxaInformada, int prazoMeses, String mesReferencia) {
        Double taxaMercado = buscarTaxaMensalPorBanco(banco, mesReferencia);
        if (taxaMercado == null) taxaMercado = 8.05;

        double taxaMercadoMensal = taxaMercado / 100;
        double taxaInformadaMensal = taxaInformada / 100;

        double montanteInformado = valor * Math.pow(1 + taxaInformadaMensal, prazoMeses);
        double montanteMercado = valor * Math.pow(1 + taxaMercadoMensal, prazoMeses);

        return montanteInformado <= montanteMercado * 1.2;
    }
}
