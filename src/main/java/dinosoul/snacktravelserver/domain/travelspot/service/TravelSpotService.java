package dinosoul.snacktravelserver.domain.travelspot.service;

import dinosoul.snacktravelserver.domain.travelspot.dto.ResponseTravelSpotDto;
import dinosoul.snacktravelserver.domain.travelspot.entity.TravelSpot;
import dinosoul.snacktravelserver.domain.travelspot.repository.TravelSpotRepository;
import dinosoul.snacktravelserver.global.dto.ResponseDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelSpotService {

    private final TravelSpotRepository travelSpotRepository;

    public ResponseDataDto<?> getTravelSpot() {
        List<TravelSpot> findTravelSpot = travelSpotRepository.findAll();

        List<ResponseTravelSpotDto> result = findTravelSpot.stream()
                .map(spot -> ResponseTravelSpotDto.builder()
                        .spotName(spot.getSpotName())
                        .region(spot.getRegion())
                        .city(spot.getCity())
                        .location(spot.getLocation())
                        .mainCategory(spot.getMainCategory())
                        .subCategory(spot.getSubCategory())
                        .latitude(spot.getLatitude())
                        .longitude(spot.getLongitude())
                        .build())
                .toList();

        System.out.println(result.get(0).getSpotName());

        return new ResponseDataDto<>(result);
    }

}
