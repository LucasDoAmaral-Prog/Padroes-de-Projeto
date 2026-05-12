package br.unicamp.padroescriacionais.legacy;

import br.unicamp.padroescriacionais.legacy.domain.ConfiguracaoSistema;
import br.unicamp.padroescriacionais.legacy.service.ConfiguracaoService;
import br.unicamp.padroescriacionais.legacy.service.ExportacaoService;
import br.unicamp.padroescriacionais.legacy.service.RelatorioService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes para o padrao Singleton aplicado ao ConfiguracaoSistema.
 */
class SingletonConfiguracaoTest {

    @BeforeEach
    void setUp() {
        ConfiguracaoSistema.resetInstancia();
    }

    @AfterEach
    void tearDown() {
        ConfiguracaoSistema.resetInstancia();
    }

    @Test
    void getInstanciaDeveRetornarMesmaInstancia() {
        ConfiguracaoSistema instancia1 = ConfiguracaoSistema.getInstancia();
        ConfiguracaoSistema instancia2 = ConfiguracaoSistema.getInstancia();

        assertSame(instancia1, instancia2,
                "getInstancia() deve retornar sempre a mesma instancia (Singleton)");
    }

    @Test
    void getInstanciaDeveRetornarInstanciaNaoNula() {
        ConfiguracaoSistema instancia = ConfiguracaoSistema.getInstancia();
        assertNotNull(instancia, "Singleton nao deve retornar null");
    }

    @Test
    void singletonDeveConterValoresPadrao() {
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstancia();

        assertEquals("Empresa XPTO Ltda.", config.getNomeEmpresa());
        assertEquals("PROD", config.getAmbiente());
        assertEquals("/var/exports/relatorios", config.getDiretorioExportacao());
        assertFalse(config.isDebugAtivo());
    }

    @Test
    void alteracaoNoSingletonDeveSerVisivelEmOutrasReferencias() {
        ConfiguracaoSistema instancia1 = ConfiguracaoSistema.getInstancia();
        instancia1.setAmbiente("HOMOLOG");

        ConfiguracaoSistema instancia2 = ConfiguracaoSistema.getInstancia();
        assertEquals("HOMOLOG", instancia2.getAmbiente(),
                "Alteracao via uma referencia deve ser visivel na outra");
    }

    @Test
    void resetInstanciaDeveCriarNovaInstancia() {
        ConfiguracaoSistema instancia1 = ConfiguracaoSistema.getInstancia();
        instancia1.setAmbiente("QA");

        ConfiguracaoSistema.resetInstancia();
        ConfiguracaoSistema instancia2 = ConfiguracaoSistema.getInstancia();

        assertNotSame(instancia1, instancia2,
                "Apos reset, deve ser criada nova instancia");
        assertEquals("PROD", instancia2.getAmbiente(),
                "Nova instancia deve ter valores padrao");
    }

    @Test
    void servicosDevemCompartilharMesmaConfiguracao() {
        ConfiguracaoService configService = new ConfiguracaoService();
        ConfiguracaoSistema configDoService = configService.getConfiguracao();
        ConfiguracaoSistema configSingleton = ConfiguracaoSistema.getInstancia();

        assertSame(configSingleton, configDoService,
                "ConfiguracaoService deve usar a mesma instancia Singleton");
    }

    @Test
    void alteracaoViaSingletonDeveRefletirNosServicos() {
        ConfiguracaoSistema.getInstancia().setNomeEmpresa("Nova Empresa S.A.");

        ConfiguracaoService configService = new ConfiguracaoService();
        assertEquals("Nova Empresa S.A.", configService.getConfiguracao().getNomeEmpresa(),
                "Alteracao no Singleton deve refletir no ConfiguracaoService");
    }

    @Test
    void construtorPublicoDeveCriarInstanciaIndependente() {
        ConfiguracaoSistema singleton = ConfiguracaoSistema.getInstancia();
        ConfiguracaoSistema independente = new ConfiguracaoSistema("Outra", "DEV", "/tmp", true);

        assertNotSame(singleton, independente,
                "Construtor publico deve criar instancia independente do Singleton");
        assertEquals("Outra", independente.getNomeEmpresa());
        assertEquals("Empresa XPTO Ltda.", singleton.getNomeEmpresa());
    }
}
