package io.pivotal.pks.geode;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.gemfire.GemfireTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static Logger LOG = LoggerFactory.getLogger(HomeController.class);	
	
	@Autowired private GemfireTemplate _template;
	
	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,String> get(@PathVariable String id) {
		LOG.info("Getting value for key [" + id + "] from Gemfire");
		Map<String,String> result = new HashMap<String,String>();
		String value = _template.get(id);
		if(value != null) result.put(id, value);
		return result;
	}
	
	@RequestMapping(value = "/put/{id}/{value}", method = RequestMethod.GET)
	@ResponseBody
	public void put(@PathVariable String id, @PathVariable String value) {
		LOG.info("Putting value [" + value + "]] for key [" + id + "] from Gemfire");
		_template.put(id,  value);
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	@ResponseBody
	public void delete(@PathVariable String id) {
		LOG.info("Deleting key [" + id + "] from Gemfire");
		_template.remove(id);
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,String> getAll() {
		LOG.info("Getting all values");
		Map<String,String> result = new HashMap<String,String>();
		for(Object key : _template.getRegion().keySetOnServer()) {
			result.put(key.toString(), (String) _template.get(key));
		}
		return result;
	}
	
}
