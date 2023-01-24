package com.peoplespheres.mappers;

// Local imports

import com.peoplespheres.dto.PopulationDTO;
import com.peoplespheres.entites.PopulationEntity;
import com.peoplespheres.domain.PopulationDomain;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;

/** Mapper to switch from population DTO, to domain instance and entity */
public final class PopulationMapper {
    /** Private default constructor to prevent instantiation */
    private PopulationMapper() {}

    /** Mapping a population domain instance to a population entity.
     *
     * @param populationDomain The population domain instance to map to a population entity.
     * @return PopulationDomain - The population entity mapped from the input population domain instance.
     */
    public static @NonNull PopulationEntity fromDomainToEntity(@NonNull final PopulationDomain populationDomain) {
        final PopulationEntity populationEntity = new PopulationEntity();
        BeanUtils.copyProperties(populationDomain, populationEntity);
        return populationEntity;
    }

    /** Mapping a population entity to a population domain instance.
     *
     * @param populationEntity The population entity to map to a population domain instance.
     * @return ScheduleDomain - The population domain instance mapped from the input population entity.
     */
    public static @NonNull PopulationDomain fromEntityToDomain(@NonNull final PopulationEntity populationEntity) {
        final PopulationDomain populationDomain = new PopulationDomain();
        BeanUtils.copyProperties(populationEntity, populationDomain);
        return populationDomain;
    }

    /** Mapping a population domain instance to a population DTO.
     *
     * @param populationDomain The population domain instance to map to a population DTO.
     * @return PopulationDomain - The population DTO mapped from the input population domain instance.
     */
    public static @NonNull PopulationDTO fromDomainToDTO(final PopulationDomain populationDomain) {
        final PopulationDTO populationEntity = new PopulationDTO();
        BeanUtils.copyProperties(populationDomain, populationEntity);
        return populationEntity;
    }

    /** Mapping a population DTO to a population domain instance.
     *
     * @param populationDTO The population DTO to map to a population domain instance.
     * @return ScheduleDomain - The population domain instance mapped from the input population DTO.
     */
    public static @NonNull PopulationDomain fromDtoToDomain(@NonNull final PopulationDTO populationDTO) {
        final PopulationDomain populationDomain = new PopulationDomain();
        BeanUtils.copyProperties(populationDTO, populationDomain);
        return populationDomain;
    }

}
