package org.example.deal.entity.help;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DealContractorRoleId implements Serializable {

    private UUID dealContractor;

    private String contractorRole;

}
