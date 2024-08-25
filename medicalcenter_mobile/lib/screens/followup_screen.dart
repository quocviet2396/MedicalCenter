import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:medicalcenter_mobile/apis/endpoint.dart';
import 'package:medicalcenter_mobile/models/followup.dart';
import 'package:medicalcenter_mobile/services/api_service.dart';
import 'package:intl/intl.dart';

class FollowUpAppointmentScreen extends StatefulWidget {
  @override
  _FollowUpAppointmentScreenState createState() => _FollowUpAppointmentScreenState();
}

class _FollowUpAppointmentScreenState extends State<FollowUpAppointmentScreen> {
  final AppointmentService appointmentService = AppointmentService();
  List<dynamic> _followUps = [];
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
   getFollowUp();
  }

   Future<void> getFollowUp() async {
  try {
    final fetchedFollowUps = await appointmentService.getFollowUp();
    setState(() {
      _followUps = fetchedFollowUps
          .map((json) => FollowUpAppointment.fromJson(json))
          .toList();
      _isLoading = false;
    });
  } catch (error) {
    print('Error fetching follow-up appointments: $error');
    setState(() {
      _isLoading = false;
    });
  }
}


  @override
Widget build(BuildContext context) {
  return Scaffold(
    appBar: AppBar(
      title: Text('Follow up List'),
      leading: IconButton(
        icon: Icon(Icons.arrow_back),
        onPressed: () {
          GoRouter.of(context).go('/home');
        },
      ),
    ),
    body: _isLoading
        ? Center(child: CircularProgressIndicator())
        : _followUps.isEmpty
            ? Center(child: Text('No follow-up appointments found'))
            : ListView.builder(
                itemCount: _followUps.length,
                itemBuilder: (context, index) {
                  final followUp = _followUps[index];
                  return Card(
                    margin: EdgeInsets.symmetric(vertical: 8.0, horizontal: 16.0),
                    child: ListTile(
                      contentPadding: EdgeInsets.all(16.0),
                      title: Row(
                        children: [
                          CircleAvatar(
                            backgroundImage: followUp.doctorUser.photo != null
                                ? NetworkImage(Endpoint.imgUrl + followUp.doctorUser.photo!)
                                : null,
                          ),
                          SizedBox(width: 8.0),
                          GestureDetector(
                            onTap: () {
                              showDialog(
                                context: context,
                                builder: (_) => AlertDialog(
                                  title: Text('Doctor Name'),
                                  content: Text(followUp.doctorUser.fullName ?? 'N/A'),
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
                              width: MediaQuery.of(context).size.width * 0.6,
                              child: Text(
                                "Doctor examines: ${followUp.doctorUser.fullName ?? 'N/A'}",
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
                            "Specialty: ${followUp.special.nameSpecialty ?? 'N/A'}",
                          ),
                          Text(
                            "Appointment Date: ${followUp.appointmentDate != null ? DateFormat('yyyy-MM-dd').format(followUp.appointmentDate!) : 'N/A'}",
                          ),
                          Text(
                            "Appointment Period: ${followUp.appointmentPeriod ?? 'N/A'}",
                          ),
                          SizedBox(height: 8.0),
                          Text(
                            "Appointment Period: ${followUp.description ?? ''}",
                          ),
                        ],
                      ),
                      onTap: () {
                        // Handle follow-up appointment tap event
                      },
                    ),
                  );
                },
              ),
  );
}

  }

