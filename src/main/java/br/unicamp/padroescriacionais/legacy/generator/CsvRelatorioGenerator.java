package br.unicamp.padroescriacionais.legacy.generator;

import br.unicamp.padroescriacionais.legacy.domain.Relatorio;

public class CsvRelatorioGenerator implements RelatorioGenerator {

    @Override
    public String gerar(Relatorio relatorio) {
        StringBuilder sb = new StringBuilder();
        sb.append("titulo,tipo,dataGeracao,conteudo\n");
        sb.append(escapeCsv(relatorio.getTitulo())).append(",");
        sb.append(relatorio.getTipo()).append(",");
        sb.append(relatorio.getDataGeracao()).append(",");
        sb.append(escapeCsv(relatorio.getConteudo())).append("\n");
        return sb.toString();
    }

    private String escapeCsv(String valor) {
        if (valor == null) return "";
        String escapado = valor.replace("\"", "\"\"");
        return "\"" + escapado + "\"";
    }
}
