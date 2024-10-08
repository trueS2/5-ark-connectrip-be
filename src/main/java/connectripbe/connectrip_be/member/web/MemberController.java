package connectripbe.connectrip_be.member.web;

import connectripbe.connectrip_be.auth.config.LoginUser;
import connectripbe.connectrip_be.member.dto.FirstUpdateMemberRequest;
import connectripbe.connectrip_be.member.dto.MemberHeaderInfoResponse;
import connectripbe.connectrip_be.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public MemberHeaderInfoResponse getMemberHeaderInfo(@LoginUser String email) {
        return memberService.getMemberHeaderInfo(email);
    }

    @PostMapping("/first")
    public MemberHeaderInfoResponse firstUpdateMember(@LoginUser String email, @RequestBody FirstUpdateMemberRequest request) {
        return memberService.getFirstUpdateMemberResponse(email, request);
    }
}
