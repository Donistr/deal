package org.example.deal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.deal.entity.help.ContractorRoleEnum;

@Entity
@Table(name = "contractor_role")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractorRole {

    @Id
    @Column(name = "id", nullable = false)
    @Enumerated(EnumType.STRING)
    private ContractorRoleEnum id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

}
