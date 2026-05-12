package br.unicamp.padroescriacionais.legacy;

import br.unicamp.padroescriacionais.legacy.domain.FormatoRelatorio;
import br.unicamp.padroescriacionais.legacy.domain.Relatorio;
import br.unicamp.padroescriacionais.legacy.domain.TipoRelatorio;
import br.unicamp.padroescriacionais.legacy.generator.*;
import br.unicamp.padroescriacionais.legacy.service.RelatorioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para os novos formatos XML e HTML e para a fabrica de geradores.
 */
class RelatorioGeneratorFactoryTest {

    private RelatorioGeneratorFactory factory;
    private Relatorio relatorioTeste;

    @BeforeEach
    void setUp() {
        factory = new RelatorioGeneratorFactory();
        relatorioTeste = new Relatorio(
                "Relatorio de Vendas",
                "Produto A: 150 unidades vendidas",
                TipoRelatorio.VENDAS,
                LocalDateTime.of(2026, 5, 12, 10, 0)
        );
    }

    // ===== Testes da Fabrica (Factory) =====

    @Test
    void deveRetornarPdfGeneratorParaFormatoPdf() {
        RelatorioGenerator generator = factory.criarGerador(FormatoRelatorio.PDF);
        assertNotNull(generator);
        assertInstanceOf(PdfRelatorioGenerator.class, generator);
    }

    @Test
    void deveRetornarCsvGeneratorParaFormatoCsv() {
        RelatorioGenerator generator = factory.criarGerador(FormatoRelatorio.CSV);
        assertNotNull(generator);
        assertInstanceOf(CsvRelatorioGenerator.class, generator);
    }

    @Test
    void deveRetornarJsonGeneratorParaFormatoJson() {
        RelatorioGenerator generator = factory.criarGerador(FormatoRelatorio.JSON);
        assertNotNull(generator);
        assertInstanceOf(JsonRelatorioGenerator.class, generator);
    }

    @Test
    void deveRetornarXmlGeneratorParaFormatoXml() {
        RelatorioGenerator generator = factory.criarGerador(FormatoRelatorio.XML);
        assertNotNull(generator);
        assertInstanceOf(XmlRelatorioGenerator.class, generator);
    }

    @Test
    void deveRetornarHtmlGeneratorParaFormatoHtml() {
        RelatorioGenerator generator = factory.criarGerador(FormatoRelatorio.HTML);
        assertNotNull(generator);
        assertInstanceOf(HtmlRelatorioGenerator.class, generator);
    }

    @Test
    void fabricaDeveCriarGeradorParaTodosOsFormatos() {
        for (FormatoRelatorio formato : FormatoRelatorio.values()) {
            RelatorioGenerator generator = factory.criarGerador(formato);
            assertNotNull(generator, "Fabrica retornou null para formato: " + formato);
        }
    }

    @Test
    void fabricaDeveCriarNovasInstanciasACadaChamada() {
        RelatorioGenerator g1 = factory.criarGerador(FormatoRelatorio.PDF);
        RelatorioGenerator g2 = factory.criarGerador(FormatoRelatorio.PDF);
        assertNotSame(g1, g2, "Fabrica deve criar instancias distintas a cada chamada");
    }

    // ===== Testes do Formato XML =====

    @Test
    void xmlDeveConterDeclaracaoXml() {
        RelatorioGenerator generator = factory.criarGerador(FormatoRelatorio.XML);
        String resultado = generator.gerar(relatorioTeste);

        assertTrue(resultado.contains("<?xml"), "Saida XML deve conter declaracao XML");
    }

    @Test
    void xmlDeveConterTagRelatorio() {
        RelatorioGenerator generator = factory.criarGerador(FormatoRelatorio.XML);
        String resultado = generator.gerar(relatorioTeste);

        assertTrue(resultado.contains("<relatorio>"), "Saida XML deve conter tag <relatorio>");
        assertTrue(resultado.contains("</relatorio>"), "Saida XML deve conter tag </relatorio>");
    }

    @Test
    void xmlDeveConterTituloDoRelatorio() {
        RelatorioGenerator generator = factory.criarGerador(FormatoRelatorio.XML);
        String resultado = generator.gerar(relatorioTeste);

        assertTrue(resultado.contains("<titulo>"), "Saida XML deve conter tag <titulo>");
        assertTrue(resultado.contains("Relatorio de Vendas"), "Saida XML deve conter o titulo do relatorio");
    }

    @Test
    void xmlDeveConterTipoDoRelatorio() {
        RelatorioGenerator generator = factory.criarGerador(FormatoRelatorio.XML);
        String resultado = generator.gerar(relatorioTeste);

        assertTrue(resultado.contains("<tipo>VENDAS</tipo>"), "Saida XML deve conter o tipo do relatorio");
    }

