package loverduck.clover.service;

import loverduck.clover.entity.Company;

public interface CompanyService {

    /**
     * 수정하기 - 주소, 세부주소, 폰, url
     */
    Company updateCom(String address, String detailAddress, String phone, String homepage, String email);

    Company updateCompanyType(Long id, Integer status);

    Company getMapCompany(Long id);
}
