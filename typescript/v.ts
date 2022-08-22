let str1 = "Jason Yu";
console.log(str1);

let str2: string = "the string";
console.log(str2);

let num : number = 123;
console.log(num);

let bl : boolean = true;
console.log(bl);

//array
//const arr1: string[] = [];
const arr1: string[] = ["aaa","bbb"];
arr1.push("abc");
arr1.push("def");
console.log(arr1);

//tuple, 
let tuple1 : [number,boolean,string];
tuple1 = [123,true,"test string"];
console.log(tuple1);

//a good practice is to make your tuple readonly
let tuple2 : readonly [number,boolean,string];
tuple2 = [123,true,"test string"];
//tuple2.push("test");//throws error as it is readonly
console.log(tuple2);

//named tuple
const graph : [x:number,y:number] = [55.2,41.3];
graph[0] = 1.0;
graph[1] = 2.0;
//graph[2] = 2.0;//throws error as it defines two index
console.log(graph);

const graph2 : [number,number] = [55.2,41.3];
const [x,y] = graph2;
console.log(graph2);

//object
const car : {type:string,model:string,year:number,mileage?: number} = { //use ? to define optional property
    type:"Toyota",
    model:"Corolla",
    year:2022
}
car.type = "Ford";//change the object property
car.mileage = 2000;
console.log(car);
//Index signatures can be used for objects without a defined list of properties
const nameAgeMap : {[index:string]:number} = {};//index:string is property type, number is property value type, like map
nameAgeMap.Jack = 25;
nameAgeMap.Mikde = 35;
//nameAgeMap.Mark = "test";// Error: Type 'string' is not assignable to type 'number'.
console.log(nameAgeMap);//{ Jack: 25, Mikde: 35 }

//integer enum
enum Directions{
    North,//fist default is 0,add 1 to each additional value
    East = 2,//You can set the value of the specify enum and have it auto increment
    South,
    West
}
let dir1 = Directions.North;
console.log(dir1);
console.log(Directions.West);
console.log(Directions);

//You can assign unique number values for each enum value. Then the values will not incremented automatically
enum StatusCode{
    NotFound = 404,
    Success = 200,
    Accepted = 202,
    BadRequest = 400
};
console.log(StatusCode);
console.log(StatusCode.NotFound);

//string enum
enum Directions2{
    North = "North",
    East = "East",
    South = "South",
    West = "West"
};
console.log(Directions2.North);
console.log(Directions2);

//interface, it is also data type
interface Rectangle{
    height : number,
    width : number
}

const rect1 : Rectangle = {
    height : 20,
    width : 10
};
console.log(rect1);

//Extending an interface means you are creating a new interface with the same properties as the original, plus something new.
interface ColorRectangle extends Rectangle{
    color : string
}

const rect2 : ColorRectangle = {
    height: 20,
    width:10,
    color:"red"
}
rect2["height"] = 30;//assign property value
rect2["color"] = "green";
console.log(rect2);

//union type are used when a value can be more than a single type
function printStatusCode(code : string | number){
    console.log(`status code is ${code}`);
}
printStatusCode(404);
printStatusCode("404");

//function
function getTime(): number{//it specifies this function returns a number
    return new Date().getTime();
}
console.log(getTime());

function printHello() : void{//it specifies this function desnot return anything
    console.log("Hello!");
}
printHello();

function multiply(a:number,b:number) : number{//it is good particate to define the return type
    return a * b;
}
console.log(multiply(10,20));

function add(a:number,b:number,c?:number) : number{//use ? to define optional parameter
    return a+b+(c || 1);//1 is default value for c
}
console.log(add(10,20));
console.log(add(10,20,3));

function pow(a:number,b:number = 10) : number{// set default value for parameter
    return a * b;
}
console.log(pow(2));
console.log(pow(2,8));

function divide({divided,divisor}:{divided:number,divisor:number}) : number{//defined named parameter
    return divided/divisor;
}
console.log(divide({divided:10,divisor:2}));

function add2(a:number,b:number,...rest:number[]) : number{//rest parameter
    return a + b + rest.reduce((p,c)=>p-c,0);
}
console.log(add2(10,10,20,20,20));//10+10-20-20-20=40

//casting
let x1: unknown = 'hello';
console.log((x1 as string).length);//use as to cast
console.log((<string>x1).length)//user <> to cast, this type of casting will not work with TSX, such as when working on React files

let x2 = 123;
//console.log((x2 as string).length);//compile error as number cannot cast to string
console.log(((x2 as unknown) as string).length); //force cast, return undefined

//class
class Person{
    private readonly name:string;//readonly property only can be changed in constructor

    public constructor(name:string){
        this.name = name;
       
    }
   // public constructor(private name:string){} define property in constructor

    public getName():string{
        //this.name = "another name";//throw error as readonly porperty cannot be changed except constructor
        return this.name;
    }
}
const person = new Person("Jason");
console.log(person.getName());

//implement interface
interface Shape{
    getArea:()=>number;//define the function which needs to be implemented
}
class Rectangle3 implements Shape{

    protected readonly width:number; //reaonly property only can be changed in constructor
    protected readonly height:number;
   
    public constructor(width:number,height:number){
        this.width = width;
        this.height = height;
    }

    public getArea():number{
        return this.width * this.height;
    }

    public toString():string{
        return `Rectangle[width=${this.width}, height=${this.height}]`;
    }
}
const rect3 = new Rectangle3(10,20);
console.log(rect3.getArea());

//extend class
class Square extends Rectangle3{
    public constructor(width:number) {
        super(width,width);//call super constructor
    }
    //getArea inherited from Rectangle3

