package br.com.robertocarneiro.desafio_tech.service;

import br.com.robertocarneiro.desafio_tech.dto.Sale;

import java.util.List;

public interface SaleService {

    List<Sale> transformByLines(List<String> lines);

    Sale findMoreExpensiveSaleBySales(List<Sale> sales);
}
