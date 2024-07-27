package org.example.deal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

/**
 * Entity представляющее сообщение о выставлении признака наличия сделок контрагенту, где он является основным заемщиком
 */
@Entity
@Table(name = "contractor_set_main_borrower_message")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetMainBorrowerMessage {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contractor_id", nullable = false)
    private String contractorId;

    @Column(name = "is_active_main_borrower", nullable = false)
    private Boolean isActiveMainBorrower;

    @Column(name = "is_success", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isSuccess = false;

    @Column(name = "create_date", nullable = false, updatable = false, insertable = false)
    private ZonedDateTime createDate;

}
