import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:medicalcenter_mobile/services/auth_service.dart';

class SettingsScreen extends StatefulWidget {
  @override
  _SettingsScreenState createState() => _SettingsScreenState();
}

class _SettingsScreenState extends State<SettingsScreen> {
  final AuthService _authService = AuthService(); // Instance của AuthService
  bool _isLoggedIn = false;

  @override
  void initState() {
    super.initState();
    checkLoginStatus();
  }

  void checkLoginStatus() async {
    // Kiểm tra xem người dùng có đăng nhập hay không
    bool isLoggedIn = await _authService.isLoggedIn();
    setState(() {
      _isLoggedIn = isLoggedIn;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Settings'),
        leading: IconButton(
          icon: Icon(Icons.arrow_back),
          onPressed: () {
            GoRouter.of(context).go('/home');
          },
        ),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            if (_isLoggedIn)
              FutureBuilder(
                future: _authService.getLoggedInUser(),
                builder: (context, snapshot) {
                  if (snapshot.connectionState == ConnectionState.waiting) {
                    return CircularProgressIndicator();
                  } else {
                    final user = snapshot.data;
                    // Kiểm tra user có giá trị không trước khi truy cập thuộc tính fullName
                    return Column(
                      children: [
                        Text(
                          'Hello ${user?.fullName ?? "Unknown"}',
                          style: TextStyle(fontSize: 24),
                        ),
                        SizedBox(height: 20),
                        ElevatedButton.icon(
                          onPressed: () {
                            GoRouter.of(context).go('/profile');
                          },
                          icon: Icon(Icons.account_circle), // Icon hình account
                          label: Text('Go to Profile'),
                        ),
                        SizedBox(height: 20),
                        ElevatedButton.icon(
                          onPressed: () {
                            GoRouter.of(context).go('/appointments');
                          },
                          icon: Icon(Icons.local_hospital), // Icon hình account
                          label: Text('Appointment'),
                        ),
                        SizedBox(height: 20),
                        ElevatedButton.icon(
                          onPressed: () {
                            GoRouter.of(context).go('/folloup');
                          },
                          icon: Icon(Icons.local_hospital), // Icon hình account
                          label: Text('Follow up'),
                        ),
                        SizedBox(height: 20,),
                        ElevatedButton.icon(
                          onPressed: () {
                            GoRouter.of(context).go('/listm');
                          },
                          icon: Icon(Icons.local_hospital), // Icon hình account
                          label: Text('Medical'),
                        ),
                        SizedBox(height: 20),
                        SizedBox(height: 20),
                        ElevatedButton.icon(
                          onPressed: () {
                            GoRouter.of(context).go('/newAppointment');
                          },
                          icon: Icon(Icons.add), // Icon hình account
                          label: Text('New appointment'),
                        ),
                        SizedBox(height: 20),
                        ElevatedButton(
                          onPressed: () {
                            // Gọi hàm logout() khi người dùng chọn đăng xuất
                            _authService.logout();
                            // Chuyển hướng người dùng đến màn hình đăng nhập hoặc màn hình khác
                            GoRouter.of(context).go('/signin');
                          },
                          child: Text('Logout'),
                        ),
                      ],
                    );
                  }
                },
              )
            else
              ElevatedButton(
                onPressed: () {
                  // Chuyển hướng người dùng đến trang đăng nhập
                  GoRouter.of(context).go('/signin');
                },
                child: Text('Login'),
              ),
          ],
        ),
      ),
    );
  }
}
