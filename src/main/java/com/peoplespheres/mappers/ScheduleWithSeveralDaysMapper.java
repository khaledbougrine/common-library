package com.peoplespheres.mappers;

import com.peoplespheres.dto.ScheduleWithSeveralDaysDTO;
import com.peoplespheres.dto.SynchroScheduleDTO;
import com.peoplespheres.enums.FlowScheduleFrequencyEnum;
import com.peoplespheres.domain.ScheduleDomain;
import com.peoplespheres.domain.ScheduleWithSeveralDaysDomain;
import com.peoplespheres.entites.ClientEntity;
import com.peoplespheres.entites.FlowEntity;
import com.peoplespheres.entites.ModuleEntity;
import com.peoplespheres.entites.ScheduleEntity;
import com.peoplespheres.exception.ScheduleNotValidException;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUtil;
import java.util.*;
import java.util.stream.Stream;

public class ScheduleWithSeveralDaysMapper {
    private static final String DATE_FORMAT ="dd/MM/yyyy";
    private ScheduleWithSeveralDaysMapper() {
    }


    public static LocalDate convertToDate(@NonNull String date,@NonNull Long flowId) throws ScheduleNotValidException {
        LocalDate convertedCurrentDate = null;
        try {
            convertedCurrentDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
        } catch (Exception e) {
            throw new ScheduleNotValidException(flowId,"Text '%s' could not be parsed".formatted(date));
        }
        return convertedCurrentDate;
    }

    public static @NonNull ScheduleWithSeveralDaysDomain fromDTOToDomain(@NonNull final ScheduleWithSeveralDaysDTO scheduleWithSeveralDaysDTO) {
        var scheduleWithSeveralDaysDomain = new ScheduleWithSeveralDaysDomain();
        BeanUtils.copyProperties(scheduleWithSeveralDaysDTO, scheduleWithSeveralDaysDomain);
        return scheduleWithSeveralDaysDomain;
    }

    public static @NonNull List<ScheduleEntity> fromDomainToEntities(@NonNull final ScheduleWithSeveralDaysDomain scheduleWithSeveralDaysDomain) throws ScheduleNotValidException {
        List<Long> schedulesIdsList = (scheduleWithSeveralDaysDomain.getSchedulesIds() != null ?
                scheduleWithSeveralDaysDomain.getSchedulesIds().stream().toList() : null);
        List<String> selectDaysList = scheduleWithSeveralDaysDomain.getSelectDays().stream().toList();
        List<ScheduleEntity> scheduleEntities = new ArrayList<>();
        for (int index = 0; index < selectDaysList.size(); ++index) {
            ScheduleEntity scheduleEntity = new ScheduleEntity();
            BeanUtils.copyProperties(scheduleWithSeveralDaysDomain, scheduleEntity);
            String selectedDay = (scheduleWithSeveralDaysDomain.getFrequency() == FlowScheduleFrequencyEnum.MONTHLY ? convertToDate(selectDaysList.get(index),scheduleWithSeveralDaysDomain.getFlowId()).getDayOfWeek().toString():selectDaysList.get(index));
            Instant startdDate=(scheduleWithSeveralDaysDomain.getFrequency() == FlowScheduleFrequencyEnum.MONTHLY ? convertToDate(selectDaysList.get(index),scheduleWithSeveralDaysDomain.getFlowId()).atStartOfDay(ZoneId.systemDefault()).toInstant():scheduleWithSeveralDaysDomain.getStartDate());
            scheduleEntity.setSelectDay(selectedDay);
            scheduleEntity.setStartDate(startdDate);
            scheduleEntity.setSelectDay(selectDaysList.get(index));
            if (schedulesIdsList != null) {
                scheduleEntity.setId(schedulesIdsList.get(index));
            }
            scheduleEntities.add(scheduleEntity);
        }
        return scheduleEntities;
    }

