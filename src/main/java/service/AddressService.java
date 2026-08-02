package service;

import java.util.List;

import dao.AddressDao;
import entity.Address;
import entity.User;



public class AddressService {
    private final AddressDao addressDao = new AddressDao();

    // Lấy tất cả địa chỉ của user
    public List<Address> getAddressesByUserId(long userId) {
        return addressDao.getAddressesByUserId(userId);
    }

    // Lấy địa chỉ theo ID (kèm kiểm tra quyền sở hữu)
    public Address getAddressById(long addressId, long userId) {
        Address address = addressDao.getAddressById(addressId);
        if (address == null) {
            throw new RuntimeException("Không tìm thấy địa chỉ");
        }
        if (address.getUser().getUserId() != userId) {
            throw new RuntimeException("Bạn không có quyền truy cập địa chỉ này");
        }
        return address;
    }

    // Lấy địa chỉ mặc định
    public Address getDefaultAddress(long userId) {
        return addressDao.getDefaultAddress(userId);
    }

    // Thêm địa chỉ mới
    public void addAddress(long userId, String receiverName, String phone,
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

        User user = new User();
        user.setUserId(userId);

        Address address = new Address(0, user, receiverName, phone, province, district, ward, detailAddress, isDefault);
        addressDao.insertAddress(address);
    }

    // Cập nhật địa chỉ
    public void updateAddress(long addressId, long userId, String receiverName, String phone,
                              String province, String district, String ward,
                              String detailAddress, boolean isDefault) {
        // Kiểm tra quyền sở hữu trước khi cập nhật
        getAddressById(addressId, userId);

        if (receiverName == null || receiverName.isBlank()) {
            throw new RuntimeException("Tên người nhận không được để trống");
        }
        if (phone == null || !phone.matches("^0\\d{9}$")) {
            throw new RuntimeException("Số điện thoại không hợp lệ");
        }

        User user = new User();
        user.setUserId(userId);

        Address address = new Address(addressId, user, receiverName, phone, province, district, ward, detailAddress, isDefault);
        addressDao.updateAddress(address);
    }

    // Xóa địa chỉ
    public void deleteAddress(long addressId, long userId) {
        // Kiểm tra quyền sở hữu trước khi xóa
        getAddressById(addressId, userId);
        addressDao.deleteAddress(addressId);
    }

    // Đặt địa chỉ làm mặc định
    public void setDefaultAddress(long userId, long addressId) {
        // Kiểm tra quyền sở hữu
        getAddressById(addressId, userId);
        boolean success = addressDao.setDefaultAddress(userId, addressId);
        if (!success) {
            throw new RuntimeException("Không thể đặt địa chỉ mặc định");
        }
    }
}
