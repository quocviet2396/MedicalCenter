class Contact {
  final String name;
  final String title;
  final String email;
  final String phone;
  final String description;
  final bool emailSent;

  Contact({
    required this.name,
    required this.title,
    required this.email,
    required this.phone,
    required this.description,
    this.emailSent = false,
  });

  factory Contact.fromJson(Map<String, dynamic> json) {
    return Contact(
      name: json['name'],
      title: json['title'],
      email: json['email'],
      phone: json['phone'],
      description: json['description'],
      emailSent: json['emailSent'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'name': name,
      'title': title,
      'email': email,
      'phone': phone,
      'description': description,
    };
  }
}