    //override the method of super class
    public toString(): string {
        return `Square[width=${this.width}]`;
    }
}
const mySquare = new Square(20);
console.log(mySquare.getArea());
console.log(mySquare.toString());

//abstract class
abstract class Polygon{
    public abstract getArea():number;

    public toString():string{
        return `Polygon[area=${this.getArea()}]`;
    }
}

class Rectangle4 extends Polygon{

    public constructor(protected readonly width:number,//define property in constructor
        protected readonly height:number){
            super();
    }

    public getArea(): number {
        return this.width * this.height;
    }
}
const rect4 = new Rectangle4(10,20);
console.log(rect4.toString());

//Generics
function createPair<S,T>(v1:S,v2:T):[S,T]{
    return [v1,v2];
}
console.log(createPair<string,number>('hello',40));//['hello', 40]

class NamedValue<T=string>{ //default is string, it can be removed
    private _value : T | undefined;//more than a single type

    public constructor(private name: string){}

    public setValue(value: T){
        this._value = value;
    }

    public getValue() : T | undefined{
        return this._value;
    }

    public toString():string{
        return `${this.name}:${this._value}`;
    }
}

let valueObject = new NamedValue<number>("myNumber");
valueObject.setValue(10);
console.log(valueObject.toString());

//aliase name for generics
type Wrapped<T> = {value:T};
const wrappedValue : Wrapped<number> = {value:10};
console.log(wrappedValue);

//generic extends
function createLoggedPair<S extends string|number,T extends string|number>(v1:S,v2:T):[S,T]{
    console.log(`creating pair:v1='${v1}',v2='${v2}'`);
    return [v1,v2];
};

createLoggedPair("abc",123);

//Partial  changes all the properties in an object to be optional.
interface Point{
    x:number,
    y:number
};

let pointPart : Partial<Point> = {};
pointPart.x = 10;
console.log(pointPart);

//Required changes all the properties in an object to be required.
interface Car2{
    make:string,
    model:string,
    mileage?:number,
};
let myCar : Required<Car2> = {
    make : "Ford",
    model:"Focus",
    mileage:12000
}
console.log(myCar);

//Record is a shortcut to defining an object type with a specific key type and value type.
const nameAgeMap2 : Record<string,number> = {};
nameAgeMap2.Alice = 21;
nameAgeMap2.Bob = 25;
nameAgeMap2["Mike"] = 24;
console.log(nameAgeMap2);

//Omit removes keys from an object type.
interface Person2{
    name:string,
    age:number,
    location?:string
}
const bob : Omit<Person2,'age'|'location'> = {
    name : "Bob"
    // `Omit` has removed age and location from the type and they can't be defined here
    //age : 20,
    //location:"HK"
};
console.log(bob);

//Pick removes all but the specified keys from an object type.
const bob2 : Pick<Person2,"name"> = {
    name:"Bob2"
    // `Pick` has only kept name, so age and location were removed from the type and they can't be defined here
};
console.log(bob2);

//Exclude removes types from a union.

type Primitive = string | number | boolean;
const v3 : Exclude<Primitive,string> = true;// a string cannot be used here since Exclude removed it from the type.
console.log(v3);

//ReturnType extracts the return type of a function type.
type PointGenerator = () =>{x:number;y:number;};
const point2 : ReturnType<PointGenerator> = {
    x:10,y:20
};
console.log(point2);

//Parameters extracts the parameter types of a function type as an array.
type PointPrinter = (p:{x:number;y:number;}) => void;
const point3 : Parameters<PointPrinter>[0] = {
    x:10,y:20
};
console.log(point3);

//keyof is a keyword in TypeScript which is used to extract the key type from an object type.
interface Person3{
    name:string,
    age:number
};
function printPersonProperty(person:Person3, property: keyof Person3){
    console.log(`Printing person propety${property}:${person[property]}`);
}

const p1:Person3 = {
    name:"Max",
    age:27
};
printPersonProperty(p1,"name");// Printing person property

//null & Undefined
let v4 : string | number ;
v4 = "hello";
console.log(v4);

//optional chaining allows accessing properties on an object, that may or may not exist(undefined), with a compact syntax
interface House{
    sqft : number,
    yard?:{sqft2 : number}
};
function printYardSize(house : House) : void{
    const yardSize = house.yard?.sqft2;//use with the ?. operator when accessing properties.
    if(yardSize === undefined){
        console.log("No yard");
    }else{
        console.log(`Yard is ${yardSize} sqft`);
    }
}
let home : House = {
    sqft : 500,
    yard : {sqft2:300}
};
printYardSize(home);

//Nullish Coalescence
function printMileage(mileage : number | null | undefined){
    console.log(`Mileage:${mileage ?? "Not available"}`);//print the value if it is not null & undefined,otherwise print "Not available"
};

printMileage(null);
printMileage(undefined);
printMileage(100);

//Null Assertion
function getValue2(): string | undefined{
    return "hello";
}

let v5 = getValue2();
console.log(`value length: ${v5!.length}`);

//any, it can be assigned any type value
let var1 : any;
var1 = "abc";
console.log(var1);
var1 = 123;
console.log(var1);
var1 = true;
console.log(var1);
var1 = {"id":1,"name":"Jason"};
console.log(var1);

//unknow, is simliar to any
let var2 : unknown;
//var2 = "abc"; the variable will be string type, without assignment, var2 type is undefined
console.log(var2 + typeof var2);
let var3 : unknown = var2;console.log(var3);//no error, unknow can be assigen to unknown
let var4 : any = var2;console.log(var4);//no error, unknow can be assigen to any
//let var5 : boolean = var2;//Type 'unknown' is not assignable to type 'boolean'

//console.log(var2.length);//the TS compiler won't allow any operation on values typed unknown

