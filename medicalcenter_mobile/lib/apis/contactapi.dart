import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/contact.dart';
import 'endpoint.dart';

class ContactApi {
  Future<Contact> createContact(Contact contact) async {
    var headers = {'Content-Type': 'application/json'};
    var url = Uri.parse(Endpoint.contact);
    Map<String, dynamic> body = {
      'name': contact.name,
      'title': contact.title,
      'email': contact.email,
      'phone': contact.phone,
      'description': contact.description,
    };

    final response =
        await http.post(url, headers: headers, body: jsonEncode(body));
    print(response.statusCode);

    if (response.statusCode == 200) {
      // Yêu cầu thành công, trả về đối tượng Contact đã được tạo
      // return Contact.fromJson(jsonDecode(response.body));
      final json = jsonDecode(response.body);
      print('json : $json');
      // Tạo đối tượng Contact từ dữ liệu JSON đã nhận được
      Contact contact = Contact.fromJson(json);
      return contact;
    } else {
      final errorJson = jsonDecode(response.body);
      final errorMessage = errorJson['message'] ?? 'Unknown error occurred';

      // In ra thông báo lỗi
      print('Error message: $errorMessage');

      throw Exception(errorMessage);
    }
  }
}
