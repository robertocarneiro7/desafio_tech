package br.com.robertocarneiro.desafio_tech.service.impl;

import br.com.robertocarneiro.desafio_tech.dto.Item;
import br.com.robertocarneiro.desafio_tech.service.ItemService;
import br.com.robertocarneiro.desafio_tech.transformer.impl.ItemTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemTransformer itemTransformer;

    @Override
    public List<Item> transformByFullLine(String line) {
        return itemTransformer.transformByFullLine(line);
    }
}
