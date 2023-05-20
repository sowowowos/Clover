package loverduck.clover.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import loverduck.clover.entity.CrawlingData;
import loverduck.clover.repository.CrawlingRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class CrawlingServiceImpl implements CrawlingService {
    private final CrawlingRepository crawlingRepository;
    private final String prefix = "src/test/resources/json/";

    private Map<String, Object> getFile(String path) {
        File file = new File(path);
        FileReader fileReader;
        StringBuilder sb;
        try {
            fileReader = new FileReader(file);

            BufferedReader bf = new BufferedReader(fileReader);
            String line;
            sb = new StringBuilder();

            while ((line = bf.readLine()) != null) {
                sb.append(line);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            return (Map<String, Object>) objectMapper.readValue(path, Map.class).get("compInfo");
        } catch (IOException e) {
//            throw new RuntimeException(e);
            return null;
        }
    }


    @Override
    public void saveCrawlingData(CrawlingData crawlingData) {
        crawlingRepository.save(crawlingData);
    }

    @Override
    public CrawlingData getCrawlingData(String name) {
        return crawlingRepository.findByName(name);
    }

    @Override
    public Map<String, Object> getFinancialData(String companyName) {
        Map<String, Object> map = getFile(prefix + "financial/" + crawlingRepository.findByName(companyName));
        return map;
    }

    @Override
    public List<Map<String, Object>> getThreeYearFinanceList(String companyName) {
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> obj = getFile(prefix + "info/" + crawlingRepository.findByName(companyName));
        if (obj == null) return null;
        list.addAll((Collection<? extends Map<String, Object>>) obj.get("finance3Year"));
        return list;
    }

    @Override
    public Map<String, Object> getCompanyDetail(String companyName) {
        Map<String, Object> map = getFile(prefix + "/compInfo/" + crawlingRepository.findByName(companyName));
        if (map == null) return null;
        return (Map<String, Object>) map.get("compInfo");
    }


    @Override
    public List<String> getKeyWordList(String companyName) {
        ArrayList<String> list = new ArrayList<>();
        Map<String, Object> map = getFile(prefix + "info/" + crawlingRepository.findByName(companyName));
        if (map == null) return list;
        Map<String, Object> process = (Map<String, Object>) map.get("greenLight");
        process.get("");
        list.addAll((Collection<? extends String>) process.get("GREEN"));
        return list;
    }
}
