package br.com.robertocarneiro.desafio_tech.service;

import br.com.robertocarneiro.desafio_tech.dto.Sale;
import br.com.robertocarneiro.desafio_tech.dto.Salesman;
import br.com.robertocarneiro.desafio_tech.exception.SalesmanWithWorstResultNotFoundException;
import br.com.robertocarneiro.desafio_tech.service.impl.SalesmanServiceImpl;
import br.com.robertocarneiro.desafio_tech.transformer.impl.SalesmanTransformer;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SalesmanServiceImplTest {

    private static final String PEDRO = "Pedro";
    private static final String PAULO = "Paulo";
    private static final String TOTAL_EIGHT = "8.45";
    public static final String TOTAL_TWENTY_TWO = "22.01";

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
        assertEquals(2, salesmen.get(0).getSales().size());
        assertEquals("10", salesmen.get(0).getSales().get(0).getId());
        assertEquals("11", salesmen.get(0).getSales().get(1).getId());
        assertEquals(new BigDecimal("20.00"), salesmen.get(0).getTotal());
        assertEquals(PAULO, salesmen.get(1).getName());
        assertNotNull(salesmen.get(1).getSales());
        assertEquals(1, salesmen.get(1).getSales().size());
        assertEquals("08", salesmen.get(1).getSales().get(0).getId());
        assertEquals(new BigDecimal(TOTAL_EIGHT), salesmen.get(1).getTotal());
    }

    @Test
    public void whenAllSalesmenAreOkThenFindWorstSalesmanBySalesmenSuccess() {
        Salesman salesman = salesmanService.findWorstSalesmanBySalesmen(buildAllSalesmen());

        assertEquals(new BigDecimal(TOTAL_TWENTY_TWO), salesman.getTotal());
    }

    @Test
    public void whenSalesAreEmptyThenFindWorstSalesmanBySalesmenThrowsSalesmanWithWorstResultNotFoundException() {
        assertThrows(SalesmanWithWorstResultNotFoundException.class,
                () -> salesmanService.findWorstSalesmanBySalesmen(new ArrayList<>()));
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
        return Arrays.asList(buildSalesman("1234567891234", PEDRO, "50000", TOTAL_TWENTY_TWO),
                buildSalesman("3245678865434", PAULO, "40000.99", "22.02"));
    }

    private Salesman buildSalesman(String cpf, String name, String salary, String total) {
        return Salesman.builder()
                .cpf(cpf)
                .name(name)
                .salary(salary)
                .total(new BigDecimal(total))
                .build();
    }

    private List<Sale> buildAllSales() {
        return Arrays.asList(buildSale("10", PEDRO, "10.50"),
                buildSale("11", PEDRO, "9.50"),
                buildSale("08", PAULO, "8.45"));
    }

    private Sale buildSale(String id, String salesmanName, String total) {
        BigDecimal totalNumber = new BigDecimal(total);
        return Sale.builder()
                .id(id)
                .salesmanName(salesmanName)
                .total(totalNumber)
                .build();
    }

}
