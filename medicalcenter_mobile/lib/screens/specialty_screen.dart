import 'package:flutter/material.dart';
import 'package:medicalcenter_mobile/apis/endpoint.dart';
import 'package:medicalcenter_mobile/models/specialty.dart';
import 'package:medicalcenter_mobile/apis/specialtyapi.dart';
import 'package:medicalcenter_mobile/widgets/navbar.dart';
import 'package:html/parser.dart';
import 'package:flutter_html/flutter_html.dart';


class SpecialtyScreen extends StatefulWidget {
  @override
  _SpecialtyScreenState createState() => _SpecialtyScreenState();
}

class _SpecialtyScreenState extends State<SpecialtyScreen> {
  String searchQuery = '';
  late Future<List<Specialty>> futureSpecialties;
  final SpecialtyApi _specialtyApi = SpecialtyApi();

   void _searchByNameSpecialty(String query) {
    setState(() {
      searchQuery = query;
      futureSpecialties = _specialtyApi.searchSpecialtiesByNameSpecialty(query);
    });
  }

  @override
  void initState() {
    super.initState();
    futureSpecialties = _specialtyApi.getSpecialties();
  }

  void _navigateToSpecialtyDetails(Specialty specialty) {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => SpecialtyDetailsScreen(specialty: specialty),
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


  Widget _buildSpecialtyCard(Specialty specialty) {
    return GestureDetector(
      onTap: () => _navigateToSpecialtyDetails(specialty),
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
                  Endpoint.imgUrl + specialty.photo,
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
                    'Title: ${specialty.nameSpecialty}',
                    style: TextStyle(
                      fontSize: 16.0,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  SizedBox(height: 8.0),
                  Text(
                    _parseHtmlString(specialty.description),
                    style: TextStyle(fontSize: 14.0),
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
      title: 'Specialties List',
      backButton: true,
    ),
    body: Column(
      children: [
        Padding(
          padding: const EdgeInsets.all(8.0),
          child: TextField(
            onChanged: _searchByNameSpecialty,
            decoration: InputDecoration(
              labelText: 'Search by Name Specialty',
              suffixIcon: Icon(Icons.search),
            ),
          ),
        ),
        Expanded(
          child: FutureBuilder<List<Specialty>>(
            future: futureSpecialties,
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
                List<Specialty> filteredSpecialties = snapshot.data!.where((specialty) {
                  return specialty.nameSpecialty.toLowerCase().contains(searchQuery.toLowerCase());
                }).toList();

                return Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: GridView.builder(
                    gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
                      maxCrossAxisExtent: 2000,
                      crossAxisSpacing: 12.0,
                      mainAxisSpacing: 12.0,
                    ),
                    itemCount: filteredSpecialties.length,
                    itemBuilder: (context, index) {
                      Specialty specialty = filteredSpecialties[index];
                      return _buildSpecialtyCard(specialty);
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

class SpecialtyDetailsScreen extends StatelessWidget {
  final Specialty specialty;

  const SpecialtyDetailsScreen({required this.specialty});

  String _parseHtmlString(String htmlString) {
    final document = parse(htmlString);
   final parsedString = parse(document.body!.text.replaceAll("'", "\\'").replaceAll('"', '\\"')).documentElement!.text;
    return parsedString;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Specialty Details'),
      ),
      body: SingleChildScrollView(
        padding: EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            AspectRatio(
              aspectRatio: 1.0,
              child: Image.network(
                Endpoint.imgUrl + specialty.photo,
                fit: BoxFit.cover,
              ),
            ),
            SizedBox(height: 16.0),
            Text(
              specialty.nameSpecialty,
              style: TextStyle(
                fontSize: 20.0,
                fontWeight: FontWeight.bold,
              ),
            ),
            SizedBox(height: 8.0),
            Html(
              data: specialty.description,
              style: {
                'strong': Style(fontWeight: FontWeight.bold),
              },
            ),
          ],
        ),
      ),
    );
  }
}