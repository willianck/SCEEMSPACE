package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    //member 저장
    public void save(Member member){ em.persist(member); }

    //member 한명 검색
    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    //member 여러명 검색
    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    //이름으로 찾기
    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<Member> findByEmailAddress(String email_address){
        return em.createQuery("select m from Member m where m.email_address = :email_address", Member.class)
                .setParameter("email_address", email_address)
                .getResultList();
    }

    public int deleteMember(Long id) {
        Query query = em.createQuery("delete from Member m where m.id = :id").setParameter("id", id);
        int records = query.executeUpdate();
        return records;
    }

    public List<Member> findMemberByCriteria(MemberSearch memberSearch){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> cq = cb.createQuery(Member.class);
        Root<Member> member = cq.from(Member.class);
        List<Predicate> criteria = new ArrayList<>();

        if (StringUtils.hasText(memberSearch.getMemberName())) {
            Predicate namePredicate = cb.like(member.get("name"), "%" + memberSearch.getMemberName() + "%");
            criteria.add(namePredicate);
        }

        if (StringUtils.hasText(memberSearch.getMemberPriority())) {
            Predicate namePredicate = cb.like(member.get("priority"), "%" + memberSearch.getMemberPriority() + "%");
            criteria.add(namePredicate);
        }

        if (StringUtils.hasText(memberSearch.getMemberDepartment())) {
            Predicate namePredicate = cb.like(member.get("department"), "%" + memberSearch.getMemberDepartment() + "%");
            criteria.add(namePredicate);
        }

        if (StringUtils.hasText(memberSearch.getMemberPathway())) {
            Predicate namePredicate = cb.like(member.get("pathway"), "%" + memberSearch.getMemberPathway() + "%");
            criteria.add(namePredicate);
        }

        if (StringUtils.hasText(memberSearch.getMemberGroup())) {
            Predicate namePredicate = cb.like(member.get("groupAllocation"), "%" + memberSearch.getMemberGroup() + "%");
            criteria.add(namePredicate);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Member> query = em.createQuery(cq).setMaxResults(10000); //최대 1000건
        return query.getResultList();

    }
}
