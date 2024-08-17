package connectripbe.connectrip_be.accompany_status.service;

import connectripbe.connectrip_be.accompany_status.entity.AccompanyStatusEntity;
import connectripbe.connectrip_be.accompany_status.exception.NotFoundAccompanyStatusException;
import connectripbe.connectrip_be.accompany_status.repository.AccompanyStatusJpaRepository;
import connectripbe.connectrip_be.accompany_status.response.AccompanyStatusResponse;
import connectripbe.connectrip_be.member.entity.Member;
import connectripbe.connectrip_be.member.exception.MemberNotOwnerException;
import connectripbe.connectrip_be.member.exception.NotFoundMemberException;
import connectripbe.connectrip_be.member.repository.MemberRepository;
import connectripbe.connectrip_be.post.entity.AccompanyPostEntity;
import connectripbe.connectrip_be.post.exception.NotFoundAccompanyPostException;
import connectripbe.connectrip_be.post.repository.AccompanyPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccompanyStatusService {

    private final AccompanyPostRepository accompanyPostRepository;
    private final AccompanyStatusJpaRepository accompanyStatusJpaRepository;
    private final MemberRepository memberRepository;

    // fixme-noah: 최적화 고민 중
    @Transactional(readOnly = true)
    public AccompanyStatusResponse getAccompanyStatus(long AccompanyPostId) {
        AccompanyPostEntity accompanyPostEntity = accompanyPostRepository.findById(AccompanyPostId)
                .orElseThrow(NotFoundAccompanyPostException::new);

        AccompanyStatusEntity accompanyStatusEntity = accompanyStatusJpaRepository.findTopByAccompanyPostEntityOrderByCreatedAtDesc(accompanyPostEntity)
                .orElseThrow(NotFoundAccompanyStatusException::new);

        return new AccompanyStatusResponse(accompanyPostEntity.getId(), accompanyStatusEntity.getAccompanyStatusEnum());
    }

    // fixme-noah: 코드 수정 고민 중
    public void updateAccompanyStatus(String memberEmail, long postId) {
        Member memberEntity = memberRepository.findByEmail(memberEmail)
                .orElseThrow(NotFoundMemberException::new);

        AccompanyPostEntity accompanyPostEntity = accompanyPostRepository.findById(postId)
                .orElseThrow(NotFoundAccompanyPostException::new);

        validateAccompanyPostOwnership(memberEntity, accompanyPostEntity);

        AccompanyStatusEntity accompanyStatusEntity = accompanyStatusJpaRepository.findTopByAccompanyPostEntityOrderByCreatedAtDesc(accompanyPostEntity)
                .orElseThrow(NotFoundAccompanyPostException::new);

        accompanyStatusEntity.updateStatus();
    }

    private void validateAccompanyPostOwnership(Member member, AccompanyPostEntity accompanyPostEntity) {
        if (!member.getId().equals(accompanyPostEntity.getMember().getId())) {
            throw new MemberNotOwnerException();
        }
    }
}
