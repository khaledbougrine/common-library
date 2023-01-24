package com.peoplespheres.entites;

// Local imports

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.peoplespheres.enums.RuleTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/** Entity to manage the persistence of a specific transformation rule */
@Entity
@Table(name = "transformation_rule", indexes = {
        @Index(name = "idx_transformation_rule_entity_id_unq", columnList = "id", unique = true),
        @Index(name = "idx_transformation_rule_entity_function_unq", columnList = "function", unique = true)
}, uniqueConstraints = {
        @UniqueConstraint(name = "uc_transformationruleentity", columnNames = {"function"})
})
@Cacheable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TransformationRuleEntity extends AAuditableVersionedEntity {
    /** The unique identifier of this transformation rule */
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    /** The name given to this transformation rule */
    @Column(name = "name", nullable = false)
    @NotBlank(message = "{transformation.rule.name.not-blank}")
    private String name;

    /** The function associated to this transformation rule */
    @Enumerated(value=EnumType.STRING)
    @Column(name = "function", nullable = false, unique = true)
    @NotNull(message = "{transformation.rule.type.not-null}")
    private RuleTypeEnum function;

    /** The minimum number of parameters required for this specific transformation rule */
    @JsonProperty("param_number_min")
    @Column(name = "param_number_min", nullable = false)
    @NotNull(message = "{transformation.rule.min.param.not-null}")
    @PositiveOrZero(message = "{transformation.rule.min.param.positive}")
    private Integer paramNumberMin;

    /** The maximum number of parameters required for this specific transformation rule */
    @JsonProperty("param_number_max")
    @Column(name = "param_number_max")
    @NotNull(message = "{transformation.rule.max.param.not-null}")
    @PositiveOrZero(message = "{transformation.rule.max.param.positive}")
    private Integer paramNumberMax;

    /** Boolean flag indicating if the transformation is purely internal */
    @Column(name = "is_internal", nullable = false)
    @NotNull(message = "{transformation.rule.internal.not-null}")
    private Boolean isInternal = false;

    ///////////////
    // RELATIONS //
    ///////////////
    /* The set of transformations using this specific transformation rule */
    @JsonIgnore
    @OneToMany(mappedBy = "transformationRule")
    private Set<TransformationEntity> transformations = new LinkedHashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(id, name, function, paramNumberMin, paramNumberMax);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransformationRuleEntity that = (TransformationRuleEntity) o;
        return Objects.equals(name, that.name) && function == that.function && Objects.equals(paramNumberMin, that.paramNumberMin)
                && Objects.equals(paramNumberMax, that.paramNumberMax);
    }

}
