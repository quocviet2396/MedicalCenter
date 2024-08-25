import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/user.dart';
import 'endpoint.dart';

class ProfileApi {
  // Phương thức để cập nhật thông tin hồ sơ người dùng
  Future<User> updateProfile(User user) async {
    var headers = {'Content-Type': 'application/json'};
    var url = Uri.parse(Endpoint.profile);

    try {
      final response = await http.post(
        url,
        headers: headers,
        body: jsonEncode(user.toJson()), // Chuyển đổi đối tượng user thành chuỗi JSON
      );

      if (response.statusCode == 200) {
        // Nếu yêu cầu thành công, cập nhật thông tin người dùng và trả về
        final updatedUser = User.fromJson(jsonDecode(response.body));
        return updatedUser;
      } else {
        // Nếu không thành công, ném ra một ngoại lệ
        throw Exception('Failed to update profile');
      }
    } catch (e) {
      // Bắt bất kỳ ngoại lệ nào xảy ra trong quá trình gửi yêu cầu HTTP
      throw Exception('Failed to connect to the server: $e');
    }
  }

  // Phương thức để lấy thông tin hồ sơ người dùng
  Future<User> getProfile(int userId) async {
    var headers = {'Content-Type': 'application/json'};
    var url = Uri.parse('${Endpoint.profile}/$userId');

    try {
      final response = await http.get(
        url,
        headers: headers,
      );

      if (response.statusCode == 200) {
        // Nếu yêu cầu thành công, chuyển đổi dữ liệu JSON nhận được thành đối tượng người dùng và trả về
        final user = User.fromJson(jsonDecode(response.body));
        return user;
      } else {
        // Nếu không thành công, ném ra một ngoại lệ
        throw Exception('Failed to get profile');
      }
    } catch (e) {
      // Bắt bất kỳ ngoại lệ nào xảy ra trong quá trình gửi yêu cầu HTTP
      throw Exception('Failed to connect to the server: $e');
    }
  }
}
