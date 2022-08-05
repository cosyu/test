var __extends = (this && this.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (Object.prototype.hasOwnProperty.call(b, p)) d[p] = b[p]; };
        return extendStatics(d, b);
    };
    return function (d, b) {
        if (typeof b !== "function" && b !== null)
            throw new TypeError("Class extends value " + String(b) + " is not a constructor or null");
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var str1 = "Jason Yu";
console.log(str1);
var str2 = "the string";
console.log(str2);
var num = 123;
console.log(num);
var bl = true;
console.log(bl);
//array
//const arr1: string[] = [];
var arr1 = ["aaa", "bbb"];
arr1.push("abc");
arr1.push("def");
console.log(arr1);
//tuple, 
var tuple1;
tuple1 = [123, true, "test string"];
console.log(tuple1);
//a good practice is to make your tuple readonly
var tuple2;
tuple2 = [123, true, "test string"];
//tuple2.push("test");//throws error as it is readonly
console.log(tuple2);
//named tuple
var graph = [55.2, 41.3];
graph[0] = 1.0;
graph[1] = 2.0;
//graph[2] = 2.0;//throws error as it defines two index
console.log(graph);
var graph2 = [55.2, 41.3];
var x = graph2[0], y = graph2[1];
console.log(graph2);
//object
var car = {
    type: "Toyota",
    model: "Corolla",
    year: 2022
};
car.type = "Ford"; //change the object property
car.mileage = 2000;
console.log(car);
//Index signatures can be used for objects without a defined list of properties
var nameAgeMap = {}; //index:string is property type, number is property value type, like map
nameAgeMap.Jack = 25;
nameAgeMap.Mikde = 35;
//nameAgeMap.Mark = "test";// Error: Type 'string' is not assignable to type 'number'.
console.log(nameAgeMap); //{ Jack: 25, Mikde: 35 }
//integer enum
var Directions;
(function (Directions) {
    Directions[Directions["North"] = 0] = "North";
    Directions[Directions["East"] = 2] = "East";
    Directions[Directions["South"] = 3] = "South";
    Directions[Directions["West"] = 4] = "West";
})(Directions || (Directions = {}));
var dir1 = Directions.North;
console.log(dir1);
console.log(Directions.West);
console.log(Directions);
//You can assign unique number values for each enum value. Then the values will not incremented automatically
var StatusCode;
(function (StatusCode) {
    StatusCode[StatusCode["NotFound"] = 404] = "NotFound";
    StatusCode[StatusCode["Success"] = 200] = "Success";
    StatusCode[StatusCode["Accepted"] = 202] = "Accepted";
    StatusCode[StatusCode["BadRequest"] = 400] = "BadRequest";
})(StatusCode || (StatusCode = {}));
;
console.log(StatusCode);
console.log(StatusCode.NotFound);
//string enum
var Directions2;
(function (Directions2) {
    Directions2["North"] = "North";
    Directions2["East"] = "East";
    Directions2["South"] = "South";
    Directions2["West"] = "West";
})(Directions2 || (Directions2 = {}));
;
console.log(Directions2.North);
console.log(Directions2);
var rect1 = {
    height: 20,
    width: 10
};
console.log(rect1);
var rect2 = {
    height: 20,
    width: 10,
    color: "red"
};
rect2["height"] = 30; //assign property value
rect2["color"] = "green";
console.log(rect2);
//union type are used when a value can be more than a single type
function printStatusCode(code) {
    console.log("status code is ".concat(code));
}
printStatusCode(404);
printStatusCode("404");
//function
function getTime() {
    return new Date().getTime();
}
console.log(getTime());
function printHello() {
    console.log("Hello!");
}
printHello();
function multiply(a, b) {
    return a * b;
}
console.log(multiply(10, 20));
function add(a, b, c) {
    return a + b + (c || 1); //1 is default value for c
}
console.log(add(10, 20));
console.log(add(10, 20, 3));
function pow(a, b) {
    if (b === void 0) { b = 10; }
    return a * b;
}
console.log(pow(2));
console.log(pow(2, 8));
function divide(_a) {
    var divided = _a.divided, divisor = _a.divisor;
    return divided / divisor;
}
console.log(divide({ divided: 10, divisor: 2 }));
function add2(a, b) {
    var rest = [];
    for (var _i = 2; _i < arguments.length; _i++) {
        rest[_i - 2] = arguments[_i];
    }
    return a + b + rest.reduce(function (p, c) { return p - c; }, 0);
}
console.log(add2(10, 10, 20, 20, 20)); //10+10-20-20-20=40
//casting
var x1 = 'hello';
console.log(x1.length); //use as to cast
console.log(x1.length); //user <> to cast, this type of casting will not work with TSX, such as when working on React files
var x2 = 123;
//console.log((x2 as string).length);//compile error as number cannot cast to string
console.log(x2.length); //force cast, return undefined
//class
var Person = /** @class */ (function () {
    function Person(name) {
        this.name = name;
    }
    // public constructor(private name:string){} define property in constructor
    Person.prototype.getName = function () {
        //this.name = "another name";//throw error as readonly porperty cannot be changed except constructor
        return this.name;
    };
    return Person;
}());
var person = new Person("Jason");
console.log(person.getName());
var Rectangle3 = /** @class */ (function () {
    function Rectangle3(width, height) {
        this.width = width;
        this.height = height;
    }
    Rectangle3.prototype.getArea = function () {
        return this.width * this.height;
    };
    Rectangle3.prototype.toString = function () {
        return "Rectangle[width=".concat(this.width, ", height=").concat(this.height, "]");
    };
    return Rectangle3;
}());
var rect3 = new Rectangle3(10, 20);
console.log(rect3.getArea());
//extend class
var Square = /** @class */ (function (_super) {
    __extends(Square, _super);
    function Square(width) {
        return _super.call(this, width, width) || this;
    }
    //getArea inherited from Rectangle3
    //override the method of super class
    Square.prototype.toString = function () {
        return "Square[width=".concat(this.width, "]");
    };
    return Square;
}(Rectangle3));
var mySquare = new Square(20);
console.log(mySquare.getArea());
console.log(mySquare.toString());
//abstract class
var Polygon = /** @class */ (function () {
    function Polygon() {
    }
    Polygon.prototype.toString = function () {
        return "Polygon[area=".concat(this.getArea(), "]");
    };
    return Polygon;
}());
var Rectangle4 = /** @class */ (function (_super) {
    __extends(Rectangle4, _super);
    function Rectangle4(width, //define property in constructor
    height) {
        var _this = _super.call(this) || this;
        _this.width = width;
        _this.height = height;
        return _this;
    }
    Rectangle4.prototype.getArea = function () {
        return this.width * this.height;
    };
    return Rectangle4;
}(Polygon));
var rect4 = new Rectangle4(10, 20);
console.log(rect4.toString());
//Generics
function createPair(v1, v2) {
    return [v1, v2];
}
console.log(createPair('hello', 40)); //['hello', 40]
var NamedValue = /** @class */ (function () {
    function NamedValue(name) {
        this.name = name;
    }
    NamedValue.prototype.setValue = function (value) {
        this._value = value;
    };
    NamedValue.prototype.getValue = function () {
        return this._value;
    };
    NamedValue.prototype.toString = function () {
        return "".concat(this.name, ":").concat(this._value);
    };
    return NamedValue;
}());
var valueObject = new NamedValue("myNumber");
valueObject.setValue(10);
console.log(valueObject.toString());
var wrappedValue = { value: 10 };
console.log(wrappedValue);
//generic extends
function createLoggedPair(v1, v2) {
    console.log("creating pair:v1='".concat(v1, "',v2='").concat(v2, "'"));
    return [v1, v2];
}
;
createLoggedPair("abc", 123);
;
var pointPart = {};
pointPart.x = 10;
console.log(pointPart);
;
var myCar = {
    make: "Ford",
    model: "Focus",
    mileage: 12000
};
console.log(myCar);
//Record is a shortcut to defining an object type with a specific key type and value type.
var nameAgeMap2 = {};
nameAgeMap2.Alice = 21;
nameAgeMap2.Bob = 25;
nameAgeMap2["Mike"] = 24;
console.log(nameAgeMap2);
var bob = {
    name: "Bob"
    // `Omit` has removed age and location from the type and they can't be defined here
    //age : 20,
    //location:"HK"
};
console.log(bob);
//Pick removes all but the specified keys from an object type.
var bob2 = {
    name: "Bob2"
    // `Pick` has only kept name, so age and location were removed from the type and they can't be defined here
};
console.log(bob2);
var v3 = true; // a string cannot be used here since Exclude removed it from the type.
console.log(v3);
var point2 = {
    x: 10, y: 20
};
console.log(point2);
var point3 = {
    x: 10, y: 20
};
console.log(point3);
;
function printPersonProperty(person, property) {
    console.log("Printing person propety".concat(property, ":").concat(person[property]));
}
var p1 = {
    name: "Max",
    age: 27
};
printPersonProperty(p1, "name"); // Printing person property
//null & Undefined
var v4;
v4 = "hello";
console.log(v4);
;
function printYardSize(house) {
    var _a;
    var yardSize = (_a = house.yard) === null || _a === void 0 ? void 0 : _a.sqft2; //use with the ?. operator when accessing properties.
    if (yardSize === undefined) {
        console.log("No yard");
    }
    else {
        console.log("Yard is ".concat(yardSize, " sqft"));
    }
}
var home = {
    sqft: 500,
    yard: { sqft2: 300 }
};
printYardSize(home);
//Nullish Coalescence
function printMileage(mileage) {
    console.log("Mileage:".concat(mileage !== null && mileage !== void 0 ? mileage : "Not available")); //print the value if it is not null & undefined,otherwise print "Not available"
}
;
printMileage(null);
printMileage(undefined);
printMileage(100);
//Null Assertion
function getValue2() {
    return "hello";
}
var v5 = getValue2();
console.log("value length: ".concat(v5.length));
//any, it can be assigned any type value
var var1;
var1 = "abc";
console.log(var1);
var1 = 123;
console.log(var1);
var1 = true;
console.log(var1);
var1 = { "id": 1, "name": "Jason" };
console.log(var1);
//unknow, is simliar to any
var var2;
//var2 = "abc"; the variable will be string type
console.log(var2 + typeof var2);
var var3 = var2;
console.log(var3); //no error
var var4 = var2;
console.log(var4); //no error
//let var5 : boolean = var2;//Type 'unknown' is not assignable to type 'boolean'
//console.log(var2.length);//the TS compiler won't allow any operation on values typed unknown
