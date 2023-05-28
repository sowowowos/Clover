package loverduck.clover.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import loverduck.clover.entity.*;
import loverduck.clover.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 펀딩 전체 목록 페이지, 펀딩 상세 페이지, 펀딩 투자하기
 */
@Controller
@RequiredArgsConstructor
public class FundingController {

    private final CrawlingService crawlingService;
    private final FundingService fundingService;
    private final CompanyService companyService;
    private final ObjectMapper objectMapper;
    //임시 - 적용시 지울것
    //private final UsersRepository usersRepository;

    @Autowired
    PointHistoryService pointHistoryService;

    @Autowired
    WalletService walletService;


    @ModelAttribute("user")
    public Users getUser(HttpSession session) {
        return (Users) session.getAttribute("user");
    }

    @Autowired
    UsersService usersService;


    /**
     * 펀딩 전체 목록 페이지
     */
    @RequestMapping("/fundingList")
    public String fundingList(Model model) {
        List<Funding> fundingList = fundingService.fundingList();
        model.addAttribute("fundingList", fundingList);
        return "/fundingList";
    }

    /**
     * 펀딩 상세 페이지
     */
    @RequestMapping("/fundingDetail/{id}")
    public String fundingDetail(@PathVariable Long id, Model model, @ModelAttribute("user") Users user) {
        // 유저 정보
        model.addAttribute("user", user);

        // 펀딩 정보 출력
        Funding fund = fundingService.fundingDetail(id);
        model.addAttribute("fund", fund);

        // 해당 펀딩의 기업의 진행 중인 펀딩들 출력
        List<Funding> nowFunds = fundingService.findNowFundingsById(fund.getCompany().getId());
        model.addAttribute("nowFunds", nowFunds);

        // 해당 펀딩의 기업의 완료된 펀딩들 출력
        List<Funding> doneFunds = fundingService.findDoneFundingsById(fund.getCompany().getId());
        model.addAttribute("doneFunds", doneFunds);

        // 해당 펀딩의 댓글 출력
        List<FundingReply> commentList = fundingService.commentList(id);
//        System.out.println("commentList ->" + commentList);
        if (commentList != null && !commentList.isEmpty()) {
            model.addAttribute("commentList", commentList);
        }

        return "fundingDetail";
    }

