package br.com.robertocarneiro.desafio_tech.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
public class Sale {

    public static final String IDENTIFY = "003";
    private Long id;
    private String salesmanName;
    private BigDecimal salary;
    private List<Item> items;
}
