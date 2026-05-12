package br.unicamp.padroescriacionais.legacy.domain;

/**
 * Configuracao global do sistema.
 * Aplica o padrao Singleton para garantir uma unica instancia
 * compartilhada por todos os servicos, evitando configuracoes
 * inconsistentes entre diferentes partes da aplicacao.
 */
public class ConfiguracaoSistema {

    private static ConfiguracaoSistema instancia;

    private String nomeEmpresa;
    private String ambiente;
    private String diretorioExportacao;
    private boolean debugAtivo;

    /**
     * Construtor publico mantido para compatibilidade com codigo existente
     * e testes legados que criam instancias independentes.
     */
    public ConfiguracaoSistema(String nomeEmpresa, String ambiente,
                               String diretorioExportacao, boolean debugAtivo) {
        this.nomeEmpresa = nomeEmpresa;
        this.ambiente = ambiente;
        this.diretorioExportacao = diretorioExportacao;
        this.debugAtivo = debugAtivo;
    }

    /**
     * Retorna a instancia unica (Singleton) da configuracao do sistema.
     * Utiliza inicializacao preguicosa (lazy initialization).
     *
     * @return instancia unica de ConfiguracaoSistema
     */
    public static ConfiguracaoSistema getInstancia() {
        if (instancia == null) {
            instancia = new ConfiguracaoSistema(
                    "Empresa XPTO Ltda.",
                    "PROD",
                    "/var/exports/relatorios",
                    false
            );
        }
        return instancia;
    }

    /**
     * Reinicia a instancia Singleton. Utilizado em testes para garantir
     * isolamento entre cenarios de teste.
     */
    public static void resetInstancia() {
        instancia = null;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public String getDiretorioExportacao() {
        return diretorioExportacao;
    }

    public void setDiretorioExportacao(String diretorioExportacao) {
        this.diretorioExportacao = diretorioExportacao;
    }

    public boolean isDebugAtivo() {
        return debugAtivo;
    }

    public void setDebugAtivo(boolean debugAtivo) {
        this.debugAtivo = debugAtivo;
    }
}
