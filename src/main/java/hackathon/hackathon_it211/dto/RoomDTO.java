package hackathon.hackathon_it211.dto;

import hackathon.hackathon_it211.entity.Room;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {

    @NotNull(message = "Số phòng không được để trống")
    private Integer roomNumber;

    @NotBlank(message = "Loại phòng không được để trống")
    private String roomType;

    @NotNull(message = "Giá không được để trống")
    @Positive(message = "Giá phải lớn hơn 0")
    private Long pricePerNight;

    private Room.RoomStatus status;
}
