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

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Entity представляющее сделку
 */
@Entity
@Table(name = "deal")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Deal {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "description")
    private String description;

    @Column(name = "agreement_number")
    private String agreementNumber;

    @Column(name = "agreement_date")
    private LocalDateTime agreementDate;

    @Column(name = "agreement_start_dt")
    private LocalDateTime agreementStartDate;

    @Column(name = "availability_date")
    private LocalDateTime availabilityDate;

    @ManyToOne
    @JoinColumn(name = "type")
    private DealType type;

    @ManyToOne
    @JoinColumn(name = "status")
    private DealStatus status;

    @Column(name = "sum")
    private Double sum;

    @Column(name = "close_dt")
    private LocalDateTime closeDate;

    @OneToMany(mappedBy = "deal")
    private List<Contractor> contractors;

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
    private Boolean isActive;

}
