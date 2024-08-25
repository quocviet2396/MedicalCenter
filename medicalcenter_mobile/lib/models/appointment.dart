import 'package:flutter/foundation.dart';

class User {
  final int id;
  final String email;
  final String firstName;
  final String lastName;
  final String fullName;
  // Thêm các trường dữ liệu khác nếu cần

  User({
    required this.id,
    required this.email,
    required this.firstName,
    required this.lastName,
    required this.fullName,
    // Thêm các trường dữ liệu khác nếu cần
  });

  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      id: json['id'],
      email: json['email'],
      firstName: json['firstName'],
      lastName: json['lastName'],
      fullName: json['fullName'],
      // Thêm các trường dữ liệu khác nếu cần
    );
  }
}

class Doctor {
  final int id;
  final String email;
  final String firstName;
  final String lastName;
  final String fullName;
  final String? photo;
  // Thêm các trường dữ liệu khác nếu cần

  Doctor({
    required this.photo,
    required this.id,
    required this.email,
    required this.firstName,
    required this.lastName,
    required this.fullName,
    // Thêm các trường dữ liệu khác nếu cần
  });

  factory Doctor.fromJson(Map<String, dynamic> json) {
    return Doctor(
      id: json['id'],
      email: json['email'],
      firstName: json['firstName'],
      lastName: json['lastName'],
      fullName: json['fullName'], 
      photo: json['photo'],
      // Thêm các trường dữ liệu khác nếu cần
    );
  }
}

class Specialty {
  final int id;
  final String nameSpecialty;
  final String description;
  // Thêm các trường dữ liệu khác nếu cần

  Specialty({
    required this.id,
    required this.nameSpecialty,
    required this.description,
    // Thêm các trường dữ liệu khác nếu cần
  });

  factory Specialty.fromJson(Map<String, dynamic> json) {
    return Specialty(
      id: json['id'],
      nameSpecialty: json['nameSpecialty'],
      description: json['description'],
      // Thêm các trường dữ liệu khác nếu cần
    );
  }
}

class Appointment {
  final int id;
  final DateTime createOn;
  final DateTime updateOn;
  final User patientUser;
  final Doctor doctor;
  final DateTime appointmentDate;
  final String appointmentPeriod;
  final Specialty specialties;
  final String? arrived;
  final String symptoms;
  // Thêm các trường dữ liệu khác nếu cần

  Appointment({
    required this.id,
    required this.createOn,
    required this.updateOn,
    required this.patientUser,
    required this.doctor,
    required this.appointmentDate,
    required this.appointmentPeriod,
    required this.specialties,
    this.arrived,
    required this.symptoms,
    // Thêm các trường dữ liệu khác nếu cần
  });

 factory Appointment.fromJson(Map<String, dynamic> json) {
  
  return Appointment(
    id: json['id'],
    createOn: DateTime.parse(json['createOn']),
    updateOn: DateTime.parse(json['updateOn']),
    patientUser: User.fromJson(json['patientUser']),
 doctor: json['doctor'] != null ? Doctor.fromJson(json['doctor']) : Doctor(id: 0, email: '', firstName: '', lastName: '', fullName: '',photo: ''), // Kiểm tra nếu 'doctor' không null
    appointmentDate: DateTime.parse(json['appointmentDate']),
    appointmentPeriod: convertToEnglish(json['appointmentPeriod'] ?? ''),
    specialties: Specialty.fromJson(json['specialties']),
    arrived: json['arrived'],
    symptoms: json['symptoms'],
    // Thêm các trường dữ liệu khác nếu cần
  );
}
static String convertToEnglish(String vietnameseText) {
    switch (vietnameseText) {
      case 'BUỔI_SÁNG':
        return 'morning';
      // Thêm các trường hợp cho các giá trị tiếng Việt khác nếu cần
      default:
        return vietnameseText; // Trả về giá trị gốc nếu không có trường hợp nào khớp
    }
  }

}
