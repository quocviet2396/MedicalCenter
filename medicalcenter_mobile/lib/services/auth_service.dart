import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:medicalcenter_mobile/models/user.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:medicalcenter_mobile/apis/endpoint.dart';

class AuthService {
  Future<dynamic> loginWebAccount(String email, String password) async {
    var headers = {'Content-Type': 'application/json'};
    var url = Uri.parse(Endpoint.signIn);
    Map body = {'username': email, 'password': password};

    final response =
        await http.post(url, headers: headers, body: jsonEncode(body));
    print(response.statusCode);

    if (response.statusCode == 200) {
      final json = jsonDecode(response.body);
      if (json.containsKey('accessToken')) {
        var fullName = json['fullName'];
        var email = json['email'];
        var accessToken = json['accessToken'];
        var id = json['id'];
        var firstName = json['firstName'];
        var lastName = json['lastName'];
        var address = json['address'];
        var phone = json['phone'];
        var state = json['state'];
        var country = json['country'];
        var postalCode = json['postalCode'];
        var dateOfBirth = json['dateOfBirth'];
        var gender = json['gender'];
        print('$fullName -- $email');
        print('accessToken: $accessToken');
        print('id: $id');
        print(json);

        final prefs = await SharedPreferences.getInstance();
        prefs.setString('token', accessToken);
        prefs.setString('fullName', fullName);
        prefs.setString('email', email);
        prefs.setInt('id', id);
        prefs.setString('firstName', firstName);
        prefs.setString('lastName', lastName);
        prefs.setString('address', address);
        prefs.setString('phone', phone);
        prefs.setString('state', state);
        prefs.setString('country', country);
        prefs.setString('postalCode', postalCode);
        prefs.setString('dateOfBirth', dateOfBirth);
        prefs.setString('gender', gender);
        return accessToken;
      }
      throw 'Sai con me no pass roi';
    } else {
      final errorJson = jsonDecode(response.body);
      final errorMessage = errorJson['message'] ?? 'Unknown error occurred';

      // In ra thông báo lỗi
      print('Error message: $errorMessage');

      throw errorMessage;
    }
  }

  Future<bool> isLoggedIn() async {
    final prefs = await SharedPreferences.getInstance();
    final id = prefs.getInt('id');
    print("Id khi dang nhap :  $id");
    if (id != null) {
      return true;
    } else {
      return false;
    } // Trả về true nếu có id, ngược lại trả về false
  }

  Future<void> logout() async {
    final prefs = await SharedPreferences.getInstance();
    final id = prefs.getInt('id');
    await prefs.remove('id');
    await prefs.remove('token');
    await prefs.remove('fullName');
    await prefs.remove('email');
    await prefs.remove('firstName');
    await prefs.remove('lastName');
    await prefs.remove('address');
    await prefs.remove('phone');
    await prefs.remove('state');
    await prefs.remove('country');
    await prefs.remove('postalCode');
    await prefs.remove('dateOfBirth');
    await prefs.remove('gender');
    print("Id khi dang xuat :  $id");
    // Thêm các lệnh xóa các thông tin khác liên quan đến người dùng nếu cần
  }

  Future<User> getLoggedInUser() async {
    final prefs = await SharedPreferences.getInstance();
    final fullName = prefs.getString('fullName');
    final email = prefs.getString('email');
    final id = prefs.getInt('id'); // Thêm nếu có id của người dùng
    final firstName = prefs.getString('firstName');
    final lastName = prefs.getString('lastName');
    final additionalAddress = prefs.getString('address');
    final phone = prefs.getString('phone');
    final state = prefs.getString('state');
    final country = prefs.getString('country');
    final postalCode = prefs.getString('postalCode');
    final dateOfBirth = prefs.getString('dateOfBirth');
    final gender = prefs.getString('gender');

    
    // Tạo một đối tượng Address với các giá trị phone, state, country, postalCode, và additionalAddress
    final address = Address(
      phone: phone,
      state: state,
      country: country,
      postalCode: postalCode,
      address: additionalAddress, // Gán giá trị cho trường address
    );
    // Trả về các giá trị từ SharedPreferences, không tạo ngay đối tượng User
    return User(
      id: id ?? 0, // Gán giá trị mặc định cho id nếu id không có giá trị
      email: email ?? '',
      fullName: fullName,
      firstName: firstName,
      lastName: lastName,
      address: address,
      dateOfBirth: dateOfBirth,
      gender: gender,
      // Thêm các trường thông tin khác nếu có
    );

    
  }

  Future<void> updateProfile(
      String firstName,
      String lastName,
      String? phone,
      String? address,
      String? state,
      String? country,
      String? postalCode,
      String? dateOfBirth,
      String? gender) async {
    final loggedInUser = await getLoggedInUser();
    final updatedAddress = Address(
      phone: phone,
      address: address,
      state: state,
      country: country,
      postalCode: postalCode,
    );

    final updatedUser = User(
      id: loggedInUser.id,
      email: loggedInUser.email,
      firstName: firstName,
      lastName: lastName,
      fullName: loggedInUser.fullName, // Cập nhật fullName
      gender: gender, // Thêm gender
      dateOfBirth: dateOfBirth, // Thêm dateOfBirth
      address: updatedAddress, // Cập nhật thông tin địa chỉ mới
    );

    final headers = {'Content-Type': 'application/json'};
    final url = Uri.parse(Endpoint.profile);

    final response = await http.post(
      url,
      headers: headers,
      body: jsonEncode(updatedUser.toJson()),
    );

    if (response.statusCode == 200) {
      print('Profile updated successfully');
    } else {
      throw Exception('Failed to update profile');
    }
  }

  Future<dynamic> registerUser(
      String email, String password, String lastName, String firstName) async {
    var headers = {'Content-Type': 'application/json'};
    var url = Uri.parse(Endpoint.signUp);
    Map<String, dynamic> body = {
      'email': email,
      'password': password,
      'firstName': firstName,
      'lastName': lastName
    };

    final response =
        await http.post(url, headers: headers, body: jsonEncode(body));
    print(response.statusCode);

    if (response.statusCode == 200) {
      final json = jsonDecode(response.body);
      print('json : $json');
      var message = json['message'];

      if (message == "User registered successfully!") {
        print('Registration successful');
        final prefs = await SharedPreferences.getInstance();
        prefs.setString('firstName', firstName);
        prefs.setString('lastName', lastName);
        prefs.setString('email', email);

        return message;
      } else {
        throw 'Registration failed: $message';
      }
    } else {
      final errorJson = jsonDecode(response.body);
      final errorMessage = errorJson['message'] ?? 'Unknown error occurred';

      // In ra thông báo lỗi
      print('Error message: $errorMessage');

      throw errorMessage;
    }
  }
}
