package br.com.robertocarneiro.desafio_tech.service;

import br.com.robertocarneiro.desafio_tech.dto.Salesman;

import java.util.List;

public interface SalesmanService {

    List<Salesman> transformByLines(List<String> lines);
}
