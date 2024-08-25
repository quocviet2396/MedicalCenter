import 'package:flutter/material.dart';
import 'package:medicalcenter_mobile/constants/theme.dart';
import 'package:medicalcenter_mobile/data/app_images.dart';
import 'package:medicalcenter_mobile/data/productcard.dart';
import 'package:medicalcenter_mobile/screens/beauty_screen.dart';
import 'package:medicalcenter_mobile/screens/fashion_screen.dart';
import 'package:medicalcenter_mobile/screens/product_screen.dart';
import 'package:medicalcenter_mobile/widgets/card_horizontal.dart';
import 'package:medicalcenter_mobile/widgets/card_small.dart';
import 'package:medicalcenter_mobile/widgets/navbar.dart';
import 'package:medicalcenter_mobile/widgets/table_cell.dart';

class SearchScreen extends StatefulWidget {
  const SearchScreen({super.key});

  @override
  State<SearchScreen> createState() => _SearchScreenState();
}

class _SearchScreenState extends State<SearchScreen> {
  final myController = TextEditingController();

  String searchText = 'Nike';
  List results = [
    {
      'title': 'Nike Air Max',
      'image': images['fashion']?['Nike Air Max'],
    },
    {
      'title': 'Blue Adidas',
      'image': images['fashion']?['Blue Adidas'],
    },
  ];

  @override
  void dispose() {
    myController.dispose();
    super.dispose();
  }

  _searchText(String searchTerm) {
    setState(() {
      results.clear();
    });
    if (searchTerm.isNotEmpty) {
      productCards.forEach((k, v) => v.map((e) {
            if (e['title'].toLowerCase().contains(searchTerm.toLowerCase())) {
              setState(() {
                results.add(e);
              });
            }
          }).toList());
    }
    print(results);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: Navbar(
            backButton: true,
            title: 'Search',
            rightOptions: false,
            searchBar: true,
            isOnSearch: true,
            searchOnChanged: (String typedText) {
              setState(() {
                searchText = typedText;
              });
              _searchText(searchText);
            },
            searchAutofocus: true,
            searchController: myController),
        backgroundColor: ArgonColors.bgColorScreen,
        body: Container(
            padding: EdgeInsets.only(left: 27, right: 27, top: 24),
            child: searchText.isNotEmpty
                ? (results.isNotEmpty
                    ? ListView.builder(
                        itemCount: results.length,
                        itemBuilder: (BuildContext context, int index) =>
                            Padding(
                              padding: const EdgeInsets.only(bottom: 8.0),
                              child: CardHorizontal(
                                  title: results[index]['title'],
                                  img: results[index]['image'],
                                  tap: () {
                                    Navigator.push(
                                        context,
                                        MaterialPageRoute(
                                          builder: (context) => ProductScreen(
                                            title: results[index]['title'],
                                            urlImg: results[index]['image'],
                                          ),
                                        ));
                                  }),
                            ))
                    : SingleChildScrollView(
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text("We didn't find \"$searchText\" in our store.",
                                style: TextStyle(
                                    fontSize: 18, color: ArgonColors.text)),
                            Padding(
                              padding: const EdgeInsets.only(top: 16.0),
                              child: Text(
                                  'You can see more products from other categories.',
                                  style: TextStyle(
                                      fontSize: 18, color: ArgonColors.text)),
                            ),
                            Padding(
                              padding:
                                  const EdgeInsets.only(top: 8.0, bottom: 8.0),
                              child: TableCellSettings(
                                  title: 'Fashion',
                                  onTap: () {
                                    Navigator.push(
                                        context,
                                        MaterialPageRoute(
                                          builder: (context) => FashionScreen(),
                                        ));
                                  }),
                            ),
                            Padding(
                              padding:
                                  const EdgeInsets.only(top: 8.0, bottom: 8.0),
                              child: TableCellSettings(
                                  title: 'Beauty',
                                  onTap: () {
                                    Navigator.push(
                                        context,
                                        MaterialPageRoute(
                                          builder: (context) => BeautyScreen(),
                                        ));
                                  }),
                            ),
                            Padding(
                              padding: const EdgeInsets.only(
                                  top: 16.0, bottom: 16.0),
                              child: Text('Daily Deals',
                                  style: TextStyle(
                                      fontSize: 18, color: ArgonColors.text)),
                            ),
                            Row(
                              children: [
                                CardSmall(
                                    img: productCards['Places']?[2]['image'],
                                    title: productCards['Places']?[2]['title'],
                                    tap: () {
                                      Navigator.push(
                                          context,
                                          MaterialPageRoute(
                                            builder: (context) => ProductScreen(
                                              title: productCards['Places']?[2]
                                                  ['title'],
                                              urlImg: productCards['Places']?[2]
                                                  ['image'],
                                            ),
                                          ));
                                    }),
                                CardSmall(
                                    img: productCards['Places']?[1]['image'],
                                    title: productCards['Places']?[1]['title'],
                                    tap: () {
                                      Navigator.push(
                                          context,
                                          MaterialPageRoute(
                                            builder: (context) => ProductScreen(
                                              title: productCards['Places']?[1]
                                                  ['title'],
                                              urlImg: productCards['Places']?[1]
                                                  ['image'],
                                            ),
                                          ));
                                    })
                              ],
                            ),
                            CardHorizontal(
                                img: productCards['Places']?[0]['image'],
                                title: productCards['Places']?[0]['title'],
                                tap: () {
                                  Navigator.push(
                                      context,
                                      MaterialPageRoute(
                                        builder: (context) => ProductScreen(
                                          title: productCards['Places']?[0]
                                              ['title'],
                                          urlImg: productCards['Places']?[0]
                                              ['image'],
                                        ),
                                      ));
                                })
                          ],
                        ),
                      ))
                : Container()));
  }
}
