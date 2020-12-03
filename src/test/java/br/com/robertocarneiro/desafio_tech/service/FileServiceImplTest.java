package br.com.robertocarneiro.desafio_tech.service;

import br.com.robertocarneiro.desafio_tech.dto.Sale;
import br.com.robertocarneiro.desafio_tech.dto.Salesman;
import br.com.robertocarneiro.desafio_tech.service.impl.FileServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileServiceImplTest {

    private static final String PEDRO = "Pedro";
    private static final String PAULO = "Paulo";
    private static final String PATH_OUT = System.getProperty("user.home") + File.separator + "data" + File.separator + "out";
    private static final String TEST_FILE = "testando.dat";
    private static final String TEST_OUTPUT_FILE = "testando.done.dat";

    @InjectMocks
    private FileServiceImpl fileService;

    @Mock
    private ClientService clientService;

    @Mock
    private SalesmanService salesmanService;

    @Mock
    private SaleService saleService;

    @Test
    public void whenFileIsCorrectThenProcessFileSuccess() {
        String path = this.getClass().getClassLoader().getResource(TEST_FILE).getPath();
        File file = new File(path);
        List<Salesman> salesmen = buildAllSalesmen();

        ReflectionTestUtils.setField(fileService, "allowedFileType", "dat");
        ReflectionTestUtils.setField(fileService, "suffixDone", "done");

        when(salesmanService.transformByLines(any()))
                .thenReturn(buildAllSalesmen());
        when(clientService.countClientByLines(any()))
                .thenReturn(2);
        when(saleService.findMoreExpensiveSaleBySales(any()))
                .thenReturn(buildSale("08"));
        when(salesmanService.findWorstSalesmanBySalesmen(salesmen))
                .thenReturn(salesmen.get(1));

        fileService.processFile(file);

        verify(salesmanService, times(1)).transformByLines(any());
        verify(clientService, times(1)).countClientByLines(any());
        verify(saleService, times(1)).findMoreExpensiveSaleBySales(any());
        verify(salesmanService, times(1)).findWorstSalesmanBySalesmen(any());

        File fileSaved = new File(PATH_OUT + File.separator + TEST_OUTPUT_FILE);
        assertTrue(fileSaved.delete());
    }

    @Test
    public void whenFileNullThenProcessFileNotExecuteService() {
        fileService.processFile(null);

        verify(salesmanService, times(0)).transformByLines(any());
        verify(clientService, times(0)).countClientByLines(any());
        verify(saleService, times(0)).findMoreExpensiveSaleBySales(any());
        verify(salesmanService, times(0)).findWorstSalesmanBySalesmen(any());
    }

    private List<Salesman> buildAllSalesmen() {
        return Arrays.asList(buildSalesman("1234567891234", PEDRO, "50000", "22.01"),
                buildSalesman("3245678865434", PAULO, "40000.99", "22.02"));
    }

    private Salesman buildSalesman(String cpf, String name, String salary, String total) {
        return Salesman.builder()
                .cpf(cpf)
                .name(name)
                .salary(salary)
                .total(new BigDecimal(total))
                .sales(Collections.singletonList(buildSale(String.valueOf(System.currentTimeMillis()))))
                .build();
    }

    private Sale buildSale(String saleId) {
        return Sale.builder()
                .id(saleId)
                .build();
    }

}
