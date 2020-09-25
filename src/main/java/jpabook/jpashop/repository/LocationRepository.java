package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Location;
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
public class LocationRepository {

    private final EntityManager em;

    public void save(Location location){
        if(location.getId() == null){
            em.persist(location);
        } else {
            em.merge(location); //similar to update
        }
    }

    public Location findOne(Long id){
        return em.find(Location.class, id);
    }

    public List<Location> findAll(){
        return em.createQuery("select i from Location i", Location.class)
                .getResultList();
    }

    public List<Location> findByName(String name){
        return em.createQuery("select i from Location i where i.name = :name", Location.class)
                .setParameter("name", name)
                .getResultList();
    }

    public int deleteLocation(Long id) {
        Query query = em.createQuery("delete from Location l where l.id = :id").setParameter("id", id);
        int records = query.executeUpdate();
        return records;
    }

    public List<Location> findLocationByCriteria(LocationSearch locationSearch){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Location> cq = cb.createQuery(Location.class);
        Root<Location> location = cq.from(Location.class);
        List<Predicate> criteria = new ArrayList<>();

        if (StringUtils.hasText(locationSearch.getLocationName())) {
            Predicate namePredicate = cb.like(location.get("name"), "%" + locationSearch.getLocationName() + "%");
            criteria.add(namePredicate);
        }

        //주문 상태 검색
        if (locationSearch.getRoomType() != null) {
            Predicate status = cb.equal(location.get("roomType"), locationSearch.getRoomType());
            criteria.add(status);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Location> query = em.createQuery(cq).setMaxResults(10000); //최대 1000건
        return query.getResultList();
    }
}
