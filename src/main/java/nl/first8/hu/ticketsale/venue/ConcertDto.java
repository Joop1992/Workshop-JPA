package nl.first8.hu.ticketsale.venue;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConcertDto {
	@Id
	@GeneratedValue
	private Long id;
	
	private Long artist_id;
	
	private Long location_id;
}
