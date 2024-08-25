import 'package:json_annotation/json_annotation.dart';

part 'user.g.dart';

@JsonSerializable()
class User {
  final int id;
  final String email;
  String? firstName;
  String? lastName;
  String? fullName;
  String? photo;
  String? photosImagePath;
  String? gender;
  String? dateOfBirth;
  Address? address;

  User({
    required this.id,
    required this.email,
    this.firstName,
    this.lastName,
    this.fullName,
    this.photo,
    this.photosImagePath,
    this.gender,
    this.dateOfBirth,
    this.address,
  });

  factory User.fromJson(Map<String, dynamic> json) => _$UserFromJson(json);
  Map<String, dynamic> toJson() => _$UserToJson(this);
}

@JsonSerializable()
class Address {
  String? address;
  String? phone;
  String? state;
  String? country;
  String? postalCode;

  Address({
    this.address,
    this.phone,
    this.state,
    this.country,
    this.postalCode,
  });

  factory Address.fromJson(Map<String, dynamic> json) =>
      _$AddressFromJson(json);
  Map<String, dynamic> toJson() => _$AddressToJson(this);
}