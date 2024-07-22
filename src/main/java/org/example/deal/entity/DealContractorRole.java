package org.example.deal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.deal.entity.help.DealContractorRoleId;

@Entity
@Table(name = "deal")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealContractorRole {

    @EmbeddedId
    private DealContractorRoleId id;

    @ManyToOne
    @JoinColumn(name = "contractor_id")
    private DealContractor dealContractor;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private ContractorRole contractorRole;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

}
