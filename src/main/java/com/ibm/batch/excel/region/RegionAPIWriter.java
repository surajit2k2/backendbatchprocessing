package com.ibm.batch.excel.region;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.ibm.batch.dto.RegionDTO;

/**
 * This custom {@code ItemWriter} writes the information of the student to the
 * log.
 *
 * 
 */
public class RegionAPIWriter implements ItemWriter<RegionDTO> {

	private static final Logger LOGGER = LoggerFactory.getLogger(RegionAPIWriter.class);
	private final String regionURL = "region/addoneregion";
	
	private final String apiUrl;
	private final RestTemplate restTemplate;

	public RegionAPIWriter(String apiUrl, RestTemplate restTemplate) {
		this.apiUrl = apiUrl + regionURL;
		this.restTemplate = restTemplate;
	}

	@Override
	public void write(List<? extends RegionDTO> items) throws Exception {
		LOGGER.info("Received the information of {} Region", items.size());

		items.forEach(region -> postRegionDataFromAPI(region));
		LOGGER.debug("Send the information of all region ");
	}

	private int postRegionDataFromAPI(RegionDTO input) {
		LOGGER.debug("Fetching student data from an external API by using the url: {}", apiUrl);

		int ret = 0;

		URI uri;
		try {
			uri = new URI(apiUrl);

			HttpHeaders headers = new HttpHeaders();
			// set `content-type` header
			headers.setContentType(MediaType.APPLICATION_JSON);
			// set `accept` header
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

			HttpEntity<RegionDTO> request = new HttpEntity<>(input, headers);

			ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);

			ret = result.getStatusCodeValue();

			LOGGER.debug("Data Submitted", input.toString());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;
	}
}
