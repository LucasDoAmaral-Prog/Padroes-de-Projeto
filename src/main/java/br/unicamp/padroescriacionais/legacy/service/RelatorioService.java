package br.unicamp.padroescriacionais.legacy.service;

import br.unicamp.padroescriacionais.legacy.domain.ConfiguracaoSistema;
import br.unicamp.padroescriacionais.legacy.domain.FormatoRelatorio;
import br.unicamp.padroescriacionais.legacy.domain.Relatorio;
import br.unicamp.padroescriacionais.legacy.domain.TipoRelatorio;
import br.unicamp.padroescriacionais.legacy.generator.RelatorioGenerator;
import br.unicamp.padroescriacionais.legacy.generator.RelatorioGeneratorFactory;

import java.time.LocalDateTime;

public class RelatorioService {

    private final ConfiguracaoSistema configuracao = ConfiguracaoSistema.getInstancia();
    private final RelatorioGeneratorFactory generatorFactory = new RelatorioGeneratorFactory();

    public Relatorio criarRelatorio(TipoRelatorio tipo) {
        String titulo;
        String conteudo;

        switch (tipo) {
            case VENDAS:
                titulo = "Relatorio de Vendas";
                conteudo = gerarConteudoVendas();
                break;
            case ESTOQUE:
                titulo = "Relatorio de Estoque";
                conteudo = gerarConteudoEstoque();
                break;
            case CLIENTES:
                titulo = "Relatorio de Clientes";
                conteudo = gerarConteudoClientes();
                break;
            default:
                throw new IllegalArgumentException("Tipo de relatorio desconhecido: " + tipo);
        }

        return new Relatorio(titulo, conteudo, tipo, LocalDateTime.now());
    }

    public String gerarRelatorio(TipoRelatorio tipo, FormatoRelatorio formato) {
        Relatorio relatorio = criarRelatorio(tipo);

        if (configuracao.isDebugAtivo()) {
            System.out.println("[DEBUG-RelatorioService] Gerando: " + tipo + " -> " + formato);
        }

        RelatorioGenerator generator = generatorFactory.criarGerador(formato);
        return generator.gerar(relatorio);
    }

    private String gerarConteudoVendas() {
        return "Produto A: 150 unidades vendidas - R$ 12.000,00\n"
             + "Produto B: 230 unidades vendidas - R$ 23.000,00\n"
             + "Produto C:  80 unidades vendidas - R$ 10.000,00\n"
             + "Total geral: R$ 45.000,00";
    }

    private String gerarConteudoEstoque() {
        return "Item X: 500 unidades disponiveis\n"
             + "Item Y: 120 unidades disponiveis\n"
             + "Item Z:  80 unidades disponiveis (estoque critico)";
    }

    private String gerarConteudoClientes() {
        return "Cliente 001: Joao Silva       - ativo\n"
             + "Cliente 002: Maria Santos     - ativo\n"
             + "Cliente 003: Pedro Oliveira   - inativo\n"
             + "Total: 3 clientes cadastrados";
    }
}
