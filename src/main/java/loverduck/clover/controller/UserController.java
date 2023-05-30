package loverduck.clover.controller;

import lombok.RequiredArgsConstructor;
import loverduck.clover.entity.Company;
import loverduck.clover.entity.Funding;
import loverduck.clover.entity.FundingReply;
import loverduck.clover.entity.PointHistory;
import loverduck.clover.entity.Users;
import loverduck.clover.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 회원가입 , 로그인 , 마이페이지
 */
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UsersService usersService;

    private final FundingService fundingService;

    private final PointHistoryService pointHistoryService;

    @Autowired
    private KakaoServiceImpl kakaoService;

    @Autowired
    private CompanyServiceImpl companyService;


    @ModelAttribute("user")
    public Users getUser(HttpSession session) {
        return (Users) session.getAttribute("user");
    }


    /**
     * 메인
     */
    @GetMapping("/")
    public String mainPage() {

        return "mypage/index";
    }

    /**
     * 회원가입 - 투자자 폼
     */
    @GetMapping("/registerInvestor")
    public String registerInvestor() {

        return "registerInvestor";
    }

    /**
     * 회원가입 투자자
     */
    @PostMapping("/registerInvestor")
    public String register(String name, String nickname, String email, String userid, String password, String password2,
                           String phone, String postalCode, String address, String detailAddress, @RequestParam("logo") MultipartFile logoFile) {


        try {
            String origFilename = logoFile.getOriginalFilename();
            String filename = new MD5Generator(origFilename).toString();
            /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
            String savePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\uploadLogo";
            /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "\\" + filename;
            logoFile.transferTo(new File(filePath));

//            dbCompany.setLogoPath(filename);

            Users dbUser = Users.builder()
                    .userid(userid)
                    .password(password)
                    .email(email)
                    .name(name)
                    .nickname(nickname)
                    .imgProfile(filename)
                    .type(1)
                    .phone(phone)
                    .postalCode(postalCode)
                    .address(address)
                    .detailAddress(detailAddress)
                    .build();

            int userCreateForm = usersService.register(dbUser);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }

    /**
     * 회원가입 - 기업 폼
     */
    @GetMapping("/registerCorp")
    public String registerCorp() {

        return "registerCorp";
    }

    /**
     * 회원가입 기업
     * 추가로 받는 정보 : 사업자 등록번호, 산업선택, 기업url
     * no     sector  homepage
     */
    @PostMapping("/registerCorp")
    public String register2(String name, String nickname, String email, String userid, String password, String password2,
                            String phone, String postalCode, String address, String detailAddress,
                            String no, String sector, String homepage, @RequestParam("logo") MultipartFile logoFile) {

        Company dbCompany
                = Company.builder()
                .no(no)
                .name(nickname)
                .address(address)
                .detailAddress(detailAddress)
                .phone(phone)
                .email(email)
                .homepage(homepage)
                .type(0)
                .sector(sector)
                .build();
        //new Company(null, no, nickname, address, detailAddress, phone, email, homepage, null, null, 0, sector, null, null, null);

        try {
            String origFilename = logoFile.getOriginalFilename();
            String filename = new MD5Generator(origFilename).toString();
            /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
            String savePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\uploadLogo";
            /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "\\" + filename;
            logoFile.transferTo(new File(filePath));

            dbCompany.setLogoPath(filename);

            Users dbUser = Users.builder()
                    .userid(userid)
                    .password(password)
                    .email(email)
                    .name(name)
                    .nickname(nickname)
                    .imgProfile(filename)
                    .type(0)
                    .phone(phone)
                    .postalCode(postalCode)
                    .address(address)
                    .detailAddress(detailAddress)
                    .build();
            //new Users(null, userid, password, email , name , nickname, filename , 0, phone, postalCode, address, detailAddress, null, null);
//	            dbCompany.builder().logo(filename).build();
            dbCompany.setLogo(filename);

            int userCreateForm = usersService.register(dbUser);
            int companyCreate = usersService.register2(dbCompany);
            usersService.mappingCompanyUser(dbUser, dbCompany);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }

    /**
     * 로고이미지 불러오기
     */
    @GetMapping("/logo")
    public ResponseEntity<Resource> getLogo(HttpSession session) throws IOException {
        // 로고 파일의 이름을 가져온다.
        String email = (String) session.getAttribute("loginEmail");
        Users dbUsers = (Users) session.getAttribute("user");

        String logoFileName = dbUsers.getImgProfile();

        // 로고 파일의 실제 경로를 설정한다.
        String savePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\uploadLogo";
        String logoFilePath = savePath + "\\" + logoFileName;

        // 로고 파일을 읽어서 Resource 객체로 생성한다.
        Resource resource = new UrlResource("file:" + logoFilePath);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // 이미지 타입에 따라 적절한 MediaType을 설정한다
                .body(resource);


    }

    /**
     * 진짜 - 이메일 중복체크
     */
    @GetMapping("/checkEmail")
    @ResponseBody
    public ResponseEntity<Object> checkEmail(@RequestParam("email") String email) {
        boolean emailExists = usersService.checkEmailExists(email);
        Map<String, Object> response = new HashMap<>();
        response.put("exists", emailExists);
        return ResponseEntity.ok(response);
    }


    /**
     * 로그인 폼
     */
    @RequestMapping("/loginForm")
    public String login() {

        return "login";
    }


    /**
     * 로그인하기
     * post -> 페이지 전환 없이 값만 전달
     */
    @PostMapping("/loginCheck")
    public String loginCheck(String email, String password, HttpSession session, Model model) {
        try {

            Users dbUser = usersService.logincheck(email, password);

            session.setAttribute("loginUserId", dbUser.getUserid());
            session.setAttribute("loginEmail", dbUser.getEmail());

            session.setAttribute("user", dbUser);

            //회원 로그인 할때 투자자면 1 기업이면 0
            //기업회원이면 세션에 company 정보 저장
            if (dbUser.getType() == 0) {

                Company dbCom = usersService.findCompany(email);
                session.setAttribute("company", dbCom);
            }

            return "redirect:/fundingList";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }


    /**
     * 로그인하기 - 카카오톡 로그인
     */
    @RequestMapping("/kakao")
    public String kakaoLogin(@RequestParam("code") String code, HttpSession session) {
        String access_Token = kakaoService.getAccessToken(code);

        HashMap<String, Object> userInfo = kakaoService.getUserInfo(access_Token);

        String email = (String) userInfo.get("email");
        String nickname = (String) userInfo.get("nickname");
        session.setAttribute("loginEmail", email);
//	    클라이언트의 이메일이 존재할 때 세션에 해당 이메일 등록 토큰은 x
        if (userInfo.get("email") != null) {
        	System.out.println("카카오톡 가입 시작");
//			session.setAttribute("loginEmail", userInfo.get("email"));
//			session.setAttribute("access_Token", access_Token);

            boolean emailExists = usersService.checkEmailExists(email);
            if (emailExists == false) {
                //이메일 존재 안할때 저장하기
            	Users dbUser = new Users(email, access_Token, email, nickname, nickname);
                session.setAttribute("user", dbUser);
            	System.out.println("카카오톡 가입");
                usersService.register(dbUser);
            }
        }

        
        System.out.println("카카오톡 가입확인 후 나가기");
        Users us = (Users) session.getAttribute("user");

        System.out.println(email +" email");
        Users us2 = usersService.getUsers(email);
     
        if (us2.getType()==null) {
            return "mypage/choose";
        }
        session.setAttribute("user", us2);
        System.out.println("확인2: "+us2.getType());
        System.out.println("확인3: "+us2.getEmail());
        return "redirect:/fundingList";


    }

    /**
     * 로그아웃 하기 (카톡 로그아웃 포함)
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/";
    }

    /**
     * 카톡 로그인 후 투자자 회원으로 시작하기 폼
     */
    @GetMapping("/updateInvestorKakao")
    public String kakaoInvestorForm() {


        return "mypage/updateInvestorKakao";
    }

    /**
     * 카톡 로그인 후 투자자 회원으로 시작하기
     */
    @PostMapping("/updateInvestorKakao")
    public String kakaoInvestorUpdate(String name, String nickname, String password, String phone, String postalCode, String address, String detailAddress,
                                      @RequestParam("logo") MultipartFile logoFile, HttpSession session) {

        Users dbUser = (Users) session.getAttribute("user");
        String email = dbUser.getEmail();

        try {
            String origFilename = logoFile.getOriginalFilename();
            String filename = new MD5Generator(origFilename).toString();
            /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
            String savePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\uploadLogo";
            /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "\\" + filename;
            logoFile.transferTo(new File(filePath));

            Users UpUser = usersService.updateAll(name, nickname, filename, 1, phone, postalCode, address, detailAddress, email);
            session.setAttribute("user", UpUser);
            System.out.println(UpUser.getType()+" 유저 타입");

        } catch (Exception e) {
            e.printStackTrace();
        }


        return "redirect:/fundingList";
    }

    /**
     * 카톡 로그인 후 기업 회원으로 시작하기 폼
     */
    @GetMapping("/updateCorpKakao")
    public String kakaoCorpForm() {


        return "mypage/updateCorpKakao";
    }

    /**
     * 카톡 로그인 후 기업 회원으로 시작하기
     */
    @PostMapping("/updateCorpKakao")
    public String kakaoCorpUpdate(String name, String nickname, String phone, String postalCode, String address,
                                  String detailAddress, String no, String sector, String homepage, @RequestParam("logo") MultipartFile logoFile, HttpSession session) {
        Users dbUser = (Users) session.getAttribute("user");
        String email = dbUser.getEmail();


        try {
            String origFilename = logoFile.getOriginalFilename();
            String filename = new MD5Generator(origFilename).toString();
            /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
            String savePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\uploadLogo";
            /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "\\" + filename;
            logoFile.transferTo(new File(filePath));

            Users UpUser = usersService.updateAll(name, nickname, filename, 0, phone, postalCode, address, detailAddress, email);
            session.setAttribute("user", UpUser);

            Company dbCompany = Company.builder().no(no).name(nickname)
                    .address(address)
                    .detailAddress(detailAddress)
                    .phone(phone)
                    .email(email)
                    .homepage(homepage)
                    .logo(filename)
                    .type(0)
                    .sector(sector)
                    .build();

//    		Company dbCompany = new Company(null, no, nickname, address, detailAddress, phone, email, homepage, null, null, 0, sector, null, null, null);
            int companyCreate = usersService.register2(dbCompany);
            session.setAttribute("company", dbCompany);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/fundingList";
    }


    /**
     * 마이페이지 - 투자자 (개인정보 수정폼)
     */
    @GetMapping("/updateInvestor")
    public String updateInvestorForm() {

        return "mypage/updateInvestor";
    }

    /**
     * 마이페이지 - 투자자 (개인정보 수정폼 수정하기)
     */
    @PostMapping("/updateInvestor")
    public String updateInvestor(String nickname, String password, String phone, String postalCode, String address, String detailAddress, HttpSession session) {
        Users dbUser = (Users) session.getAttribute("user");

        String email = dbUser.getEmail();

        if (nickname == null) {
            nickname = dbUser.getNickname();
        }
        if (phone == null) {
            phone = dbUser.getPhone();
        }
        if (postalCode == null) {
            postalCode = dbUser.getPostalCode();
        }
        if (address == null) {
            address = dbUser.getAddress();
        }
        if (detailAddress == null) {
            detailAddress = dbUser.getDetailAddress();
        }

        Users UpUser = usersService.update(password, nickname, phone, postalCode, address, detailAddress, email);

        session.setAttribute("user", UpUser);

        return "redirect:/";
    }

    /**
     * 마이페이지 - 기업 (개인정보 수정폼)
     */
    @GetMapping("/updateCorp")
    public String updateCorpForm() {
        return "mypage/updateCorp";
    }

    /**
     * 마이페이지 - 기업 (개인정보 수정폼)
     */
    @PostMapping("/updateCorp")
    public String updateCorp(String nickname, String password, String phone, String postalCode, String address, String detailAddress, String homepage, HttpSession session) {
        Users dbUser = (Users) session.getAttribute("user");

        String email = dbUser.getEmail();

        if (nickname == null) {
            nickname = dbUser.getNickname();
        }
        if (phone == null) {
            phone = dbUser.getPhone();
        }
        if (postalCode == null) {
            postalCode = dbUser.getPostalCode();
        }
        if (address == null) {
            address = dbUser.getAddress();
        }
        if (detailAddress == null) {
            detailAddress = dbUser.getDetailAddress();
        }
        Users UpUser = usersService.update(password, nickname, phone, postalCode, address, detailAddress, email);
        Company UpCom = companyService.updateCom(address, detailAddress, phone, homepage, email);

        session.setAttribute("user", UpUser);
        session.setAttribute("company", UpCom);
        return "redirect:/";
    }

    /**
     * 마이페이지 - 투자자 (내 펀딩 (기본))
     */
    @RequestMapping("mypage/investor/{id}")
    public String mypageInvestor(@PathVariable Long id, Model model, @ModelAttribute("user") Users user) {
        if (user != null) {
            // 유저가 투자한 펀딩 목록
            List<Funding> myFunds = usersService.findMyFundingsByUserId(user);

            model.addAttribute("myFunds", myFunds);

            Long wallet_id = user.getWallet().getId();
            model.addAttribute("wallet_id", wallet_id);

            Integer nowPoint = pointHistoryService.updateWalletAmount(wallet_id);
            model.addAttribute("nowPoint", nowPoint);

            return "mypage/mypageInvestor";

        }
        return "redirect:/loginForm";

    }

    /**
     * 마이페이지 - 기업 (펀딩 현황 - 현 진행 중인 펀딩 목록)
     */
    @RequestMapping("/mypage/company/{id}")
    public String mypageCorp(@PathVariable Long id, Model model, @ModelAttribute("user") Users user) {
        if (user != null) {
        	
            // 기업이 진행 중인 펀딩 목록
            List<Funding> nowFunds = fundingService.findNowFundingsById(user.getCompany().getId());
            model.addAttribute("nowFunds", nowFunds);
            System.out.println("nowFunds" + nowFunds);

            // 기업의 완료된 펀딩 목록
            List<Funding> doneFunds = fundingService.findDoneFundingsById(user.getCompany().getId());
            model.addAttribute("doneFunds", doneFunds);
            //System.out.println("doneFunds" + doneFunds);

            
            Long wallet_id = user.getWallet().getId();
            model.addAttribute("wallet_id", wallet_id);

            Integer nowPoint = pointHistoryService.updateWalletAmount(wallet_id);
            model.addAttribute("nowPoint", nowPoint);

            return "/mypage/mypageCorp";
        }
        return "redirect:/loginForm";
    }

    ///////////////////////////////////////////////////////////////

    /**
     * 마이페이지 - 투자자 (배당 내역 (정산))
     */
    @RequestMapping("/mypage/investor/{id}/allocationHistoryInvestor")
    public String allocationHistoryInvestor(@PathVariable("id") Long wallet_id, Model model, @ModelAttribute("user") Users user) {
        if (user != null) {
            System.out.println("user_id 2" + wallet_id);

            Long wallet = user.getWallet().getId();
            model.addAttribute("wallet", wallet);

            Integer nowPoint = pointHistoryService.updateWalletAmount(wallet_id);
            model.addAttribute("nowPoint", nowPoint);

            List<PointHistory> allocations = usersService.allocationHistoryInvestor(wallet_id);
            model.addAttribute("allocations", allocations);

            //회원별 포인트 상세 내역 출력
            List<PointHistory> phDetailList =  pointHistoryService.pointDividendHistory(wallet_id);
            model.addAttribute("phDetailList", phDetailList);
            return "mypage/allocationHistoryInvestor";
        }

        return "redirect:/loginForm";
    }


    /**
     * 마이페이지 - 투자자 (포인트 충전)
     */
    @RequestMapping("/charge")
    public String pointCharge() {

        return "mypage/pointCharge";
    }

    /**
     * 마이페이지 - 투자자 (포인트 충전완료)
     */
    @RequestMapping("/chargeFin")
    public String pointChargeFin() {

        return "mypage/pointChargeFin";
    }

    //////////////////////////////////////

    /**
     * 마이페이지 - 투자자 & 기업 (문의 내역)
     */
    @RequestMapping("/historyAsk")
    public String historyAsk() {

        return "mypage/historyAsk";
    }

    /**
     * 마이페이지 - 투자자 & 기업 (문의 내역 > 문의하기)
     */
    @RequestMapping("/ask")
    public String doAsk() {

        return "mypage/ask";
    }

    /**
     * 마이페이지 - 기업 펀드 신청
     */
    @RequestMapping("/fundSubmitForm")
    public String fundSubmitForm() {

        return "mypage/fundSubmitForm";
    }

    /**
     * 마이페이지 - 기업 (펀딩 신청) - 펀딩 신청 내역
     * 펀드 신청한 것 승인 상태 조회
     */
    @GetMapping("/fundSubmitHistory")
    public String historyCorp(Model model, HttpSession session) {

        Company company = (Company) session.getAttribute("company");
        Long company_id = company.getId();
        List<Funding> fundingList = fundingService.fundingSubmitList(company_id);

        model.addAttribute("fundingList", fundingList);


        return "mypage/fundSubmitHistory";
    }

    /**
     * 마이페이지 - 기업 (정산) - 페이지 레이아웃 미완료
     */

    /**
     * 마이페이지 - 기업 (포인트)  - 페이지 레이아웃 미완료 (투자자랑 비슷)
     */
    @RequestMapping("/pointCorp")
    public String pointCorp() {

        return "mypage/pointCharge";
    }


}