    public static ScheduleWithSeveralDaysDTO fromDomainToDTO(final ScheduleWithSeveralDaysDomain scheduleWithSeveralDaysDomain) {
        if(scheduleWithSeveralDaysDomain==null) return null;
        final ScheduleWithSeveralDaysDTO scheduleWithSeveralDaysDTO = new ScheduleWithSeveralDaysDTO();
        BeanUtils.copyProperties(scheduleWithSeveralDaysDomain, scheduleWithSeveralDaysDTO);
        return scheduleWithSeveralDaysDTO;
    }

    public static ScheduleWithSeveralDaysDomain fromEntitiesToDomain(@NonNull List<ScheduleEntity> scheduleEntities) {
        if(scheduleEntities.isEmpty())  return null;
        ScheduleWithSeveralDaysDomain scheduleWithSeveralDaysDomain = new ScheduleWithSeveralDaysDomain();
        if (scheduleEntities.isEmpty()) return scheduleWithSeveralDaysDomain;

        // Converting the schedule entities into an aggregated domain instance
        BeanUtils.copyProperties(scheduleEntities.get(0), scheduleWithSeveralDaysDomain);
        Set<Long> scheduleId = new LinkedHashSet<>();
        Set<String> selectedDays = new LinkedHashSet<>();
        Instant startDate=scheduleEntities.get(0).getStartDate();
        scheduleWithSeveralDaysDomain.setFrequency(scheduleEntities.get(0).getFrequency());

        // Converting if necessary the associated client
        final PersistenceUtil persistentUtil = Persistence.getPersistenceUtil();
        final boolean isClientLoaded = persistentUtil.isLoaded(scheduleEntities.get(0), "client");
        if (isClientLoaded && (scheduleEntities.get(0).getClient() != null)) {
            scheduleWithSeveralDaysDomain.setClientId(scheduleEntities.get(0).getClient().getId());
        }

        // Converting if necessary the associated flow
        final boolean isFlowLoaded = persistentUtil.isLoaded(scheduleEntities.get(0), "flow");
        if (isFlowLoaded && (scheduleEntities.get(0).getFlow() != null)) {
            scheduleWithSeveralDaysDomain.setFlowId(scheduleEntities.get(0).getFlow().getId());
        }

        // Converting the remaining schedule properties
        scheduleId.add(scheduleEntities.get(0).getId());
        selectedDays.add(scheduleEntities.get(0).getSelectDay());
        scheduleEntities.subList(1, scheduleEntities.size()).stream().forEach(scheduleEntity -> {
            if (scheduleWithSeveralDaysDomain.equalsEntity(scheduleEntity)) {
                selectedDays.add(scheduleEntity.getSelectDay());
                scheduleId.add(scheduleEntity.getId());
            }
            scheduleId.add(scheduleEntity.getId());
            String selectedDay = (scheduleEntity.getFrequency() == FlowScheduleFrequencyEnum.MONTHLY
                    ? DateTimeFormatter.ofPattern(DATE_FORMAT).withZone(ZoneId.systemDefault()).format(scheduleEntity.getStartDate())
                    :scheduleEntity.getSelectDay());
            selectedDays.add(selectedDay);
        });
        scheduleWithSeveralDaysDomain.setSelectDays(selectedDays);
        scheduleWithSeveralDaysDomain.setSchedulesIds(scheduleId);
        scheduleWithSeveralDaysDomain.setStartDate(startDate);

        // Returning the converted schedule entities
        return scheduleWithSeveralDaysDomain;
    }

