package nl.first8.hu.ticketsale.sales;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import nl.first8.hu.ticketsale.util.TestRepository;
import nl.first8.hu.ticketsale.venue.Concert;
import nl.first8.hu.ticketsale.venue.Genre;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Rollback(false)
public class VenueTest {
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private TestRepository helper;
	
	@Test
	public void searchByArtist() throws Exception {
		
		helper.createConcert("Martin Garrix", Genre.EDM, "Amsertdam");
		helper.createConcert("AC/DC", Genre.ROCK, "Koln");
		
		String requestedName = "Martin Garrix";
		mvc.perform(
					get("/concerts/search/artist/"+requestedName)
						.accept(MediaType.APPLICATION_JSON_UTF8)
				).andExpect(status().isOk());
	}

	@Test
	public void searchByGenre() throws Exception {
		helper.createConcert("Martin Garrix", Genre.EDM, "Amsertdam");
		helper.createConcert("AC/DC", Genre.ROCK, "Koln");
		
		String requestedName = "EDM";
		mvc.perform(
					get("/concerts/search/genre/"+requestedName)
						.accept(MediaType.APPLICATION_JSON_UTF8)
				).andExpect(status().isOk());
	}
	
	@Test
	public void searchByDate() throws Exception {
		String date1 = "14-03-1992";
		String date2 = "19-04-2017";
		helper.createConcertWithDate("Martin Garrix", date1, Genre.EDM, "Amsertdam");
		helper.createConcertWithDate("AC/DC", date2, Genre.ROCK, "Koln");
		
		String requestedDate = "14-02-1993";
		mvc.perform(
					get("/concerts/search/date/"+requestedDate)
						.accept(MediaType.APPLICATION_JSON_UTF8)
				).andExpect(status().isOk());
	}
	
	@Test
	public void searchByLocation() throws Exception {
		helper.createConcert("Martin Garrix", Genre.EDM, "Amsterdam");
		helper.createConcert("AC/DC", Genre.ROCK, "Koln");
		
		String requestedName = "Amsterdam";
		mvc.perform(
					get("/concerts/search/location/"+requestedName)
						.accept(MediaType.APPLICATION_JSON_UTF8)
				).andExpect(status().isOk());
	}
}
