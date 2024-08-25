class DoctorInfo {
  final int id;
  final String title;
  final String content;
  final String photo;
  final String nameSpecialty;
  final String photosImagePath;

  DoctorInfo({
    required this.id,
    required this.title,
    required this.content,
    required this.photo,
    required this.nameSpecialty,
    required this.photosImagePath,
  });

  factory DoctorInfo.fromJson(Map<String, dynamic> json) {
    String nameSpecialty = '';
    if (json.containsKey('specialty') && json['specialty'] is Map<String, dynamic>) {
      Map<String, dynamic> specialty = json['specialty'];
      if (specialty.containsKey('nameSpecialty') && specialty['nameSpecialty'] is String) {
        nameSpecialty = specialty['nameSpecialty'];
      }
    }

    return DoctorInfo(
      id: json['id'],
      title: json['title'],
      content: json['content'],
      photo: json['photo'],
      nameSpecialty: nameSpecialty,
      photosImagePath: json['photosImagePath'],
    );
  }
}