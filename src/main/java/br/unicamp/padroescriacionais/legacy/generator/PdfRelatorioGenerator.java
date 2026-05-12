package br.unicamp.padroescriacionais.legacy.generator;

import br.unicamp.padroescriacionais.legacy.domain.Relatorio;

public class PdfRelatorioGenerator implements RelatorioGenerator {

    @Override
    public String gerar(Relatorio relatorio) {
        StringBuilder sb = new StringBuilder();
        sb.append("╔══════════════════════════════════════════╗\n");
        sb.append("║           RELATORIO  [PDF]               ║\n");
        sb.append("╚══════════════════════════════════════════╝\n");
        sb.append("Titulo   : ").append(relatorio.getTitulo()).append("\n");
        sb.append("Tipo     : ").append(relatorio.getTipo()).append("\n");
        sb.append("Gerado em: ").append(relatorio.getDataGeracao()).append("\n");
        sb.append("──────────────────────────────────────────\n");
        sb.append(relatorio.getConteudo()).append("\n");
        sb.append("══════════════════════════════════════════\n");
        return sb.toString();
    }
}
