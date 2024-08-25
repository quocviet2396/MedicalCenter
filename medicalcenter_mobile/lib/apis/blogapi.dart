import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/blog.dart';
import 'endpoint.dart';

class BlogApi {
  Future<List<Blog>> getBlogs({String? title}) async {
    try {
      final uri = Uri.parse(Endpoint.blogs);
      final queryParameters = {'title': title};
      final response = await http.get(uri.replace(queryParameters: queryParameters));

      if (response.statusCode == 200) {
        List jsonResponse = json.decode(utf8.decode(response.bodyBytes));
        return jsonResponse.map((data) => Blog.fromJson(data)).toList();
      } else {
        print('Failed to fetch Blogs: ${response.statusCode}');
        return [];
      }
    } catch (e) {
      print('Error in BlogApi getBlogs: $e');
      throw Exception('Failed to load blogs');
    }
  }

  Future<List<Blog>> searchBlogsByTitle(String title) async {
    return await getBlogs(title: title);
  }
}