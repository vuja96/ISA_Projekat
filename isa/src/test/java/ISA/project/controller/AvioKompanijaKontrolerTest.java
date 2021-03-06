package ISA.project.controller;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.ws.rs.POST;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ISA.project.dto.DatumskiOpsegDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AvioKompanijaKontrolerTest {

	private static final String URL_PREFIX = "/AvioKompanija";
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testVratiSveKompanije() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/vratiSveKompanije" )).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.[*].id").value(hasItem(3)))
		.andExpect(jsonPath("$.[*].id").value(hasItem(4)));
	}
	
	@Test
	public void testVratiKompaniju() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/vratiKompaniju/7" )).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.id").value(3));
	}
	
	@Test
	public void testVratiPrihod() throws Exception {
		DatumskiOpsegDTO dt = new DatumskiOpsegDTO();
		dt.setDatum1(new GregorianCalendar(2019, Calendar.SEPTEMBER, 4).getTime());
		dt.setDatum2(new GregorianCalendar(2019, Calendar.SEPTEMBER, 9).getTime());
		String json = ISA.project.utils.TestUtil.json(dt);
		this.mockMvc.perform(post(URL_PREFIX + "/vratiPrihod/7").contentType(contentType).content(json)).andExpect(status().isOk())
		.andExpect(jsonPath("$.iznos").value(14));
	}
}
