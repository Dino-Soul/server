package dinosoul.snacktravelserver.domain.travelspot.controller;

import dinosoul.snacktravelserver.domain.travelspot.service.TravelSpotService;
import dinosoul.snacktravelserver.global.dto.ResponseDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/travlespot")
@RequiredArgsConstructor
public class travelSpotController {

    private final TravelSpotService travelSpotService;

    @GetMapping
    public ResponseDataDto<?> getTravelSpot() {
        return travelSpotService.getTravelSpot();
    }

}
