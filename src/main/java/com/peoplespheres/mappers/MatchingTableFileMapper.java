package com.peoplespheres.mappers;

// Local imports
import com.peoplespheres.model.PSObject;
import com.peoplespheres.entites.MatchingTableFileEntity;

// Lombok imports
import lombok.NonNull;

// J2EE imports
import javax.persistence.Persistence;
import javax.persistence.PersistenceUtil;
import java.util.List;

/** Mapper from matching entity to matching domain instance */
public final class MatchingTableFileMapper {
    /** Private default constructor to prevent instantiation */
    private MatchingTableFileMapper() {}

    /** Conversion of a matching table file entity to a generic DTO.
     *
     * @param matchingTableFileEntity The initial matching table file entity to convert through this mapper.
     * @return PSObject - The generic DTO got from the conversion performed through this mapper.
     */
    public static @NonNull PSObject fromEntityToDTO(@NonNull final MatchingTableFileEntity matchingTableFileEntity) {
        final PSObject matchingGenericDTO = new PSObject();
        matchingGenericDTO.getContent().put("id", matchingTableFileEntity.getId());
        matchingGenericDTO.getContent().put("matchingTable", matchingTableFileEntity.getMatchingTable());
        matchingGenericDTO.getContent().put("filename", matchingTableFileEntity.getFilename());
        matchingGenericDTO.getContent().put("referenceAlias", matchingTableFileEntity.getReferenceAlias());

        // Converting if necessary the matching table entries
        final PersistenceUtil persistentUtil = Persistence.getPersistenceUtil();
        final boolean areMatchingTableEntriesLoaded = persistentUtil.isLoaded(matchingTableFileEntity, "matchingTableEntries");
        if (areMatchingTableEntriesLoaded && (matchingTableFileEntity.getMatchingTableEntries() != null)) {
            final List<PSObject> matchingValues = matchingTableFileEntity.getMatchingTableEntries().stream().map(mv -> {
                final PSObject matchingValue = new PSObject();
                matchingValue.getContent().put("id", mv.getId());
                matchingValue.getContent().put("field_option_alias", mv.getFieldOptionName());
                matchingValue.getContent().put("field_value", mv.getFieldValue());
                matchingValue.getContent().put("partner_value", mv.getExternalValue());
                return matchingValue;
            }).toList();
            matchingGenericDTO.getContent().put("matchingValues", matchingValues);
        }

        // Returning the converted matching table file DTO
        return matchingGenericDTO;
    }

}
