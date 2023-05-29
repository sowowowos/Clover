package loverduck.clover.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import loverduck.clover.entity.Funding;
import loverduck.clover.entity.FundingReply;
import loverduck.clover.entity.QFunding;
import loverduck.clover.entity.LikedFunding;
import loverduck.clover.entity.Users;
import loverduck.clover.repository.FundingReplyRepository;
import loverduck.clover.repository.FundingRepository;
import loverduck.clover.repository.LikedFundingRepository;
import loverduck.clover.repository.UsersRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class FundingServiceImpl implements FundingService{

	private final FundingRepository fundingRepository;
	private final FundingReplyRepository fundingReplyRepository;
	
	@Autowired
	private final EntityManager entityManager;
	
	private final JPAQueryFactory qFactory;
	
	QFunding qfunding = QFunding.funding;
    private final LikedFundingRepository likedFundingRepository;
    private final UsersRepository usersRepository;

	@Override
	public List<Funding> historyCorp() {
		return null;
	}

	@Override
	public void fundSubmit(Funding funding) {
		fundingRepository.save(funding);
	}

	@Override
	public List<Funding> fundingList() {
		List<Funding> funds = qFactory.selectFrom(QFunding.funding).orderBy(QFunding.funding.id.asc()).fetch();
//		System.out.println("size ! -> " + funds.size());
		return funds;
	}

	@Override
	public Funding fundingDetail(Long id) {
		Funding fund = fundingRepository.findById(id).orElse(null);
		return fund;
	}

	@Override
	public Funding findById(Long id) {		
		Funding fund = fundingRepository.findById(id).orElse(null);
		return fund;
	}
	
	@Override
	public List<Funding> findByCompanyName(String name) {
		List<Funding> funds = fundingRepository.findByCompanyName(name);
		return funds;
	}

	@Override
	public void fundingComment(FundingReply fundingReply) {
		 fundingReplyRepository.save(fundingReply);

	}
	
	@Override
	public List<FundingReply> commentList(Long id) {
	    List<FundingReply> commentList = fundingReplyRepository.findByFundingId(id);
	    return commentList;
	}
	
	@Override
	public List<Funding> searchFundingByTitle(String keyword) {
        return fundingRepository.findByTitleContaining(keyword);
    }
	
	@Override
	public List<Funding> searchFundingByCompany(String keyword){
		return fundingRepository.findByCompanyName(keyword);
	}
	
	@Override
	public List<Funding> searchFundingByContent(String keyword) {
		return fundingRepository.findByContent(keyword);
	}

	@Override
	public List<Funding> findNowFundingsById(Long id) {
		return fundingRepository.findNowFundingsById(id);
	}
	
	@Override
	public List<Funding> findDoneFundingsById(Long id) {
		return fundingRepository.findDoneFundingsById(id);
	}

	@Override
	public boolean addLike(Long funding_id, Long user_id) {
		if(likedFundingRepository.findByFundingAndUser(funding_id, user_id) == null) {
			
			LikedFunding fund = LikedFunding.builder()
			.user(usersRepository.findByUserid(user_id.toString()))
			.funding(fundingRepository.findById(funding_id).orElseThrow(RuntimeException::new))
			.build();
			likedFundingRepository.save(fund);
			return true;
		}
		return false;
		
//		likedFundingRepository.save(LikedFunding.builder().funding(funding).user(user).build());
	}

	@Override
	public boolean removeLike(Funding funding, Users user) {
		if(likedFundingRepository.findByFundingAndUser(funding.getId(), user.getId()) != null) {
			likedFundingRepository.delete(LikedFunding.builder().funding(funding).user(user).build());
			return true;
		}
		return false;
	}

	@Override
	public List<Funding> fundingSubmitList(Long company_id) {
		
		List<Funding> fundingList = qFactory.selectFrom(qfunding)
				.where(qfunding.company.id.eq(company_id))
				.orderBy(qfunding.createdAt.desc())
				.fetch();
		
		return fundingList;
	}

}
