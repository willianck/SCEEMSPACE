package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.MemberSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입 (로케이션 저장)
    @Transactional(readOnly = false)    //기본값
    public Long join(Member member){
        validateDuplicateMember(member);    //중복 회원 검증
        validateDuplicatedEmailAddress(member);    //중복 회원 검증
        memberRepository.save(member);  //맴버 저장
        return member.getId();  //
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("Already an existing member!");
        }
    }

    private void validateDuplicatedEmailAddress(Member member) {
        //EXCEPTION
        List<Member> findMembersByEmailAddress = memberRepository.findByEmailAddress(member.getEmail_address());
        if(!findMembersByEmailAddress.isEmpty()){
            throw new IllegalStateException("Already an existing email address!");
        }
    }

    @Transactional
    public void updateMember(Long memberId, String studentId, String department, OrderStatus orderStatus, String pathway, String email_address, String first_name, String last_name, String groupAllocation, String role, String supervisor, String researchGroup, String priority, String notes){
        Member findMember = memberRepository.findOne(memberId);
        findMember.setName(studentId);
        findMember.setDepartment(department);
        findMember.setStatus(orderStatus);
        findMember.setPathway(pathway);
        findMember.setEmail_address(email_address);
        findMember.setFirst_name(first_name);
        findMember.setLast_name(last_name);
        findMember.setGroupAllocation(groupAllocation);
        findMember.setRole(role);
        findMember.setSupervisor(supervisor);
        findMember.setResearchGroup(researchGroup);
        findMember.setPriority(priority);
        findMember.setNotes(notes);
    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public int deleteMember(Long memberId) {
        return memberRepository.deleteMember(memberId);
    }

    public List<Member> findOneMember(MemberSearch memberSearch){
        return memberRepository.findMemberByCriteria(memberSearch);
    }
}
