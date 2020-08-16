package br.com.robertocarneiro.desafio_tech.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
public class Item {

    public static final String START_IDENTIFY = "[";
    public static final String END_IDENTIFY = "]";
    public static final int PROPERTIES_NUMBER = 3;
    private String id;
    private BigDecimal quantity;
    private BigDecimal price;
}
