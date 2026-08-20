package controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import DTO.request.AddressRequestDTO;
import DTO.response.AddressResponseDTO;
import entity.Address;
import jakarta.validation.Valid;
import service.AddressService;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressResponseDTO>> getAddressesByUserId(@PathVariable("userId") long userId) {
        List<AddressResponseDTO> list = addressService.getAddressesByUserId(userId).stream()
                .map(AddressResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{addressId}/user/{userId}")
    public ResponseEntity<AddressResponseDTO> getAddressById(@PathVariable("addressId") long addressId, @PathVariable("userId") long userId) {
        Address address = addressService.getAddressById(addressId, userId);
        return ResponseEntity.ok(AddressResponseDTO.fromEntity(address));
    }

    @GetMapping("/user/{userId}/default")
    public ResponseEntity<AddressResponseDTO> getDefaultAddress(@PathVariable("userId") long userId) {
        Address address = addressService.getDefaultAddress(userId);
        return ResponseEntity.ok(AddressResponseDTO.fromEntity(address));
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<AddressResponseDTO> addAddress(
            @PathVariable("userId") long userId,
            @Valid @RequestBody AddressRequestDTO request) {

        Address address = addressService.addAddress(
                userId,
                request.getReceiverName(),
                request.getPhone(),
                request.getProvince(),
                request.getDistrict(),
                request.getWard(),
                request.getDetailAddress(),
                request.isDefault()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(AddressResponseDTO.fromEntity(address));
    }

    @PutMapping("/{addressId}/user/{userId}")
    public ResponseEntity<AddressResponseDTO> updateAddress(
            @PathVariable("addressId") long addressId,
            @PathVariable("userId") long userId,
            @Valid @RequestBody AddressRequestDTO request) {

        Address address = addressService.updateAddress(
                addressId,
                userId,
                request.getReceiverName(),
                request.getPhone(),
                request.getProvince(),
                request.getDistrict(),
                request.getWard(),
                request.getDetailAddress(),
                request.isDefault()
        );
        return ResponseEntity.ok(AddressResponseDTO.fromEntity(address));
    }

    @DeleteMapping("/{addressId}/user/{userId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable("addressId") long addressId, @PathVariable("userId") long userId) {
        addressService.deleteAddress(addressId, userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{addressId}/user/{userId}/default")
    public ResponseEntity<Map<String, String>> setDefaultAddress(@PathVariable("userId") long userId, @PathVariable("addressId") long addressId) {
        addressService.setDefaultAddress(userId, addressId);
        return ResponseEntity.ok(Map.of("message", "Đã thiết lập địa chỉ mặc định thành công"));
    }
}
