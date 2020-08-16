package br.com.robertocarneiro.desafio_tech.transformer;

import java.util.List;

public interface ObjectTransformer<T> {

    List<T> transformByLines(List<String> lines);
}
