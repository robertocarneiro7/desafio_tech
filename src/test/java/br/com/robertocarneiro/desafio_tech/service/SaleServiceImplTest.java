package br.com.robertocarneiro.desafio_tech.service;

import br.com.robertocarneiro.desafio_tech.dto.Sale;
import br.com.robertocarneiro.desafio_tech.exception.SaleMoreExpensiveNotFoundException;
import br.com.robertocarneiro.desafio_tech.service.impl.SaleServiceImpl;
import br.com.robertocarneiro.desafio_tech.transformer.impl.SaleTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SaleServiceImplTest {

    private static final String STRING = "10.11";

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

    @Test
    public void whenAllSalesAreOkThenFindMoreExpensiveSaleBySalesSuccess() {
        Sale sale = saleService.findMoreExpensiveSaleBySales(buildAllSales());

        assertEquals(new BigDecimal(STRING), sale.getTotal());
    }

    @Test
    public void whenSalesAreEmptyThenFindMoreExpensiveSaleBySalesThrowsSaleMoreExpensiveNotFoundException() {
        assertThrows(SaleMoreExpensiveNotFoundException.class,
                () -> saleService.findMoreExpensiveSaleBySales(new ArrayList<>()));
    }

    private List<Sale> buildAllSales() {
        return Arrays.asList(buildSale(new BigDecimal("10.1")),
                buildSale(new BigDecimal("10.01")),
                buildSale(new BigDecimal(STRING)));
    }

    private Sale buildSale(BigDecimal total) {
        return Sale.builder()
                .total(total)
                .build();
    }

}
