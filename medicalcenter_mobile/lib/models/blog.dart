class Blog {
  final int id;
  final String title;
  final String content;
  final String photo;
  final String author;
  final String photosImagePath;

  Blog(
      {required this.id,
      required this.title,
      required this.content,
      required this.photo,
      required this.author,
      required this.photosImagePath});

  factory Blog.fromJson(Map<String, dynamic> json) {
    return Blog(id: json['id'], title: json['title'], content: json['content'], photo: json['photo'], author: json['author'], photosImagePath: json['photosImagePath']);
  }
  
}