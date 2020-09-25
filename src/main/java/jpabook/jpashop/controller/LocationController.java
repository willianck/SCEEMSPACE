package jpabook.jpashop.controller;


import jpabook.jpashop.domain.Location;
import jpabook.jpashop.repository.LocationSearch;
import jpabook.jpashop.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Comparator;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @GetMapping("/locations/new")
    public String createForm(Model model) {
        model.addAttribute("form", new LocationForm());
        return "locations/createLocationForm";

    }

    @PostMapping("/locations/new")
    public String create(LocationForm form) {

        Location location = new Location();
        location.setName(form.getName());
        location.setRoomType(form.getRoomType());
        location.setStockQuantity(form.getStockQuantity());

        locationService.saveLocation(location);
        return "redirect:/";
    }

    @GetMapping("/locations")
    public String list(@ModelAttribute("locationSearch") LocationSearch locationSearch, Model model) {
        List<Location> locations = locationService.findLocations();
        List<Location> findLocationByCriteria = locationService.findOneLocation(locationSearch);

        model.addAttribute("locations", locations);
        model.addAttribute("locations", findLocationByCriteria);
        model.addAttribute("byLocationStockQuantity", Comparator.comparing(Location::getStockQuantity));
        return "locations/locationList";
    }

    @GetMapping("locations/{locationId}/edit")
    public String updateLocationForm(@PathVariable("locationId") Long locationId, Model model) {
        Location location = (Location) locationService.findOne(locationId);
        LocationForm locationForm = new LocationForm();
        locationForm.setId(location.getId());
        locationForm.setName(location.getName());
        locationForm.setRoomType(location.getRoomType());
        locationForm.setStockQuantity(location.getStockQuantity());

        model.addAttribute("locationForm", locationForm);
        return "locations/updateLocationForm";
    }

    @PostMapping("locations/{locationId}/edit")
    public String updateLocation(@PathVariable Long locationId, @ModelAttribute("locationForm") LocationForm locationForm) {

        locationService.updateItem(locationId, locationForm.getName(), locationForm.getRoomType(), locationForm.getStockQuantity());

        return "redirect:/locations";
    }

    @GetMapping("locations/{locationId}/delete")
    public String deleteLocation(@PathVariable Long locationId) {
        locationService.deleteLocation(locationId);

        return "redirect:/locations";
    }
}
