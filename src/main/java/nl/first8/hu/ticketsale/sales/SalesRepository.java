package nl.first8.hu.ticketsale.sales;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import nl.first8.hu.ticketsale.registration.Account;
import nl.first8.hu.ticketsale.sales.audittrail.AuditTrail;

@Repository
public class SalesRepository {

    private final EntityManager entityManager;

    @Autowired
    public SalesRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Persists the given <code>account</code> in the underlying data source.
     *
     * @param ticket the account to persist
     *
     * @throws EntityExistsException if the entity already exists
     */
    public void insert(final Ticket ticket) {
        entityManager.persist(ticket);
    }

    public void insert(final Sale sale) {
        entityManager.persist(sale);
    }
    
    public void save(final Sale sale) {
    	try{
    		entityManager.createQuery("UPDATE Sale SET price = :price WHERE ID = :id")
    			.setParameter("price", sale.getPrice()).setParameter("id", sale.getId()).executeUpdate();
    	}catch(Exception ex) {
    		this.deleteSale(sale.getId());
    	}
    }
    
    public void insert(final AuditTrail auditTrail) {
		entityManager.persist(auditTrail);
    }

    Optional<Sale> findSaleByTicket(final Ticket ticket) {

        try {
            return Optional.of(entityManager.createQuery("SELECT s FROM Sale s WHERE s.ticket =:ticket", Sale.class)
                    .setParameter("ticket", ticket)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }


    }

    public List<Ticket> findByAccount(Account account) {
        String jpql = "SELECT ticket FROM Ticket ticket WHERE ticket.account = :account";
        TypedQuery<Ticket> query = entityManager.createQuery(jpql, Ticket.class);
        query.setParameter("account", account);
        return query.getResultList();
    }

    public Optional<Ticket> findTicket(final TicketId ticketId) {
        return Optional.ofNullable(entityManager.find(Ticket.class, ticketId));
    }

    Optional<Ticket> findById(long ticketId) {
        return Optional.ofNullable(entityManager.find(Ticket.class, ticketId));
    }

	public void deleteSale(Long id) {
		entityManager.createQuery("DELETE FROM Sale WHERE id = :id").setParameter("id", id).executeUpdate();
	}

	public void deleteTicket(Ticket ticket) {
		String query = "DELETE FROM Ticket ticket WHERE ticket.account.id = :acc_id AND ticket.concert.id = :tick_id";
		entityManager.createQuery(query).setParameter("acc_id", ticket.getAccount().getId()).setParameter("tick_id", ticket.getConcert().getId()).executeUpdate();		
	}


}