    public static ScheduleWithSeveralDaysDomain scheduleDomainAggregation(@NonNull final List<ScheduleDomain> scheduleDomains) throws ScheduleNotValidException {
        if(scheduleDomains.isEmpty()) return new ScheduleWithSeveralDaysDomain();
        ScheduleWithSeveralDaysDomain scheduleWithSeveralDaysDomain = new ScheduleWithSeveralDaysDomain();
        Set<Long> scheduleId = new HashSet<>();
        Set<String> selectedDays = new HashSet<>();
        Instant startDate=scheduleDomains.get(0).getStartDate();
        BeanUtils.copyProperties(scheduleDomains.get(0), scheduleWithSeveralDaysDomain);
        for (ScheduleDomain scheduleDomain:scheduleDomains) {
            if(scheduleDomain.getFrequency()!=scheduleWithSeveralDaysDomain.getFrequency())
                throw new ScheduleNotValidException(scheduleWithSeveralDaysDomain.getFlowId(),"schedules with different types");
            scheduleId.add(scheduleDomain.getId());
            String selectedDay = (scheduleDomain.getFrequency() == FlowScheduleFrequencyEnum.MONTHLY ? DateTimeFormatter.ofPattern(DATE_FORMAT).withZone(ZoneId.systemDefault()).format(scheduleDomain.getStartDate()):scheduleDomain.getSelectDay());
            selectedDays.add(selectedDay);
            startDate=(scheduleDomain.getFrequency() == FlowScheduleFrequencyEnum.MONTHLY && startDate.isBefore(scheduleDomain.getStartDate()) ?startDate:scheduleDomain.getStartDate());
        }
        scheduleWithSeveralDaysDomain.setSelectDays(selectedDays);
        scheduleWithSeveralDaysDomain.setSchedulesIds(scheduleId);
        scheduleWithSeveralDaysDomain.setStartDate(startDate);
        return scheduleWithSeveralDaysDomain;

    }
    public static List<SynchroScheduleDTO> fromSchedulesToSynchroSchedule(ScheduleWithSeveralDaysDTO scheduleWithSeveralDaysDTO, FlowEntity flowEntity, ClientEntity clientEntity) throws ScheduleNotValidException {
        List<SynchroScheduleDTO> synchroScheduleDTOs =new ArrayList<>();
        List<Long> schedulesIdsList =  Optional.ofNullable(scheduleWithSeveralDaysDTO.getSchedulesIds()).map(Collection::stream).orElseGet(Stream::empty).toList();
        List<String> selectDaysList = scheduleWithSeveralDaysDTO.getSelectDays().stream().toList();
        final ModuleEntity moduleEntity = flowEntity.getModule();
        for (int index = 0; index < selectDaysList.size(); ++index) {
            SynchroScheduleDTO synchroScheduleDTO=new SynchroScheduleDTO();
            BeanUtils.copyProperties(scheduleWithSeveralDaysDTO, synchroScheduleDTO);
            String selectedDay = (scheduleWithSeveralDaysDTO.getFrequency() == FlowScheduleFrequencyEnum.MONTHLY ? convertToDate(selectDaysList.get(index),scheduleWithSeveralDaysDTO.getFlowId()).getDayOfWeek().toString():selectDaysList.get(index));
            Instant startdDate=(scheduleWithSeveralDaysDTO.getFrequency() == FlowScheduleFrequencyEnum.MONTHLY ? convertToDate(selectDaysList.get(index),scheduleWithSeveralDaysDTO.getFlowId()).atStartOfDay(ZoneId.systemDefault()).toInstant():scheduleWithSeveralDaysDTO.getStartDate());
            synchroScheduleDTO.setSelectDay(selectedDay);
            synchroScheduleDTO.setStartDate(startdDate);
            synchroScheduleDTO.setFlowId(flowEntity.getId());
            synchroScheduleDTO.setFlowName(flowEntity.getName());
            synchroScheduleDTO.setClientId(clientEntity.getId());
            synchroScheduleDTO.setClientName(clientEntity.getName());
            synchroScheduleDTO.setFlowStatus(flowEntity.getStatus().getCode());
            synchroScheduleDTO.setModuleId(moduleEntity.getId());
            synchroScheduleDTO.setModuleName(moduleEntity.getName());
            if (!schedulesIdsList.isEmpty()) {
                synchroScheduleDTO.setId(schedulesIdsList.get(index));
            }
            synchroScheduleDTOs.add(synchroScheduleDTO);
        }
            return synchroScheduleDTOs;
    }
}
