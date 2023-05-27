package loverduck.clover.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import loverduck.clover.entity.CrawlingData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@Slf4j
public class CrawlerDBTest {

    @Autowired
    private CrawlingDataRepository crawlingDataRepository;

    @Test
    void insertCompanyList() throws InterruptedException {
        final String path = "crawledData/companylist/";
        File file = new File(path);
        ExecutorService executor = Executors.newFixedThreadPool(16);
        log.info(Arrays.toString(file.listFiles()));
        for (File f : Objects.requireNonNull(file.listFiles())) {
            if (f.isDirectory()) continue;
            executor.execute(() -> {
                        try {
                            FileReader fileReader;
                            BufferedReader bf;
                            fileReader = new FileReader(f);
                            bf = new BufferedReader(fileReader);
                            String line;
                            StringBuilder sb = new StringBuilder();
                            while ((line = bf.readLine()) != null) {
                                sb.append(line);
                            }
                            ObjectMapper objectMapper = new ObjectMapper();
                            Map<String, Object> map = objectMapper.readValue(sb.toString(), Map.class);
                            CrawlingData c = CrawlingData.builder().code(map.get("CompID").toString()).name(map.get("CompName").toString()).build();
//                            crawlingService.saveCrawlingData(c);
                            crawlingDataRepository.save(c);
                            Objects.requireNonNull(bf).close();
                            Objects.requireNonNull(fileReader).close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            );
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
            Thread.sleep(100);
        }
    }
}
