package loverduck.clover.service;

import loverduck.clover.entity.Company;
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

    /**
     * 회사의 연결/별도재무제표 조회
     * consolidated : 연결재무제표
     * separate : 별도재무제표
     * @param companyName 회사명
     * @return Map<String, List<Map<String, Object>> <br>String : 재무제표 종류, List<Map<String, Object>> : 3년간 재무제표 리스트
     */
    Map<String, List<Map<String, ?>>> getFinancialData(Company companyName);

    /**
     * 회사의 3년간 재무제표 조회
     * catch의 회사 3년간의 재무제표 조회
     * @return List<Map<String, Object>> 재무제표 리스트
     */
    List<Map<String,?>> getThreeYearFinanceList(Company companyName);

    /**
     * 회사 상세 정보 조회
     * catch의 회사 상세 정보
     * @param companyName 회사명
     * @return Map<String, Object> 회사 상세 정보
     */
    Map<String,?> getCompanyDetail(Company companyName);

    /**
     * 키워드 리스트 조회
     * catch의 회사 green light 키워드 조회
     * @param companyName 회사명
     * @return List<String> 키워드 리스트
     */
    List<String> getKeyWordList(Company companyName);
}
