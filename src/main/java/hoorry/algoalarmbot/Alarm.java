package hoorry.algoalarmbot;

import hoorry.algoalarmbot.alarm.Members;
import hoorry.algoalarmbot.alarm.SlackService;
import hoorry.algoalarmbot.common.TxtFileWriter;
import hoorry.algoalarmbot.crawling.Crawler;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Alarm {

	public static void main(String[] args) throws IOException {
		DayOfWeek today = LocalDate.now().getDayOfWeek();
		if (today == DayOfWeek.MONDAY || today == DayOfWeek.THURSDAY) {
			SlackService slackService = new SlackService(new Members());
			slackService.postSlackMessage();
		}

		if (today == DayOfWeek.THURSDAY) {
			Crawler crawler = new Crawler();
			crawler.updateFeeInfo(new TxtFileWriter<>());
		}
		System.exit(0);
	}

}
