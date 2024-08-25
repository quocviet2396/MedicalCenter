import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:medicalcenter_mobile/constants/theme.dart';
import 'package:medicalcenter_mobile/services/auth_service.dart';
import 'package:medicalcenter_mobile/widgets/drawer.dart';
import 'package:medicalcenter_mobile/widgets/input.dart';
import 'package:medicalcenter_mobile/widgets/navbar.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({Key? key}) : super(key: key);

  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  bool _checkboxValue = false;
  bool _showPassword = false;
  bool _showError = false;
  final _emailTextController = TextEditingController();
  final _passwordTextController = TextEditingController();

  final _authService = AuthService();
  bool _isLoggedIn = false;

 
  Future<bool> _signInWithEmail() async {
    try {
      final token = await _authService.loginWebAccount(
          _emailTextController.text, _passwordTextController.text);
      return true;
    } catch (exception) {
      print('Login exception: $exception');
      setState(() {
        _showError = true;
      });
    }
    return false;
  }

  void _toggleShowPassword() {
    setState(() {
      _showPassword = !_showPassword;
    });
  }

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
    if (_isLoggedIn) {
      // Nếu chưa đã đăng nhập, chuyển hướng đến màn hình chào mừng
      GoRouter.of(context).go('/settings');
    }
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: Navbar(transparent: true, title: ''),
      extendBodyBehindAppBar: true,
      drawer: ArgonDrawer(currentPage: 'Login'),
      body: Stack(
        children: [
          Container(
            decoration: BoxDecoration(
              image: DecorationImage(
                image: AssetImage('assets/imgs/register-bg.png'),
                fit: BoxFit.cover,
              ),
            ),
          ),
          SafeArea(
            child: ListView(
              children: [
                Padding(
                  padding: const EdgeInsets.only(
                    top: 16,
                    left: 24.0,
                    right: 24.0,
                    bottom: 32,
                  ),
                  child: Card(
                    elevation: 5,
                    clipBehavior: Clip.antiAlias,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(4.0),
                    ),
                    child: Column(
                      children: [
                        Container(
                          height: MediaQuery.of(context).size.height * 0.15,
                          decoration: BoxDecoration(
                            color: ArgonColors.white,
                            border: Border(
                              bottom: BorderSide(
                                width: 0.5,
                                color: ArgonColors.muted,
                              ),
                            ),
                          ),
                          child: Column(
                            mainAxisAlignment: MainAxisAlignment.spaceAround,
                            children: [
                              // Customization 1: Add your custom content here
                              // For example, you can add a logo or a custom header
                              // Replace the code below with your desired content
                              Image.asset(
                                'assets/imgs/logo.png', // Đường dẫn tới hình ảnh logo
                                width: 340, // Chiều rộng của hình ảnh logo
                                height: 100, // Chiều cao của hình ảnh logo
                              ),
                            ],
                          ),
                        ),
                        Container(
                          height: MediaQuery.of(context).size.height * 0.63,
                          color: Color.fromRGBO(244, 245, 247, 1),
                          child: Padding(
                            padding: const EdgeInsets.all(8.0),
                            child: Center(
                              child: Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                mainAxisAlignment: MainAxisAlignment.start,
                                children: [
                                  Padding(
                                    padding: const EdgeInsets.only(
                                      top: 24.0,
                                      bottom: 24.0,
                                    ),
                                    child: Center(
                                      child: Text(
                                        'Login to join our system!',
                                        style: TextStyle(
                                          color: ArgonColors.text,
                                          fontWeight: FontWeight.w200,
                                          fontSize: 16,
                                        ),
                                      ),
                                    ),
                                  ),
                                  Column(
                                    crossAxisAlignment:
                                        CrossAxisAlignment.start,
                                    children: [
                                      Padding(
                                        padding: const EdgeInsets.all(8.0),
                                        child: Input(
                                          controller: _emailTextController,
                                          placeholder: 'Email',
                                          prefixIcon: Icon(Icons.email),
                                        ),
                                      ),
                                      Padding(
                                        padding: const EdgeInsets.all(8.0),
                                        child: Input(
                                          controller: _passwordTextController,
                                          placeholder: 'Password',
                                          prefixIcon: Icon(Icons.lock),
                                          obscureText: !_showPassword,
                                          suffixIcon: IconButton(
                                            icon: Icon(
                                              _showPassword
                                                  ? Icons.visibility
                                                  : Icons.visibility_off,
                                            ),
                                            onPressed: _toggleShowPassword,
                                          ),
                                        ),
                                      ),
                                      Padding(
                                        padding: const EdgeInsets.only(
                                          left: 8.0,
                                          top: 8.0,
                                          bottom: 16.0,
                                        ),
                                        child: Row(
                                          children: [
                                            Checkbox(
                                              activeColor: Theme.of(context)
                                                  .primaryColor,
                                              value: _checkboxValue,
                                              onChanged: (value) {
                                                setState(() {
                                                  _checkboxValue =
                                                      value ?? false;
                                                });
                                              },
                                            ),
                                            Text(
                                              'I agree to the terms and conditions.',
                                            ),
                                          ],
                                        ),
                                      ),
                                    ],
                                  ),
                                  Center(
                                    child: TextButton(
                                      style: TextButton.styleFrom(
                                        foregroundColor: Colors.white,
                                        backgroundColor:
                                            Theme.of(context).primaryColor,
                                        padding: EdgeInsets.symmetric(
                                          vertical: 16,
                                          horizontal: 32,
                                        ),
                                        shape: RoundedRectangleBorder(
                                          borderRadius:
                                              BorderRadius.circular(4.0),
                                        ),
                                      ),
                                      onPressed: _checkboxValue
                                          ? () {
                                              _signInWithEmail().then((value) {
                                                if (value) {
                                                  GoRouter.of(context)
                                                      .go('/home');
                                                }
                                              });
                                            }
                                          : null, // Disable the button if the checkbox is not checked
                                      child: Text(
                                        'Login',
                                        style: TextStyle(
                                          fontSize: 16,
                                        ),
                                      ),
                                    ),
                                  ),
                                  Center(
                                    child: TextButton(
                                      onPressed: () {
                                        GoRouter.of(context).go('/register');
                                      },
                                      child: Text(
                                        'Not have account yet? Sign up.',
                                        style: TextStyle(
                                          fontSize: 16,
                                          color: Theme.of(context).primaryColor,
                                        ),
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
