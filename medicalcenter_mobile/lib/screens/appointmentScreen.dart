import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:intl/intl.dart';
import 'package:medicalcenter_mobile/apis/endpoint.dart';
import 'package:medicalcenter_mobile/models/appointment.dart';
import 'package:medicalcenter_mobile/services/api_service.dart';
import 'package:medicalcenter_mobile/widgets/navbar.dart';
import 'package:html/parser.dart';
import 'package:flutter_html/flutter_html.dart';

class AppointmentListScreen extends StatefulWidget {
  @override
  _AppointmentListScreenState createState() => _AppointmentListScreenState();
}

class _AppointmentListScreenState extends State<AppointmentListScreen> {
  final AppointmentService appointmentService = AppointmentService();
  List<dynamic> appointments = [];

  @override
  void initState() {
    super.initState();

    getAppointments();
  }

  Future<void> getAppointments() async {
    try {
      final fetchedAppointments = await appointmentService.getAppointments();
      setState(() {
        appointments = fetchedAppointments
            .map((json) => Appointment.fromJson(json))
            .toList();
      });

      _checkAppointmentDates();
    } catch (error) {
      print('Error fetching appointments: $error');
    }
  }

  Future<void> _checkAppointmentDates() async {
    final now = DateTime.now();
    for (final appointment in appointments) {
      final appointmentDate = appointment.appointmentDate;
      if (appointmentDate != null &&
          appointmentDate.year == now.year &&
          appointmentDate.month == now.month &&
          appointmentDate.day == now.day) {}
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Appointment List'),
        leading: IconButton(
          icon: Icon(Icons.arrow_back),
          onPressed: () {
            GoRouter.of(context).go('/home');
          },
        ),
      ),
      body: ListView.builder(
        itemCount: appointments.length,
        itemBuilder: (BuildContext context, int index) {
          final appointment = appointments[index];
          return Card(
            margin: EdgeInsets.symmetric(vertical: 8.0, horizontal: 16.0),
            child: ListTile(
              contentPadding: EdgeInsets.all(16.0),
              title:Row(
  children: [
    CircleAvatar(
      backgroundImage: appointment.doctor.photo != null
          ? NetworkImage(
              Endpoint.imgUrl + appointment.doctor.photo!)
          : null,
    ),
    SizedBox(width: 8.0),
    GestureDetector(
      onTap: () {
        showDialog(
          context: context,
          builder: (_) => AlertDialog(
            title: Text('Doctor Name'),
            content: Text(appointment.doctor.fullName ?? 'N/A'),
            actions: [
              TextButton(
                onPressed: () {
                  Navigator.pop(context);
                },
                child: Text('Close'),
              ),
            ],
          ),
        );
      },
      child: Container(
        width: MediaQuery.of(context).size.width * 0.6, // adjust the width as needed
        child: Text(
          "Doctor examines: ${appointment.doctor.fullName ?? 'N/A'}",
          style: TextStyle(
            fontSize: 16.0,
            fontWeight: FontWeight.bold,
            fontFamily: 'Roboto',
          ),
          overflow: TextOverflow.ellipsis,
          maxLines: 1,
        ),
      ),
    ),
  ],
),

              subtitle: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  SizedBox(height: 8.0),
                  Text(
                    "Specialty: ${appointment.specialties.nameSpecialty ?? 'N/A'}",
                  ),
                  Text(
                    "Appointment Date: ${appointment.appointmentDate != null ? DateFormat('yyyy-MM-dd').format(appointment.appointmentDate!) : 'N/A'}",
                  ),
                  Text(
                    "Specialty: ${appointment.appointmentPeriod ?? 'N/A'}",
                  ),
                  SizedBox(height: 8.0),
                  Text(
                    "Symptoms: ${appointment.symptoms ?? 'N/A'}",
                  ),
                ],
              ),
              onTap: () {
                // Handle appointment tap event
              },
            ),
          );
        },
      ),
    );
  }
}
