package hoorry.algoalarmbot.alarm;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SlackService {

	private final String token = System.getenv("SLACK_BOT_TOKEN");
	private final String channel = "C03EUE9HLDA";
	private final Members members;

	public SlackService(Members members) {
		this.members = members;
	}

	public void postSlackMessage() {
		try {
			MethodsClient methods = Slack.getInstance().methods(token);
			ChatPostMessageRequest request = ChatPostMessageRequest.builder()
				.channel(channel)
				.text(createAlertMessage())
				.build();

			methods.chatPostMessage(request);

		} catch (SlackApiException | IOException e) {
			log.error(e.getMessage());
		}
	}

	private String createAlertMessage() throws IOException {
		return "<@" + members.getThisTurn() + "> [" + LocalDate.now()
			.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
			+ "] 문제 제출해주세요!!🚨🚨🚨";
	}

}
