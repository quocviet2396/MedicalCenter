class Specialty {
  final int id;
  final DateTime createOn;
  final DateTime updateOn;
  final String nameSpecialty;
  final String description;
  final String? photo;
  final List<dynamic> appointmentNoLogins;
  final List<dynamic> appointments;
  final List<dynamic> followUpAppointments;
  final List<dynamic> followUpNoLogins;

  Specialty({
    required this.id,
    required this.createOn,
    required this.updateOn,
    required this.nameSpecialty,
    required this.description,
    this.photo,
    required this.appointmentNoLogins,
    required this.appointments,
    required this.followUpAppointments,
    required this.followUpNoLogins,
  });

  factory Specialty.fromJson(Map<String, dynamic> json) {
    return Specialty(
      id: json['id'] as int,
      createOn: DateTime.parse(json['createOn'] as String),
      updateOn: DateTime.parse(json['updateOn'] as String),
      nameSpecialty: json['nameSpecialty'] as String,
      description: json['description'] as String,
      photo: json['photo'] as String?,
      appointmentNoLogins: json['appointmentnologins'] as List<dynamic>,
      appointments: json['appointments'] as List<dynamic>,
      followUpAppointments: json['followUpAppointments'] as List<dynamic>,
      followUpNoLogins: json['followUpNoLogins'] as List<dynamic>,
    );
  }
}
