package loverduck.clover.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import loverduck.clover.entity.Funding;
import loverduck.clover.repository.FundingRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class FundingServiceImpl implements FundingService{

	private final FundingRepository fundingRepository;
	

	@Override
	public List<Funding> historyCorp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fundSubmit(Funding funding) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Funding> fundingList() {
		List<Funding> funds = fundingRepository.findAll();
		System.out.println("size ! -> " + funds.size());
		return funds;
	}

}
