class Endpoint {
  //Cái này phải đổi theo IPv của internet
  static const String baseUrl = 'http://172.16.1.179:8081/apis/v1';
  static const String baseUrlTest = 'http://172.16.1.179:8081/apis/test';
  static const String imgUrl = 'http://172.16.1.179:8081/files/';

  static const String signIn = '$baseUrl/signin';
  static const String signUp = '$baseUrl/signup';
  static const String users = '$baseUrl/users';
  static const String blogs = '$baseUrl/blogsgrid';
  static const String specialties = '$baseUrl/specialtiesgrid';
  static const String doctorInfo = '$baseUrl/doctorinfosgrid';
  static const String contact = '$baseUrl/contactus';
  static const String profile = '$baseUrl/profile';
  static const String appointment = '$baseUrl/appointmentUserLogin/list';
  static const String NewAppointment = '$baseUrl/appointmentUserLogin/save';
  static const String listSpecial = '$baseUrl/appointmentUserLogin/listspecialty';
  static const String listSchedule = '$baseUrl/appointmentUserLogin/schedule-items';
  static const String listFolloUp = '$baseUrl/appointmentUserLogin/listFollowUp';
  static const String listm = '$baseUrl/appointmentUserLogin/listm';
  //static const String forgotpass = '$baseUrl/forgotpassflutter/forgot_password';
  //static const String resetpass = '$baseUrl/forgotpassflutter/reset_password';
}
