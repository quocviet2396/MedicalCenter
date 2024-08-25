import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:medicalcenter_mobile/constants/theme.dart';
import 'package:medicalcenter_mobile/widgets/card_shop.dart';
import 'package:medicalcenter_mobile/widgets/navbar.dart';
import 'package:medicalcenter_mobile/widgets/shopping.dart';

List<CardShop> smallCards = [
  CardShop(
      title: 'We\'ll start with the basic and go ...',
      image:
          'https://images.unsplash.com/photo-1586423702505-b13505519074?ixlib=rb-1.2.1&amp;ixid=eyJhcHBfaWQiOjEyMDd9&amp;auto=format&amp;fit=crop&amp;w=750&amp;q=80'),
  CardShop(
      title: 'Harley Davidson motorcycle has a ...',
      image:
          'https://images.unsplash.com/photo-1558980395-be8a5fcb4251?ixlib=rb-1.2.1&amp;ixid=eyJhcHBfaWQiOjEyMDd9&amp;auto=format&amp;fit=crop&amp;w=751&amp;q=80'),
  CardShop(
      title: 'Whether is a beatiful one you have to ...',
      image:
          'https://images.unsplash.com/photo-1447752875215-b2761acb3c5d?ixlib=rb-1.2.1&amp;ixid=eyJhcHBfaWQiOjEyMDd9&amp;auto=format&amp;fit=crop&amp;w=750&amp;q=80'),
];

class CartScreen extends StatefulWidget {
  const CartScreen({super.key});

  @override
  State<CartScreen> createState() => _CartScreenState();
}

class _CartScreenState extends State<CartScreen> {
  List<Map> shoppingCards = [
    {
      'body': "Discover What's New In Beauty And\nRec ...",
      'stock': true,
      'price': '180',
      'img':
          'https://images.unsplash.com/photo-1466150036782-869a824aeb25?fit=crop&w=1350&q=80',
    },
    {
      'body': 'Carry the charm of New Orlean\nwith you ...',
      'stock': false,
      'price': '230',
      'img':
          'https://images.unsplash.com/photo-1464375117522-1311d6a5b81f?fit=crop&w=1050&q=80',
    },
    {
      'body': 'Make the most of what you wear today with ...',
      'stock': false,
      'price': '140',
      'img':
          'https://images.unsplash.com/photo-1485120750507-a3bf477acd63?fit=crop&w=1050&q=80'
    }
  ];

  @override
  Widget build(BuildContext context) {
    num basketValue = 0;
    for (int i = 0; i < shoppingCards.length; i++) {
      basketValue += int.parse(shoppingCards[i]['price']);
    }

    return Scaffold(
      appBar: Navbar(
        title: 'Cart',
        backButton: true,
        rightOptions: false,
      ),
      body: SingleChildScrollView(
        child: SafeArea(
          bottom: true,
          child: Container(
              width: double.infinity,
              padding: EdgeInsets.only(
                  top: 30.0, left: 15.0, right: 15.0, bottom: 8.0),
              child: Column(
                mainAxisSize: MainAxisSize.max,
                children: [
                  Row(
                    mainAxisSize: MainAxisSize.max,
                    children: [
                      Text('Cart subtotal (${shoppingCards.length} items): ',
                          style: TextStyle(
                            fontSize: 16,
                          )),
                      Text('\$$basketValue',
                          style: TextStyle(
                              color: ArgonColors.error,
                              fontWeight: FontWeight.bold,
                              fontSize: 16))
                    ],
                  ),
                  SizedBox(
                    height: 20.0,
                  ),
                  Container(
                      width: double.infinity,
                      decoration: BoxDecoration(
                        boxShadow: [
                          BoxShadow(
                            color: Colors.grey.withOpacity(0.2),
                            spreadRadius: 1,
                            blurRadius: 7,
                            offset: Offset(0, 2), // changes position of shadow
                          ),
                        ],
                      ),
                      child: TextButton(
                        style: TextButton.styleFrom(
                            foregroundColor: ArgonColors.white,
                            backgroundColor: ArgonColors.primary,
                            shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(4))),
                        onPressed: () {
                          context.pushReplacement('/home');
                        },
                        child: Padding(
                          padding: const EdgeInsets.symmetric(
                              vertical: 6, horizontal: 8),
                          child: const Text('PROCESS TO CHECKOUT',
                              style: TextStyle(
                                  fontWeight: FontWeight.w600, fontSize: 14.0)),
                        ),
                      )),
                  Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      for (var i = 0; i < shoppingCards.length; i++)
                        Padding(
                            padding: EdgeInsets.only(top: 32.0),
                            child: Shopping(
                              body: shoppingCards[i]['body'],
                              stock: shoppingCards[i]['stock'],
                              price: shoppingCards[i]['price'],
                              img: shoppingCards[i]['img'],
                              deleteOnPress: () {
                                setState(() {
                                  shoppingCards.removeAt(i);
                                });
                              },
                            ))
                    ],
                  ),
                  SizedBox(
                    height: 20.0,
                  ),
                  Padding(
                    padding: const EdgeInsets.all(15.0),
                    child: Text(
                      'Customers who shopped for items in your cart also shopped for:',
                      style: TextStyle(
                          color: ArgonColors.initial,
                          fontSize: 16.0,
                          fontWeight: FontWeight.w700),
                    ),
                  ),
                  SizedBox(
                    height: 10.0,
                  ),
                  SizedBox(
                    height: 300.0,
                    child: ListView.builder(
                        itemCount: smallCards.length,
                        scrollDirection: Axis.horizontal,
                        itemBuilder: (BuildContext context, int index) =>
                            Column(
                              children: [
                                Column(
                                  children: [
                                    smallCards[index],
                                    SizedBox(
                                        width: 170.0,
                                        child: TextButton(
                                          style: TextButton.styleFrom(
                                              foregroundColor:
                                                  ArgonColors.white,
                                              backgroundColor:
                                                  ArgonColors.primary,
                                              shape: RoundedRectangleBorder(
                                                  borderRadius:
                                                      BorderRadius.circular(
                                                          4))),
                                          onPressed: () {
                                            context.pushReplacement('/home');
                                          },
                                          child: Padding(
                                            padding: const EdgeInsets.symmetric(
                                                vertical: 6, horizontal: 8),
                                            child: Text('ADD TO CART',
                                                style: TextStyle(
                                                    fontWeight: FontWeight.w600,
                                                    fontSize: 12.0)),
                                          ),
                                        )),
                                  ],
                                ),
                              ],
                            )),
                  ),
                  Container(
                      width: double.infinity,
                      decoration: BoxDecoration(
                        boxShadow: [
                          BoxShadow(
                            color: Colors.grey.withOpacity(0.2),
                            spreadRadius: 1,
                            blurRadius: 7,
                            offset: Offset(0, 2), // changes position of shadow
                          ),
                        ],
                      ),
                      child: TextButton(
                        style: TextButton.styleFrom(
                            foregroundColor: ArgonColors.white,
                            backgroundColor: ArgonColors.primary,
                            shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(4))),
                        onPressed: () {
                          context.pushReplacement('/home');
                        },
                        child: Padding(
                          padding: const EdgeInsets.symmetric(
                              vertical: 6, horizontal: 8),
                          child: const Text('PROCESS TO CHECKOUT',
                              style: TextStyle(
                                  fontWeight: FontWeight.w600, fontSize: 14.0)),
                        ),
                      )),
                ],
              )),
        ),
      ),
    );
  }
}
