package org.example.deal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.deal.entity.help.DealContractorRoleId;

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
