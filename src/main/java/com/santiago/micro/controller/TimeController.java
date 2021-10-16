package com.santiago.micro.controller;

import java.util.Collection;
import javax.ws.rs.core.MediaType;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.santiago.micro.model.Province;
import com.santiago.micro.utils.UtilsConnection;

@RestController
public class TimeController {

	@GetMapping(value = "/time/{province}", produces = MediaType.APPLICATION_JSON)
	private String getTest(@PathVariable(value = "province") String inputProvince) {

		ObjectMapper objectMapper = new ObjectMapper();
		Collection<Province> provinces = null;
		Province province = null;
		try {
			provinces = objectMapper.readValue(
					UtilsConnection.getContentFromRequest("https://www.el-tiempo.net/api/json/v1/provincias"),
					new TypeReference<Collection<Province>>() {
					});

			province = provinces.stream().filter(prov -> prov.getName().equalsIgnoreCase(inputProvince)).findFirst()
					.orElse(null);

		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (province != null) {
			return UtilsConnection
					.getContentFromRequest("https://www.el-tiempo.net/api/json/v2/provincias/" + province.getCodprov());
		} else {
			return "{'message':'not found'}";
		}
	}
}
