package nl.first8.hu.ticketsale.venue;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NonNull;

@Service
public class VenueService {

	private final VenueRepository repository;
	
	@Autowired
	public VenueService(VenueRepository repository) {
		this.repository = repository;
	}
	
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void insert(@NonNull final Concert concert){
		repository.insert(concert);
	}
	
	public Optional<List<Concert>> getByArtist(@NonNull final String artistName) {
		return repository.findByArtistName(artistName);
	}
	
	public Optional<List<Concert>> getByGenre(@NonNull final String genre) {
		return repository.findByGenre(genre);
	}
	
	public Optional<List<Concert>> getFromDate(@NonNull final String date) {
		return repository.findFromDate(date);
	}
	
	public Optional<List<Concert>> getFromLocation(@NonNull final String location) {
		return repository.findFromLocation(location);
	}
}
