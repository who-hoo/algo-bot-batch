package hoorry.algobot.alarm;

import hoorry.algobot.common.MyFileReader;
import hoorry.algobot.common.MyFileWriter;
import hoorry.algobot.common.TxtFileReader;
import hoorry.algobot.common.TxtFileWriter;
import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Members {

	private final File file = new File(System.getenv("SEQUENCE_PATH"));
	private Queue<String> memberz;

	public Members() {
		loadMembers(new TxtFileReader<>());
	}

	private void loadMembers(MyFileReader<String> fileReader) {
		memberz = new LinkedList<>(fileReader.read(file, " ", args -> args[0]));
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
