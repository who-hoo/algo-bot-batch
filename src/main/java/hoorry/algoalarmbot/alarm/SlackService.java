package hoorry.algoalarmbot.alarm;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Queue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SlackService {

	private final String token = System.getenv("SLACK_BOT_TOKEN");
	private final String channel = "C03EUE9HLDA";
	private int memberCount = 7;
	private Queue<String> members = new LinkedList<>();

	public void sendAlertMessage() {
		loadMembers();
		postSlackMessage();
		saveMembers();
	}

	private void postSlackMessage() {
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
		return "<@" + getThisTurn() + "> [" + LocalDate.now()
			.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
			+ "] 문제 제출해주세요!!🚨🚨🚨";
	}


	private String getThisTurn() {
		return members.peek();
	}

	private void loadMembers() {
		try (BufferedReader br = new BufferedReader(new FileReader("sequence.txt"))) {
			while (memberCount-- > 0) {
				members.offer(br.readLine());
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void saveMembers() {
		changeSequence();
		try (FileWriter writer = new FileWriter("sequence.txt", false)) {
			for (String n : members) {
				writer.write(n);
				writer.write('\n');
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void changeSequence() {
		members.offer(members.poll());
	}

}
