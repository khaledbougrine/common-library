package com.peoplespheres.mappers;

// Local imports

import com.peoplespheres.dto.ScheduleDTO;
import com.peoplespheres.dto.ScheduleFullDTO;
import com.peoplespheres.entites.ScheduleEntity;
import com.peoplespheres.domain.ScheduleDomain;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;

/** Mapper to switch from schedule DTO, to domain instance and entity */
public final class ScheduleMapper {
    /** Private default constructor to prevent instantiation */
    private ScheduleMapper() {}

    /** Mapping schedule DTO to schedule domain instance.
     *
     * @param scheduleDTO The schedule DTO to map to schedule domain instance.
     * @return ScheduleDomain - The schedule domain instance mapped from the schedule DTO.
     */
    public static @NonNull ScheduleDomain fromDTOToDomain(@NonNull final ScheduleDTO scheduleDTO) {
        final ScheduleDomain scheduleDomain = new ScheduleDomain();
        BeanUtils.copyProperties(scheduleDTO, scheduleDomain);
        return scheduleDomain;
    }

    /** Mapping a schedule domain instance to a schedule DTO.
     *
     * @param scheduleDomain The schedule domain instance to map to a schedule DTO.
     * @return ScheduleDTO - The schedule DTO mapped from the input schedule domain instance.
     */
    public static @NonNull ScheduleDTO fromDomainToDTO(@NonNull final ScheduleDomain scheduleDomain) {
        final ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(scheduleDomain, scheduleDTO);
        return scheduleDTO;
    }

    /** Mapping a schedule domain instance to a schedule DTO.
     *
     * @param scheduleDomain The schedule domain instance to map to a schedule DTO.
     * @return ScheduleDTO - The schedule DTO mapped from the input schedule domain instance.
     */
    public static @NonNull ScheduleFullDTO fromDomainToFullDTO(@NonNull final ScheduleDomain scheduleDomain) {
        final ScheduleFullDTO scheduleDTO = new ScheduleFullDTO();
        BeanUtils.copyProperties(scheduleDomain, scheduleDTO);
        return scheduleDTO;
    }

    /** Mapping a schedule domain instance to a schedule entity.
     *
     * @param scheduleDomain The schedule domain instance to map to a schedule entity.
     * @return ScheduleEntity - The schedule entity mapped from the input schedule domain instance.
     */
    public static @NonNull ScheduleEntity fromDomainToEntity(@NonNull final ScheduleDomain scheduleDomain) {
        final ScheduleEntity scheduleEntity = new ScheduleEntity();
        BeanUtils.copyProperties(scheduleDomain, scheduleEntity);
        return scheduleEntity;
    }

    /** Mapping a schedule entity to a schedule domain instance.
     *
     * @param scheduleEntity The schedule entity to map to a schedule domain instance.
     * @return ScheduleDomain - The schedule domain instance mapped from the input schedule entity.
     */
    public static @NonNull ScheduleDomain  fromEntityToDomain(@NonNull final ScheduleEntity scheduleEntity) {
        final ScheduleDomain scheduleDomain = new ScheduleDomain();
        BeanUtils.copyProperties(scheduleEntity, scheduleDomain);
        scheduleDomain.setClientId((scheduleEntity.getClient() != null) ? scheduleEntity.getClient().getId() : null);
        scheduleDomain.setFlowId((scheduleEntity.getFlow() != null) ? scheduleEntity.getFlow().getId() : null);
        scheduleDomain.setFrequency(scheduleEntity.getFrequency());
        return scheduleDomain;
    }

    /** Mapping schedule DTO to schedule domain instance.
     *
     * @param scheduleFullDTO The schedule DTO to map to schedule domain instance.
     * @return ScheduleDomain - The schedule domain instance mapped from the schedule DTO.
     */
    public static @NonNull ScheduleDomain fromFullDtoToDomain(@NonNull final ScheduleFullDTO scheduleFullDTO) {
        final ScheduleDomain scheduleDomain = new ScheduleDomain();
        BeanUtils.copyProperties(scheduleFullDTO, scheduleDomain);
        if (scheduleFullDTO.getFrequency() != null) scheduleDomain.setFrequency(scheduleFullDTO.getFrequency());
        return scheduleDomain;
    }
}
