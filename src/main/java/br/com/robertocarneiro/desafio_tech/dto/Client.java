package br.com.robertocarneiro.desafio_tech.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "cnpj")
public class Client {

    public static final String IDENTIFY = "002";
    private String cnpj;
    private String name;
    private String businessArea;
}
