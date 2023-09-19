package dinosoul.snacktravelserver.domain.travelspot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class TravelSpot {

    @Id
    @Column(name = "travel_spot_Id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String spotName;
    private String region;
    private String city;
    private String location;
    private String mainCategory;
    private String subCategory;
    private Double latitude;
    private Double longitude;

}
