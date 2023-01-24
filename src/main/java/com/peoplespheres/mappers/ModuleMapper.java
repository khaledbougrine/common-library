package com.peoplespheres.mappers;

// Local imports
import com.peoplespheres.dto.ModuleDTO;
import com.peoplespheres.entites.ModuleEntity;
import com.peoplespheres.domain.ModuleDomain;

// Spring imports
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;

/** Mapper to switch from module DTO, to domain instance and entity */
public final class ModuleMapper {
    /** Private default constructor to prevent instantiation */
    private ModuleMapper() {}

    /** Mapping module DTO to module domain instance.
     *
     * @param moduleDTO The module DTO to map to module domain instance.
     * @return ModuleDomain - The module domain instance mapped from the module DTO.
     */
    public static @NonNull ModuleDomain fromDTOToDomain(@NonNull final ModuleDTO moduleDTO) {
        final ModuleDomain moduleDomain = new ModuleDomain();
        BeanUtils.copyProperties(moduleDTO, moduleDomain);
        return moduleDomain;
    }

    /** Mapping a module domain instance to a module DTO.
     *
     * @param moduleDomain The module domain instance to map to a module DTO.
     * @return ModuleDTO - The module DTO mapped from the input module domain instance.
     */
    public static @NonNull ModuleDTO fromDomainToDTO(@NonNull final ModuleDomain moduleDomain) {
        final ModuleDTO moduleDTO = new ModuleDTO();
        BeanUtils.copyProperties(moduleDomain, moduleDTO);
        return moduleDTO;
    }

    /** Mapping a module domain instance to a module entity.
     *
     * @param moduleDomain The module domain instance to map to a module entity.
     * @return ModuleEntity - The module entity mapped from the input module domain instance.
     */
    public static @NonNull ModuleEntity fromDomainToEntity(@NonNull final ModuleDomain moduleDomain) {
        final ModuleEntity moduleEntity = new ModuleEntity();
        BeanUtils.copyProperties(moduleDomain, moduleEntity);
        return moduleEntity;
    }

    /** Mapping a module entity to a module domain instance.
     *
     * @param moduleEntity The module entity to map to a module domain instance.
     * @return ModuleDomain - The module domain instance mapped from the input module entity.
     */
    public static @NonNull ModuleDomain fromEntityToDomain(@NonNull final ModuleEntity moduleEntity) {
        final ModuleDomain moduleDomain = new ModuleDomain();
        BeanUtils.copyProperties(moduleEntity, moduleDomain);
        return moduleDomain;
    }

}
