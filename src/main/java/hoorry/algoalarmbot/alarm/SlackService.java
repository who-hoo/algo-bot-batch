package hoorry.algoalarmbot.alarm;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.model.block.element.ImageElement;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.slack.api.model.block.Blocks.*;

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
			String alertMessage = createAlertMessage();
			ChatPostMessageRequest request = ChatPostMessageRequest.builder()
				.channel(channel)
				.text(alertMessage)
				.blocks(asBlocks(
					section(section -> section.text(markdownText(alertMessage))
							.accessory(ImageElement.builder()
							.imageUrl("https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMjA0MjRfMjI2%2FMDAxNjUwNzgyNTY4NTQ1.yFg8zmQ_BCcF0d1kwqTwruNPjRuHRYn7yfuQvJOwBOIg.zZeOvkzVHlSiQAAMSY4uJpRj6dskBv0UB81bhWE7gBEg.JPEG.lbhcook%2FKakaoTalk_20220412_124754714_21.jpg&type=a340")
							.altText("cute dog")
							.build())
					)
				))
				.build();

			methods.chatPostMessage(request);

		} catch (SlackApiException | IOException e) {
			log.error(e.getMessage());
		}
	}

	private String createAlertMessage() {
		return "<@" + members.getThisTurn() + "> [" + LocalDate.now()
			.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
			+ "] 문제 제출해주세요!!🚨🚨🚨";
	}

}
