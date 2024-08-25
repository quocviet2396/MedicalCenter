import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:medicalcenter_mobile/models/MedicalAndMedicineRecordDTO.dart';
import 'package:medicalcenter_mobile/services/api_service.dart';


class MedicalRecordsScreen extends StatefulWidget {
  @override
  _MedicalRecordsScreenState createState() => _MedicalRecordsScreenState();
}

class _MedicalRecordsScreenState extends State<MedicalRecordsScreen> {
  List<MedicalAndMedicineRecordDTO> medicalRecords = [];
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    fetchMedicalRecords();
  }

  Future<void> fetchMedicalRecords() async {
    try {
      List<MedicalAndMedicineRecordDTO> records = await AppointmentService().fetchMedicalRecords();
      setState(() {
        medicalRecords = records;
        isLoading = false;
      });
    } catch (error) {
      print('Error fetching medical records: $error');
    }
  }

   @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Medical Records'),
        leading: IconButton(
          icon: Icon(Icons.arrow_back),
          onPressed: () {
            GoRouter.of(context).go('/home');
          },
        ),
      ),
      body: isLoading
          ? Center(child: CircularProgressIndicator())
          : ListView.builder(
              itemCount: medicalRecords.length,
              itemBuilder: (context, index) {
                MedicalAndMedicineRecordDTO record = medicalRecords[index];
                return Card(
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          'PATIENT Name: ${record.patientName}',
                          style: TextStyle(
                            fontWeight: FontWeight.bold,
                            fontSize: 18,
                          ),
                        ),
                        SizedBox(height: 5),
                        Text('Doctor Name: ${record.doctorName}'),
                        Text('Last Visit Date: ${record.lastVisitDate.toString()}'),
                        Text('Follow-Up Date: ${record.followUpDate.toString()}'),
                        Text('Medicine: ${record.medicineName}'),
                        Text('Medicine Form: ${record.form}'),
                        Text('Medicine Quantity: ${record.quantity}'),
                        Text('Medicine Note: ${record.note}'),
                        Text('Diagnostic: ${record.description}'),

                      ],
                    ),
                  ),
                );
              },
            ),
    );
  }
}
