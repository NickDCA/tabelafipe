package br.com.nicolas.tabelafipe.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DadosModeloWrapper {
    private List<DadosModelo> modelos;

    public List<DadosModelo> getModelos() {
        return modelos;
    }

    public void setModelos(List<DadosModelo> modelos) {
        this.modelos = modelos;
    }

}
