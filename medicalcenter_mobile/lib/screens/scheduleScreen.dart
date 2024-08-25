import 'package:flutter/material.dart';
import 'package:medicalcenter_mobile/apis/endpoint.dart';
import 'package:medicalcenter_mobile/models/scheduleItem.dart';
import 'package:medicalcenter_mobile/services/api_service.dart';
import 'package:intl/intl.dart';
class ScheduleItemListScreen extends StatefulWidget {
  final String? workdateString;
  final String? specialty;

  const ScheduleItemListScreen({Key? key, this.workdateString, this.specialty}) : super(key: key);

  @override
  _ScheduleItemListScreenState createState() => _ScheduleItemListScreenState();
}

class _ScheduleItemListScreenState extends State<ScheduleItemListScreen> {
  late Future<List<ScheduleItem>> _futureScheduleItems;
  String searchKeyword = '';

  @override
  void initState() {
    super.initState();
    _futureScheduleItems = AppointmentService.fetchScheduleItemList();
  }

  List<ScheduleItem> getFilteredScheduleItems(List<ScheduleItem> scheduleItems) {
    // Lọc danh sách các mục lịch trình dựa trên từ khóa tìm kiếm
    return scheduleItems.where((item) {
      return item.doctor.fullName.toLowerCase().contains(searchKeyword.toLowerCase());
    }).toList();
  }

 @override
Widget build(BuildContext context) {
  return Scaffold(
    appBar: AppBar(
      title: Text('Schedule Items'),
    ),
    body: Column(
      children: [
        Padding(
          padding: const EdgeInsets.all(8.0),
          child: TextField(
            onChanged: (value) {
              setState(() {
                searchKeyword = value;
              });
            },
            decoration: InputDecoration(
              hintText: 'Search Doctor...',
              hintStyle: TextStyle(color: Colors.grey),
              prefixIcon: Icon(Icons.search, color: Colors.grey),
              border: OutlineInputBorder(
                borderRadius: BorderRadius.circular(8.0),
              ),
              focusedBorder: OutlineInputBorder(
                borderRadius: BorderRadius.circular(8.0),
                borderSide: BorderSide(color: Theme.of(context).primaryColor, width: 2.0),
              ),
            ),
          ),
        ),
        Expanded(
          child: FutureBuilder<List<ScheduleItem>>(
            future: _futureScheduleItems,
            builder: (context, snapshot) {
              if (snapshot.connectionState == ConnectionState.waiting) {
                return Center(
                  child: CircularProgressIndicator(),
                );
              } else if (snapshot.hasError) {
                return Center(
                  child: Text('Error: ${snapshot.error}'),
                );
              } else {
                final List<ScheduleItem> scheduleItems = snapshot.data!;
                final filteredScheduleItems = getFilteredScheduleItems(scheduleItems);
                return ListView.builder(
                  itemCount: filteredScheduleItems.length,
                  itemBuilder: (context, index) {
                    final ScheduleItem scheduleItem = filteredScheduleItems[index];
                    return GestureDetector(
                      onTap: () {
                        // Handle onTap event here
                      },
                      child: Container(
                        margin: EdgeInsets.all(5),
                        padding: EdgeInsets.all(10),
                        decoration: BoxDecoration(
                          color: Colors.white,
                          borderRadius: BorderRadius.circular(20),
                          boxShadow: [
                            BoxShadow(
                              color: Colors.grey.withOpacity(0.1),
                              spreadRadius: 1,
                              blurRadius: 1,
                              offset: Offset(1, 1), // changes position of shadow
                            ),
                          ],
                        ),
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            ClipRRect(
                              borderRadius: BorderRadius.circular(0), // Hình vuông
                              child: Align(
                                alignment: Alignment.center,
                                child: Container(
                                  width: 100, // Kích thước hình vuông
                                  height: 100, // Kích thước hình vuông
                                  child: CircleAvatar(
                                    backgroundImage: scheduleItem.doctor.photo != null
                                        ? NetworkImage(Endpoint.imgUrl + scheduleItem.doctor.photo!)
                                        : null,
                                  ),
                                ),
                              ),
                            ),
                            SizedBox(height: 10),
                            Center(
                              child: Text('Doctor: ${scheduleItem.doctor.fullName}'),
                            ),
                            SizedBox(height: 4),
                            Center(
                                child: Text('Workdate: ${DateFormat('dd-MM-yyyy').format(scheduleItem.workdates)}'),
                            ),
                            SizedBox(height: 4),
                            Center(
                              child: Text('Specialty: ${scheduleItem.doctor.specialty.nameSpecialty}'),
                            ),
                            SizedBox(height: 4),
                            Center(
                              child: Text('Room: ${scheduleItem.room}'),
                            ),
                            SizedBox(height: 4),
                            Center(
                              child: Text('Appointment: ${scheduleItem.count}'),
                            ),
                          ],
                        ),
                      ),
                    );
                  },
                );
              }
            },
          ),
        ),
      ],
    ),
  );
}


}
