package de.priv.icalbackup;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BackupActor
{
	@Scheduled(fixedDelay = 1000)
	void test()
	{

	}
}
