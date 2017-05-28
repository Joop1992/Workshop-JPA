package nl.first8.hu.ticketsale.sales.audittrail;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class AuditTrail {
	
	@Id
	@GeneratedValue
	private Long id;

	private Long audit_sale_id;
	
	private Long audit_account_id;
	
	@Autowired
	public AuditTrail(Long sale_id, Long acc_id) {
		this.audit_sale_id = sale_id;
		this.audit_account_id = acc_id;
	}
}
