package loverduck.clover.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import loverduck.clover.service.AdminService;
import loverduck.clover.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final CompanyService companyService;
    private final AdminService adminService;

    @GetMapping("/admin/company")
    public String company(Model model) {
        model.addAttribute("companyList", adminService.getCompanyList());
//        model.addAttribute("companyFundingCount", adminService.getCompanyFundingCount());
        return "admin/manageCorp";
    }

    @PostMapping("/admin/company/{id}/{status}")
    @ResponseBody
    public String updateCompanyStatus(@PathVariable Long id, @PathVariable Integer status) {
        log.info("id: {}, status: {}", id, status);
        if (id == null || status == null || companyService.updateCompanyType(id, status) == null) {
            return "fail";
        }
        return "success";
    }

    @GetMapping("/admin/funding")
    public String funding(Model model) {
        model.addAttribute("fundingList", adminService.getFundingList());
        return "admin/manageFund";
    }

    @PostMapping("/admin/funding/{id}/{status}")
    @ResponseBody
    public String updateFundingStatus(@PathVariable Long id, @PathVariable Integer status) {
        log.info("id: {}, status: {}", id, status);
        if (id == null || status == null || adminService.updateFundingStatus(id, status) == null) {
            return "fail";
        }
        return "success";
    }
}
