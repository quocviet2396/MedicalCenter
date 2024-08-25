import 'package:flutter/material.dart';
import 'package:medicalcenter_mobile/apis/endpoint.dart';
import 'package:medicalcenter_mobile/models/doctorinfo.dart';
import 'package:medicalcenter_mobile/apis/doctorinfoapi.dart';
import 'package:medicalcenter_mobile/widgets/navbar.dart';
import 'package:html/parser.dart';
import 'package:flutter_html/flutter_html.dart';

class DoctorInfoScreen extends StatefulWidget {
  @override
  _DoctorInfoScreenState createState() => _DoctorInfoScreenState();
}

class _DoctorInfoScreenState extends State<DoctorInfoScreen> {
  String searchQuery = '';
  late Future<List<DoctorInfo>> futureDoctorInfos;
  final DoctorInfoApi _doctorInfoApi = DoctorInfoApi();

  void _searchByTitle(String query) {
    setState(() {
      searchQuery = query;
      futureDoctorInfos = _doctorInfoApi.searchDoctorInfosByTitle(query);
    });
  }

  @override
  void initState() {
    super.initState();
    futureDoctorInfos = _doctorInfoApi.getDoctorInfos();
  }

  void _navigateToDoctorInfoDetails(DoctorInfo doctorInfo) {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => DoctorInfoDetailsScreen(doctorInfo: doctorInfo),
      ),
    );
  }

  String _parseHtmlString(String htmlString) {
    final document = parse(htmlString);
    final parsedString = parse(document.body!.text).documentElement!.text;
    return parsedString.length > 25
        ? parsedString.substring(0, 25) + '...'
        : parsedString;
  }

  Widget _buildDoctorInfoCard(DoctorInfo doctorInfo) {
    return GestureDetector(
      onTap: () => _navigateToDoctorInfoDetails(doctorInfo),
      child: Card(
        elevation: 2.0,
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(8.0),
        ),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Expanded(
              child: AspectRatio(
                aspectRatio: 1.0,
                child: Image.network(
                  Endpoint.imgUrl + doctorInfo.photo,
                  fit: BoxFit.cover,
                ),
              ),
            ),
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    'Tên bác sĩ: ${doctorInfo.title}',
                    style: TextStyle(
                        fontSize: 16.0,
                        fontWeight: FontWeight.bold,
                        fontFamily: 'Roboto'),
                  ),
                  SizedBox(height: 4.0),
                  Text(
                    'Specialty: ${doctorInfo.nameSpecialty}',
                    style: TextStyle(fontSize: 14.0, color: Colors.blue),
                  ),
                  SizedBox(height: 8.0),
                  Text(
                    _parseHtmlString(doctorInfo.content),
                    style: TextStyle(fontSize: 14.0, fontFamily: 'Roboto'),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: Navbar(
        title: 'List DoctorInformation',
        backButton: true,
      ),
      body: Column(
        children: [
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: TextField(
              onChanged: _searchByTitle,
              decoration: InputDecoration(
                labelText: 'Search by Name Doctor',
                suffixIcon: Icon(Icons.search),
              ),
            ),
          ),
          Expanded(
            child: FutureBuilder<List<DoctorInfo>>(
              future: futureDoctorInfos,
              builder: (context, snapshot) {
                if (snapshot.connectionState == ConnectionState.waiting) {
                  return Center(
                    child: CircularProgressIndicator(),
                  );
                } else if (snapshot.hasError) {
                  return Center(
                    child: Text('Error: ${snapshot.error}'),
                  );
                } else if (snapshot.hasData) {
                  List<DoctorInfo> filteredDoctorInfos =
                      snapshot.data!.where((doctorInfo) {
                    return doctorInfo.title
                        .toLowerCase()
                        .contains(searchQuery.toLowerCase());
                  }).toList();

                  return Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: GridView.builder(
                      gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
                        maxCrossAxisExtent: 2000,
                        crossAxisSpacing: 12.0,
                        mainAxisSpacing: 12.0,
                      ),
                      itemCount: filteredDoctorInfos.length,
                      itemBuilder: (context, index) {
                        DoctorInfo doctorInfo = filteredDoctorInfos[index];
                        return _buildDoctorInfoCard(doctorInfo);
                      },
                    ),
                  );
                } else {
                  return Center(
                    child: Text('No data available'),
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

class DoctorInfoDetailsScreen extends StatelessWidget {
  final DoctorInfo doctorInfo;

  const DoctorInfoDetailsScreen({required this.doctorInfo});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(doctorInfo.title),
      ),
      body: SingleChildScrollView(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Image.network(
              Endpoint.imgUrl + doctorInfo.photo,
              width: double.infinity,
              height: 200,
              fit: BoxFit.cover,
            ),
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    'Title: ${doctorInfo.title}',
                    style: TextStyle(
                        fontSize: 20.0,
                        fontWeight: FontWeight.bold,
                        fontFamily: 'Roboto'),
                  ),
                  SizedBox(height: 8.0),
                  Text(
                    'Specialty: ${doctorInfo.nameSpecialty}',
                    style: TextStyle(fontSize: 16.0, color: Colors.blue),
                  ),
                  SizedBox(height: 16.0),
                  Html(
                    data: doctorInfo.content,
                    style: {
                      "html": Style(
                        fontFamily: 'Roboto',
                      ),
                    },
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
