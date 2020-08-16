package br.com.robertocarneiro.desafio_tech.transformer;

import br.com.robertocarneiro.desafio_tech.dto.Item;
import br.com.robertocarneiro.desafio_tech.exception.ItemLineBadFormattedException;
import br.com.robertocarneiro.desafio_tech.transformer.impl.ItemTransformer;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTransformerTest {

    private ItemTransformer itemTransformer = new ItemTransformer();

    @Test
    public void whenAllLinesCorrectThenTransformByLinesWithSalesmen() {
        List<String> lines = Collections.singletonList("[1-34-10,2-33-1.50,3-40-0.10]");

        List<Item> items = itemTransformer.transformByLines(lines);

        assertNotNull(items);
        assertEquals(3, items.size());
        assertEquals("1", items.get(0).getId());
        assertEquals(new BigDecimal("34"), items.get(0).getQuantity());
        assertEquals(new BigDecimal("10"), items.get(0).getPrice());
        assertEquals("2", items.get(1).getId());
        assertEquals(new BigDecimal("33"), items.get(1).getQuantity());
        assertEquals(new BigDecimal("1.50"), items.get(1).getPrice());
        assertEquals("3", items.get(2).getId());
        assertEquals(new BigDecimal("40"), items.get(2).getQuantity());
        assertEquals(new BigDecimal("0.10"), items.get(2).getPrice());
    }

    @Test
    public void whenLineContainsMorePropertiesThenTransformByLinesThrowsItemLineBadFormattedException() {
        List<String> lines = Collections.singletonList("[1-34-10,2-33-1.20,3-40-0.10-test]");

        assertThrows(ItemLineBadFormattedException.class, () -> itemTransformer.transformByLines(lines));
    }

    @Test
    public void whenLineContainsQuantityNotNumberThenTransformByLinesThrowsNumberFormatException() {
        List<String> lines = Collections.singletonList("[1-34-10,2-test-1.20,3-40-0.10]");

        assertThrows(NumberFormatException.class, () -> itemTransformer.transformByLines(lines));
    }

    @Test
    public void whenLineContainsPriceNotNumberThenTransformByLinesThrowsNumberFormatException() {
        List<String> lines = Collections.singletonList("[1-34-10,2-33-test,3-40-0.10]");

        assertThrows(NumberFormatException.class, () -> itemTransformer.transformByLines(lines));
    }
}
