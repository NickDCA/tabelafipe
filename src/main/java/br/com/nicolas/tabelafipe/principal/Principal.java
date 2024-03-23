package br.com.nicolas.tabelafipe.principal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import br.com.nicolas.tabelafipe.models.DadosMarca;
import br.com.nicolas.tabelafipe.models.DadosModelo;
import br.com.nicolas.tabelafipe.models.DadosModeloAnos;
import br.com.nicolas.tabelafipe.models.DadosModeloWrapper;
import br.com.nicolas.tabelafipe.models.DadosVeiculo;
import br.com.nicolas.tabelafipe.service.ConsumoApi;
import br.com.nicolas.tabelafipe.service.ConverteDados;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu() {
        System.out.println("*** OPÇÕES ***");
        System.out.println("Carros\nMotos\nCaminhões");
        System.out.println("\nEscolha uma das opções: ");
        String tipoVeiculo = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + tipoVeiculo + "/marcas");
        List<DadosMarca> marcas = Arrays.asList(conversor.obterDados(json, DadosMarca[].class));

        marcas.forEach(m -> System.out.println("Cód: " + m.codigo() + "  Descrição: " + m.nome()));

        System.out.println("\nEscolha o código da marca: ");
        String marca = leitura.nextLine();
        json = consumo.obterDados(ENDERECO + tipoVeiculo + "/marcas/" + marca + "/modelos");
        DadosModeloWrapper modelosWrapper = conversor.obterDados(json, DadosModeloWrapper.class);
        List<DadosModelo> modelos = modelosWrapper.getModelos();

        modelos.forEach(m -> System.out.println("Cód: " + m.codigo() + "  Descrição: " + m.nome()));

        System.out.println("\nEscolha o código do modelo: ");
        String veiculoBusca = leitura.nextLine();
        json = consumo.obterDados(ENDERECO + tipoVeiculo + "/marcas/" + marca + "/modelos/" + veiculoBusca + "/anos");
        List<DadosModeloAnos> modeloAnos = Arrays.asList(conversor.obterDados(json, DadosModeloAnos[].class));

        List<DadosVeiculo> dadosVeiculos = new ArrayList<>();

        modeloAnos.forEach(ma -> {
            var jsonVeiculo = consumo.obterDados(
                    ENDERECO + tipoVeiculo + "/marcas/" + marca + "/modelos/" + veiculoBusca + "/anos/" + ma.codigo());
            DadosVeiculo veiculo = conversor.obterDados(jsonVeiculo, DadosVeiculo.class);
            dadosVeiculos.add(veiculo);
        });

        System.out.println("\nSegue a lista de anos de lançamento do modelo de veiculo escolhido: ");
        dadosVeiculos.forEach(System.out::println);

    }
}
