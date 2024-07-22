package org.example.deal.entity.help;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.deal.entity.ContractorRole;
import org.example.deal.entity.DealContractor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DealContractorRoleId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "contractor_id")
    private DealContractor dealContractor;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private ContractorRole contractorRole;

}
