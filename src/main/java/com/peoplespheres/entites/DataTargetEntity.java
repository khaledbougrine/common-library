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

/** Entity to manage the persistence of the partner data target in the database */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Cacheable
@Table(name = "data_target")
@EntityListeners(AuditingEntityListener.class)
public class DataTargetEntity extends ADataEntity {
    /** Copy constructor to be used to clone a target.
     *
     * @param target The reference target to consider by the copy constructor for the copy.
     */
    public DataTargetEntity(@NonNull final DataTargetEntity target) {
        super(target);
    }

    /** The mapping associated to this data */
    @OneToMany(mappedBy = "target", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<MappingEntity> mappings = new LinkedHashSet<>();
}
