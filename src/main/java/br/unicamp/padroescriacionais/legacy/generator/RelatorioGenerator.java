package br.unicamp.padroescriacionais.legacy.generator;

import br.unicamp.padroescriacionais.legacy.domain.Relatorio;

/**
 * Interface que define o contrato para geradores de relatorio.
 * Parte da refatoracao com Factory Method — cada formato de saida
 * implementa esta interface, desacoplando os servicos das implementacoes concretas.
 */
public interface RelatorioGenerator {

    /**
     * Gera a representacao formatada do relatorio.
     *
     * @param relatorio o relatorio a ser formatado
     * @return conteudo formatado como String
     */
    String gerar(Relatorio relatorio);
}
