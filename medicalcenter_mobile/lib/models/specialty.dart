class Specialty {
  final int id;
  final String nameSpecialty;
  final String description;
  final String photo;

  Specialty(
      {required this.id,
      required this.nameSpecialty,
      required this.description,
      required this.photo});

  factory Specialty.fromJson(Map<String, dynamic> json) {
    return Specialty(
      id: json['id'],
      nameSpecialty: json['nameSpecialty'],
      description: json['description'],
      photo: json['photo'],
    );
  }
}