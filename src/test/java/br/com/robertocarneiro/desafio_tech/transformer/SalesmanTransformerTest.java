package br.com.robertocarneiro.desafio_tech.transformer;

import br.com.robertocarneiro.desafio_tech.dto.Salesman;
import br.com.robertocarneiro.desafio_tech.exception.ObjectLineBadFormattedException;
import br.com.robertocarneiro.desafio_tech.transformer.impl.SalesmanTransformer;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SalesmanTransformerTest {

    private SalesmanTransformer salesmanTransformer = new SalesmanTransformer();

    @Test
    public void whenAllLinesCorrectThenTransformByLinesWithSalesmen() {
        List<String> lines = Arrays.asList("001ç1234567891234çPedroç50000",
                "001ç3245678865434çPauloç40000.99");

        List<Salesman> salesmen = salesmanTransformer.transformByLines(lines);

        assertNotNull(salesmen);
        assertEquals(2, salesmen.size());
        assertEquals("1234567891234", salesmen.get(0).getCpf());
        assertEquals("Pedro", salesmen.get(0).getName());
        assertEquals("50000", salesmen.get(0).getSalary());
        assertEquals("3245678865434", salesmen.get(1).getCpf());
        assertEquals("Paulo", salesmen.get(1).getName());
        assertEquals("40000.99", salesmen.get(1).getSalary());
    }

    @Test
    public void whenContainsLineBadFormattedThenTransformByLinesThrowsObjectLineBadFormattedException() {
        List<String> lines = Arrays.asList("002ç2345675434544345çJose da SilvaçRuralçTest",
                "001ç1234567891234çPedroç50000çTest",
                "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo");

        assertThrows(ObjectLineBadFormattedException.class,
                () -> salesmanTransformer.transformByLines(lines));
    }
}
