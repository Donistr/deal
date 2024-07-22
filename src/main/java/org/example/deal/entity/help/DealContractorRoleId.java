package org.example.deal.entity.help;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.deal.entity.ContractorRole;
import org.example.deal.entity.DealContractor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DealContractorRoleId implements Serializable {

    private DealContractor dealContractor;

    private ContractorRole contractorRole;

}
