package br.com.robertocarneiro.desafio_tech.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "cnpj")
public class Client {

    public static final String IDENTIFY = "002";
    public static final int PROPERTIES_NUMBER = 4;
    private String cnpj;
    private String name;
    private String businessArea;
}
