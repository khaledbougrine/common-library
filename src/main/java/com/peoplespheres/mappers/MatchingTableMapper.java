package com.peoplespheres.mappers;

// Local imports
import com.peoplespheres.entites.MatchingTableEntity;
import com.peoplespheres.domain.MatchingTableDomain;

// Lombok imports
import lombok.NonNull;

// Spring imports
import org.springframework.beans.BeanUtils;

/** Mapper from matching entity to matching domain instance */
public final class MatchingTableMapper {
    /** Private default constructor to prevent instantiation */
    private MatchingTableMapper() {}

    /** Mapping matching table entity to matching table domain instance.
     *
     * @param matchingEntity The matching table entity to map to a matching table domain instance.
     * @return MatchingTableDomain - The matching table domain instance mapped from the matching table entity.
     */
    public static @NonNull MatchingTableDomain fromEntityToDomain(@NonNull final MatchingTableEntity matchingEntity) {
        final MatchingTableDomain matchingDomain = new MatchingTableDomain();
        BeanUtils.copyProperties(matchingEntity, matchingDomain);
        return matchingDomain;
    }

    /** Mapping matching entity to matching table domain instance.
     *
     * @param matchingDomain The matching table domain instance to map to a matching table entity.
     * @return MatchingTableEntity - The matching table entity mapped from the matching table domain instance.
     */
    public static @NonNull MatchingTableEntity fromDomainToEntity(@NonNull final MatchingTableDomain matchingDomain) {
        final MatchingTableEntity matchingEntity = new MatchingTableEntity();
        BeanUtils.copyProperties(matchingDomain, matchingEntity);
        return matchingEntity;
    }
}
