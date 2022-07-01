package hoorry.algobot;

import hoorry.algobot.alarm.Members;
import hoorry.algobot.alarm.AlarmService;
import hoorry.algobot.common.TxtFileWriter;
import hoorry.algobot.crawling.Crawler;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DailyProcess {

	public static void main(String[] args) throws IOException {
		DayOfWeek today = LocalDate.now().getDayOfWeek();
		if (today == DayOfWeek.TUESDAY || today == DayOfWeek.WEDNESDAY || today == DayOfWeek.FRIDAY) {
			AlarmService alarmService = new AlarmService(new Members());
			if (today == DayOfWeek.TUESDAY) {
				alarmService.postAlarmOfDeadline();
			}
			if (today == DayOfWeek.WEDNESDAY || today == DayOfWeek.FRIDAY) {
				alarmService.postAlarm();
			}
		}

		if (today == DayOfWeek.THURSDAY) {
			Crawler crawler = new Crawler();
			crawler.updateFeeInfo(new TxtFileWriter<>());
		}
		System.exit(0);
	}

}
