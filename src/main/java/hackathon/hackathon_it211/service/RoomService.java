package hackathon.hackathon_it211.service;

import hackathon.hackathon_it211.dto.RoomDTO;
import hackathon.hackathon_it211.entity.Room;
import hackathon.hackathon_it211.exception.ResourceNotFoundException;
import hackathon.hackathon_it211.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    @Transactional
    public RoomDTO addRoom(RoomDTO roomDTO) {
        Room room = convertToEntity(roomDTO);
        room.setStatus(Room.RoomStatus.AVAILABLE);
        Room savedRoom = roomRepository.save(room);
        return convertToDTO(savedRoom);
    }

    public List<RoomDTO> getRooms(Integer roomNumber, String roomType) {
        List<Room> rooms;
        if (roomNumber != null || roomType != null) {
            rooms = roomRepository.searchRooms(roomNumber, roomType);
        } else {
            rooms = roomRepository.findAllActiveRooms();
        }
        return rooms.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public RoomDTO getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phòng với id: " + id));
        if (room.getIsDeleted()) {
            throw new ResourceNotFoundException("Không tìm thấy phòng với id: " + id);
        }
        return convertToDTO(room);
    }

    @Transactional
    public RoomDTO updateRoom(Long id, RoomDTO roomDTO) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phòng với id: " + id));
        if (room.getIsDeleted()) {
            throw new ResourceNotFoundException("Không tìm thấy phòng với id: " + id);
        }

        room.setRoomNumber(roomDTO.getRoomNumber());
        room.setRoomType(roomDTO.getRoomType());
        room.setPricePerNight(roomDTO.getPricePerNight());
        if (roomDTO.getStatus() != null) {
            room.setStatus(roomDTO.getStatus());
        }

        Room updatedRoom = roomRepository.save(room);
        return convertToDTO(updatedRoom);
    }

    @Transactional
    public RoomDTO partialUpdateRoom(Long id, RoomDTO roomDTO) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phòng với id: " + id));
        if (room.getIsDeleted()) {
            throw new ResourceNotFoundException("Không tìm thấy phòng với id: " + id);
        }

        if (roomDTO.getRoomNumber() != null) {
            room.setRoomNumber(roomDTO.getRoomNumber());
        }
        if (roomDTO.getRoomType() != null) {
            room.setRoomType(roomDTO.getRoomType());
        }
        if (roomDTO.getPricePerNight() != null) {
            room.setPricePerNight(roomDTO.getPricePerNight());
        }
        if (roomDTO.getStatus() != null) {
            room.setStatus(roomDTO.getStatus());
        }

        Room updatedRoom = roomRepository.save(room);
        return convertToDTO(updatedRoom);
    }

    @Transactional
    public void deleteRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phòng với id: " + id));
        room.setIsDeleted(true);
        roomRepository.save(room);
    }

    private RoomDTO convertToDTO(Room room) {
        return new RoomDTO(
                room.getRoomNumber(),
                room.getRoomType(),
                room.getPricePerNight(),
                room.getStatus()
        );
    }

    private Room convertToEntity(RoomDTO roomDTO) {
        return new Room(
                null,
                roomDTO.getRoomNumber(),
                roomDTO.getRoomType(),
                roomDTO.getPricePerNight(),
                roomDTO.getStatus() != null ? roomDTO.getStatus() : Room.RoomStatus.AVAILABLE,
                false
        );
    }
}
