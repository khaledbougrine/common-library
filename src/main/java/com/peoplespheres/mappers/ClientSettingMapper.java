package com.peoplespheres.mappers;

// Local imports
import com.peoplespheres.entites.ClientSettingEntity;
import com.peoplespheres.domain.ClientSettingDomain;

// Spring imports
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;

public final class ClientSettingMapper {
    /** Private default constructor to prevent instantiation */
    private ClientSettingMapper() {}

    /** Mapping client entity to associated client domain.
     *
     * @param clientSettingEntity The client setting entity to map.
     * @return ClientSettingDomain - The associated client setting domain instance mapped to the input client setting entity.
     */
    public static @NonNull ClientSettingDomain fromEntityToDomain(@NonNull final ClientSettingEntity clientSettingEntity) {
        final ClientSettingDomain clientSettingDomain = new ClientSettingDomain();
        BeanUtils.copyProperties(clientSettingEntity, clientSettingDomain);
        return clientSettingDomain;
    }

}
