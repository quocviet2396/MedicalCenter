import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:medicalcenter_mobile/constants/theme.dart';
import 'package:medicalcenter_mobile/widgets/drawer_title.dart';

class ArgonDrawer extends StatelessWidget {
  final String currentPage;

  const ArgonDrawer({super.key, required this.currentPage});

  @override
  Widget build(BuildContext context) {
    return Drawer(
        child: Container(
      color: ArgonColors.white,
      child: Column(children: [
        SizedBox(
            height: MediaQuery.of(context).size.height * 0.1,
            width: MediaQuery.of(context).size.width * 0.85,
            child: SafeArea(
              bottom: false,
              child: Align(
                alignment: Alignment.bottomLeft,
                child: Padding(
                  padding: const EdgeInsets.only(left: 32),
                  child: Image.asset('assets/imgs/logo.png'),
                ),
              ),
            )),
        Expanded(
          flex: 2,
          child: ListView(
            padding: EdgeInsets.only(top: 24, left: 16, right: 16),
            children: [
              DrawerTitle(
                  icon: Icons.home,
                  onTap: () {
                    if (currentPage != 'Home') {
                      context.push('/home');
                    }
                  },
                  iconColor: ArgonColors.primary,
                  title: 'Home',
                  isSelected: currentPage == 'Home' ? true : false),
              DrawerTitle(
                  icon: Icons.account_circle,
                  onTap: () {
                    if (currentPage != 'Register') {
                      context.push('/register');
                    }
                  },
                  iconColor: ArgonColors.warning,
                  title: 'Register',
                  isSelected: currentPage == 'Register' ? true : false),
              DrawerTitle(
                  icon: Icons.email,
                  onTap: () {
                    if (currentPage != 'Contact') {
                      context.push('/contact');
                    }
                  },
                  iconColor: ArgonColors.black,
                  title: 'ContactUs',
                  isSelected: currentPage == 'Contact' ? true : false),
              DrawerTitle(
                  icon: Icons.calendar_today,
                  onTap: () {
                    if (currentPage != 'schedule-item') {
                      context.push('/schedule-item');
                    }
                  },
                  iconColor: ArgonColors.success,
                  title: 'schedule of doctor to day',
                  isSelected: currentPage == 'schedule-item' ? true : false),
              DrawerTitle(
                  icon: Icons.info,
                  onTap: () {
                    if (currentPage != 'DcotorInfo') {
                      context.push('/doctorinfosgrid');
                    }
                  },
                  iconColor: Color.fromARGB(255, 169, 1, 164),
                  title: 'Our Doctors',
                  isSelected: currentPage == 'DoctorInfo' ? true : false),
              DrawerTitle(
                  icon: Icons.info,
                  onTap: () {
                    if (currentPage != 'Blog') {
                      context.push('/blogsgrid');
                    }
                  },
                  iconColor: Color.fromARGB(255, 169, 1, 164),
                  title: 'Our Blogs',
                  isSelected: currentPage == 'Blog' ? true : false),
              DrawerTitle(
                  icon: Icons.info,
                  onTap: () {
                    if (currentPage != 'Specialty') {
                      context.push('/specialtiesgrid');
                    }
                  },
                  iconColor: Color.fromARGB(255, 169, 1, 164),
                  title: 'Our Specialties',
                  isSelected: currentPage == 'Specialty' ? true : false),
              DrawerTitle(
                  icon: Icons.settings,
                  onTap: () {
                    if (currentPage != 'Settings') {
                      context.push('/settings');
                    }
                  },
                  iconColor: ArgonColors.success,
                  title: 'Settings',
                  isSelected: currentPage == 'Settings' ? true : false),
            ],
          ),
        ),
      ]),
    ));
  }
}
