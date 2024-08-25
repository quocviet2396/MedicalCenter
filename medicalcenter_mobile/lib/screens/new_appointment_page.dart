import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:http/http.dart' as http;
import 'package:intl/intl.dart';
import 'package:medicalcenter_mobile/apis/endpoint.dart';
import 'package:medicalcenter_mobile/constants/theme.dart';
import 'package:medicalcenter_mobile/enum/Period.dart';

import 'package:medicalcenter_mobile/models/spec.dart';
import 'package:medicalcenter_mobile/screens/appointmentScreen.dart';
import 'package:medicalcenter_mobile/services/api_service.dart';

class NewAppointmentPage extends StatefulWidget {
  @override
  _NewAppointmentPageState createState() => _NewAppointmentPageState();
}

class _NewAppointmentPageState extends State<NewAppointmentPage> {
  String? _lastAppointmentDate;
  bool _hasAlreadyBookedAppointment(String date) {
    // So sánh ngày cuối cùng người dùng đã đăng ký với ngày hiện tại
    return _lastAppointmentDate == date;
  }

  List<Specialty> _specialties = [];
  Specialty? _selectedSpecialty;

  @override
  void initState() {
    super.initState();
    _fetchSpecialties();
  }

  Future<void> _fetchSpecialties() async {
    try {
      final response = await http.get(Uri.parse(Endpoint.listSpecial));
      if (response.statusCode == 200) {
        final List<dynamic> data = json.decode(response.body);
        setState(() {
          _specialties = data.map((item) => Specialty.fromJson(item)).toList();
        });
      } else {
        throw 'Failed to fetch specialties: ${response.statusCode}';
      }
    } catch (error) {
      print('Error fetching specialties: $error');
    }
  }

  Period _selectedPeriod = Period.Morning;
  TextEditingController _appointmentDateController = TextEditingController();
  TextEditingController _appointmentPeriodController = TextEditingController();
  TextEditingController _specialtyIdController = TextEditingController();
  TextEditingController _arrivedController = TextEditingController();
  TextEditingController _symptomsController = TextEditingController();

  Future<void> _selectDate(BuildContext context) async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: DateTime.now(),
      firstDate: DateTime(2022),
      lastDate: DateTime(2100),
    );
    if (picked != null) {
      setState(() {
        _appointmentDateController.text =
            DateFormat('yyyy-MM-dd').format(picked);
      });
    }
  }

  Future<void> _createAppointment(BuildContext context) async {
    final appointmentService = AppointmentService();

    Map<String, dynamic> appointmentData = {
      "appointmentDate": _appointmentDateController.text,
      "appointmentPeriod": _appointmentPeriodController.text,
      "specialties": {
        "id": int.parse(_specialtyIdController.text),
      },
      "arrived": _arrivedController.text,
      "symptoms": _symptomsController.text,
    };

    try {
      await appointmentService.createAppointment(appointmentData);
      print('Appointment created successfully!');
      GoRouter.of(context).go('/appointments');
    } catch (e) {
      print('Failed to create appointment: $e');
    }
  }

  @override
Widget build(BuildContext context) {
  return Scaffold(
    appBar: AppBar(
      title: Text('New Appointment'),
      leading: IconButton(
        icon: Icon(Icons.arrow_back),
        onPressed: () {
          GoRouter.of(context).go('/home');
        },
      ),
    ),
    body: Stack(
      children: [
        Container(
          decoration: BoxDecoration(
            image: DecorationImage(
              image: AssetImage('assets/imgs/register-bg.png'),
              fit: BoxFit.cover,
            ),
          ),
        ),
        SafeArea(
          child: Padding(
            padding: const EdgeInsets.all(16.0),
            child: SingleChildScrollView(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Card(
                    elevation: 5,
                    clipBehavior: Clip.antiAlias,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(4.0),
                    ),
                    child: Container(
                      color: Color.fromRGBO(244, 245, 247, 1),
                      child: Padding(
                        padding: const EdgeInsets.all(16.0),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Image.asset(
                              'assets/imgs/logo.png',
                              width: 340,
                              height: 100,
                            ),
                            Center(
                              child: Text(
                                'Get Appointment',
                                style: TextStyle(
                                  color: ArgonColors.text,
                                  fontWeight: FontWeight.w400,
                                  fontSize: 16,
                                ),
                              ),
                            ),
                            SizedBox(height: 16),
                            GestureDetector(
                              onTap: () => _selectDate(context),
                              child: AbsorbPointer(
                                child: TextFormField(
                                  controller: _appointmentDateController,
                                  decoration: InputDecoration(labelText: 'Appointment Date'),
                                ),
                              ),
                            ),
                            SizedBox(height: 16),
                            DropdownButtonFormField<Period>(
                              value: _selectedPeriod,
                              items: Period.values.map((period) {
                                return DropdownMenuItem<Period>(
                                  value: period,
                                  child: Text(period.toString().split('.').last),
                                );
                              }).toList(),
                              onChanged: (value) {
                                setState(() {
                                  _selectedPeriod = value!;
                                  _appointmentPeriodController.text =
                                      value.toString().split('.').last;
                                });
                              },
                              decoration: InputDecoration(labelText: 'Appointment Period'),
                            ),
                            SizedBox(height: 16),
                            DropdownButtonFormField<Specialty>(
                              value: _selectedSpecialty,
                              items: _specialties.map((specialty) {
                                return DropdownMenuItem<Specialty>(
                                  value: specialty,
                                  child: Text(specialty.nameSpecialty), // Hiển thị tên chuyên môn
                                );
                              }).toList(),
                              onChanged: (value) {
                                setState(() {
                                  _selectedSpecialty = value!;
                                  _specialtyIdController.text = value!.id.toString();
                                });
                              },
                              decoration: InputDecoration(labelText: 'Specialty'),
                            ),
                            SizedBox(height: 16),
                            TextFormField(
                              controller: _symptomsController,
                              decoration: InputDecoration(labelText: 'Symptoms'),
                            ),
                            SizedBox(height: 16),
                            Center(
                              child: ElevatedButton(
                                onPressed: () => _createAppointment(context),
                                child: Text('Create Appointment'),
                              ),
                            ),
                          ],
                        ),
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ),
        ),
      ],
    ),
  );
}
}