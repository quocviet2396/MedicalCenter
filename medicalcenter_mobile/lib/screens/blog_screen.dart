import 'package:flutter/material.dart';
import 'package:medicalcenter_mobile/apis/endpoint.dart';
import 'package:medicalcenter_mobile/models/blog.dart';
import 'package:medicalcenter_mobile/apis/blogapi.dart';
import 'package:medicalcenter_mobile/widgets/navbar.dart';
import 'package:html/parser.dart';
import 'package:flutter_html/flutter_html.dart';


class BlogScreen extends StatefulWidget {
  @override
  _BlogScreenState createState() => _BlogScreenState();
}

class _BlogScreenState extends State<BlogScreen> {
  String searchQuery = '';
  late Future<List<Blog>> futureBlogs;
  final BlogApi _blogApi = BlogApi();

   void _searchByTitle(String query) {
    setState(() {
      searchQuery = query;
      futureBlogs = _blogApi.searchBlogsByTitle(query);
    });
  }

  @override
  void initState() {
    super.initState();
    futureBlogs = _blogApi.getBlogs();
  }

  void _navigateToBlogDetails(Blog blog) {
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => BlogDetailsScreen(blog: blog),
      ),
    );
  }

  String _parseHtmlString(String htmlString) {
    final document = parse(htmlString);
    final parsedString = parse(document.body!.text).documentElement!.text;
    return parsedString.length > 25
        ? parsedString.substring(0, 25) + '...'
        : parsedString;
  }

  Widget _buildBlogCard(Blog blog) {
    return GestureDetector(
      onTap: () => _navigateToBlogDetails(blog),
      child: Card(
        elevation: 2.0,
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(8.0),
        ),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Expanded(
              child: AspectRatio(
                aspectRatio: 1.0,
                child: Image.network(
                  Endpoint.imgUrl + blog.photo,
                  fit: BoxFit.cover,
                ),
              ),
            ),
            Padding(
              padding: const EdgeInsets.all(8.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    'Title: ${blog.title}',
                    style: TextStyle(
                      fontSize: 16.0,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  SizedBox(height: 4.0),
                  Text(
                    'Author: ${blog.author}',
                    style: TextStyle(fontSize: 14.0, color: Colors.blue),
                  ),
                  SizedBox(height: 8.0),
                  Text(
                    _parseHtmlString(blog.content),
                    style: TextStyle(fontSize: 14.0),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  @override
Widget build(BuildContext context) {
  return Scaffold(
    appBar: Navbar(
      title: 'List Blogs',
      backButton: true,
    ),
    body: Column(
      children: [
        Padding(
          padding: const EdgeInsets.all(8.0),
          child: TextField(
            onChanged: _searchByTitle,
            decoration: InputDecoration(
              labelText: 'Search by title',
              suffixIcon: Icon(Icons.search),
            ),
          ),
        ),
        Expanded(
          child: FutureBuilder<List<Blog>>(
            future: futureBlogs,
            builder: (context, snapshot) {
              if (snapshot.connectionState == ConnectionState.waiting) {
                return Center(
                  child: CircularProgressIndicator(),
                );
              } else if (snapshot.hasError) {
                return Center(
                  child: Text('Error: ${snapshot.error}'),
                );
              } else if (snapshot.hasData) {
                List<Blog> filteredBlogs = snapshot.data!.where((blog) {
                  return blog.title.toLowerCase().contains(searchQuery.toLowerCase());
                }).toList();

                return Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: GridView.builder(
                    gridDelegate: SliverGridDelegateWithMaxCrossAxisExtent(
                      maxCrossAxisExtent: 2000,
                      crossAxisSpacing: 12.0,
                      mainAxisSpacing: 12.0,
                    ),
                    itemCount: filteredBlogs.length,
                    itemBuilder: (context, index) {
                      Blog blog = filteredBlogs[index];
                      return _buildBlogCard(blog);
                    },
                  ),
                );
              } else {
                return Center(
                  child: Text('No data available'),
                );
              }
            },
          ),
        ),
      ],
    ),
  );
}
}

class BlogDetailsScreen extends StatelessWidget {
  final Blog blog;

  const BlogDetailsScreen({required this.blog});

  String _parseHtmlString(String htmlString) {
    final document = parse(htmlString);
   final parsedString = parse(document.body!.text.replaceAll("'", "\\'").replaceAll('"', '\\"')).documentElement!.text;
    return parsedString;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Blog Details'),
      ),
      body: SingleChildScrollView(
        padding: EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            AspectRatio(
              aspectRatio: 1.0,
              child: Image.network(
                Endpoint.imgUrl + blog.photo,
                fit: BoxFit.cover,
              ),
            ),
            SizedBox(height: 16.0),
            Text(
              blog.title,
              style: TextStyle(
                fontSize: 20.0,
                fontWeight: FontWeight.bold,
              ),
            ),
            SizedBox(height: 8.0),
            Text(
              'Author: ${blog.author}',
              style: TextStyle(fontSize: 16.0),
            ),
            SizedBox(height: 8.0),
            Html(
              data: blog.content,
              style: {
                'strong': Style(fontWeight: FontWeight.bold),
              },
            ),
          ],
        ),
      ),
    );
  }
}