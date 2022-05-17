package hoorry.algoalarmbot.common;

import java.io.File;
import java.util.Collection;

public interface MyFileWriter<T> {

	void write(File file, Collection<T> content);
}
