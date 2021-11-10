package br.com.agrostok.converter;

import br.com.agrostok.dto.IngredienteDto;
import br.com.agrostok.entity.Ingrediente;

public class IngredienteConverter {

    public static Ingrediente dtoToEntity(IngredienteDto dto){
        return new Ingrediente()
                .setName(dto.getName())
                .setValue(dto.getValue())
                .setQuantidade(dto.getQuantidade());

    }
}
