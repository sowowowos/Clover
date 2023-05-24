package loverduck.clover.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import loverduck.clover.entity.*;
import loverduck.clover.repository.PointHistoryRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.List;
import java.util.Queue;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AllocationService{
    private final JPAQueryFactory qFactory;
    private final PointHistoryRepository pointHistoryRepository;
    @Scheduled(cron = "0 0 3 * * *")
    public void cronJob() {

        log.info("매일 3시에 실행되는 스케줄러");
        Queue<Funding> endedFunding = new ArrayDeque<>(this.getEndedFunding());

        while (!endedFunding.isEmpty()) {
            Funding funding = endedFunding.poll();
            this.allocateDividend(funding);
        }
    }

    private List<Funding> getEndedFunding() {
        Date now = Date.from(Instant.from(LocalDateTime.now()));
        return qFactory.selectFrom(QFunding.funding)
                .where(QFunding.funding.endDate.before(now))
                .where(QFunding.funding.currentAmount.goe(QFunding.funding.targetMinAmount))
                .where(QFunding.funding.status.eq(10))
                .fetch();
    }

    private void allocateDividend(Funding funding) {
        List<Ordered> fundedList =
                qFactory.selectFrom(QOrdered.ordered)
                        .where(QOrdered.ordered.funding.eq(funding))
                        .fetch();
        Queue<Ordered> orderedQueue = new ArrayDeque<>(fundedList);
        while (!orderedQueue.isEmpty()) {
            Ordered ordered = orderedQueue.poll();
            Long amount = ordered.getAmount();
            Long dividend = (long) (amount * funding.getDividend());
            ordered.getUser().getWallet()
                    .setAmount(ordered.getUser().getWallet().getAmount() + dividend);
            PointHistory history = PointHistory.builder()
                    .amount(dividend)
                    .type(1)
                    .wallet(ordered.getUser().getWallet())
                    .funding(funding)
                    .build();
            pointHistoryRepository.save(history);
        }
    }
}
