import 'package:flutter/material.dart';
import 'package:medicalcenter_mobile/constants/theme.dart';
import 'package:medicalcenter_mobile/data/fashioncard.dart';
import 'package:medicalcenter_mobile/screens/product_screen.dart';
import 'package:medicalcenter_mobile/widgets/card_horizontal.dart';
import 'package:medicalcenter_mobile/widgets/card_small.dart';
import 'package:medicalcenter_mobile/widgets/card_square.dart';
import 'package:medicalcenter_mobile/widgets/navbar.dart';

class FashionScreen extends StatefulWidget {
  const FashionScreen({super.key});

  @override
  State<FashionScreen> createState() => _FashionScreenState();
}

class _FashionScreenState extends State<FashionScreen> {
  final List<String> tags = ['Shoes', 'Beauty', 'Fashion', 'Places'];
  static String currentTag = '';

  _getCurrentPage(activeTag) {
    setState(() {
      currentTag = activeTag;
      print('currentTag is $currentTag');
    });
  }

  @override
  void initState() {
    currentTag = tags[0];
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: Navbar(
          title: 'Fashion',
          tags: tags,
          getCurrentPage: _getCurrentPage,
          backButton: true,
        ),
        backgroundColor: ArgonColors.bgColorScreen,
        body: Container(
            padding: EdgeInsets.only(left: 24.0, right: 24.0),
            child: SingleChildScrollView(
              child: Column(
                children: [
                  Padding(
                    padding: const EdgeInsets.only(top: 16.0),
                    child: CardHorizontal(
                        cta: 'View article',
                        title: fashionCards[currentTag]?[0]['title'],
                        img: fashionCards[currentTag]?[0]['image'],
                        tap: () {
                          Navigator.push(
                              context,
                              MaterialPageRoute(
                                builder: (context) => ProductScreen(
                                  title: fashionCards[currentTag]?[0]['title'],
                                  urlImg: fashionCards[currentTag]?[0]['image'],
                                ),
                              ));
                        }),
                  ),
                  SizedBox(height: 8.0),
                  Row(
                    children: [
                      CardSmall(
                          cta: 'View article',
                          title: fashionCards[currentTag]?[1]['title'],
                          img: fashionCards[currentTag]?[1]['image'],
                          tap: () {
                            Navigator.push(
                                context,
                                MaterialPageRoute(
                                  builder: (context) => ProductScreen(
                                    title: fashionCards[currentTag]?[1]
                                        ['title'],
                                    urlImg: fashionCards[currentTag]?[1]
                                        ['image'],
                                  ),
                                ));
                          }),
                      CardSmall(
                          cta: 'View article',
                          title: fashionCards[currentTag]?[2]['title'],
                          img: fashionCards[currentTag]?[2]['image'],
                          tap: () {
                            Navigator.push(
                                context,
                                MaterialPageRoute(
                                  builder: (context) => ProductScreen(
                                    title: fashionCards[currentTag]?[2]
                                        ['title'],
                                    urlImg: fashionCards[currentTag]?[2]
                                        ['image'],
                                  ),
                                ));
                          })
                    ],
                  ),
                  SizedBox(height: 8.0),
                  CardHorizontal(
                      cta: 'View article',
                      title: fashionCards[currentTag]?[3]['title'],
                      img: fashionCards[currentTag]?[3]['image'],
                      tap: () {
                        Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => ProductScreen(
                                title: fashionCards[currentTag]?[3]['title'],
                                urlImg: fashionCards[currentTag]?[3]['image'],
                              ),
                            ));
                      }),
                  SizedBox(height: 8.0),
                  Padding(
                    padding: const EdgeInsets.only(bottom: 32.0),
                    child: CardSquare(
                        cta: 'View article',
                        title: fashionCards[currentTag]?[4]['title'],
                        img: fashionCards[currentTag]?[4]['image'],
                        tap: () {
                          Navigator.push(
                              context,
                              MaterialPageRoute(
                                builder: (context) => ProductScreen(
                                  title: fashionCards[currentTag]?[4]['title'],
                                  urlImg: fashionCards[currentTag]?[4]['image'],
                                ),
                              ));
                        }),
                  )
                ],
              ),
            )));
  }
}
