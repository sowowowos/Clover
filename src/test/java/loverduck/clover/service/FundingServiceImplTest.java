package loverduck.clover.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class FundingServiceImplTest {
    @Autowired
    private FundingService fundingService;

    @Autowired
    private CompanyService companyService;

    @Test
    void findbyid() {
        log.info(fundingService.findById(10L).getTitle());
    }

    @Test
    void getMapCompany() {
        log.info(companyService.getMapCompany(10L).getName());
    }
}
