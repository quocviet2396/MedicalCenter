import 'dart:convert';

import 'package:flutter/foundation.dart';

class FollowUpAppointment {
  final int id;
  final DateTime createOn;
  final DateTime updateOn;
  final PatientUser patientUser;
  final DoctorUser? doctorUser;
  final Specialty special;
  final DateTime appointmentDate;
  final String appointmentPeriod;
  final String? description;

  FollowUpAppointment( {
    required this.id,
    required this.createOn,
    required this.updateOn,
    required this.patientUser,
    required this.doctorUser, // Change `doctor` to `doctorUser`
    required this.special,
    required this.appointmentDate,
    required this.appointmentPeriod,
    this.description,
  });

  factory FollowUpAppointment.fromJson(Map<String, dynamic> json) {
    return FollowUpAppointment(
      id: json['id'],
      createOn: DateTime.parse(json['createOn']),
      updateOn: DateTime.parse(json['updateOn']),
      patientUser: PatientUser.fromJson(json['patientUser']),
       doctorUser: json['doctorUser'] != null ? DoctorUser.fromJson(json['doctorUser']) : DoctorUser(id: 0, email: '', firstName: '', lastName: '', fullName: '', photosImagePath: '', photo: ''),
      special: Specialty.fromJson(json['special']),
      appointmentDate: DateTime.parse(json['appointmentDate']),
      appointmentPeriod: json['appointmentPeriod'],
      description: json['description']
    );
  }
}

class PatientUser {
  final int id;
  final String email;
  final String firstName;
  final String lastName;
  final String fullName;
  final String photosImagePath;

  PatientUser({
    required this.id,
    required this.email,
    required this.firstName,
    required this.lastName,
    required this.fullName,
    required this.photosImagePath,
  });

  factory PatientUser.fromJson(Map<String, dynamic> json) {
    return PatientUser(
      id: json['id'],
      email: json['email'],
      firstName: json['firstName'],
      lastName: json['lastName'],
      fullName: json['fullName'],
      photosImagePath: json['photosImagePath'],
    );
  }
}

class DoctorUser {
  final int id;
  final String email;
  final String firstName;
  final String lastName;
  final String fullName;
  final String photosImagePath;
  final String? photo;

  DoctorUser({
    required this.id,
    required this.email,
    required this.firstName,
    required this.lastName,
    required this.fullName,
    required this.photosImagePath,
     this.photo,
  });

  factory DoctorUser.fromJson(Map<String, dynamic> json) {
    return DoctorUser(
      id: json['id'],
      email: json['email'],
      firstName: json['firstName'],
      lastName: json['lastName'],
      fullName: json['fullName'],
      photosImagePath: json['photosImagePath'],
      photo: json['photo'],
    );
  }
}

class Specialty {
  final int id;
  final String nameSpecialty;
  final String description;

  Specialty({
    required this.id,
    required this.nameSpecialty,
    required this.description,
  });

  factory Specialty.fromJson(Map<String, dynamic> json) {
    return Specialty(
      id: json['id'],
      nameSpecialty: json['nameSpecialty'],
      description: json['description'],
    );
  }
}
