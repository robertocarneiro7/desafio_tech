package br.com.robertocarneiro.desafio_tech.service;

import br.com.robertocarneiro.desafio_tech.dto.Item;
import br.com.robertocarneiro.desafio_tech.service.impl.ItemServiceImpl;
import br.com.robertocarneiro.desafio_tech.transformer.impl.ItemTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemTransformer itemTransformer;

    @Test
    public void transformByLinesSuccess() {
        when(itemTransformer.transformByFullLine("")).thenReturn(new ArrayList<>());

        List<Item> items = itemService.transformByFullLine("");

        assertNotNull(items);
        assertEquals(0, items.size());
    }
}
