package loverduck.clover.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import loverduck.clover.entity.Company;
import loverduck.clover.entity.CrawlingData;
import loverduck.clover.entity.QCompanyMapData;
import loverduck.clover.repository.CrawlingRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CrawlingServiceImpl implements CrawlingService {
    private final CrawlingRepository crawlingRepository;
    private final JPAQueryFactory qFactory;
    private final String prefix = "crawledData/";

    private Map<String, ?> getFile(String path) {
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
            return (Map<String, ?>) objectMapper.readValue(sb.toString(), Map.class);
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
        return qFactory.selectFrom(QCompanyMapData.companyMapData)
                .where(QCompanyMapData.companyMapData.company.name.eq(name))
                .select(QCompanyMapData.companyMapData.crawlingData)
                .fetchOne();
    }

    @Override
    public Map<String, List<Map<String, ?>>> getFinancialData(Company companyName) {
        Map<String, ?> map = getFile(prefix + "financial/" + crawlingRepository.findByName(companyName.getName()));
        if (map == null) return null;
        Map<String, ?> process = (Map<String, ?>) map.get("fnList");
        Map<String, List<Map<String, ?>>> res = new HashMap<>();
        res.put("consolidated", (List<Map<String, ?>>) process.get("1"));
        res.put("separate", (List<Map<String, ?>>) process.get("2"));
        return res;
    }

    @Override
    @Transactional
    public List<Map<String, ?>> getThreeYearFinanceList(CrawlingData crawlingData) {
        String s = prefix + "info/" + crawlingData.getCode() + ".json";
        Map<String, ?> obj = getFile(s);
        if (obj == null) return null;
        return new ArrayList<>((Collection<? extends Map<String, ?>>) obj.get("finance3Year"));
    }

    @Override
    public Map<String, ?> getCompanyDetail(Company company) {
        String code = qFactory.selectFrom(QCompanyMapData.companyMapData)
                .where(QCompanyMapData.companyMapData.company.eq(company))
                .select(QCompanyMapData.companyMapData.crawlingData.code)
                .fetchOne();
        String s = prefix + "commonTop/" + code + ".json";
        Map<String, ?> map = getFile(s);
        if (map == null) return null;
        return (Map<String, ?>) map.get("compInfo");
    }


    @Override
    public List<String> getKeyWordList(Company companyName) {
        ArrayList<String> list = new ArrayList<>();
        Map<String, ?> map = getFile(prefix + "info/" + crawlingRepository.findByName(companyName.getName()));
        if (map == null) return list;
        Map<String, ?> process = (Map<String, ?>) map.get("greenLight");
        list.addAll((Collection<? extends String>) process.get("GREEN"));
        return list;
    }
}
