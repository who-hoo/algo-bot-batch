package hoorry.algoalarmbot.alarm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Members {

	private final File file = new File(System.getenv("SEQUENCE_PATH"));
	private int memberCount = 7;
	private final Queue<String> memberz;

	public Members() {
		memberz = new LinkedList<>();
		loadMembers();
	}

	private void loadMembers() {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			while (memberCount-- > 0) {
				memberz.offer(br.readLine());
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void saveMembers() {
		changeSequence();
		try (FileWriter writer = new FileWriter(file, false)) {
			for (String n : memberz) {
				writer.write(n);
				writer.write('\n');
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void changeSequence() {
		memberz.offer(memberz.poll());
	}

	public String getThisTurn() {
		String thisTurn = memberz.peek();
		saveMembers();
		return thisTurn;
	}

}
