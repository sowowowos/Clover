package loverduck.clover.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import loverduck.clover.entity.CrawlingData;
import loverduck.clover.service.CrawlingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.Map;
import java.util.Objects;

@SpringBootTest
public class CrawlerDBTest {

    @Autowired
    private CrawlingService crawlingService;

    @Test
    void insertCompanyList() throws IOException {
        String path = "src/test/resources/json/companylist/";
        File file = new File(path);
        FileReader fileReader = null;
        BufferedReader bf = null;
        for (File f : Objects.requireNonNull(file.listFiles())) {
            if (f.isDirectory()) continue;
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

            crawlingService.saveCrawlingData(c);
        }
        Objects.requireNonNull(bf).close();
        Objects.requireNonNull(fileReader).close();
    }
}
