package br.unicamp.padroescriacionais.legacy;

import br.unicamp.padroescriacionais.legacy.domain.FormatoRelatorio;
import br.unicamp.padroescriacionais.legacy.domain.Relatorio;
import br.unicamp.padroescriacionais.legacy.domain.TipoRelatorio;
import br.unicamp.padroescriacionais.legacy.service.ConfiguracaoService;
import br.unicamp.padroescriacionais.legacy.service.ExportacaoService;
import br.unicamp.padroescriacionais.legacy.service.RelatorioService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        RelatorioService relatorioService = new RelatorioService();
        ExportacaoService exportacaoService = new ExportacaoService();
        ConfiguracaoService configuracaoService = new ConfiguracaoService();

        Scanner scanner = new Scanner(System.in);

        System.out.println("==================================================");
        System.out.println("   Sistema de Relatorios Corporativos v2.0");
        System.out.println("==================================================");
        configuracaoService.exibirConfiguracao();
        System.out.println();

        boolean executando = true;
        while (executando) {
            exibirMenu();
            int opcao = lerInteiro(scanner);

            switch (opcao) {
                case 1 -> fluxoGerarRelatorio(scanner, relatorioService);
                case 2 -> fluxoExportarRelatorio(scanner, relatorioService, exportacaoService);
                case 3 -> configuracaoService.exibirConfiguracao();
                case 0 -> executando = false;
                default -> System.out.println("Opcao invalida. Tente novamente.");
            }
            System.out.println();
        }

        System.out.println("Sistema encerrado.");
        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("--- Menu Principal ---");
        System.out.println("1. Gerar relatorio (visualizar na tela)");
        System.out.println("2. Gerar e exportar relatorio (simular gravacao)");
        System.out.println("3. Exibir configuracoes do sistema");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opcao: ");
    }

    private static void fluxoGerarRelatorio(Scanner scanner, RelatorioService service) {
        TipoRelatorio tipo = selecionarTipo(scanner);
        if (tipo == null) return;

        FormatoRelatorio formato = selecionarFormato(scanner);
        if (formato == null) return;

        System.out.println();
        String resultado = service.gerarRelatorio(tipo, formato);
        System.out.println(resultado);
    }

    private static void fluxoExportarRelatorio(Scanner scanner,
                                               RelatorioService relatorioService,
                                               ExportacaoService exportacaoService) {
        TipoRelatorio tipo = selecionarTipo(scanner);
        if (tipo == null) return;

        FormatoRelatorio formato = selecionarFormato(scanner);
        if (formato == null) return;

        Relatorio relatorio = relatorioService.criarRelatorio(tipo);
        System.out.println();
        exportacaoService.exportar(relatorio, formato);
    }

    private static TipoRelatorio selecionarTipo(Scanner scanner) {
        System.out.println("Tipo de relatorio:");
        System.out.println("  1. Vendas");
        System.out.println("  2. Estoque");
        System.out.println("  3. Clientes");
        System.out.print("Escolha: ");

        return switch (lerInteiro(scanner)) {
            case 1 -> TipoRelatorio.VENDAS;
            case 2 -> TipoRelatorio.ESTOQUE;
            case 3 -> TipoRelatorio.CLIENTES;
            default -> {
                System.out.println("Tipo invalido.");
                yield null;
            }
        };
    }

    private static FormatoRelatorio selecionarFormato(Scanner scanner) {
        System.out.println("Formato de saida:");
        System.out.println("  1. PDF");
        System.out.println("  2. CSV");
        System.out.println("  3. JSON");
        System.out.println("  4. XML");
        System.out.println("  5. HTML");
        System.out.print("Escolha: ");

        return switch (lerInteiro(scanner)) {
            case 1 -> FormatoRelatorio.PDF;
            case 2 -> FormatoRelatorio.CSV;
            case 3 -> FormatoRelatorio.JSON;
            case 4 -> FormatoRelatorio.XML;
            case 5 -> FormatoRelatorio.HTML;
            default -> {
                System.out.println("Formato invalido.");
                yield null;
            }
        };
    }

    private static int lerInteiro(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
