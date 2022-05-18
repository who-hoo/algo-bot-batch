package hoorry.algobot.alarm;

import hoorry.algobot.common.MyFileWriter;
import hoorry.algobot.common.TxtFileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Members {

	private final File file = new File(System.getenv("SEQUENCE_PATH"));
	private final Queue<String> memberz;

	public Members() {
		memberz = new LinkedList<>();
		loadMembers();
	}

	private void loadMembers() {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				memberz.offer(line);
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void saveMembers(MyFileWriter<String> fileWriter) {
		changeSequence();
		fileWriter.write(file, memberz);
	}

	private void changeSequence() {
		memberz.offer(memberz.poll());
	}

	public String getThisTurn() {
		String thisTurn = memberz.peek();
		saveMembers(new TxtFileWriter<>());
		return thisTurn;
	}

}
