import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/doctorinfo.dart';
import 'endpoint.dart';

class DoctorInfoApi {
  Future<List<DoctorInfo>> getDoctorInfos({String? title}) async {
    try {
      final uri = Uri.parse(Endpoint.doctorInfo);
      final queryParameters = {'title': title};
      final response = await http.get(uri.replace(queryParameters: queryParameters));

      if (response.statusCode == 200) {
        List jsonResponse = json.decode(utf8.decode(response.bodyBytes));
        return jsonResponse.map((data) => DoctorInfo.fromJson(data)).toList();
      } else {
        print('Failed to fetch doctorinfos: ${response.statusCode}');
        return [];
      }
    } catch (e) {
      print('Error in doctorinfoApi getdoctorinfos: $e');
      throw Exception('Failed to load doctorinfos');
    }
  }

  Future<List<DoctorInfo>> searchDoctorInfosByTitle(String title) async {
    return await getDoctorInfos(title: title);
  }
}