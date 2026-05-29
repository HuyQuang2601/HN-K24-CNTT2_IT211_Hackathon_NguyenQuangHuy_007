package hackathon.hackathon_it211.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer roomNumber;

    @Column(nullable = false)
    private String roomType;

    @Column(nullable = false)
    private Long pricePerNight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomStatus status;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    public enum RoomStatus {
        AVAILABLE,
        BOOKED
    }
}

