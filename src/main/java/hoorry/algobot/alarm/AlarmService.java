package hoorry.algobot.alarm;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.model.block.element.ImageElement;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;

@Service
@Slf4j
public class AlarmService {

	private final String token = System.getenv("SLACK_BOT_TOKEN");
	private final String channel = "C03EUE9HLDA";
	private final Members members;
	private final Random random = new Random();

	public AlarmService(Members members) {
		this.members = members;
	}

	public void postAlarm() {
		try {
			MethodsClient methods = Slack.getInstance().methods(token);
			String thisTurn = members.getThisTurn();
			String imageName = thisTurn + (random.nextInt(5) + 1) + ".png";
			String preURL = "https://s3.ap-northeast-2.amazonaws.com/www.jerry.io/s3image/";

			String alertMessage = createAlertMessage(thisTurn);
			ChatPostMessageRequest request = ChatPostMessageRequest.builder()
				.channel(channel)
				.text(alertMessage)
				.blocks(asBlocks(
					section(section -> section.text(markdownText(alertMessage))
							.accessory(ImageElement.builder()
							.imageUrl(preURL + imageName)
							.altText(imageName)
							.build())
					)
				))
				.build();

			methods.chatPostMessage(request);

		} catch (SlackApiException | IOException e) {
			log.error(e.getMessage());
		}
	}

	public void postAlarmOfDeadline() {
		try {
			MethodsClient methods = Slack.getInstance().methods(token);
			String alertMessage = createDeadLineAlertMessage();
			ChatPostMessageRequest request = ChatPostMessageRequest.builder()
				.channel(channel)
				.text(alertMessage)
				.blocks(asBlocks(
					section(section -> section.text(markdownText(alertMessage)))
				))
				.build();

			methods.chatPostMessage(request);

		} catch (SlackApiException | IOException e) {
			log.error(e.getMessage());
		}
	}

	private String createAlertMessage(String thisTurn) {
		return "<@" + thisTurn + "> [" + LocalDate.now()
			.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
			+ "] 문제 제출해주세요!!🚨🚨🚨";
	}

	private String createDeadLineAlertMessage() {
		return "<!everyone> [" + LocalDate.now()
			.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
			+ "] 제출 마감일 오늘 자정까지입니다!!\uD83D\uDCB8\uD83D\uDCB8\uD83D\uDCB8";
	}

}
