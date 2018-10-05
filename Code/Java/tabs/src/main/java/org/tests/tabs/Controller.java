package org.tests.tabs;

import org.apache.commons.codec.net.URLCodec;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Controller
public class Controller
{
	@CrossOrigin(origins = "*")
	@PostMapping("/openTabs")
	public String openTabs(Model model, @RequestParam Map<String, String> allRequestParams) throws UnsupportedEncodingException
	{
		List<String> result = new ArrayList<>();
		URLCodec codec = new URLCodec();
		for (String url : allRequestParams.values())
		{
			result.add(codec.encode(url, StandardCharsets.UTF_8.displayName()));
		}
		model.addAttribute("test3",result);
		return "redirector";
	}
}
