package org.example.deal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.deal.entity.help.DealContractorRoleId;

/**
 * Entity представляющее связь контрагента и его роли
 */
@Entity
@Table(name = "contractor_to_role")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealContractorRole {

    @EmbeddedId
    private DealContractorRoleId id;

    @Column(name = "is_active", nullable = false, insertable = false)
    private Boolean isActive = true;

}
