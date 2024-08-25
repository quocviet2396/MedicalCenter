import 'package:flutter/material.dart';
import 'package:medicalcenter_mobile/constants/theme.dart';
import 'package:medicalcenter_mobile/data/articlescard.dart';
import 'package:medicalcenter_mobile/screens/category_screen.dart';
import 'package:medicalcenter_mobile/screens/product_screen.dart';
import 'package:medicalcenter_mobile/services/user_service.dart';
import 'package:medicalcenter_mobile/widgets/card_category.dart';
import 'package:medicalcenter_mobile/widgets/card_horizontal.dart';
import 'package:medicalcenter_mobile/widgets/card_square.dart';
import 'package:medicalcenter_mobile/widgets/drawer.dart';
import 'package:medicalcenter_mobile/widgets/navbar.dart';
import 'package:medicalcenter_mobile/widgets/product_sider.dart';

class ArticlesScreen extends StatefulWidget {
  const ArticlesScreen({super.key});

  @override
  State<ArticlesScreen> createState() => _ArticlesScreenState();
}

class _ArticlesScreenState extends State<ArticlesScreen> {
  final userService = UserService();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: Navbar(
          title: 'Articles',
          rightOptions: false,
        ),
        backgroundColor: ArgonColors.bgColorScreen,
        drawer: ArgonDrawer(currentPage: 'Articles'),
        body: Container(
            padding: EdgeInsets.only(right: 24, left: 24, bottom: 36),
            child: SingleChildScrollView(
              child: Column(
                children: [
                  FutureBuilder(
                    future: userService.getListUsers(),
                    builder: (context, snapshot) {
                      return Text('Render List User herer');
                    },
                  ),
                  Padding(
                    padding: const EdgeInsets.only(left: 8.0, top: 32),
                    child: Align(
                      alignment: Alignment.centerLeft,
                      child: Text('Cards',
                          style: TextStyle(
                              color: ArgonColors.text,
                              fontWeight: FontWeight.w600,
                              fontSize: 16)),
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.only(top: 16.0),
                    child: CardHorizontal(
                        cta: 'View article',
                        title: articlesCards['Ice Cream']?['title'],
                        img: articlesCards['Ice Cream']?['image'],
                        tap: () {
                          Navigator.push(
                              context,
                              MaterialPageRoute(
                                builder: (context) => ProductScreen(
                                  title: articlesCards['Ice Cream']?['title'],
                                  urlImg: articlesCards['Ice Cream']?['image'],
                                ),
                              ));
                        }),
                  ),
                  SizedBox(height: 8.0),
                  CardHorizontal(
                      cta: 'View article',
                      title: articlesCards['Fashion']?['title'],
                      img: articlesCards['Fashion']?['image'],
                      tap: () {
                        Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => ProductScreen(
                                title: articlesCards['Fashion']?['title'],
                                urlImg: articlesCards['Fashion']?['image'],
                              ),
                            ));
                      }),
                  SizedBox(height: 8.0),
                  CardSquare(
                      cta: 'View article',
                      title: articlesCards['Argon']?['title'],
                      img: articlesCards['Argon']?['image'],
                      tap: () {
                        Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => ProductScreen(
                                title: articlesCards['Argon']?['title'],
                                urlImg: articlesCards['Argon']?['image'],
                              ),
                            ));
                      }),
                  CardCategory(
                      tap: () {
                        Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => CategoryScreen(
                                  screenTitle: articlesCards['Music']?['title'],
                                  suggestionsArray: articlesCards['Music']
                                      ?['suggestions'],
                                  imgArray: articlesCards['Music']
                                      ?['products']),
                            ));
                      },
                      title: articlesCards['Music']?['title'],
                      img: articlesCards['Music']?['image']),
                  Padding(
                      padding: EdgeInsets.only(left: 25, right: 25),
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          Text(
                            'Album',
                            style: TextStyle(
                                fontWeight: FontWeight.bold,
                                fontSize: 16.0,
                                color: ArgonColors.text),
                          ),
                          Text(
                            'View All',
                            style: TextStyle(
                                color: ArgonColors.primary,
                                fontSize: 12.0,
                                fontWeight: FontWeight.w600),
                          )
                        ],
                      )),
                  SizedBox(
                    height: 250,
                    child: GridView.count(
                        primary: false,
                        padding: EdgeInsets.symmetric(
                            horizontal: 24.0, vertical: 15.0),
                        crossAxisSpacing: 10,
                        mainAxisSpacing: 10,
                        crossAxisCount: 3,
                        children: <Widget>[
                          Container(
                              height: 100,
                              decoration: BoxDecoration(
                                borderRadius:
                                    BorderRadius.all(Radius.circular(6.0)),
                                image: DecorationImage(
                                    image: NetworkImage(
                                        'https://images.unsplash.com/photo-1501601983405-7c7cabaa1581?fit=crop&w=240&q=80'),
                                    fit: BoxFit.cover),
                              )),
                          Container(
                              decoration: BoxDecoration(
                            borderRadius:
                                BorderRadius.all(Radius.circular(6.0)),
                            image: DecorationImage(
                                image: NetworkImage(
                                    'https://images.unsplash.com/photo-1543747579-795b9c2c3ada?fit=crop&w=240&q=80hoto-1501601983405-7c7cabaa1581?fit=crop&w=240&q=80'),
                                fit: BoxFit.cover),
                          )),
                          Container(
                              decoration: BoxDecoration(
                            borderRadius:
                                BorderRadius.all(Radius.circular(6.0)),
                            image: DecorationImage(
                                image: NetworkImage(
                                    'https://images.unsplash.com/photo-1551798507-629020c81463?fit=crop&w=240&q=80'),
                                fit: BoxFit.cover),
                          )),
                          Container(
                              decoration: BoxDecoration(
                            borderRadius:
                                BorderRadius.all(Radius.circular(6.0)),
                            image: DecorationImage(
                                image: NetworkImage(
                                    'https://images.unsplash.com/photo-1470225620780-dba8ba36b745?fit=crop&w=240&q=80'),
                                fit: BoxFit.cover),
                          )),
                          Container(
                              decoration: BoxDecoration(
                            borderRadius:
                                BorderRadius.all(Radius.circular(6.0)),
                            image: DecorationImage(
                                image: NetworkImage(
                                    'https://images.unsplash.com/photo-1503642551022-c011aafb3c88?fit=crop&w=240&q=80'),
                                fit: BoxFit.cover),
                          )),
                          Container(
                              decoration: BoxDecoration(
                            borderRadius:
                                BorderRadius.all(Radius.circular(6.0)),
                            image: DecorationImage(
                                image: NetworkImage(
                                    'https://images.unsplash.com/photo-1482686115713-0fbcaced6e28?fit=crop&w=240&q=80'),
                                fit: BoxFit.cover),
                          )),
                        ]),
                  ),
                  ProductCarousel(
                      imgArray: articlesCards['Music']?['products']),
                ],
              ),
            )));
  }
}