    @Test
    void xmlDeveConterConteudo() {
        RelatorioGenerator generator = factory.criarGerador(FormatoRelatorio.XML);
        String resultado = generator.gerar(relatorioTeste);

        assertTrue(resultado.contains("<conteudo>"), "Saida XML deve conter tag <conteudo>");
        assertTrue(resultado.contains("Produto A"), "Saida XML deve conter conteudo do relatorio");
    }

    @Test
    void xmlNaoDeveSerVazio() {
        RelatorioGenerator generator = factory.criarGerador(FormatoRelatorio.XML);
        String resultado = generator.gerar(relatorioTeste);

        assertNotNull(resultado);
        assertFalse(resultado.isBlank());
    }

    // ===== Testes do Formato HTML =====

    @Test
    void htmlDeveConterDoctype() {
        RelatorioGenerator generator = factory.criarGerador(FormatoRelatorio.HTML);
        String resultado = generator.gerar(relatorioTeste);

        assertTrue(resultado.contains("<!DOCTYPE html>"), "Saida HTML deve conter DOCTYPE");
    }

    @Test
    void htmlDeveConterTagHtml() {
        RelatorioGenerator generator = factory.criarGerador(FormatoRelatorio.HTML);
        String resultado = generator.gerar(relatorioTeste);

        assertTrue(resultado.contains("<html>"), "Saida HTML deve conter tag <html>");
        assertTrue(resultado.contains("</html>"), "Saida HTML deve conter tag </html>");
    }

    @Test
    void htmlDeveConterTituloNoH1() {
        RelatorioGenerator generator = factory.criarGerador(FormatoRelatorio.HTML);
        String resultado = generator.gerar(relatorioTeste);

        assertTrue(resultado.contains("<h1>"), "Saida HTML deve conter tag <h1>");
        assertTrue(resultado.contains("Relatorio de Vendas"), "Saida HTML deve conter o titulo do relatorio");
    }

    @Test
    void htmlDeveConterTituloNoTitle() {
        RelatorioGenerator generator = factory.criarGerador(FormatoRelatorio.HTML);
        String resultado = generator.gerar(relatorioTeste);

        assertTrue(resultado.contains("<title>Relatorio de Vendas</title>"),
                "Saida HTML deve conter o titulo na tag <title>");
    }

    @Test
    void htmlDeveConterConteudoNoBody() {
        RelatorioGenerator generator = factory.criarGerador(FormatoRelatorio.HTML);
        String resultado = generator.gerar(relatorioTeste);

        assertTrue(resultado.contains("<body>"), "Saida HTML deve conter tag <body>");
        assertTrue(resultado.contains("Produto A"), "Saida HTML deve conter o conteudo do relatorio");
    }

    @Test
    void htmlNaoDeveSerVazio() {
        RelatorioGenerator generator = factory.criarGerador(FormatoRelatorio.HTML);
        String resultado = generator.gerar(relatorioTeste);

        assertNotNull(resultado);
        assertFalse(resultado.isBlank());
    }

    // ===== Testes de integracao com RelatorioService =====

    @Test
    void relatorioServiceDeveGerarXmlValido() {
        RelatorioService service = new RelatorioService();
        String resultado = service.gerarRelatorio(TipoRelatorio.VENDAS, FormatoRelatorio.XML);

        assertNotNull(resultado);
        assertFalse(resultado.isBlank());
        assertTrue(resultado.contains("<?xml"), "Resultado deve ser XML valido");
        assertTrue(resultado.contains("<relatorio>"), "Resultado deve conter tag <relatorio>");
    }

    @Test
    void relatorioServiceDeveGerarHtmlValido() {
        RelatorioService service = new RelatorioService();
        String resultado = service.gerarRelatorio(TipoRelatorio.ESTOQUE, FormatoRelatorio.HTML);

        assertNotNull(resultado);
        assertFalse(resultado.isBlank());
        assertTrue(resultado.contains("<!DOCTYPE html>"), "Resultado deve ser HTML valido");
        assertTrue(resultado.contains("<html>"), "Resultado deve conter tag <html>");
    }

    @Test
    void todosNovosFormatosDevemSerGeradosPeloService() {
        RelatorioService service = new RelatorioService();

        String xml = service.gerarRelatorio(TipoRelatorio.CLIENTES, FormatoRelatorio.XML);
        assertNotNull(xml);
        assertFalse(xml.isBlank());

        String html = service.gerarRelatorio(TipoRelatorio.CLIENTES, FormatoRelatorio.HTML);
        assertNotNull(html);
        assertFalse(html.isBlank());
    }
}
