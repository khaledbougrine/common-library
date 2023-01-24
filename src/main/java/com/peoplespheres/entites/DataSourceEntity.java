package com.peoplespheres.entites;

// Lombok imports

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

/** Entity to manage the persistence of the PS data source in the database */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Cacheable
@Table(name = "data_source")
@EntityListeners(AuditingEntityListener.class)
public class DataSourceEntity extends ADataEntity {
    /** Copy constructor to be used to clone a source.
     *
     * @param source The reference source to consider by the copy constructor for the copy.
     */
    public DataSourceEntity(@NonNull final DataSourceEntity source) {
        super(source);
    }

    /** The mapping associated to this data */
    @OneToMany(mappedBy = "source", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<MappingEntity> mappings = new LinkedHashSet<>();
}
