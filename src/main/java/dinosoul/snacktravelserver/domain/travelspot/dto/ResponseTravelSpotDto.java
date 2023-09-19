package dinosoul.snacktravelserver.domain.travelspot.dto;

import lombok.*;

import static lombok.AccessLevel.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class ResponseTravelSpotDto {

    private String spotName;
    private String region;
    private String city;
    private String location;
    private String mainCategory;
    private String subCategory;
    private Double latitude;
    private Double longitude;

}
