package br.com.robertocarneiro.desafio_tech.service;

import br.com.robertocarneiro.desafio_tech.dto.Sale;
import br.com.robertocarneiro.desafio_tech.service.impl.SaleServiceImpl;
import br.com.robertocarneiro.desafio_tech.transformer.impl.SaleTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SaleServiceImplTest {

    @InjectMocks
    private SaleServiceImpl saleService;

    @Mock
    private SaleTransformer saleTransformer;

    @Test
    public void transformByLinesSuccess() {
        when(saleTransformer.transformByLines(any())).thenReturn(new ArrayList<>());

        List<Sale> sales = saleService.transformByLines(new ArrayList<>());

        assertNotNull(sales);
        assertEquals(0, sales.size());
    }
}
