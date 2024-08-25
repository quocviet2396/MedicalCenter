import 'package:go_router/go_router.dart';
import 'package:medicalcenter_mobile/data/homecard.dart';
import 'package:medicalcenter_mobile/screens/appointmentScreen.dart';
import 'package:medicalcenter_mobile/screens/articles_screen.dart';
import 'package:medicalcenter_mobile/screens/cart_screen.dart';
import 'package:medicalcenter_mobile/screens/category_screen.dart';
import 'package:medicalcenter_mobile/screens/chat_screen.dart';
import 'package:medicalcenter_mobile/screens/contact_screen.dart';
import 'package:medicalcenter_mobile/screens/doctorinfo_screen.dart';
import 'package:medicalcenter_mobile/screens/hello_screen.dart';
import 'package:medicalcenter_mobile/screens/home_screen.dart';
import 'package:medicalcenter_mobile/screens/listmScreen.dart';
import 'package:medicalcenter_mobile/screens/login_screen.dart';
import 'package:medicalcenter_mobile/screens/new_appointment_page.dart';
import 'package:medicalcenter_mobile/screens/notifications_screen.dart';
import 'package:medicalcenter_mobile/screens/onboarding_screen.dart';
import 'package:medicalcenter_mobile/screens/product_screen.dart';
import 'package:medicalcenter_mobile/screens/profile_screen.dart';
import 'package:medicalcenter_mobile/screens/register_screen.dart';
import 'package:medicalcenter_mobile/screens/scheduleScreen.dart';
import 'package:medicalcenter_mobile/screens/search_screen.dart';
import 'package:medicalcenter_mobile/screens/settings_screen.dart';
import 'package:medicalcenter_mobile/screens/blog_screen.dart';
import 'package:medicalcenter_mobile/screens/specialty_screen.dart';
import 'package:medicalcenter_mobile/screens/followup_screen.dart';

GoRouter router() {
  return GoRouter(
    initialLocation: '/onboarding',
    routes: [
      GoRoute(
        path: '/onboarding',
        builder: (context, state) => OnboardingScreen(),
      ),
      GoRoute(
        path: '/register',
        builder: (context, state) => RegisterScreen(),
      ),
      GoRoute(
        path: '/signin',
        builder: (context, state) => LoginScreen(),
      ),
      GoRoute(
        path: '/home',
        builder: (context, state) => HomeScreen(),
      ),
      GoRoute(
        path: '/search',
        builder: (context, state) => SearchScreen(),
      ),
      GoRoute(
        path: '/contact',
        builder: (context, state) => ContactScreen(),
      ),
      GoRoute(
        path: '/blogsgrid',
        builder: (context, state) => BlogScreen(),
      ),
      GoRoute(
        path: '/specialtiesgrid',
        builder: (context, state) => SpecialtyScreen(),
      ),
      GoRoute(
        path: '/doctorinfosgrid',
        builder: (context, state) => DoctorInfoScreen(),
      ),
      GoRoute(
        path: '/profile',
        builder: (context, state) => ProfileScreen(),
      ),
      GoRoute(
        path: '/chat',
        builder: (context, state) => ChatScreen(),
      ),
      GoRoute(
          path: '/category',
          builder: (context, state) => CategoryScreen(
              suggestionsArray: articlesCards['Music']?['suggestions'],
              imgArray: articlesCards['Music']?['products'])),
      GoRoute(
        path: '/product',
        builder: (context, state) => ProductScreen(),
      ),
      GoRoute(
        path: '/cart',
        builder: (context, state) => CartScreen(),
      ),
      GoRoute(
        path: '/settings',
        builder: (context, state) => SettingsScreen(),
      ),
      GoRoute(
        path: '/notifications',
        builder: (context, state) => NotificationsScreen(),
      ),
      GoRoute(
        path: '/articles',
        builder: (context, state) => ArticlesScreen(),
      ),
      GoRoute(
        path: '/hellotest',
        builder: (context, state) => HelloScreen(),
      ),
      GoRoute(
        path: '/appointments',
        builder: (context, state) => AppointmentListScreen(),
      ),
      GoRoute(
        path: '/newAppointment',
        builder: (context, state) => NewAppointmentPage(),
      ),
       GoRoute(
        path: '/schedule-item',
        builder: (context, state) => ScheduleItemListScreen(),
      ),
      GoRoute(
        path: '/folloup',
        builder: (context, state) => FollowUpAppointmentScreen(),
      ),
      GoRoute(
        path: '/listm',
        builder: (context, state) => MedicalRecordsScreen(),
      ),
    ],
  );
}
