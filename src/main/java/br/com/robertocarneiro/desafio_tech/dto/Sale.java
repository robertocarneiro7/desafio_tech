package br.com.robertocarneiro.desafio_tech.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
public class Sale {

    public static final String IDENTIFY = "003";
    public static final int PROPERTIES_NUMBER = 4;
    private String id;
    private String salesmanName;
    private List<Item> items;
}
