package com.solondiego.algatransito.common;

import com.solondiego.algatransito.api.model.VeiculoModel;
import com.solondiego.algatransito.domain.model.Veiculo;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Veiculo.class, VeiculoModel.class)
                .addMappings(mapper -> mapper.map(Veiculo::getPlaca, VeiculoModel::setNumeroPlaca));

        return modelMapper;

    }

}
