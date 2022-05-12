package hoorry.algoalarmbot;

import hoorry.algoalarmbot.alarm.SlackService;

public class AlgoAlarmBotApplication {

	public static void main(String[] args) throws Exception {
		SlackService slackService = new SlackService();
		slackService.sendAlertMessage();
	}

}
