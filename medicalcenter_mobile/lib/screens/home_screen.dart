import 'package:flutter/material.dart';
import 'package:medicalcenter_mobile/constants/theme.dart';
import 'package:medicalcenter_mobile/data/homecard.dart';
import 'package:medicalcenter_mobile/widgets/card_horizontal.dart';
import 'package:medicalcenter_mobile/widgets/card_small.dart';
import 'package:medicalcenter_mobile/widgets/card_square.dart';
import 'package:medicalcenter_mobile/widgets/drawer.dart';
import 'package:medicalcenter_mobile/widgets/navbar.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: Navbar(
        title: 'Home',
      ),
      backgroundColor: ArgonColors.bgColorScreen,
      drawer: ArgonDrawer(currentPage: 'Home'),
      body: SingleChildScrollView(
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              SizedBox(height: 20),
              Image.asset(
                "assets/imgs/logo.png",
                height: 100,
              ),
              SizedBox(height: 20),
              Text(
                "Welcome to Medical Center",
                style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                ),
              ),
              SizedBox(height: 16),
              Text(
                "Medical check-up process",
                style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.bold,
                ),
              ),
              SizedBox(height: 10),
              Image.asset(
                "assets/imgs/checkup_image.jpg",
                height: 200,
              ),
              SizedBox(height: 10),
              Text(
                "Hospital facilities for exceptional patient care",
                style: TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.bold,
                ),
              ),
              SizedBox(height: 16),
              Column(
                children: [
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      ServiceImageWithTitle(
                        imagePath: "assets/imgs/service1.jpg",
                        title: "Empty Doctor's Office",
                      ),
                      SizedBox(width: 8),
                      ServiceImageWithTitle(
                        imagePath: "assets/imgs/service2.jpg",
                        title: "Examination Room In Doctor's Office",
                      ),
                    ],
                  ),
                  SizedBox(height: 16),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      ServiceImageWithTitle(
                        imagePath: "assets/imgs/service3.jpg",
                        title: "Mature female doctor consulting",
                      ),
                      SizedBox(width: 8),
                      ServiceImageWithTitle(
                        imagePath: "assets/imgs/service4.jpg",
                        title: "Interior of doctor's office in hospital",
                      ),
                    ],
                  ),
                  SizedBox(height: 16),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class ServiceImageWithTitle extends StatelessWidget {
  final String imagePath;
  final String title;

  const ServiceImageWithTitle({
    Key? key,
    required this.imagePath,
    required this.title,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      width: MediaQuery.of(context).size.width / 2 - 12,
      child: Stack(
        children: [
          Image.asset(
            imagePath,
            fit: BoxFit.cover,
          ),
          Positioned.fill(
            child: Container(
              color: Colors.black.withOpacity(0.5),
            ),
          ),
          Positioned(
            bottom: 8,
            left: 8,
            child: Text(
              title,
              style: TextStyle(
                fontSize: 10,
                fontWeight: FontWeight.bold,
                color: Colors.white,
              ),
            ),
          ),
        ],
      ),
    );
  }
}
