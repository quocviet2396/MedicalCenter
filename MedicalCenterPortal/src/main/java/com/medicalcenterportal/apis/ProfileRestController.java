package com.medicalcenterportal.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicalcenter.entities.Address;
import com.medicalcenter.entities.User;
import com.medicalcenter.exceptions.UserNotFoundException;
import com.medicalcenter.services.UserService;

@RestController
@RequestMapping({ "/apis/v1/profile", "/apis/test/profile" })
public class ProfileRestController {

	@Autowired
	private UserService userService;

	@PostMapping
	public User updateProfile(@RequestBody User user) throws UserNotFoundException {
		// Kiểm tra xem người dùng có tồn tại trong cơ sở dữ liệu hay không
		User existingUser = userService.get(user.getId());

		// Cập nhật thông tin hồ sơ từ dữ liệu được gửi trong yêu cầu
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setDateOfBirth(user.getDateOfBirth());
		existingUser.setGender(user.getGender());
		Address address = user.getAddress();
	    if (address != null) {
	        // Cập nhật thông tin địa chỉ nếu có
	        if (existingUser.getAddress() != null) {
	            existingUser.getAddress().setAddress(address.getAddress());
	            existingUser.getAddress().setCountry(address.getCountry());
	            existingUser.getAddress().setPhone(address.getPhone());
	            existingUser.getAddress().setPostalCode(address.getPostalCode());
	            existingUser.getAddress().setState(address.getState());
	        } else {
	            // Tạo một đối tượng Address mới nếu chưa tồn tại
	            Address newAddress = new Address();
	            newAddress.setAddress(address.getAddress());
	            newAddress.setCountry(address.getCountry());
	            newAddress.setPhone(address.getPhone());
	            newAddress.setPostalCode(address.getPostalCode());
	            newAddress.setState(address.getState());
	            existingUser.setAddress(newAddress);
	        }
	    }
		// Cập nhật các thông tin khác tương tự
	    // Loại bỏ mật khẩu nếu nó là null

		// Lưu thông tin hồ sơ đã cập nhật vào cơ sở dữ liệu
		User updatedUser = userService.updateAccountFlutter(existingUser);

		// Trả về thông tin người dùng đã được cập nhật
		return updatedUser;
	}
}
