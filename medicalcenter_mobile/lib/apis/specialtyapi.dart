import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/specialty.dart';
import 'endpoint.dart';

class SpecialtyApi {
  Future<List<Specialty>> getSpecialties({String? nameSpecialty}) async {
    try {
      final uri = Uri.parse(Endpoint.specialties);
      final queryParameters = {'nameSpecialty': nameSpecialty};
      final response = await http.get(uri.replace(queryParameters: queryParameters));

      if (response.statusCode == 200) {
        List jsonResponse = json.decode(utf8.decode(response.bodyBytes));
        return jsonResponse.map((data) => Specialty.fromJson(data)).toList();
      } else {
        print('Failed to fetch Specialties: ${response.statusCode}');
        return [];
      }
    } catch (e) {
      print('Error in SpecialtyApi getSpecialties: $e');
      throw Exception('Failed to load Specialties');
    }
  }

  Future<List<Specialty>> searchSpecialtiesByNameSpecialty(String nameSpecialty) async {
    return await getSpecialties(nameSpecialty: nameSpecialty);
  }
}
