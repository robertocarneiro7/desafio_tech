package br.com.robertocarneiro.desafio_tech.transformer;

import br.com.robertocarneiro.desafio_tech.dto.Item;
import br.com.robertocarneiro.desafio_tech.dto.Sale;
import br.com.robertocarneiro.desafio_tech.exception.ObjectLineBadFormattedException;
import br.com.robertocarneiro.desafio_tech.service.ItemService;
import br.com.robertocarneiro.desafio_tech.transformer.impl.SaleTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SaleTransformerTest {

    @InjectMocks
    private SaleTransformer saleTransformer;

    @Mock
    private ItemService itemService;

    @Test
    public void whenAllLinesCorrectThenTransformByLinesWithSales() {
        List<String> lines = Arrays.asList("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro",
                "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo");
        List<Item> itemsPedro = Arrays.asList(buildItem("1", "10", "100"),
                buildItem("2", "30", "2.50"),
                buildItem("3", "40", "3.10"));
        List<Item> itemsPaulo = Arrays.asList(buildItem("1", "34", "10"),
                buildItem("2", "33", "1.50"),
                buildItem("3", "40", "0.10"));

        when(itemService.transformByFullLine("[1-10-100,2-30-2.50,3-40-3.10]"))
                .thenReturn(itemsPedro);
        when(itemService.transformByFullLine("[1-34-10,2-33-1.50,3-40-0.10]"))
                .thenReturn(itemsPaulo);

        List<Sale> sales = saleTransformer.transformByLines(lines);

        assertNotNull(sales);
        assertEquals(2, sales.size());
        assertEquals("10", sales.get(0).getId());
        assertEquals("Pedro", sales.get(0).getSalesmanName());
        assertEquals(3, sales.get(0).getItems().size());
        assertEquals("08", sales.get(1).getId());
        assertEquals("Paulo", sales.get(1).getSalesmanName());
        assertEquals(3, sales.get(1).getItems().size());
    }

    @Test
    public void whenContainsLineBadFormattedThenTransformByLinesThrowsObjectLineBadFormattedException() {
        List<String> lines = Arrays.asList("002ç2345675434544345çJose da SilvaçRuralçTest",
                "001ç1234567891234çPedroç50000çTest",
                "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPauloçTest");

        assertThrows(ObjectLineBadFormattedException.class,
                () -> saleTransformer.transformByLines(lines));
    }

    private Item buildItem(String id, String quantity, String price) {
        return Item.builder()
                .id(id)
                .quantity(new BigDecimal(quantity))
                .price(new BigDecimal(price))
                .build();
    }
}
