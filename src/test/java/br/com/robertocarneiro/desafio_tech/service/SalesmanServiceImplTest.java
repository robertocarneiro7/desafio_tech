package br.com.robertocarneiro.desafio_tech.service;

import br.com.robertocarneiro.desafio_tech.dto.Sale;
import br.com.robertocarneiro.desafio_tech.dto.Salesman;
import br.com.robertocarneiro.desafio_tech.service.impl.SalesmanServiceImpl;
import br.com.robertocarneiro.desafio_tech.transformer.impl.SalesmanTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SalesmanServiceImplTest {

    private static final String PEDRO = "Pedro";
    private static final String PAULO = "Paulo";

    @InjectMocks
    private SalesmanServiceImpl salesmanService;

    @Mock
    private SalesmanTransformer salesmanTransformer;

    @Mock
    private SaleService saleService;

    @Test
    public void transformByLinesSuccess() {
        List<String> allLines = buildAllLines();
        List<Salesman> allSalesmen = buildAllSalesmen();
        List<Sale> allSales = buildAllSales();

        when(salesmanTransformer.transformByLines(allLines)).thenReturn(allSalesmen);
        when(saleService.transformByLines(allLines)).thenReturn(allSales);

        List<Salesman> salesmen = salesmanService.transformByLines(allLines);

        assertNotNull(salesmen);
        assertEquals(2, salesmen.size());
        assertEquals(PEDRO, salesmen.get(0).getName());
        assertNotNull(salesmen.get(0).getSales());
        assertEquals(1, salesmen.get(0).getSales().size());
        assertEquals("10", salesmen.get(0).getSales().get(0).getId());
        assertEquals(PAULO, salesmen.get(1).getName());
        assertNotNull(salesmen.get(1).getSales());
        assertEquals(1, salesmen.get(1).getSales().size());
        assertEquals("08", salesmen.get(1).getSales().get(0).getId());
    }

    private List<String> buildAllLines() {
        return Arrays.asList("001ç1234567891234çPedroç50000",
                "001ç3245678865434çPauloç40000.99",
                "002ç2345675434544345çJose da SilvaçRural",
                "002ç2345675433444345çEduardo PereiraçRural",
                "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro",
                "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo");
    }

    private List<Salesman> buildAllSalesmen() {
        return Arrays.asList(buildSalesman("1234567891234", PEDRO, "50000"),
                buildSalesman("3245678865434", PAULO, "40000.99"));
    }

    private Salesman buildSalesman(String cpf, String name, String salary) {
        return Salesman.builder()
                .cpf(cpf)
                .name(name)
                .salary(salary)
                .build();
    }

    private List<Sale> buildAllSales() {
        return Arrays.asList(buildSale("10", PEDRO),
                buildSale("08", PAULO));
    }

    private Sale buildSale(String id, String salesmanName) {
        return Sale.builder()
                .id(id)
                .salesmanName(salesmanName)
                .build();
    }

}
