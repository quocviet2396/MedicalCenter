import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:medicalcenter_mobile/constants/theme.dart';
import 'package:medicalcenter_mobile/widgets/input.dart';
import '../models/contact.dart';
import '../apis/contactapi.dart';

class ContactScreen extends StatefulWidget {
  @override
  _ContactScreenState createState() => _ContactScreenState();
}

class _ContactScreenState extends State<ContactScreen> {
  TextEditingController nameController = TextEditingController();
  TextEditingController titleController = TextEditingController();
  TextEditingController emailController = TextEditingController();
  TextEditingController phoneController = TextEditingController();
  TextEditingController descriptionController = TextEditingController();

  ContactApi contactApi = ContactApi();

  Future<void> createContact() async {
    String name = nameController.text;
    String title = titleController.text;
    String email = emailController.text;
    String phone = phoneController.text;
    String description = descriptionController.text;

    if (name.isEmpty ||
        title.isEmpty ||
        email.isEmpty ||
        phone.isEmpty ||
        description.isEmpty) {
      showErrorDialog('Please fill in all fields');
      return;
    }

    Contact contact = Contact(
      name: name,
      title: title,
      email: email,
      phone: phone,
      description: description,
    );

    try {
      Contact createdContact = await contactApi.createContact(contact);
      // Xử lý sau khi tạo đối tượng liên hệ thành công
      print('Created contact: ${createdContact.toJson()}');
      showSuccessDialog('Contact created successfully');
    } catch (e) {
      // Xử lý khi có lỗi xảy ra
      print('Error creating contact: $e');
      showErrorDialog('Error creating contact');
    }
  }

  void showSuccessDialog(String message) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Success'),
          content: Text(message),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.of(context).pop();
                GoRouter.of(context).go('/home');
              },
              child: Text('OK'),
            ),
          ],
        );
      },
    );
  }

  void showErrorDialog(String message) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('Error'),
          content: Text(message),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.of(context).pop();
              },
              child: Text('OK'),
            ),
          ],
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Contact Us'),
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
                                      'Contact us via email. Please use a valid email address!',
                                      style: TextStyle(
                                        color: ArgonColors.text,
                                        fontWeight: FontWeight.w400,
                                        fontSize: 16,
                                      ),
                                    ),
                                  ),
                                  SizedBox(height: 16),
                                  Input(
                                    placeholder: 'Name',
                                    prefixIcon: Icon(Icons.person),
                                    controller: nameController,
                                  ),
                                  Input(
                                    placeholder: 'Title',
                                    prefixIcon: Icon(Icons.title),
                                    controller: titleController,
                                  ),
                                  Input(
                                    placeholder: 'Email',
                                    prefixIcon: Icon(Icons.email),
                                    controller: emailController,
                                  ),
                                  Input(
                                    placeholder: 'Phone',
                                    prefixIcon: Icon(Icons.phone),
                                    controller: phoneController,
                                  ),
                                  TextField(
                                    decoration: InputDecoration(
                                      labelText: 'Message',
                                      prefixIcon: Icon(Icons.message),
                                      filled: true,
                                      fillColor: Colors.white,
                                      border: OutlineInputBorder(
                                        borderRadius:
                                            BorderRadius.circular(4.0),
                                      ),
                                    ),
                                    maxLines: null,
                                    controller: descriptionController,
                                  ),
                                  SizedBox(height: 16),
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
                                      onPressed: () {
                                        createContact();
                                      },
                                      child: Text(
                                        'Send Mail',
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
                                        GoRouter.of(context).go('/home');
                                      },
                                      child: Text(
                                        'Back HomePage',
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
