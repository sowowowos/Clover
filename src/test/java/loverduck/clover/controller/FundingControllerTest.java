package loverduck.clover.controller;

import lombok.extern.slf4j.Slf4j;
import loverduck.clover.entity.Company;
import loverduck.clover.entity.CrawlingData;
import loverduck.clover.service.CompanyService;
import loverduck.clover.service.CrawlingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
@Slf4j
class FundingControllerTest {
    @Autowired
    private CrawlingService crawlingService;
    @Autowired
    private CompanyService companyService;

    @Test
    public void test() {

        Long id = 1L;
        Company c = companyService.getMapCompany(id);
        CrawlingData data = crawlingService.getCrawlingData(c.getName());
        List<Map<String, ?>> p = crawlingService.getThreeYearFinanceList(data);
        log.error(Objects.requireNonNull(c).getName());
        List<String> labels = new ArrayList<>();
        List<Integer> sales = new ArrayList<>();
        if (p != null && !p.isEmpty()) {
            p.forEach(e -> {
                labels.add((String) e.get("YMD"));
                sales.add(Integer.parseInt(e.get("매출액").toString().replaceAll(",", "")));
            });
        }
        log.error(Objects.requireNonNull(p).toString());
        Map<String, Object> map = new HashMap<>();
        map.put("type", "bar");
        map.put("data", new HashMap<String, Object>() {
            {
                put("labels", labels);
                put("datasets", new ArrayList<Map<String, Object>>() {
                    {
                        add(new HashMap<>() {
                            {
                                put("label", "# of Votes");
                                put("data", sales);
                                put("borderWidth", 1);
                            }
                        });
                    }
                });
            }
        });
        map.put("options", new HashMap<String, Object>() {{
            put("scales", new HashMap<>() {{
                put("y", new HashMap<>() {{
                    put("beginAtZero", true);
                }});
            }});
        }});

        log.info(map.toString());
    }

    @Test
    void mapdataTest() {
        Long id = 1L;
        Company c = companyService.getMapCompany(id);
//        CrawlingData data = c.getCompanyMapData().getCrawlingData();
        CrawlingData data = crawlingService.getCrawlingData(c.getName());
//        log.info("CODE:" + data.getCode());

//        File file = new File("crawledData/info/" + data.getCode() + ".json");
        List<Map<String, ?>> s = crawlingService.getThreeYearFinanceList(data);
        s.forEach(e -> {
            log.info(e.get("YMD").toString());
//            log.info(e.get("매출액").toString());
            log.info(String.valueOf(Integer.parseInt(e.get("매출액").toString().replaceAll(",", ""))));
        });
    }
}
