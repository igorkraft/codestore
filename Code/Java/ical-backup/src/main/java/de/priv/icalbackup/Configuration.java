package de.priv.icalbackup;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@org.springframework.context.annotation.Configuration
@ConfigurationProperties(prefix = "nextcloud")
public class Configuration
{
	private Map<String, String> calendars;

	@Value("${repo}")
	private String repo;
}