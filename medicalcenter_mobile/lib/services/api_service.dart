import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:medicalcenter_mobile/apis/endpoint.dart';
import 'package:medicalcenter_mobile/models/scheduleItem.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:medicalcenter_mobile/models/MedicalAndMedicineRecordDTO.dart';

class AppointmentService {
  Future<String> getToken() async {
    final prefs = await SharedPreferences.getInstance();
    final token = prefs.getString('token');
    if (token == null) {
      throw 'Token not found';
    }
    return token;
  }

  Future<List<dynamic>> getAppointments() async {
    try {
      final token = await getToken();

      final headers = {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': 'Bearer $token',
      };

      final response = await http.get(
        Uri.parse(Endpoint.appointment),
        headers: headers,
      );

      if (response.statusCode == 200) {
        final List<dynamic> fetchedAppointments = json.decode(utf8.decode(response.bodyBytes));
        return fetchedAppointments;
      } else {
        throw 'Failed to load appointments: ${response.statusCode}';
      }
    } catch (error) {
      throw 'Error loading appointments: $error';
    }
  }
Future<List<dynamic>> getFollowUp() async {
    try {
      final token = await getToken();

      final headers = {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': 'Bearer $token',
      };

      final response = await http.get(
        Uri.parse(Endpoint.listFolloUp),
        headers: headers,
      );

      if (response != null && response.statusCode == 200) {
        final List<dynamic> fetchedFollowUps = json.decode(utf8.decode(response.bodyBytes));
        return fetchedFollowUps;
      } else {
        throw 'Failed to load appointments: ${response?.statusCode}';
      }
    } catch (error) {
      throw 'Error loading appointments: $error';
    }
  }
  Future<void> createAppointment(Map<String, dynamic> appointmentData) async {
  try {
    final token = await getToken();
    final headers = {
      'Content-Type': 'application/json; charset=UTF-8',
      'Authorization': 'Bearer $token',
    };
    final response = await http.post(
      Uri.parse(Endpoint.NewAppointment),
      headers: headers,
      body: jsonEncode(appointmentData),
    );
    if (response.statusCode == 201) {
      print('Appointment created successfully!');
    } else {
      throw 'Failed to create appointment: ${response.statusCode}';
    }
  } catch (error) {
    throw 'Error creating appointment: $error';
  }
}


  static Future<List<ScheduleItem>> fetchScheduleItemList({
    String? workdateString,
    String? specialty,
  }) async {
    // Xây dựng URL với các tham số query nếu chúng được cung cấp
    final Uri url = Uri.parse(Endpoint.listSchedule).replace(queryParameters: {
      'workdateString': workdateString ?? '',
      'specialty': specialty ?? '',
    });

    // Gửi yêu cầu HTTP GET với URL đã được xây dựng
    final response = await http.get(url);

    if (response.statusCode == 200) {
      final List<dynamic> data = json.decode(utf8.decode(response.bodyBytes));
      final List<ScheduleItem> scheduleItems =
          data.map((itemJson) => ScheduleItem.fromJson(itemJson)).toList();
      return scheduleItems;
    } else {
      throw Exception('Failed to load schedule items');
    }
  }


  //medicalrecord
  Future<List<MedicalAndMedicineRecordDTO>> fetchMedicalRecords() async {
    try {
      final token = await getToken();

      final headers = {
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': 'Bearer $token',
      };

      final response = await http.get(
        Uri.parse(Endpoint.listm),
        headers: headers,
      );

      if (response.statusCode == 200) {
        final List<dynamic> data = json.decode(utf8.decode(response.bodyBytes));
        final List<MedicalAndMedicineRecordDTO> medicalRecords = data.map((itemJson) => MedicalAndMedicineRecordDTO.fromJson(itemJson)).toList();
        return medicalRecords;
      } else {
        throw 'Failed to load medical records: ${response.statusCode}';
      }
    } catch (error) {
      throw 'Error loading medical records: $error';
    }
  }
}
