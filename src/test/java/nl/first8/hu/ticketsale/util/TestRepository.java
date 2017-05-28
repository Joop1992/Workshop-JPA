package nl.first8.hu.ticketsale.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.first8.hu.ticketsale.registration.Account;
import nl.first8.hu.ticketsale.registration.AccountInfo;
import nl.first8.hu.ticketsale.sales.Ticket;
import nl.first8.hu.ticketsale.sales.TicketId;
import nl.first8.hu.ticketsale.venue.Artist;
import nl.first8.hu.ticketsale.venue.Concert;
import nl.first8.hu.ticketsale.venue.Genre;
import nl.first8.hu.ticketsale.venue.Location;

@Service
public class TestRepository {

    @Autowired
    private EntityManager entityManager;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Account createDefaultAccount(String emailAdress) {
        AccountInfo info = new AccountInfo("TestStraat", "0612345678", "Utrecht");
        Account account = new Account(emailAdress);
        account.setInfo(info);
        entityManager.persist(info);
        entityManager.persist(account);
        return account;
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Account createAccount(String emailAdress, String city) {
        AccountInfo info = new AccountInfo("TestStraat", "0612345678", city);
        Account account = new Account(emailAdress);
        account.setInfo(info);
        entityManager.persist(info);
        entityManager.persist(account);
        return account;
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Ticket createDefaultTicket(Account acc, String artist, String location) {
        Account account = entityManager.find(Account.class, acc.getId());
        Concert concert = createDefaultConcert(artist, location);
        Ticket ticket = new Ticket(concert, account);
        entityManager.persist(ticket);
        return ticket;
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void createTicket(Concert c, Account a) {
        Concert concert = entityManager.find(Concert.class, c.getId());
        Account account  = entityManager.find(Account.class, a.getId());
        Ticket ticket = new Ticket(concert, account);
        entityManager.persist(ticket);
    }

    public Ticket findTicket(Concert concert, Account account) {
        TicketId key = new TicketId(concert, account);
        return entityManager.find(Ticket.class, key);
    }
    
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Concert createDefaultConcert(String artistName, String locationName) {
        Location location = createLocation(locationName);
        Artist artist = createArtist(artistName, Genre.Grindcore);
        Concert concert = new Concert();
        concert.setArtist(artist);
        concert.setLocation(location);
        entityManager.persist(concert);
        return concert;

    }

	@Transactional(Transactional.TxType.REQUIRES_NEW)
    public Concert createConcert(String artistName, Genre genre, String locationName) {
        Location location = createLocation(locationName);
        Artist artist = createArtist(artistName, genre);
        Concert concert = new Concert();
        concert.setArtist(artist);
        concert.setLocation(location);
        entityManager.persist(concert);
        
        return concert;

    }
	
	@Transactional(Transactional.TxType.REQUIRES_NEW)
    public Concert createConcertWithDate(String artistName, String date, Genre genre, String locationName) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date parsedDate = null;
		try {
			parsedDate = formatter.parse(date);
		} catch (ParseException e) {e.printStackTrace();}
		
        Location location = createLocation(locationName);
        Artist artist = createArtist(artistName, genre);
        Concert concert = new Concert();
        concert.setArtist(artist);
        concert.setDate(parsedDate);
        concert.setLocation(location);
        entityManager.persist(concert);
        
        return concert;

    }
	
	@Transactional(Transactional.TxType.REQUIRES_NEW)
    private Artist createArtist(String artistName, Genre genre) {
		Artist artist = new Artist();
		artist.setGenre(genre);
		artist.setName(artistName);
		entityManager.persist(artist);
		return artist;
	}

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    private Location createLocation(String locationName) {
        Location location = new Location();
        location.setName(locationName);
        entityManager.persist(location);
        return location;
    }



}
