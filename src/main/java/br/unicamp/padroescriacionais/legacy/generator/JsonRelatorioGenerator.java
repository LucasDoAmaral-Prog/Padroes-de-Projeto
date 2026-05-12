package br.unicamp.padroescriacionais.legacy.generator;

import br.unicamp.padroescriacionais.legacy.domain.Relatorio;

public class JsonRelatorioGenerator implements RelatorioGenerator {

    @Override
    public String gerar(Relatorio relatorio) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"titulo\": ").append(jsonString(relatorio.getTitulo())).append(",\n");
        sb.append("  \"tipo\": ").append(jsonString(relatorio.getTipo().name())).append(",\n");
        sb.append("  \"dataGeracao\": ").append(jsonString(relatorio.getDataGeracao().toString())).append(",\n");
        sb.append("  \"conteudo\": ").append(jsonString(relatorio.getConteudo())).append("\n");
        sb.append("}\n");
        return sb.toString();
    }

    private String jsonString(String valor) {
        if (valor == null) return "null";
        return "\"" + valor.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
    }
}