    /**
     * 차트
     */
    @PostMapping("/fundingDetail/{id}/chartdata")
    @ResponseBody
    public Map<String, Object> chartData(@PathVariable Long id) {
        System.out.println("chartData");
        System.out.println("id = " + id);
        Company c = fundingService.fundingDetail(id).getCompany();
        CrawlingData data;
        List<Map<String, ?>> p = null;
        if (c.getCompanyMapData() != null) {
            data = crawlingService.getCrawlingData(c.getName());
            p = crawlingService.getThreeYearFinanceList(data);
        }
        List<String> labels = new ArrayList<>();
        List<Integer> sales = new ArrayList<>();
        if (p != null && !p.isEmpty()) {
            List<String> finalLabels = labels;
            List<Integer> finalSales = sales;
            p.forEach(e -> {
                finalLabels.add((String) e.get("YMD"));
                finalSales.add(Integer.parseInt(e.get("영업이익").toString().replaceAll(",", "")));
            });
        }
        switch (labels.size()) {
            case 0:
                labels.add("2020");
                sales.add(0);
            case 1:
                labels.add("2021");
                sales.add(0);
            case 2:
                labels.add("2022");
                sales.add(0);
                break;
            default:
                labels = labels.subList(labels.size()-3, labels.size());
                sales = sales.subList(sales.size()-3, sales.size());
                break;
        }
//        System.out.println("labels = " + labels);
//        System.out.println("sales = " + sales);
        Map<String, Object> map = new HashMap<>();
        map.put("type", "bar");
        List<String> finalLabels1 = labels;
        List<Integer> finalSales1 = sales;
        map.put("data", new HashMap<String, Object>() {
            {
                put("labels", finalLabels1);
                put("datasets", new ArrayList<Map<String, Object>>() {
                    {
                        add(new HashMap<>() {
                            {
                                put("label", "영업이익");
                                put("data", finalSales1);
                                put("backgroundColor", "rgb(0, 66, 160)");
                                put("borderWidth", 1);
                                put("align", "left");
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
//        try {
//            return objectMapper.writeValueAsString(map);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
        return map;
    }


    /**
     * 펀딩 투자하기 - 펀딩하기
     */
    @RequestMapping("/fundingPay")
    public String fundingPay(Model model, @ModelAttribute("user") Users user) {

        //세션 담기
        model.addAttribute("user", user);

        Long wallet_id = user.getWallet().getId();
        model.addAttribute("wallet_id", wallet_id);

        //포인트 충전/사용 결과에 따른 잔여 포인트 값 변경 저장
        Integer nowPoint = pointHistoryService.updateWalletAmount(wallet_id);
        model.addAttribute("nowPoint", nowPoint);

        return "fundingPay";
    }


    /**
     * 펀딩 투자하기 - 펀딩 완료
     * 추후 펀딩 아이디가 세션에 담겨야함
     */
    @PostMapping("/fundingPay")
    @ResponseBody
    public Map<String, Object> fundingPayFin(@ModelAttribute("user") Users user,
                                             @RequestParam("amount") Long amount,
                                             @RequestParam("type") Integer type,
                                             @RequestParam("wallet_id") Long wallet_id,
                                             @RequestParam("funding_id") Long funding_id,
                                             HttpSession session, Model model) {

        //세션 담기
        model.addAttribute("user", user);

        LocalDateTime currentTime = LocalDateTime.now();
        Funding funding = fundingService.findById(funding_id);
        Wallet wallet = walletService.findById(wallet_id);

        //funding_id 추후 저장된 값으로 변경하여 저장 --> company, funding table data 존재해야함
        //포인트 사용 내역 저장 --> funding_id도 함께 저장
        pointHistoryService.fundingPayInsert(amount, currentTime, type, funding, wallet);

        Map<String, Object> map = new HashMap<String, Object>();

        map.put("amount", amount);
        map.put("data", "success");
        map.put("date", currentTime);
        map.put("wallet", wallet);

        return map;
    }

    /**
     * 펀딩 투자하기 완료시
     */


    @RequestMapping("/fundingPayFin")
    public ModelAndView fundingPayFin(
            Long amount,
            Long wallet_id,
            Long funding_id, String exchangeDate) {
        //추후 세션 로그인 회원 정보에 따른 wallet_id로 코드 수정할 예정

        ModelAndView mv = new ModelAndView();

        mv.addObject("amount", amount);
        mv.addObject("walletId", wallet_id);
        mv.addObject("exchangeDate", exchangeDate);

        mv.setViewName("fundingPayFin");

        return mv;
    }


    /**
     * 펀딩 상세 페이지 댓글 작성
     */
    @RequestMapping(value = "/fundingDetail/{id}/comment", method = RequestMethod.POST)
    public String fundingComment(FundingReply fundingReply, HttpSession session) throws Exception {
//		System.out.println("reply -> " + fundingReply.toString());

        fundingReply.setUser((Users) session.getAttribute("user"));

        //fundingReply.setUser(usersRepository.findAll().get(0));

        fundingService.fundingComment(fundingReply);

        return "redirect:/fundingDetail/{id}";
    }

    /**
     * 검색
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchFunding(@RequestParam("keyword") String keyword, Model model) {
        List<Funding> searchResults1 = fundingService.searchFundingByTitle(keyword);
        List<Funding> searchResults2 = fundingService.searchFundingByCompany(keyword);
        List<Funding> searchResults3 = fundingService.searchFundingByContent(keyword);


        model.addAttribute("searchResults1", searchResults1);
        model.addAttribute("searchResults2", searchResults2);
        model.addAttribute("searchResults3", searchResults3);
        model.addAttribute("keyword", keyword);
        return "searchResults";
    }

    /**
     * 펀딩 신청 - 기업 펀드신청 폼
     */
    @GetMapping("/fundSubmitForm")
    public String fundSubmitForm() {

        return "mypage/fundSubmitForm";
    }


    /**
     * 펀딩 신청 - 기업 펀드신청 폼
     */
    @PostMapping(value = "/fundSubmitForm")
    public String fundSubmit(String title, String content, Long targetMinAmount, Long targetMaxAmount,
                             Long currentAmount,
                             String startDate,
                             String endDate, Double dividend, HttpSession session) {

        String email = (String) session.getAttribute("loginEmail");
        Company company = usersService.findCompany(email);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateStartDate = LocalDate.parse(startDate, formatter);
        LocalDate dateEndDate = LocalDate.parse(endDate, formatter);

        LocalDateTime dateTimeStartDate = dateStartDate.atStartOfDay();
        LocalDateTime dateTimeEndDate = dateEndDate.atStartOfDay();


        String company_name = company.getName() + ".png";

        Funding funding = Funding.builder().title(title).content(content).targetMinAmount(targetMinAmount)
                .targetMaxAmount(targetMaxAmount)
                .currentAmount(0L)
                .startDate(dateTimeStartDate)
                .endDate(dateTimeEndDate)
                .dividend(dividend)
                .status(0)
                .imgName(company_name)
                .company(company).build();

        fundingService.fundSubmit(funding);

        return "mypage/fundSubmitForm";
    }

    /**
     * 펀딩 좋아요
     */
    @RequestMapping(value = "/fundingDetail/{id}/addLike", method = RequestMethod.POST)
    @ResponseBody
    public boolean addLike(Long fundingId, Long userId) {
        //System.out.println("ddd = "+fundingId + userId);

//		return "redirect:/fundingDetail/{id}";
        return fundingService.addLike(fundingId, userId);
    }

    /**
     * 펀딩 좋아요 취소
     */
    @RequestMapping(value = "/fundingDetail/{id}/removeLike", method = RequestMethod.POST)
    public String removeLike(Long fundingId, Long userId) {
        fundingService.removeLike(new Funding(fundingId), new Users(userId));
        return "redirect:/fundingDetail/{id}";
    }

    /**
     * 좋아요 여부
     */

}
