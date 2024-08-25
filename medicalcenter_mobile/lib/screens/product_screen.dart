import 'package:flutter/material.dart';
import 'package:medicalcenter_mobile/constants/theme.dart';
import 'package:medicalcenter_mobile/widgets/image_slider.dart';
import 'package:medicalcenter_mobile/widgets/navbar.dart';

class ProductScreen extends StatelessWidget {
  final String title;
  final String urlImg;

  ProductScreen(
      {super.key,
      this.title = 'Shoes',
      this.urlImg =
          'https://img.freepik.com/free-photo/pair-trainers_144627-3799.jpg?w=1480&t=st=1706372663~exp=1706373263~hmac=326190418fc4790da12ac5bed9c7372654de84fc55cdab94a1a9e4654c3e2bae'});

  @override
  Widget build(BuildContext context) {
    final double height = MediaQuery.of(context).size.height;
    final List<String> imgList = [urlImg, urlImg];
    return Scaffold(
        appBar: Navbar(
          transparent: true,
          title: '',
          backButton: true,
        ),
        extendBody: true,
        backgroundColor: ArgonColors.bgColorScreen,
        extendBodyBehindAppBar: true,
        body: Column(children: [
          SizedBox(
              height: 0.35 * height, child: ImageSlider(imgArray: imgList)),
          FractionalTranslation(
            translation: Offset(0.0, -0.05),
            child: Stack(children: [
              Padding(
                padding: const EdgeInsets.only(left: 9.0, right: 9.0),
                child: SizedBox(
                  height: 0.65 * height,
                  child: Card(
                      semanticContainer: true,
                      clipBehavior: Clip.antiAliasWithSaveLayer,
                      elevation: 1.0,
                      shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.only(
                              topLeft: Radius.circular(4.0),
                              topRight: Radius.circular(4.0))),
                      child: Padding(
                        padding: const EdgeInsets.all(16.0),
                        child: Column(
                          mainAxisAlignment: MainAxisAlignment.start,
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Padding(
                              padding:
                                  const EdgeInsets.only(top: 8.0, bottom: 28.0),
                              child: Text(title,
                                  style: TextStyle(
                                      fontSize: 28.0, color: ArgonColors.text)),
                            ),
                            Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                Row(
                                  children: [
                                    CircleAvatar(
                                      backgroundImage: AssetImage(
                                          'assets/imgs/profile-screen-avatar.jpg'),
                                    ),
                                    SizedBox(width: 10.0),
                                    Column(
                                      crossAxisAlignment:
                                          CrossAxisAlignment.start,
                                      children: [
                                        Text('Jessica Jones',
                                            style: TextStyle(
                                                color: ArgonColors.text,
                                                fontSize: 14.0)),
                                        Text('Pro Seller',
                                            style: TextStyle(
                                                color: ArgonColors.text,
                                                fontSize: 14.0,
                                                fontWeight: FontWeight.w200))
                                      ],
                                    ),
                                  ],
                                ),
                                Text('\$899',
                                    style: TextStyle(
                                        color: ArgonColors.text,
                                        fontSize: 18.0))
                              ],
                            ),
                            SizedBox(height: 18),
                            Text('Size',
                                style: TextStyle(
                                    color: ArgonColors.text, fontSize: 16.0)),
                            Padding(
                              padding: const EdgeInsets.only(top: 16),
                              child: Column(
                                children: [
                                  Row(
                                    children: [
                                      Expanded(
                                        child: Container(
                                            padding: EdgeInsets.only(
                                                top: 16, bottom: 16),
                                            decoration: BoxDecoration(
                                                border: Border(
                                                  top: BorderSide(
                                                      color: ArgonColors.border,
                                                      width: 0.5),
                                                  left: BorderSide(
                                                      color: ArgonColors.border,
                                                      width: 0.5),
                                                  right: BorderSide(
                                                      color: ArgonColors.border,
                                                      width: 0.5),
                                                  bottom: BorderSide(
                                                      color: ArgonColors.border,
                                                      width: 0.5),
                                                ),
                                                borderRadius: BorderRadius.only(
                                                    topLeft:
                                                        Radius.circular(4.0))),
                                            child: Center(
                                              child: Text('36.5',
                                                  style: TextStyle(
                                                      color: ArgonColors.text,
                                                      fontSize: 14.0)),
                                            )),
                                      ),
                                      Expanded(
                                        child: Container(
                                            padding: EdgeInsets.only(
                                              top: 16,
                                              bottom: 16,
                                            ),
                                            decoration: BoxDecoration(
                                                border: Border(
                                              top: BorderSide(
                                                  color: ArgonColors.border,
                                                  width: 0.5),
                                              bottom: BorderSide(
                                                  color: ArgonColors.border,
                                                  width: 0.5),
                                            )),
                                            child: Center(
                                              child: Text('36.5',
                                                  style: TextStyle(
                                                      color: ArgonColors.text,
                                                      fontSize: 14.0)),
                                            )),
                                      ),
                                      Expanded(
                                        child: Container(
                                            padding: EdgeInsets.only(
                                              top: 16,
                                              bottom: 16,
                                            ),
                                            decoration: BoxDecoration(
                                                border: Border(
                                                  top: BorderSide(
                                                      color: ArgonColors.border,
                                                      width: 0.5),
                                                  left: BorderSide(
                                                      color: ArgonColors.border,
                                                      width: 0.5),
                                                  right: BorderSide(
                                                      color: ArgonColors.border,
                                                      width: 0.5),
                                                  bottom: BorderSide(
                                                      color: ArgonColors.border,
                                                      width: 0.5),
                                                ),
                                                borderRadius: BorderRadius.only(
                                                    topRight:
                                                        Radius.circular(4.0))),
                                            child: Center(
                                              child: Text('36.5',
                                                  style: TextStyle(
                                                      color: ArgonColors.text,
                                                      fontSize: 14.0)),
                                            )),
                                      )
                                    ],
                                  ),
                                  Row(children: [
                                    Expanded(
                                      child: Container(
                                          padding: EdgeInsets.only(
                                              top: 16, bottom: 16),
                                          decoration: BoxDecoration(
                                              border: Border(
                                                top: BorderSide(
                                                    color: ArgonColors.border,
                                                    width: 0.5),
                                                left: BorderSide(
                                                    color: ArgonColors.border,
                                                    width: 0.5),
                                                right: BorderSide(
                                                    color: ArgonColors.border,
                                                    width: 0.5),
                                                bottom: BorderSide(
                                                    color: ArgonColors.border,
                                                    width: 0.5),
                                              ),
                                              borderRadius: BorderRadius.only(
                                                  bottomLeft:
                                                      Radius.circular(4.0))),
                                          child: Center(
                                            child: Text('36.5',
                                                style: TextStyle(
                                                    color: ArgonColors.text,
                                                    fontSize: 14.0)),
                                          )),
                                    ),
                                    Expanded(
                                      child: Container(
                                          padding: EdgeInsets.only(
                                            top: 16,
                                            bottom: 16,
                                          ),
                                          decoration: BoxDecoration(
                                              border: Border(
                                            top: BorderSide(
                                                color: ArgonColors.border,
                                                width: 0.5),
                                            bottom: BorderSide(
                                                color: ArgonColors.border,
                                                width: 0.5),
                                          )),
                                          child: Center(
                                            child: Text('36.5',
                                                style: TextStyle(
                                                    color: ArgonColors.text,
                                                    fontSize: 14.0)),
                                          )),
                                    ),
                                    Expanded(
                                      child: Container(
                                          padding: EdgeInsets.only(
                                            top: 16,
                                            bottom: 16,
                                          ),
                                          decoration: BoxDecoration(
                                              border: Border(
                                                top: BorderSide(
                                                    color: ArgonColors.border,
                                                    width: 0.5),
                                                left: BorderSide(
                                                    color: ArgonColors.border,
                                                    width: 0.5),
                                                right: BorderSide(
                                                    color: ArgonColors.border,
                                                    width: 0.5),
                                                bottom: BorderSide(
                                                    color: ArgonColors.border,
                                                    width: 0.5),
                                              ),
                                              borderRadius: BorderRadius.only(
                                                  bottomRight:
                                                      Radius.circular(4.0))),
                                          child: Center(
                                            child: Text('36.5',
                                                style: TextStyle(
                                                    color: ArgonColors.text,
                                                    fontSize: 14.0)),
                                          )),
                                    )
                                  ])
                                ],
                              ),
                            ),
                            Container(
                              width: MediaQuery.of(context).size.width,
                              padding: EdgeInsets.only(top: 24),
                              child: ElevatedButton(
                                onPressed: () {},
                                child: const Text('ADD TO CART'),
                              ),
                            )
                          ],
                        ),
                      )),
                ),
              ),
              FractionalTranslation(
                translation: Offset(-0.08, -0.4),
                child: Align(
                  alignment: Alignment.topRight,
                  child: CircleAvatar(
                    radius: 30,
                    backgroundColor: ArgonColors.primary,
                    child: IconButton(
                      onPressed: () {},
                      icon: Icon(Icons.message),
                      color: ArgonColors.white,
                    ),
                  ),
                ),
              ),
            ]),
          ),
        ]));
  }
}
