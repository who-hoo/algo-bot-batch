package hoorry.algobot.crawling;

import hoorry.algobot.common.MyFileWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

@Slf4j
public class Crawler {

	private final File file = new File(System.getenv("FEE_INFO_PATH"));

	public void updateFeeInfo(MyFileWriter<String> fileWriter) throws IOException {
		Document document = Jsoup.connect(
				"https://github.com/who-hoo/algorithm-study/wiki/%ED%81%AC%EB%A1%A4%EB%A7%81-%EC%8B%A4%ED%97%98%EC%8B%A4")
			.get();
		Elements elementsByClass = document.getElementsByClass("markdown-body");
		List<String> feeInfo = elementsByClass.select("h3").eachText();
		fileWriter.write(file, feeInfo);
	}

}
