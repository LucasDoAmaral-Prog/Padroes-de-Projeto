package br.unicamp.padroescriacionais.legacy;

import br.unicamp.padroescriacionais.legacy.domain.FormatoRelatorio;
import br.unicamp.padroescriacionais.legacy.domain.Relatorio;
import br.unicamp.padroescriacionais.legacy.domain.TipoRelatorio;
import br.unicamp.padroescriacionais.legacy.service.ExportacaoService;
import br.unicamp.padroescriacionais.legacy.service.RelatorioService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes de exportacao para os novos formatos XML e HTML.
 */
class ExportacaoNovosFormatosTest {

    private ExportacaoService exportacaoService;
    private RelatorioService relatorioService;

    private final PrintStream saidaOriginal = System.out;
    private ByteArrayOutputStream saidaCapturada;

    @BeforeEach
    void setUp() {
        exportacaoService = new ExportacaoService();
        relatorioService = new RelatorioService();
        saidaCapturada = new ByteArrayOutputStream();
        System.setOut(new PrintStream(saidaCapturada));
    }

    @AfterEach
    void tearDown() {
        System.setOut(saidaOriginal);
    }

    @Test
    void deveExportarRelatorioEmXmlSemErro() {
        Relatorio relatorio = relatorioService.criarRelatorio(TipoRelatorio.VENDAS);
        assertDoesNotThrow(() -> exportacaoService.exportar(relatorio, FormatoRelatorio.XML));
    }

    @Test
    void deveExportarRelatorioEmHtmlSemErro() {
        Relatorio relatorio = relatorioService.criarRelatorio(TipoRelatorio.VENDAS);
        assertDoesNotThrow(() -> exportacaoService.exportar(relatorio, FormatoRelatorio.HTML));
    }

    @Test
    void exportacaoXmlDeveExibirCaminhoComExtensao() {
        Relatorio relatorio = relatorioService.criarRelatorio(TipoRelatorio.VENDAS);
        exportacaoService.exportar(relatorio, FormatoRelatorio.XML);

        String saida = saidaCapturada.toString();
        assertTrue(saida.contains(".xml"), "Saida deve conter extensao .xml");
        assertTrue(saida.contains("EXPORTACAO"), "Saida deve conter cabecalho de exportacao");
    }

    @Test
    void exportacaoHtmlDeveExibirCaminhoComExtensao() {
        Relatorio relatorio = relatorioService.criarRelatorio(TipoRelatorio.ESTOQUE);
        exportacaoService.exportar(relatorio, FormatoRelatorio.HTML);

        String saida = saidaCapturada.toString();
        assertTrue(saida.contains(".html"), "Saida deve conter extensao .html");
        assertTrue(saida.contains("EXPORTACAO"), "Saida deve conter cabecalho de exportacao");
    }

    @Test
    void exportacaoXmlDeveConterConteudoXml() {
        Relatorio relatorio = relatorioService.criarRelatorio(TipoRelatorio.CLIENTES);
        exportacaoService.exportar(relatorio, FormatoRelatorio.XML);

        String saida = saidaCapturada.toString();
        assertTrue(saida.contains("<?xml"), "Saida deve conter declaracao XML");
        assertTrue(saida.contains("<relatorio>"), "Saida deve conter tag <relatorio>");
    }

    @Test
    void exportacaoHtmlDeveConterConteudoHtml() {
        Relatorio relatorio = relatorioService.criarRelatorio(TipoRelatorio.CLIENTES);
        exportacaoService.exportar(relatorio, FormatoRelatorio.HTML);

        String saida = saidaCapturada.toString();
        assertTrue(saida.contains("<!DOCTYPE html>"), "Saida deve conter DOCTYPE");
        assertTrue(saida.contains("<html>"), "Saida deve conter tag <html>");
    }

    @Test
    void todosFormatosIncluindoNovosDevemSerExportadosSemErro() {
        for (FormatoRelatorio formato : FormatoRelatorio.values()) {
            Relatorio relatorio = relatorioService.criarRelatorio(TipoRelatorio.VENDAS);
            assertDoesNotThrow(
                    () -> exportacaoService.exportar(relatorio, formato),
                    "Exportacao falhou para formato: " + formato
            );
        }
    }
}
