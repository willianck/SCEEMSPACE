package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional  //enabling rollback
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;    //Ff you still want to see queries

    @Test
    @Rollback(false)
    public void REGISTER_MEMBER() throws Exception {
        //given
        Member member = new Member();
        member.setName("jp17528");

        //when
        Long saveId = memberService.join(member);

        //then
        em.flush();     //Enforce to make queries. Anyway the data for the test will be rolled-back by @Transactional
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void DUPLICATED_REGISTER_MEMBER_ID() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("jp17528");

        Member member2 = new Member();
        member2.setName("jp17528");

        //when
        memberService.join(member1);
        memberService.join(member2);

        //then
        fail("exception should be occurred");   //the program should not be arrived at this line
    }

    @Test(expected = IllegalStateException.class)
    public void DUPLICATED_REGISTER_MEMBER_EMAIL_ADDRESS() throws Exception{
        Member member1 = new Member();
        member1.setEmail_address("jp17528");

        Member member2 = new Member();
        member2.setEmail_address("jp17528");

        memberService.join(member1);
        memberService.join(member2);

        fail("exception should be occurred");   //the program should not be arrived at this line
    }

}
