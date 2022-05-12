package hoorry.algoalarmbot;

import hoorry.algoalarmbot.alarm.Members;
import hoorry.algoalarmbot.alarm.SlackService;

public class Alarm {

	public static void main(String[] args) throws Exception {
		SlackService slackService = new SlackService(new Members());
		slackService.postSlackMessage();
	}

}
