package org.example.deal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.deal.entity.help.DealStatusEnum;

@Entity
@Table(name = "deal_status")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealStatus {

    @Id
    @Column(name = "id", nullable = false)
    @Enumerated(EnumType.STRING)
    private DealStatusEnum id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

}
