package org.example.deal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Entity представляющее контрагента
 */
@Entity
@Table(name = "contractor")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contractor {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "deal_id")
    private Deal deal;

    @Column(name = "contractor_id", nullable = false)
    private String contractorId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "inn")
    private String inn;

    @Column(name = "main", nullable = false)
    private Boolean main = false;

    @OneToMany(mappedBy = "id.contractor")
    private List<DealContractorRole> roles;

    @Column(name = "create_date", nullable = false, updatable = false, insertable = false)
    private ZonedDateTime createDate;

    @Column(name = "modify_date")
    private ZonedDateTime modifyDate;

    @CreatedBy
    @Column(name = "create_user_id", updatable = false)
    private String createUserId;

    @LastModifiedBy
    @Column(name = "modify_user_id")
    private String modifyUserId;

    @Column(name = "is_active", nullable = false, insertable = false)
    private Boolean isActive = true;

}
