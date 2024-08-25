class Doctor {
  final int id;
  final DateTime createOn;
  final DateTime updateOn;
  final String email;
  final String fullName;
  final String photo;
  final Address address;
  final bool enabled;
  final bool changePassword;
  final String? resetPasswordToken;
  final List<Role> roles;
  final Specialty specialty;
  final String informationBackground;
  final String photosImagePath;

  Doctor({
    required this.id,
    required this.createOn,
    required this.updateOn,
    required this.email,
    required this.fullName,
    required this.photo,
    required this.address,
    required this.enabled,
    required this.changePassword,
    required this.resetPasswordToken,
    required this.roles,
    required this.specialty,
    required this.informationBackground,
    required this.photosImagePath,
  });

  factory Doctor.fromJson(Map<String, dynamic> json) {
    return Doctor(
      id: json['id'] ?? 0,
      createOn: DateTime.parse(json['createOn'] ?? ''),
      updateOn: DateTime.parse(json['updateOn'] ?? ''),
      email: json['email'] ?? '',
      fullName: json['fullName'] ?? '',
      photo: json['photo'] ?? '',
      address: Address.fromJson(json['address'] ?? {}),
      enabled: json['enabled'] ?? false,
      changePassword: json['changePassword'] ?? false,
      resetPasswordToken: json['resetPasswordToken'],
      roles: List<Role>.from((json['roles'] ?? []).map((role) => Role.fromJson(role))),
      specialty: Specialty.fromJson(json['specialty'] ?? {}),
      informationBackground: json['informationBackground'] ?? '',
      photosImagePath: json['photosImagePath'] ?? '',
    );
  }
}

class Address {
  final String address;
  final String postalCode;
  final String country;
  final String state;
  final String phone;

  Address({
    required this.address,
    required this.postalCode,
    required this.country,
    required this.state,
    required this.phone,
  });

  factory Address.fromJson(Map<String, dynamic> json) {
    return Address(
      address: json['address'] ?? '',
      postalCode: json['postalCode'] ?? '',
      country: json['country'] ?? '',
      state: json['state'] ?? '',
      phone: json['phone'] ?? '',
    );
  }
}

class Role {
  final int id;
  final DateTime? createOn;
  final DateTime? updateOn;
  final String name;
  final String description;

  Role({
    required this.id,
    this.createOn,
    this.updateOn,
    required this.name,
    required this.description,
  });

  factory Role.fromJson(Map<String, dynamic> json) {
    return Role(
      id: json['id'] ?? 0,
      createOn: json['createOn'] != null ? DateTime.parse(json['createOn']) : null,
      updateOn: json['updateOn'] != null ? DateTime.parse(json['updateOn']) : null,
      name: json['name'] ?? '',
      description: json['description'] ?? '',
    );
  }
}

class Specialty {
  final int id;
  final DateTime createOn;
  final DateTime updateOn;
  final String nameSpecialty;
  final String description;

  Specialty({
    required this.id,
    required this.createOn,
    required this.updateOn,
    required this.nameSpecialty,
    required this.description,
  });

  factory Specialty.fromJson(Map<String, dynamic> json) {
    return Specialty(
      id: json['id'] ?? 0,
      createOn: DateTime.parse(json['createOn'] ?? ''),
      updateOn: DateTime.parse(json['updateOn'] ?? ''),
      nameSpecialty: json['nameSpecialty'] ?? '',
      description: json['description'] ?? '',
    );
  }
}

class ScheduleItem {
  final int id;
  final DateTime createOn;
  final DateTime updateOn;
  final Doctor doctor;
  final DateTime workdates;
  final String room;
  final String appointmentPeriod;
  final int count;

  ScheduleItem({
    required this.id,
    required this.createOn,
    required this.updateOn,
    required this.doctor,
    required this.workdates,
    required this.room,
    required this.appointmentPeriod,
    required this.count,
  });

  factory ScheduleItem.fromJson(Map<String, dynamic> json) {
    return ScheduleItem(
      id: json['id'] ?? 0,
      createOn: DateTime.parse(json['createOn'] ?? ''),
      updateOn: DateTime.parse(json['updateOn'] ?? ''),
      doctor: Doctor.fromJson(json['doctor'] ?? {}),
      workdates: DateTime.parse(json['workdates'] ?? ''),
      room: json['room'] ?? '',
      appointmentPeriod: json['appointmentPeriod'] ?? '',
      count: int.parse(json['count'] ?? '0'),
    );
  }
}

