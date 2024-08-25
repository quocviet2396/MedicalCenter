class MedicalAndMedicineRecordDTO {
  final int id;
  final String medicineName;
  final String? form;
  final String note;
  final String quantity;
  final String description;
  final DateTime lastVisitDate;
  final DateTime followUpDate;
  final String patientName;
  final String doctorName;

  MedicalAndMedicineRecordDTO({
    required this.id,
    required this.medicineName,
    this.form,
    required this.note,
    required this.quantity,
    required this.description,
    required this.lastVisitDate,
    required this.followUpDate,
    required this.patientName,
    required this.doctorName,
  });

 factory MedicalAndMedicineRecordDTO.fromJson(Map<String, dynamic> json) {
  // Loại bỏ ký tự \n từ các trường dữ liệu
  String medicineName = (json['medicineName']?.replaceAll('\n', ',') ?? '').replaceAll(',,', ',');

  String form = (json['form']?.replaceAll('\n', ',') ?? '').replaceAll(',,', ',');
  String note = (json['note']?.replaceAll('\n', ',') ?? '').replaceAll(',,', ',');
  String quantity = (json['quantity']?.replaceAll('\n', ',') ?? '').replaceAll(',,', ',');
  String description = json['description']?.replaceAll('\n', '') ?? '';

  return MedicalAndMedicineRecordDTO(
    id: json['id'],
    medicineName: medicineName,
    form: form.isNotEmpty ? form : null,
    note: note,
    quantity: quantity,
    description: description,
    lastVisitDate: DateTime.parse(json['lastVisitDate']),
    followUpDate: DateTime.parse(json['followUpDate']),
    patientName: json['patientName'],
    doctorName: json['doctorName'],
  );
}

}
