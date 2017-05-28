package nl.first8.hu.ticketsale.venue;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/concerts")
@Transactional
public class VenueResource {
	
	private VenueService service;
	
	@Autowired
	public VenueResource(VenueService service) {
		this.service = service;
	}
	
	@GetMapping(path="/search/artist/{artist}")
	public ResponseEntity<List<Concert>> searchByArtistName(@PathVariable String artist) {
		try {
			Optional<List<Concert>> concerts = service.getByArtist(artist);
			return ResponseEntity.ok(concerts.get());
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
	}
	
	@GetMapping(path="/search/genre/{genre}")
	public ResponseEntity<List<Concert>> searchByGenre(@PathVariable String genre) {
		try {
			List<Concert> concerts = service.getByGenre(genre).get();
			return ResponseEntity.ok(concerts);
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
	}
	
	@GetMapping(path="/search/date/{date}")
	public ResponseEntity<List<Concert>> searchFromDate(@PathVariable String date) {
		try {
			List<Concert> concerts = service.getFromDate(date).get();
			return ResponseEntity.ok(concerts);
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
	}
	
	@GetMapping(path="/search/location/{location}")
	public ResponseEntity<List<Concert>> searchByLocation(@PathVariable String location) {
		try {
			List<Concert> concerts = service.getFromLocation(location).get();
			return ResponseEntity.ok(concerts);
			
		} catch (RuntimeException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
	}
}
