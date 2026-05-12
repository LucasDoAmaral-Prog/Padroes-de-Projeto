package br.unicamp.padroescriacionais.legacy.generator;

import br.unicamp.padroescriacionais.legacy.domain.FormatoRelatorio;

/**
 * Fabrica responsavel pela criacao dos geradores de relatorio.
 * Aplica o padrao Factory Method para centralizar a logica de criacao
 * e desacoplar os servicos das implementacoes concretas dos geradores.
 */
public class RelatorioGeneratorFactory {

    /**
     * Cria e retorna o gerador de relatorio adequado para o formato informado.
     *
     * @param formato o formato de saida desejado
     * @return uma instancia de RelatorioGenerator correspondente ao formato
     * @throws IllegalArgumentException se o formato nao for suportado
     */
    public RelatorioGenerator criarGerador(FormatoRelatorio formato) {
        return switch (formato) {
            case PDF -> new PdfRelatorioGenerator();
            case CSV -> new CsvRelatorioGenerator();
            case JSON -> new JsonRelatorioGenerator();
            case XML -> new XmlRelatorioGenerator();
            case HTML -> new HtmlRelatorioGenerator();
        };
    }
}
