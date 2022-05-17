package hoorry.algoalarmbot.common;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TxtFileWriter<T> implements MyFileWriter<T> {

	@Override
	public void write(File file, Collection<T> contents) {
		try (FileWriter writer = new FileWriter(file, false)) {
			for (T content : contents) {
				writer.write(String.valueOf(content));
				writer.write('\n');
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
