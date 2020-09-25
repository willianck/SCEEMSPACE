package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.MemberSearch;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    //에러가 생기면 BindingResult result 여기에 데이터가 하나 들어온다.
    public String create(@Valid MemberForm form, BindingResult result){

        if(result.hasErrors()){
            //회원명을 입력하지 않으면 오류 발생. 다시 members/createMemberForm 실행. 에러메세지는
            //에러메세지는 MemberForm 클래스에 있음.
            return "members/createMemberForm";
        }

        Member member = new Member();
        member.setName(form.getName());
        member.setDepartment(form.getDepartment());
        member.setPathway(form.getPathway());
        member.setEmail_address(form.getEmail_address());
        member.setFirst_name(form.getFirst_name());
        member.setLast_name(form.getLast_name());
        member.setGroupAllocation(form.getGroupAllocation());
        member.setRole(form.getRole());
        member.setSupervisor(form.getSupervisor());
        member.setResearchGroup(form.getResearchGroup());
        member.setPriority(form.getPriority());
        member.setNotes(form.getNotes());

        memberService.join(member);
        return "redirect:/"; //홈으로 보냄
    }

    @GetMapping("/members")
    public String list(@ModelAttribute("memberSearch") MemberSearch memberSearch, Model model){
        List<Member> members = memberService.findMembers();
        List<Member> findMembersByCriteria = memberService.findOneMember(memberSearch);

        model.addAttribute("members", members);
        model.addAttribute("members", findMembersByCriteria);
        model.addAttribute("byName", Comparator.comparing(Member::getName));
        return "members/memberList";
    }

    @GetMapping("/members/byPriority")
    public String listByPriority(@ModelAttribute("memberSearch") MemberSearch memberSearch, Model model){
        List<Member> members = memberService.findMembers();
        List<Member> findMembersByCriteria = memberService.findOneMember(memberSearch);

        model.addAttribute("members", members);
        model.addAttribute("members", findMembersByCriteria);
        model.addAttribute("byPriority", Comparator.comparing(Member::getPriority));
        return "members/memberListByPriority";
    }

    @GetMapping("members/{memberId}/edit")
    public String updateMemberForm(@PathVariable("memberId") Long memberId, Model model){
        Member member = memberService.findOne(memberId);
        MemberForm memberForm = new MemberForm();
        memberForm.setId(member.getId());
        memberForm.setDepartment(member.getDepartment());
        memberForm.setName(member.getName());
        memberForm.setStatus(member.getStatus());
        memberForm.setPathway(member.getPathway());
        memberForm.setEmail_address(member.getEmail_address());
        memberForm.setFirst_name(member.getFirst_name());
        memberForm.setLast_name(member.getLast_name());
        memberForm.setGroupAllocation(member.getGroupAllocation());
        memberForm.setRole(member.getRole());
        memberForm.setSupervisor(member.getSupervisor());
        memberForm.setResearchGroup(member.getResearchGroup());
        memberForm.setPriority(member.getPriority());
        memberForm.setNotes(member.getNotes());

        model.addAttribute("memberForm", memberForm);
        return "members/updateMemberForm";
    }

    @PostMapping("members/{memberId}/edit")
    public String updateMember(@PathVariable Long memberId, @ModelAttribute("memberForm") MemberForm memberForm){
        memberService.updateMember(memberId, memberForm.getName(), memberForm.getDepartment(), memberForm.getStatus(),
                memberForm.getPathway(), memberForm.getEmail_address(), memberForm.getFirst_name(), memberForm.getLast_name(), memberForm.getGroupAllocation(), memberForm.getRole(),
                memberForm.getSupervisor(), memberForm.getResearchGroup(), memberForm.getPriority(), memberForm.getNotes());
        return "redirect:/members";
    }

    @GetMapping("members/{memberId}/delete")
    public String deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);

        return "redirect:/members";
    }


}
