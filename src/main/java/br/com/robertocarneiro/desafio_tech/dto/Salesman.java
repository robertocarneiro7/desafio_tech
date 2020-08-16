package br.com.robertocarneiro.desafio_tech.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "cpf")
public class Salesman {

    public static final String IDENTIFY = "001";
    public static final int PROPERTIES_NUMBER = 4;
    private String cpf;
    private String name;
    private String salary;
    private List<Sale> sales;
    private BigDecimal total;
}
