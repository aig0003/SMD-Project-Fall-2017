CREATE TABLE storeItem(id INTEGER PRIMARY KEY, name STRING, price DOUBLE, quantity INTEGER, description STRING, supplier STRING, measurement STRING, expirationDate DATE);
INSERT into storeItem VALUES(100001, "Magic Beans", 13.37, 254, "These are super expensive fancy beans. They don't taste very good", "Beans Inc.", "1 bean", '2019-06-01');
INSERT into storeItem VALUES(100002, "Bananas", 0.50, 45, "Bananas are high in potassium and are good for you", "Banana Tree", "per lb.", '2017-09-13');
INSERT into storeItem VALUES(100003, "Tortillas", 5.00, 0, "Corn tortillas are much healthier than their flour counterparts", "The Korn Kids", "1 bag(10 tortillas)", '2016-01-01');
INSERT into storeItem VALUES(100004, "Dog Food", 4.20, 30, "Dogs need food too!", "Kellogs", "1 bag(1,000 pellets)", '2017-12-25');
INSERT into storeItem VALUES(100005, "Fidget Spinners", 2.00, 5000000, "A fidget spinner is a toy that consists of a ball-bearing in the center of a multi-lobed flat structure made from metal or plastic designed to spin along its axis with little effort. Fidget spinners became popular toys in April 2017, although similar devices had been invented as early as 1993. The popularity of the toy among children and teenagers has led some schools to ban use of the spinners in class for being a distraction, while other schools have allowed the toy to be used discreetly. The toy has been advertised as helping people who have trouble with focusing or fidgeting by relieving nervous energy or psychological stress. There are claims that a fidget spinner can be used to help calm people down who suffer from anxiety and other neurological disorders like ADHD and autism. However, as of May 2017, there is no scientific evidence that they are effective as a treatment for autism or ADHD.", "Kellogs", "1 spinner", '4200-06-09');

CREATE TABLE userList(id INTEGER PRIMARY KEY, username STRING, password STRING, realName STRING, profilePicture STRING);
INSERT into userList VALUES(999999999, "thatchdawg", "securePassw0rd", "Thatcher", "https://assets-cdn.github.com/images/modules/open_graph/github-octocat.png");
INSERT into userList VALUES(999999998, "xxMEMESxx", "memes", "Alec", "https://cdn.drawception.com/images/panels/2017/5-7/FTX3Xg1POa-2.png");
INSERT into userList VALUES(999999997, "tagivegoodgrade", "nofail", "Phong", "https://weedmaps-uploads-production.weedmaps.com/uploads/avatars/deliveries/21745/large_IMG_1785.JPG");

CREATE TABLE orders(orderId INTEGER PRIMARY KEY, customerName STRING, totalCost DOUBLE, totalTax DOUBLE, orderDate DATE);
INSERT into orders VALUES(0001, "Mike Wazowski", 40000000, 5.50, '1805-01-01');
INSERT into orders VALUES(0002, "Meme Man", 1, 0.01, '2017-01-01');