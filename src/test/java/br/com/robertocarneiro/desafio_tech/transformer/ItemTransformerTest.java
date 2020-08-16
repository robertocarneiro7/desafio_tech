package br.com.robertocarneiro.desafio_tech.transformer;

import br.com.robertocarneiro.desafio_tech.dto.Item;
import br.com.robertocarneiro.desafio_tech.exception.ObjectLineBadFormattedException;
import br.com.robertocarneiro.desafio_tech.transformer.impl.ItemTransformer;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTransformerTest {

    private ItemTransformer itemTransformer = new ItemTransformer();

    @Test
    public void whenAllLinesCorrectThenTransformByFullLineWithItems() {
        List<Item> items = itemTransformer.transformByFullLine("[1-34-10,2-33-1.50,3-40-0.10]");

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
    public void whenLineIsBadFormattedThenTransformByFullLineThrowsObjectLineBadFormattedException() {
        assertThrows(ObjectLineBadFormattedException.class,
                () -> itemTransformer.transformByFullLine("s1-34-10,2-33-1.20,3-40-0.10-tests"));
    }

    @Test
    public void whenLineContainsMorePropertiesThenTransformByFullLineThrowsObjectLineBadFormattedException() {
        assertThrows(ObjectLineBadFormattedException.class,
                () -> itemTransformer.transformByFullLine("[1-34-10,2-33-1.20,3-40-0.10-test]"));
    }

    @Test
    public void whenLineContainsQuantityNotNumberThenTransformByLinesThrowsNumberFormatException() {
        assertThrows(NumberFormatException.class,
                () -> itemTransformer.transformByFullLine("[1-34-10,2-test-1.20,3-40-0.10]"));
    }

    @Test
    public void whenLineContainsPriceNotNumberThenTransformByLinesThrowsNumberFormatException() {
        assertThrows(NumberFormatException.class,
                () -> itemTransformer.transformByFullLine("[1-34-10,2-33-test,3-40-0.10]"));
    }
}
