package nz.co.s3m.controllers;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TilesController {

	private static final Logger logger = LoggerFactory
			.getLogger(TilesController.class);

	@RequestMapping(value = "tilesListView.do", method = RequestMethod.GET)
	public String tilesListView(Locale locale, Model model) {
		logger.debug("TilesController :tilesListView");
		
		model.addAttribute("viewName", "### List View ###");
		
		// defined in 'tiles-definitions.xml' as 'definition > name' tag
		return "tiles.listView";
	}

	@RequestMapping(value = "tilesDetailView.do", method = RequestMethod.GET)
	public String tilesDetailView(Locale locale, Model model) {
		logger.debug("TilesController :tilesDetailView");
		
		model.addAttribute("viewName", "### Detail View ###");
		
		// defined in 'tiles-definitions.xml' as 'definition > name' tag
		return "tiles.detailView";
	}

}
