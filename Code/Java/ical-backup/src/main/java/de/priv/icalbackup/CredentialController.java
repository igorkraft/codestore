package de.priv.icalbackup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@Controller
public class CredentialController
{
	@RequestMapping(value = "/credentials", method = RequestMethod.GET)
	public String getCredentials()
	{
		return "credentials";
	}

	@RequestMapping(value = "/credentials", method = RequestMethod.POST)
	public ResponseEntity<String> setCredentials(Credentials credentials, @RequestParam Map<String,String> params) throws Exception
	{
		//TODO alle Credentials testen
		// trifft der Scheduler auf einen Fehler (Tippfehler in einem der Passwörter), dann muss er
		// die gecachten Credentials löschen und das initialized flag auf false setzen
		String error = credentials.setCredentials(params);

		return new ResponseEntity<String>(HttpStatus.OK);
	}
}