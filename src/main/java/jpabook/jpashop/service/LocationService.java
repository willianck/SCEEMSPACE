package jpabook.jpashop.service;

import jpabook.jpashop.domain.Location;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.RoomType;
import jpabook.jpashop.repository.LocationRepository;
import jpabook.jpashop.repository.LocationSearch;
import jpabook.jpashop.repository.MemberSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    @Transactional(readOnly = false)
    public Long saveLocation(Location location){
        validateDuplicateLocation(location);
        locationRepository.save(location);
        return location.getId();
    }

    private void validateDuplicateLocation(Location location) {
        //EXCEPTION
        List<Location> findLocations = locationRepository.findByName(location.getName());
        if(!findLocations.isEmpty()){
            throw new IllegalStateException("Already an existing location!");
        }
    }

    @Transactional
    public void updateItem(Long itemId, String name, RoomType roomType, int stockQuantity){
        Location findLocation = locationRepository.findOne(itemId);
        findLocation.setName(name);
        findLocation.setRoomType(roomType);
        findLocation.setStockQuantity(stockQuantity);
    }

    public List<Location> findLocations(){
        return locationRepository.findAll();
    }

    public Location findOne(Long locationId){
        return locationRepository.findOne(locationId);
    }

    @Transactional
    public int deleteLocation(Long locationId) {
        return locationRepository.deleteLocation(locationId);
    }

    public List<Location> findOneLocation(LocationSearch locationSearch){
        return locationRepository.findLocationByCriteria(locationSearch);
    }
}
