package loverduck.clover.service;

import loverduck.clover.entity.CrawlingData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CrawlingService {
    /**
     * 크롤링 데이터 저장
     */
    void saveCrawlingData(CrawlingData crawlingData);

    /**
     * 크롤링 데이터 조회
     *
     * @return CrawlingData
     */
    CrawlingData getCrawlingData(String name);
    Map<String, Object> getFinancialData(String code);

    List<Map<String, Object>> getThreeYearFinanceList(String code);

    Map<String, Object> getCompanyDetail(String code);

    List<String> getKeyWordList(String companyName);
}
