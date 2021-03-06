package nl.first8.hu.ticketsale.reporting;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import nl.first8.hu.ticketsale.registration.Account;
import nl.first8.hu.ticketsale.util.TestRepository;
import nl.first8.hu.ticketsale.venue.Concert;
import nl.first8.hu.ticketsale.venue.Genre;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Rollback(false)
public class ReportingIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestRepository helper;

    @Test
    public void testReport() throws Exception {

        Concert concertMetal1 = helper.createConcert("Five Finger Death Punch", Genre.metal, "Utrecht");
        Concert concertMetal2 = helper.createConcert("Disturbed", Genre.metal, "Apeldoorn");
        Concert concertElec= helper.createConcert("Pogo", Genre.electronica, "Amsterdam");
        Account accountZeist = helper.createAccount("user@zeist.museum", "Zeist");
        Account accountNieuwegein = helper.createAccount("user@nieuwegein.museum", "Nieuwegein");
        Account accountHouten = helper.createAccount("user@houten.museum", "Houten");
        helper.createTicket(concertMetal1, accountZeist);
        helper.createTicket(concertMetal1, accountNieuwegein);
        helper.createTicket(concertMetal2, accountNieuwegein);
        helper.createTicket(concertElec, accountNieuwegein);
        helper.createTicket(concertElec, accountHouten);

        String requestedGenre = "metal";
        final MvcResult result = mvc.perform(
                get("/report/location").param("genre", requestedGenre)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();


        final List<LocationReport> receivedReports= reports(result);

        assertThat(3, is(receivedReports.size()));

        assertThat(concertMetal1.getArtist().getName(), is(receivedReports.get(0).getArtist()));
        assertThat(concertMetal1.getLocation().getName(), is(receivedReports.get(0).getConcertLocations()));
        assertThat(accountZeist.getInfo().getCity(), is(receivedReports.get(0).getTicketCity()));

        assertThat(concertMetal1.getArtist().getName(), is(receivedReports.get(1).getArtist()));
        assertThat(concertMetal1.getLocation().getName(), is(receivedReports.get(1).getConcertLocations()));
        assertThat(accountNieuwegein.getInfo().getCity(), is(receivedReports.get(1).getTicketCity()));

        assertThat(concertMetal2.getArtist().getName(), is(receivedReports.get(2).getArtist()));
        assertThat(concertMetal2.getLocation().getName(), is(receivedReports.get(2).getConcertLocations()));
        assertThat(accountNieuwegein.getInfo().getCity(), is(receivedReports.get(2).getTicketCity()));

    }

    private List<LocationReport> reports(final MvcResult result) throws IOException {
        return objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<LocationReport>>() {
        });
    }


}
