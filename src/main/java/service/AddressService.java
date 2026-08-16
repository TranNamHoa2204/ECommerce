package service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import entity.Address;
import entity.User;
import respository.AddressRepository;
import respository.UserRepository;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    // Lấy tất cả địa chỉ của user
    public List<Address> getAddressesByUserId(long userId) {
        return addressRepository.findByUserUserId(userId);
    }

    // Lấy địa chỉ theo ID (kèm kiểm tra quyền sở hữu)
    public Address getAddressById(long addressId, long userId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy địa chỉ"));
        if (address.getUser().getUserId() != userId) {
            throw new RuntimeException("Bạn không có quyền truy cập địa chỉ này");
        }
        return address;
    }

    // Lấy địa chỉ mặc định
    public Address getDefaultAddress(long userId) {
        return addressRepository.findByUserUserIdAndIsDefaultTrue(userId).orElse(null);
    }

    // Thêm địa chỉ mới
    @Transactional
    public Address addAddress(long userId, String receiverName, String phone,
                              String province, String district, String ward,
                              String detailAddress, boolean isDefault) {
        if (receiverName == null || receiverName.isBlank()) {
            throw new RuntimeException("Tên người nhận không được để trống");
        }
        if (phone == null || !phone.matches("^0\\d{9}$")) {
            throw new RuntimeException("Số điện thoại không hợp lệ");
        }
        if (province == null || province.isBlank()) {
            throw new RuntimeException("Tỉnh/thành phố không được để trống");
        }
        if (district == null || district.isBlank()) {
            throw new RuntimeException("Quận/huyện không được để trống");
        }
        if (ward == null || ward.isBlank()) {
            throw new RuntimeException("Phường/xã không được để trống");
        }
        if (detailAddress == null || detailAddress.isBlank()) {
            throw new RuntimeException("Địa chỉ chi tiết không được để trống");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        if (isDefault) {
            addressRepository.clearDefaultByUserId(userId);
        }

        Address address = new Address(0, user, receiverName, phone, province, district, ward, detailAddress, isDefault);
        return addressRepository.save(address);
    }

    // Cập nhật địa chỉ
    @Transactional
    public Address updateAddress(long addressId, long userId, String receiverName, String phone,
                                 String province, String district, String ward,
                                 String detailAddress, boolean isDefault) {
        // Kiểm tra quyền sở hữu trước khi cập nhật
        Address address = getAddressById(addressId, userId);

        if (receiverName == null || receiverName.isBlank()) {
            throw new RuntimeException("Tên người nhận không được để trống");
        }
        if (phone == null || !phone.matches("^0\\d{9}$")) {
            throw new RuntimeException("Số điện thoại không hợp lệ");
        }
        if (province == null || province.isBlank()) {
            throw new RuntimeException("Tỉnh/thành phố không được để trống");
        }
        if (district == null || district.isBlank()) {
            throw new RuntimeException("Quận/huyện không được để trống");
        }
        if (ward == null || ward.isBlank()) {
            throw new RuntimeException("Phường/xã không được để trống");
        }
        if (detailAddress == null || detailAddress.isBlank()) {
            throw new RuntimeException("Địa chỉ chi tiết không được để trống");
        }

        if (isDefault) {
            addressRepository.clearDefaultByUserId(userId);
        }

        address.setReceiverName(receiverName);
        address.setPhone(phone);
        address.setProvince(province);
        address.setDistrict(district);
        address.setWard(ward);
        address.setDetailAddress(detailAddress);
        address.setDefault(isDefault);

        return addressRepository.save(address);
    }

    // Xóa địa chỉ
    @Transactional
    public void deleteAddress(long addressId, long userId) {
        // Kiểm tra quyền sở hữu trước khi xóa
        Address address = getAddressById(addressId, userId);
        addressRepository.delete(address);
    }

    // Đặt địa chỉ làm mặc định
    @Transactional
    public void setDefaultAddress(long userId, long addressId) {
        // Kiểm tra quyền sở hữu
        Address address = getAddressById(addressId, userId);
        addressRepository.clearDefaultByUserId(userId);
        address.setDefault(true);
        addressRepository.save(address);
    }
}
