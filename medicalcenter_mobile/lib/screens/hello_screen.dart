import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:medicalcenter_mobile/screens/login_screen.dart';
import 'package:medicalcenter_mobile/services/auth_service.dart';

class HelloScreen extends StatefulWidget {
  @override
  _HelloScreenState createState() => _HelloScreenState();
}

class _HelloScreenState extends State<HelloScreen> {
  final AuthService _authService = AuthService();
  bool _isLoggedIn = false;

  @override
  void initState() {
    super.initState();
    checkLoginStatus();
  }

  void checkLoginStatus() async {
    bool isLoggedIn = await _authService.isLoggedIn();
    setState(() {
      _isLoggedIn = isLoggedIn;
    });

    // Kiểm tra và điều hướng dựa trên trạng thái đăng nhập
    if (!_isLoggedIn) {
      // Nếu chưa đã đăng nhập, chuyển hướng đến màn hình chào mừng
      GoRouter.of(context).go('/signin');
    }
  }

  @override
  Widget build(BuildContext context) {
    // Hiển thị một widget tùy thuộc vào trạng thái đăng nhập
    return Scaffold(
      appBar: AppBar(
        title: Text('Hello'),
      ),
      body: Center(
        child: Text(
          _isLoggedIn ? 'Hello, User!' : 'Loading...', // Hiển thị "Loading..." trong khi kiểm tra trạng thái đăng nhập
          style: TextStyle(fontSize: 24),
        ),
      ),
    );
  }
}

