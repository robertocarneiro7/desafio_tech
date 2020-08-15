package br.com.robertocarneiro.desafio_tech.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
public class Item {

    public static final String IDENTIFY = "001";
    private Long id;
    private Long quantity;
    private BigDecimal price;
}
