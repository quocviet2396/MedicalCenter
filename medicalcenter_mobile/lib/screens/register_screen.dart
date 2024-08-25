import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:medicalcenter_mobile/constants/theme.dart';
import 'package:medicalcenter_mobile/services/auth_service.dart';
import 'package:medicalcenter_mobile/widgets/input.dart';
import 'dart:core';

class RegisterScreen extends StatefulWidget {
  @override
  _RegisterScreenState createState() => _RegisterScreenState();
}

class _RegisterScreenState extends State<RegisterScreen> {
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  final TextEditingController _firstNameController = TextEditingController();
  final TextEditingController _lastNameController = TextEditingController();
  final AuthService _authService = AuthService();

  bool _checkboxValue = false;
  bool _showPassword = false;

  // Regular expression for validating email format
  final RegExp _emailRegExp = RegExp(r'^[^\s@]+@[^\s@]+\.[^\s@]+$');

  void _registerUser() async {
    String email = _emailController.text;
    String password = _showPassword
        ? _passwordController.text
        : _passwordController.value.text;
    String firstName = _firstNameController.text;
    String lastName = _lastNameController.text;

    // Check if email is in valid format
    if (!_emailRegExp.hasMatch(email)) {
      showDialog(
        context: context,
        builder: (BuildContext context) {
          return AlertDialog(
            title: Text('Invalid Email'),
            content: Text('Please enter a valid email address.'),
            actions: [
              TextButton(
                child: Text('Close'),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              ),
            ],
          );
        },
      );
      return;
    }

    try {
      await _authService.registerUser(email, password, lastName, firstName);
      // Handle successful registration
      showDialog(
        context: context,
        builder: (BuildContext context) {
          return AlertDialog(
            title: Text('Registration Successful'),
            content: Text('Account registered successfully.'),
            actions: [
              TextButton(
                child: Text('Close'),
                onPressed: () {
                  Navigator.of(context).pop();
                  GoRouter.of(context).go('/signin');
                },
              ),
            ],
          );
        },
      );
    } catch (error) {
      // Handle registration error
      showDialog(
        context: context,
        builder: (BuildContext context) {
          return AlertDialog(
            title: Text('Registration Failed'),
            content: Text('Failed to register the account. Error: $error'),
            actions: [
              TextButton(
                child: Text('Close'),
                onPressed: () {
                  Navigator.of(context).pop();
                },
              ),
            ],
          );
        },
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Register'),
      ),
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
            child: Padding(
              padding: const EdgeInsets.all(16.0),
              child: Column(
                children: [
                  Expanded(
                    child: ListView(
                      children: [
                        Card(
                          elevation: 5,
                          clipBehavior: Clip.antiAlias,
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(4.0),
                          ),
                          child: Container(
                            color: Color.fromRGBO(244, 245, 247, 1),
                            child: Padding(
                              padding: const EdgeInsets.all(16.0),
                              child: Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Image.asset(
                                    'assets/imgs/logo.png',
                                    width: 340,
                                    height: 100,
                                  ),
                                  Center(
                                    child: Text(
                                      'Register to join our system!!',
                                      style: TextStyle(
                                        color: ArgonColors.text,
                                        fontWeight: FontWeight.w400,
                                        fontSize: 16,
                                      ),
                                    ),
                                  ),
                                  SizedBox(height: 16),
                                  Input(
                                    placeholder: 'First Name',
                                    prefixIcon: Icon(Icons.person),
                                    controller: _firstNameController,
                                  ),
                                  Input(
                                    placeholder: 'Last Name',
                                    prefixIcon: Icon(Icons.person),
                                    controller: _lastNameController,
                                  ),
                                  Input(
                                    placeholder: 'Email',
                                    prefixIcon: Icon(Icons.email),
                                    controller: _emailController,
                                  ),
                                  Input(
                                    placeholder: 'Password',
                                    prefixIcon: Icon(Icons.lock),
                                    controller: _passwordController,
                                    obscureText: !_showPassword,
                                    suffixIcon: IconButton(
                                      icon: Icon(_showPassword
                                          ? Icons.visibility_off
                                          : Icons.visibility),
                                      onPressed: () {
                                        setState(() {
                                          _showPassword = !_showPassword;
                                        });
                                      },
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
                                          activeColor:
                                              Theme.of(context).primaryColor,
                                          value: _checkboxValue,
                                          onChanged: (value) {
                                            setState(() {
                                              _checkboxValue = value ?? false;
                                            });
                                          },
                                        ),
                                        Text(
                                          'I agree to the terms and conditions.',
                                        ),
                                      ],
                                    ),
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
                                              _registerUser();
                                            }
                                          : null, // Disable the button if the checkbox is not checked
                                      child: Text(
                                        'Register',
                                        style: TextStyle(
                                          fontSize: 16,
                                        ),
                                      ),
                                    ),
                                  ),
                                  SizedBox(height: 16),
                                  Center(
                                    child: TextButton(
                                      onPressed: () {
                                        GoRouter.of(context).go('/signin');
                                      },
                                      child: Text(
                                        'Already have an account? Sign in.',
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
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}
