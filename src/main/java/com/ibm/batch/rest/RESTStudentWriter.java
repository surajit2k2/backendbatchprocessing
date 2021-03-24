package com.ibm.batch.rest;


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

import com.ibm.batch.dto.StudentDTO;

/**
 * This custom {@code ItemWriter} writes the information of the student to
 * the log.
 *
 * 
 */
public class RESTStudentWriter implements ItemWriter<StudentDTO> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RESTStudentWriter.class);
    
    private final String apiUrl;
    private final RestTemplate restTemplate;

 
    private List<StudentDTO> studentData;

    public RESTStudentWriter(String apiUrl, RestTemplate restTemplate) {
        this.apiUrl = apiUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public void write(List<? extends StudentDTO> items) throws Exception {
        LOGGER.info("Received the information of {} students", items.size());

        items.forEach(student ->postStudentDataFromAPI(student));
        LOGGER.debug("Send the information of all students ");
    }
    
    
    private int postStudentDataFromAPI(StudentDTO student) {
        LOGGER.debug("Fetching student data from an external API by using the url: {}", apiUrl);
        
        int ret=0;
        
        URI uri;
		try {
			uri = new URI(apiUrl);
		
         
        HttpHeaders headers = new HttpHeaders();
     // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    
     
        HttpEntity<StudentDTO> request = new HttpEntity<>(student, headers);
         
        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
         
        ret = result.getStatusCodeValue();
 
        LOGGER.debug("Data Submitted", student.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			LOGGER.debug("Error Occurred for ", student.toString() );
			LOGGER.debug("Cause of Error ", e.getCause() + " :: " + e.getMessage() );
		}

        return ret;
    }
}
