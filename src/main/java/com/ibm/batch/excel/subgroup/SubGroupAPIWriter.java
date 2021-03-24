package com.ibm.batch.excel.subgroup;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.ibm.batch.dto.RegionDTO;
import com.ibm.batch.dto.SubGroupDTO;

/**
 * This custom {@code ItemWriter} writes the information of the student to the
 * log.
 *
 * 
 */
public class SubGroupAPIWriter implements ItemWriter<SubGroupDTO> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SubGroupAPIWriter.class);
	private final String subGroupURL = "subgroup/addonesubgroup/";
	private final String regionURL = "region/findregionbyname/";

	private final String apiUrl;
	private final RestTemplate restTemplate;

	public SubGroupAPIWriter(String apiUrl, RestTemplate restTemplate) {
		this.apiUrl = apiUrl;
		this.restTemplate = restTemplate;
	}


	@Override
	public void write(List<? extends SubGroupDTO> items) throws Exception {
		LOGGER.info("Received the information of {} Region", items.size());

		items.forEach(item -> postSubGroupDataFromAPI(item));
		LOGGER.debug("Send the information of all region ");
	}

	private int postSubGroupDataFromAPI(SubGroupDTO input) {
		LOGGER.debug("Fetching student data from an external API by using the url: {}", apiUrl);

		int ret = 0;
		RegionDTO savedRegionDTO = fetchRegionDataFromAPI(input.getRegionName());
		int regionId = savedRegionDTO.getId();
		System.out.println("Region Id " + regionId);
//		int regionId = 1;
		if (regionId != 0){
			URI uri;
			try {
				uri = new URI(apiUrl + subGroupURL + regionId);
	
				HttpHeaders headers = new HttpHeaders();
				// set `content-type` header
				headers.setContentType(MediaType.APPLICATION_JSON);
				// set `accept` header
				headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	
				HttpEntity<SubGroupDTO> request = new HttpEntity<>(input, headers);
	
				ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
	
				ret = result.getStatusCodeValue();
	
				LOGGER.debug("Data Submitted", input.toString());
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			ret = HttpServletResponse.SC_NOT_FOUND;
		}

		return ret;
	}
	private RegionDTO fetchRegionDataFromAPI(String regioName) {
        LOGGER.debug("Fetching Region data from an external API by using the url: {}", apiUrl);

        ResponseEntity<RegionDTO> response = restTemplate.getForEntity(apiUrl + regionURL + regioName, RegionDTO.class);
        RegionDTO savedRegionDTO = response.getBody();
        LOGGER.debug("Found {} students", savedRegionDTO.toString());

        return savedRegionDTO;
    }
}
