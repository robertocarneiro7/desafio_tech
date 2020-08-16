package br.com.robertocarneiro.desafio_tech.service;

import br.com.robertocarneiro.desafio_tech.dto.Item;
import br.com.robertocarneiro.desafio_tech.dto.Sale;

import java.util.List;

public interface ItemService {

    List<Item> transformByFullLine(String line);
}
