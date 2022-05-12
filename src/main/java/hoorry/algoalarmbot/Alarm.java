package hoorry.algoalarmbot;

import hoorry.algoalarmbot.alarm.Members;
import hoorry.algoalarmbot.alarm.SlackService;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class Alarm {

	public static void main(String[] args) {
		DayOfWeek today = LocalDate.now().getDayOfWeek();
		if (today == DayOfWeek.MONDAY || today == DayOfWeek.THURSDAY) {
			SlackService slackService = new SlackService(new Members());
			slackService.postSlackMessage();
		}
	}

}
