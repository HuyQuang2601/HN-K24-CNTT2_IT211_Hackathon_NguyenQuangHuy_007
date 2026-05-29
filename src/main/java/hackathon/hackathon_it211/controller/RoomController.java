package hackathon.hackathon_it211.controller;

import hackathon.hackathon_it211.dto.ApiResponse;
import hackathon.hackathon_it211.dto.RoomDTO;
import hackathon.hackathon_it211.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<ApiResponse<RoomDTO>> addRoom(@Valid @RequestBody RoomDTO roomDTO) {
        RoomDTO savedRoom = roomService.addRoom(roomDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Đã tạo phòng thành công", savedRoom));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoomDTO>>> getRooms(
            @RequestParam(required = false) Integer roomNumber,
            @RequestParam(required = false) String roomType) {
        List<RoomDTO> rooms = roomService.getRooms(roomNumber, roomType);
        return ResponseEntity.ok(ApiResponse.success("Đã lấy danh sách phòng thành công", rooms));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoomDTO>> getRoomById(@PathVariable Long id) {
        RoomDTO room = roomService.getRoomById(id);
        return ResponseEntity.ok(ApiResponse.success("Đã lấy thông tin phòng thành công", room));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RoomDTO>> updateRoom(
            @PathVariable Long id,
            @Valid @RequestBody RoomDTO roomDTO) {
        RoomDTO updatedRoom = roomService.updateRoom(id, roomDTO);
        return ResponseEntity.ok(ApiResponse.success("Đã cập nhật phòng thành công", updatedRoom));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<RoomDTO>> partialUpdateRoom(
            @PathVariable Long id,
            @RequestBody RoomDTO roomDTO) {
        RoomDTO updatedRoom = roomService.partialUpdateRoom(id, roomDTO);
        return ResponseEntity.ok(ApiResponse.success("Đã cập nhật phòng thành công", updatedRoom));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.ok(ApiResponse.success("Đã xóa phòng thành công"));
    }
}
